/*
 *     Copyright (C) 2019 boomboompower
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.boomboompower.togglechat.updates;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.utils.ChatColor;

import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebsiteUtils, an UpdateChecker created by boomboompower, using json loading
 *
 * @author boomboompower
 * @version 3.5
 */
public class UpdateCore {
    
    private Minecraft mc = Minecraft.getMinecraft(); // The Minecraft instance
    private AtomicInteger threadNumber = new AtomicInteger(0); // The current ThreadCount
    
    private ExecutorService POOL = Executors.newFixedThreadPool(8, r -> new Thread(r, String
        .format("WebsiteUtils Thread %s",
            this.threadNumber.incrementAndGet()))); // Async task scheduler
    private ScheduledExecutorService RUNNABLE_POOL = Executors.newScheduledThreadPool(2,
        r -> new Thread(r, "WebsiteUtils Thread " + this.threadNumber
            .incrementAndGet())); // Repeating task scheduler
    
    @Getter
    private boolean isRunning = false; // Is the checker running?
    
    @Getter
    private boolean isDisabled = false; // Is the mod disabled

    @Getter
    private boolean flagged;
    
    private LinkedList<String> updateMessage = new LinkedList<>(); // A list of messages to send to the player
    private boolean hasSeenHigherMessage = false; // true if the user recieve a message for having a newer release
    private boolean showUpdateSymbol = true; // true if a symbol should be shown before every update message
    private boolean showUpdateHeader = true; // true if the updater should show "this mod is out of date"
    private boolean higherVersion = false;
    private boolean needsUpdate = false;
    private String updateVersion = "0";
    private String updateUrl = "https://hypixel.net/threads/997547";
    
    private ScheduledFuture<?> modSettingsChecker;
    
    private final String modName;

    private final String BASE_LINK = "https://gist.githubusercontent.com/boomboompower/13db9d92bc86ec49229956f1ddd7c13f/raw";

    // The version checking system
    private final VersionComparator versionComparator = new VersionComparator();
    
    public UpdateCore(String modName) {
        MinecraftForge.EVENT_BUS.register(this);
        
        this.modName = modName;
    }
    
    /**
     * Begins the WebsiteUtils updater service, starts all repeating threads, this can only be used
     * if the service is not already running.
     *
     * @throws IllegalStateException if the service is already running
     */
    public void begin() {
        if (!this.isRunning) {
            this.isRunning = true;
            
            /*
             * The checker updates every 5 minutes!
             */
            
            this.modSettingsChecker = schedule(() -> {
                // Disable the update checker
                if (this.isDisabled) {
                    return;
                }
                
                String message = rawWithAgent(this.BASE_LINK);
                JsonObject object = new JsonParser().parse(message).getAsJsonObject();
                
                if (object.has("success") && !object.get("success").getAsBoolean()) {
                    object = new JsonParser().parse(rawWithAgent(this.BASE_LINK)).getAsJsonObject();
                }
                
                // Test two, this is to test the normal url
                if (object.has("success") && !object.get("success").getAsBoolean()) {
                    return;
                }
                
                // Disables the mod
                if (!object.has("enabled") || !object.get("enabled").getAsBoolean()) {
                    disableMod();
                }
                
                // Sets the flag mode
                if (object.has("forceflag")) {
                    this.flagged = object.get("forceflag").getAsBoolean();
                }
                
                // Tells the mod to use the update symbol or not
                if (object.has("showupdatesymbol")) {
                    this.showUpdateSymbol = object.get("showupdatesymbol").getAsBoolean();
                }
                
                // Sets the seenhigherversion variable, used to display "You are using a newer version" messages
                if (object.has("seenhigherversion")) {
                    this.hasSeenHigherMessage = object.get("seenhigherversion").getAsBoolean();
                }
                
                // True if the mod should show "This mod is out of date" before anything else
                if (object.has("updateheader")) {
                    this.showUpdateHeader = object.get("updateheader").getAsBoolean();
                }
                
                if (object.has("updateurl")) {
                    this.updateUrl = object.get("updateurl").getAsString();
                }

                String retrievedVersion = object.has("latest-version") ? object.get("latest-version").getAsString() : ToggleChatMod.VERSION;

                int result = this.versionComparator.compare(retrievedVersion, ToggleChatMod.VERSION);

                if (result > 0) {
                    this.needsUpdate = true;
                    this.updateVersion =
                        object.has("latest-version") ? object.get("latest-version").getAsString()
                            : "-1";
                    
                    if (object.has("update-message") && object.get("update-message")
                        .isJsonArray()) {
                        LinkedList<String> update = new LinkedList<>();
                        JsonArray array = object.get("update-message").getAsJsonArray();
                        
                        for (JsonElement element : array) {
                            update.add(element.getAsString());
                        }
                        
                        if (!update.isEmpty()) {
                            this.updateMessage = update;
                        }
                    }
                } else if (result < 0) {
                    this.higherVersion = true;
                } else {
                    this.needsUpdate = false;
                    this.updateVersion = "-1";
                }
            }, 0, 5, TimeUnit.MINUTES);
        } else {
            throw new IllegalStateException("WebsiteUtils is already running!");
        }
    }
    
    /**
     * Stops the WebsiteUtils service. Cancels all running tasks and erases the variables
     *
     * @throws IllegalStateException if the service is not running
     */
    public void stop() {
        if (this.isRunning) {
            this.isRunning = false;
            
            this.modSettingsChecker.cancel(true);
        } else {
            throw new IllegalStateException("WebsiteUtils is not running!");
        }
    }
    
