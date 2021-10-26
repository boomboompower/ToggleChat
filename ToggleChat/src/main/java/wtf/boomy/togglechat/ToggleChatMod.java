/*
 *     Copyright (C) 2021 boomboompower
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

package wtf.boomy.togglechat;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wtf.boomy.apagoge.ApagogeHandler;
import wtf.boomy.apagoge.updater.ApagogeUpdater;
import wtf.boomy.mods.modernui.uis.ChatColor;
import wtf.boomy.togglechat.commands.impl.ToggleCommand;
import wtf.boomy.togglechat.config.ConfigLoader;
import wtf.boomy.togglechat.toggles.ToggleHandler;
import wtf.boomy.togglechat.utils.uis.blur.BlurModHandler;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.Arrays;

@Mod(modid = ToggleChatMod.MODID, version = ToggleChatMod.VERSION, acceptedMinecraftVersions = "*")
public class ToggleChatMod {

    public static final String MODID = "togglechatmod";
    public static final String VERSION = "3.1.1";
    
    private final Logger logger = LogManager.getLogger("ToggleChat - Core");
    private final ToggleHandler toggleHandler;
    private final ApagogeHandler apagogeHandler;
    
    private ConfigLoader configLoader;
    private BlurModHandler blurModHandler;

    private final ChatMixinHandler chatHandler;

    @Mod.Instance
    private static ToggleChatMod instance;
    
    public ToggleChatMod() {
        this.toggleHandler = new ToggleHandler(this);
        
        File runFile;
        try {
            runFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException | IllegalArgumentException e) {
            runFile = new File("");
        }
        
        this.apagogeHandler = hackHandler(new ApagogeHandler(runFile, "ToggleChat", ToggleChatMod.VERSION), runFile);
        chatHandler = new ChatMixinHandler(this);
    }
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModMetadata data = event.getModMetadata();
        data.version = VERSION;
        data.name = ChatColor.GOLD + "ToggleChat";
        data.authorList.addAll(Arrays.asList("boomboompower", "tterrag1098", "SirNapkin1334"));
        data.description = "Use " + ChatColor.BLUE + "/tc" + ChatColor.RESET + " to get started! " + ChatColor.GRAY
            + "|" + ChatColor.RESET + " Made with " + ChatColor.LIGHT_PURPLE + "<3" + ChatColor.RESET + " by boomboompower";

        data.url = "https://hypixel.net/threads/997547";
        data.credits = "2pi for the concept and tterrag1098 for the gui blur code";

        this.configLoader = new ConfigLoader(this, new File(event.getModConfigurationDirectory(), "togglechat"));
        this.blurModHandler = new BlurModHandler(this).load();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Requests for Apagoge to check these files. This is only a request
        // and may be ignored in some implementations of Apagoge.
        this.apagogeHandler.addValidatorClasses(
                ToggleChatMod.class,
                ChatMixinHandler.class,
                ConfigLoader.class,
                ToggleCommand.class,
                BlurModHandler.class
        );
        
        this.toggleHandler.remake();

        ClientCommandHandler.instance.registerCommand(new ToggleCommand(this));
    
        // Called once apagoge has determined if the build succeeded or not
        // if no instance of Apagoge is available this will be called
        // with a failure code. This can by bypassed by using the internal
        // hook and not the handler. With ApagogeHandler#getUpdater() which
        // will return the internal ApagogeVerifier instance (or null if
        // it has either been destroyed or cannot be found).
        this.apagogeHandler.addCompletionListener((handler, success) -> {
            if (!success) {
                if (handler.getUpdater() == null) {
                    this.logger.error("Apagoge was unable to run, no updater was found.");
                } else {
                    this.logger.error("Apagoge failed. Assuming invalid build.");
                }
            } else {
                this.logger.trace("Apagoge succeeded. This build is official.");
            }
        });
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // Loads all custom toggles from the directory.
        this.configLoader.getToggleInterpreter().interpretFiles();
        
        // Load all the toggle options
        this.configLoader.loadToggles();
        
        // Loads all the modern settings
        this.configLoader.loadModernUtils();

        // Sets the appropriate favourites.
        this.toggleHandler.inheritFavourites(this.configLoader.getFavourites());
    
        // Runs the updater
        this.apagogeHandler.begin();
    }
    
    @Mod.EventHandler
    public void onSignatureViolation(FMLFingerprintViolationEvent event) {
        this.logger.warn("ToggleChat is running in beta mode (or may be modified) - Get official releases at https://github.com/boomboompower/ToggleChat/");
        
        // Requests the updater to destroy itself.
        // Depending on the implementation this can be ignored.
        // We don't use the handler case for it, since it also
        // makes the updater instance null.
//        if (this.apagogeHandler.getUpdater() != null) this.apagogeHandler.getUpdater().kill();
    }

    /**
     * Getter for our config loader, used for saving
     *
     * @return the configloader
     */
    public ConfigLoader getConfigLoader() {
        return this.configLoader;
    }

    /**
     * Getter for the blur mod handler, used for advanced customization
     *
     * @return the blur mod
     */
    public BlurModHandler getBlurModHandler() {
        return this.blurModHandler;
    }
    
    /**
     * Returns the instance of the togglehandler which stores all the registered
     * custom and default toggles for the mod
     *
     * @return the ToggleHandler for the mod.
     */
    public ToggleHandler getToggleHandler() {
        return this.toggleHandler;
    }

    /**
     * Returns the instance of the chat mixin handler which handles the toggling
     * of chat messages in the mixin class.
     *
     * @return the ChatMixinHandler for the mod.
     */
    public ChatMixinHandler getChatHandler() {
        return this.chatHandler;
    }
    
    /**
     * Returns the mod updater instance, this may be null if the mod is running
     * in a beta environment (if the file hash cannot be checked)
     *
     * @return the mod updater instance or null if the build is not official.
     */
    public ApagogeHandler getApagogeHandler() {
        return this.apagogeHandler;
    }
    
    /**
     * Getter for this instance
     *
     * @return the instance of this togglechat
     */
    public static ToggleChatMod getInstance() {
        return instance;
    }
    
    // Never do this.
    private ApagogeHandler hackHandler(ApagogeHandler in, File runFile) {
        try {
            Class<?> clazz = in.getClass();
            
            Field updater = clazz.getDeclaredField("updater");
            updater.setAccessible(true);
            
            updater.set(in, new ApagogeUpdater(runFile, "ToggleChat", ToggleChatMod.VERSION, in));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        
        return in;
    }
}
