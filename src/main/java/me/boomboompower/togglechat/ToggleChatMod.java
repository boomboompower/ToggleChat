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

package me.boomboompower.togglechat;

import me.boomboompower.togglechat.command.ToggleCommand;
import me.boomboompower.togglechat.command.WhitelistCommand;
import me.boomboompower.togglechat.config.ConfigLoader;
import me.boomboompower.togglechat.toggles.ToggleBase;
import me.boomboompower.togglechat.utils.WebsiteUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.ArrayList;

@Mod(modid = ToggleChatMod.MODID, version = ToggleChatMod.VERSION, acceptedMinecraftVersions="*")
public class ToggleChatMod {

    public static final String MODID = "publictogglechat";
    public static final String VERSION = "2.0.0";

    private ArrayList<String> whitelist = new ArrayList<>();

    private WebsiteUtils websiteUtils;
    private ConfigLoader configLoader;

    private Boolean tutorialEnabled = true;

    @Mod.Instance
    private static ToggleChatMod instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModMetadata data = event.getModMetadata();
        data.version = VERSION;
        data.name = EnumChatFormatting.GOLD + "Hypixel " + EnumChatFormatting.GRAY + "-" + EnumChatFormatting.GREEN + " ToggleChat";
        data.authorList.add("boomboompower");
        data.description = "Use " + EnumChatFormatting.BLUE + "/tc" + EnumChatFormatting.RESET + " to get started! " + EnumChatFormatting.GRAY + "|" + EnumChatFormatting.RESET + " Made with " + EnumChatFormatting.LIGHT_PURPLE + "<3" + EnumChatFormatting.RESET + " by boomboompower";
        data.url = "https://hypixel.net/threads/997547";

        data.credits = "2Pi for the initial idea behind the mod!";

        this.websiteUtils = new WebsiteUtils("ToggleChat");
        this.configLoader = new ConfigLoader("mods" + File.separator + "togglechat" + File.separator + Minecraft.getMinecraft().getSession().getProfile().getId() + File.separator);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ToggleBase.remake();

        MinecraftForge.EVENT_BUS.register(new ToggleEvents());
        registerCommands(new ToggleCommand(), new WhitelistCommand());

        Minecraft.getMinecraft().addScheduledTask(() -> this.websiteUtils.begin());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            this.configLoader.loadToggles();
            this.configLoader.loadWhitelist();
        });
    }

    public void disableTutorial() {
        this.tutorialEnabled = false;
    }

    public boolean isTutorialEnabled() {
        return this.tutorialEnabled;
    }

    public ArrayList<String> getWhitelist() {
        return this.whitelist;
    }

    public ConfigLoader getConfigLoader() {
        return this.configLoader;
    }

    public WebsiteUtils getWebsiteUtils() {
        return this.websiteUtils;
    }

    public static ToggleChatMod getInstance() {
        return instance;
    }

    private void registerCommands(ICommand... commands) {
        for (ICommand command : commands) {
            try {
                ClientCommandHandler.instance.registerCommand(command);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
