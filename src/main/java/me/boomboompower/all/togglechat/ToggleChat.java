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
package me.boomboompower.all.togglechat;

import me.boomboompower.all.togglechat.command.ToggleCommand;
import me.boomboompower.all.togglechat.command.WhitelistCommand;
import me.boomboompower.all.togglechat.utils.FileUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.ArrayList;

@Mod(modid = ToggleChat.MODID, version = ToggleChat.VERSION, acceptedMinecraftVersions="*")
public class ToggleChat {

    public static final String MODID = "publictogglechat";
    public static final String VERSION = "1.2.5";

    public static Boolean tutorialEnabled = true;
    public static String USER_DIR;

    private static ToggleChat instance;

    public static ArrayList<String> whitelist = new ArrayList<String>();

    public ToggleChat() {
        instance = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModMetadata data = event.getModMetadata();
        data.version = VERSION;
        data.name = EnumChatFormatting.GOLD + "Hypixel " + EnumChatFormatting.GRAY + "-" + EnumChatFormatting.GREEN + " ToggleChat";
        data.authorList.add("boomboompower");
        data.description = "Use " + EnumChatFormatting.BLUE + "/tc" + EnumChatFormatting.RESET + " to get started! " + EnumChatFormatting.GRAY + "|" + EnumChatFormatting.RESET + " Made with " + EnumChatFormatting.LIGHT_PURPLE + "<3" + EnumChatFormatting.RESET + " by boomboompower";
        data.url = "https://hypixel.net/threads/997547";

        data.credits = "2Pi for the initial idea behind the mod!";

        new Options();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ToggleEvents());
        registerCommands(new ToggleCommand(), new WhitelistCommand());

        USER_DIR = "mods" + File.separator + "togglechat" + File.separator + Minecraft.getMinecraft().getSession().getProfile().getId() + File.separator;

        try {
            FileUtils.getVars();
        } catch (Throwable var21) {
            var21.printStackTrace();
        }
    }

    public static ToggleChat instance() {
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