    /**
     * Disables the mod
     */
    public void disableMod() {
        this.isDisabled = true;
    }
    
    /**
     * A getter for the higher version field, which will be true if this mod is newer than the
     * latest released version
     *
     * @return true if the version running is newer than the latest release
     */
    public boolean isRunningNewerVersion() {
        return this.higherVersion;
    }
    
    /**
     * Checks to see if this mod needs an update
     *
     * @return true if this mod version is older than the newest one
     */
    public boolean needsUpdate() {
        return this.needsUpdate;
    }
    
    /**
     * Getter for the latest availible version of the mod
     *
     * @return the latest version or -1 if not availible
     */
    public String getUpdateVersion() {
        return this.updateVersion;
    }
    
    /**
     * Runs a task async to the main thread
     *
     * @param runnable the runnable to run
     */
    public void runAsync(Runnable runnable) {
        this.POOL.execute(runnable);
    }
    
    /**
     * Schedules a repeating task that can be cancelled at any time
     *
     * @param r the runnable to run
     * @param initialDelay the delay for the first time ran
     * @param delay all the other delays
     * @param unit the time duration type for the task to be executed, eg a delay of 50 with {@link
     * TimeUnit#MILLISECONDS} will run the task every 50 milliseconds
     * @return the scheduled task
     */
    public ScheduledFuture<?> schedule(Runnable r, long initialDelay, long delay, TimeUnit unit) {
        return this.RUNNABLE_POOL.scheduleAtFixedRate(r, initialDelay, delay, unit);
    }
    
    // Other things
    
    /**
     * Grabs JSON off a site, will return its own JSON if an error occurs, the format for an error
     * is usually <i>{"success":false,"cause":"exception"}</i>
     *
     * @param url the url to grab the json off
     * @return the json recieved
     */
    public String rawWithAgent(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.addRequestProperty("User-Agent", "Mozilla/4.76");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            return IOUtils.toString(connection.getInputStream(), "UTF-8");
        } catch (Exception e) {
            
            // Generic handling for bad errors, captures the error type and message (if specified)
            JsonObject object = new JsonObject();
            object.addProperty("success", false);
            object.addProperty("cause", "Exception");
            object.addProperty("exception_type", e.getClass().getName());
            if (e.getMessage() != null) {
                object.addProperty("exception_message", e.getMessage());
            }
            return object.toString();
        }
    }
    
    // Handle message sending
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        UpdateCore utils = ToggleChatMod.getInstance().getWebsiteUtils();
    
        if (utils.isDisabled()) {
            return;
        }
        
        if (utils.needsUpdate()) {
            utils.runAsync(() -> {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (Minecraft.getMinecraft().thePlayer == null) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                sendMessage("&9&m---------------------------------------------");
                sendMessage(" ");
                if (this.showUpdateHeader) {
                    sendMessage(" %s&eYour version of " + this.modName + " is out of date!",
                        (this.showUpdateSymbol ? "&b\u21E8 " : ""));
                    sendLinkText();
                }
                if (this.updateMessage != null && !this.updateMessage.isEmpty()) {
                    if (this.showUpdateHeader) {
                        sendMessage(" %s", (this.showUpdateSymbol ? "&b\u21E8 " : ""));
                    }
                    for (String s : this.updateMessage) {
                        sendMessage(" %s&e" + s, (this.showUpdateSymbol ? "&b\u21E8 " : ""));
                    }
                }
                sendMessage(" ");
                sendMessage("&9&m---------------------------------------------");
            });
        }
        
        if (!this.hasSeenHigherMessage && utils.isRunningNewerVersion()) {
            this.hasSeenHigherMessage = true;
            utils.runAsync(() -> {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (Minecraft.getMinecraft().thePlayer == null) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                sendMessage("&9&m-----------------------------------------------");
                sendMessage(" ");
                sendMessage(" &b\u21E8 &aYou are running a newer version of " + this.modName + "!");
                sendMessage(" ");
                sendMessage("&9&m-----------------------------------------------");
            });
        }
    }
    
    /**
     * Sends a message to the player, this supports color codes
     *
     * @param message the message to send
     * @param replacements the arguments used to format the string
     */
    private void sendMessage(String message, Object... replacements) {
        if (Minecraft.getMinecraft().thePlayer == null) {
            return; // Safety first! :)
        }
        
        try {
            message = String.format(message, replacements);
        } catch (Exception ignored) {
        }
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
            new ChatComponentText(ChatColor.translateAlternateColorCodes('&', message)));
    }
    
    /**
     * Sends a clickable link to the user containing all updating information
     */
    private void sendLinkText() {
        if (Minecraft.getMinecraft().thePlayer == null) {
            return; // Safety first! :)
        }
        
        try {
            ChatComponentText text = new ChatComponentText(ChatColor.translateAlternateColorCodes(
                String.format(" %s&eYou can download v&6%s&e by ",
                    (this.showUpdateSymbol ? "&b\u21E8 " : ""), this.updateVersion)));
            ChatComponentText url = new ChatComponentText(ChatColor.GREEN + "clicking here");
            
            ChatStyle chatStyle = new ChatStyle();
            chatStyle.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ChatComponentText(ChatColor.AQUA + "Click here to open the forum thread!")));
            chatStyle.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, this.updateUrl));
            url.setChatStyle(chatStyle);
            text.appendSibling(url).appendText(ChatColor.YELLOW + "!");
            
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(text);
        } catch (Exception ignored) {
        }
    }
}
