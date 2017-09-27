/*
 *     Copyright (C) 2017 boomboompower
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

package me.boomboompower.togglechat.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.boomboompower.togglechat.ToggleChatMod;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class WebsiteUtils {

    private Minecraft mc = Minecraft.getMinecraft();
    private AtomicInteger threadNumber = new AtomicInteger(0);

    private ExecutorService POOL = Executors.newFixedThreadPool(8, r -> new Thread(r, String.format("Thread %s", threadNumber.incrementAndGet())));
    private ScheduledExecutorService RUNNABLE_POOL = Executors.newScheduledThreadPool(2, r -> new Thread(r, "Thread " + threadNumber.incrementAndGet()));

    private boolean isRunning = false;
    private boolean isDisabled = false;

    private boolean hasSeenHigherMessage = false;
    private boolean higherVersion = false;
    private boolean needsUpdate = false;
    private String updateVersion = "0";

    private ScheduledFuture<?> modSettingsChecker;
    private ScheduledFuture<?> modVersionChecker;

    private String modName = "";

    private final String SETTINGS_LINK = "https://gist.githubusercontent.com/boomboompower/13db9d92bc86ec49229956f1ddd7c13f/raw/togglechat.json";
    private final String VERSION_LINK = "https://gist.githubusercontent.com/boomboompower/13db9d92bc86ec49229956f1ddd7c13f/raw/update.json";

    public WebsiteUtils(String modName) {
        MinecraftForge.EVENT_BUS.register(this);

        this.modName = modName;
    }

    public void begin() {
        if (!this.isRunning) {

            /*
             * All threads run every 5 minutes
             */

            this.modSettingsChecker = schedule(() -> {
                JsonObject statusObject = new JsonParser().parse(rawWithAgent(SETTINGS_LINK)).getAsJsonObject();
                if (!statusObject.has("enabled") || !statusObject.get("enabled").getAsBoolean()) {
                    disableMod();
                }
            }, 0, 5, TimeUnit.MINUTES);

            this.modVersionChecker = schedule(() -> {
                JsonObject object = new JsonParser().parse(rawWithAgent(VERSION_LINK)).getAsJsonObject();
                if (object.has("success") && object.get("success").getAsBoolean()) {
                    int currentVersion = formatVersion(ToggleChatMod.VERSION);
                    int latestVersion = object.has("latest-version") ? formatVersion(object.get("latest-version").getAsString()) : -1;
                    if (currentVersion < latestVersion && latestVersion > 0) {
                        this.needsUpdate = true;
                        this.updateVersion = object.has("latest-version") ? object.get("latest-version").getAsString() : "-1";
                    } else if (currentVersion > latestVersion && latestVersion > 0) {
                        this.higherVersion = true;
                    } else {
                        this.needsUpdate = false;
                        this.updateVersion = "-1";
                    }
                }
            }, 0, 5, TimeUnit.MINUTES);
        } else {
            throw new IllegalStateException("WebsiteUtils is already running!");
        }
    }

    public void stop() {
        if (this.isRunning) {
            this.modSettingsChecker.cancel(true);
            this.modVersionChecker.cancel(true);
        } else {
            throw new IllegalStateException("WebsiteUtils is not running!");
        }
    }

    public void runAsync(Runnable runnable) {
        this.POOL.execute(runnable);
    }

    public void disableMod() {
        this.isDisabled = true;
    }

    public boolean isDisabled() {
        return this.isDisabled;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public boolean isRunningNewerVersion() {
        return this.higherVersion;
    }

    public boolean needsUpdate() {
        return this.needsUpdate;
    }

    public String getUpdateVersion() {
        return this.updateVersion;
    }

    public ScheduledFuture<?> schedule(Runnable r, long initialDelay, long delay, TimeUnit unit) {
        return this.RUNNABLE_POOL.scheduleAtFixedRate(r, initialDelay, delay, unit);
    }

    // Other things

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
            JsonObject object = new JsonObject();
            object.addProperty("success", false);
            object.addProperty("cause", "Exception");
            return object.toString();
        }
    }

    private int formatVersion(String input) {
        StringBuilder builder = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                builder.append(c);
            }
        }
        return builder.toString().trim().isEmpty() ? 0 : Integer.valueOf(builder.toString().trim());
    }

    // Handle message sending

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        WebsiteUtils utils = ToggleChatMod.getInstance().getWebsiteUtils();

        if (utils.isDisabled()) return;

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
                sendMessage(" &b\u26AB &e" + this.modName + " is out of date!");
                sendMessage(" &b\u26AB &eDownload %s from the forum page!", "&6v" + utils.getUpdateVersion() + "&e");
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
                sendMessage(" &b\u26AB &aYou are running a newer version of " + this.modName +"!");
                sendMessage(" ");
                sendMessage("&9&m-----------------------------------------------");
            });
        }
    }

    private void sendMessage(String message, Object... replacements) {
        if (Minecraft.getMinecraft().thePlayer == null) return; // Safety first! :)

        try {
            message = String.format(message, replacements);
        } catch (Exception ex) { }
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(ChatColor.translateAlternateColorCodes('&', message)));
    }
}
