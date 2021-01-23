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

import wtf.boomy.togglechat.commands.impl.ToggleCommand;
import wtf.boomy.togglechat.config.ConfigLoader;
import wtf.boomy.togglechat.toggles.ToggleHandler;
import wtf.boomy.togglechat.utils.uis.blur.BlurModHandler;
import wtf.boomy.togglechat.toggles.ToggleBase;
import wtf.boomy.togglechat.utils.ChatColor;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.Arrays;

@Mod(modid = ToggleChatMod.MODID, version = ToggleChatMod.VERSION, acceptedMinecraftVersions = "*")
public class ToggleChatMod {

    public static final String MODID = "togglechatmod";
    public static final String VERSION = "3.1.0";
    
    private final ToggleHandler toggleHandler;
    private ConfigLoader configLoader;
    private BlurModHandler blurModHandler;

    @Mod.Instance
    private static ToggleChatMod instance;

    public ToggleChatMod() {
        this.toggleHandler = new ToggleHandler(this);
    }
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModMetadata data = event.getModMetadata();
        data.version = VERSION;
        data.name = ChatColor.GOLD + "ToggleChat";
        data.authorList.addAll(Arrays.asList("boomboompower", "OrangeMarshall", "tterrag1098", "SirNapkin1334"));
        data.description = "Use " + ChatColor.BLUE + "/tc" + ChatColor.RESET + " to get started! " + ChatColor.GRAY
            + "|" + ChatColor.RESET + " Made with " + ChatColor.LIGHT_PURPLE + "<3" + ChatColor.RESET + " by boomboompower";

        data.url = "https://hypixel.net/threads/997547";

        // These are the greatest people, shower them with praise and good fortune!
        data.credits = "2Pi for the idea, OrangeMarshall for help with CustomToggles and tterrag1098 for the gui blur code";

        this.configLoader = new ConfigLoader(this, new File(event.getModConfigurationDirectory(), "togglechat"));
        this.blurModHandler = new BlurModHandler(this).preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.toggleHandler.remake();

        MinecraftForge.EVENT_BUS.register(new ToggleEvents(this));
        ClientCommandHandler.instance.registerCommand(new ToggleCommand(this));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        this.configLoader.loadCustomToggles();
        this.configLoader.loadToggles();
        this.configLoader.loadModernUtils();

        this.toggleHandler.inheritFavourites(this.configLoader.getFavourites());
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
     * Getter for this instance
     *
     * @return the instance of this togglechat
     */
    public static ToggleChatMod getInstance() {
        return instance;
    }
}
