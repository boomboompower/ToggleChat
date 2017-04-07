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

import me.boomboompower.all.togglechat.versions.Hooker;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import scala.Int;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

@Mod(modid = ToggleChat.MODID, version = ToggleChat.VERSION, acceptedMinecraftVersions="*")
public class ToggleChat {

    public static final String MODID = "publictogglechat";
    public static final String VERSION = "1.1.7";

    public static String USER_DIR;

    private static ToggleChat instance;

    public static ArrayList<String> whitelist = new ArrayList<String>();

    // v1.0.2
    public static boolean showTeam = true;
    public static boolean showJoin = true;
    public static boolean showLeave = true;
    public static boolean showGuild = true;
    public static boolean showParty = true;
    public static boolean showShout = true;
    public static boolean showMessage = true;

    // v1.0.4
    public static boolean showUHC = true;
    public static boolean showPartyInv = true;
    public static boolean showFriendReqs = true;

    // v1.1.0
    public static boolean showSpec = true;
    public static boolean showColored = true;

    public static Boolean showStatupMessage = true;
    public static Integer statupMessageRevision = 1;
    public static Integer updatedStartupRevision = 1;

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

        Hooker.update();
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

    public boolean toggleUHCChat() {
        return (showUHC = !showUHC);
    }

    public boolean toggleColored() {
        return (showColored = !showColored);
    }

    public boolean toggleSpecChat() {
        return (showSpec = !showSpec);
    }

    public boolean toggleMessages() {
        return (showMessage = !showMessage);
    }

    public boolean togglePartyInv() {
        return (showPartyInv = !showPartyInv);
    }

    public boolean toggleTeamChat() {
        return (showTeam = !showTeam);
    }

    public boolean toggleJoinChat() {
        return (showJoin = !showJoin);
    }

    public boolean toggleLeaveChat() {
        return (showLeave = !showLeave);
    }

    public boolean toggleGuildChat() {
        return (showGuild = !showGuild);
    }

    public boolean togglePartyChat() {
        return (showParty = !showParty);
    }

    public boolean toggleShoutChat() {
        return (showShout = !showShout);
    }

    public boolean toggleFriendReqs() {
        return (showFriendReqs = !showFriendReqs);
    }

    public static ToggleChat instance() {
        return instance;
    }

    public static boolean containsIgnoreCase(String message, String contains) {
        return Pattern.compile(Pattern.quote(contains), Pattern.CASE_INSENSITIVE).matcher(message).find();
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
