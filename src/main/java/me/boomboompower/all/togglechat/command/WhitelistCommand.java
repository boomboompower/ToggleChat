/*
 *     Copyright (C) 2016 boomboompower
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

package me.boomboompower.all.togglechat.command;

import me.boomboompower.all.togglechat.ToggleChat;
import me.boomboompower.all.togglechat.gui.WhitelistGui;
import me.boomboompower.all.togglechat.utils.Writer;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class WhitelistCommand implements ICommand {

    private final List<String> aliases;

    public WhitelistCommand() {
        aliases = new ArrayList<String>();
        aliases.add("wl");
        aliases.add("white");
        aliases.add("whitelist");
    }

    @Override
    public String getCommandName() {
        return "whitelist";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return EnumChatFormatting.RED + "Usage: /whitelist <player, list, clear>";
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        boolean write = false;
        new WhitelistGui().display();
//        if (args.length == 0) {
//            sendChatMessage(getCommandUsage(sender));
//        } else {
//            if (args[0].equalsIgnoreCase("list")) {
//                displayWhitelist(args);
//            } else if (args[0].equalsIgnoreCase("clear")) {
//                write = true;
//                whitelist();
//            } else {
//                write = true;
//                whitelist(args[0]);
//            }
//        }
//        if (write) Writer.execute(true);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return args.length == 1 ? CommandBase.getListOfStringsMatchingLastWord(args, getArgs()) : new ArrayList<String>();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

    private List<String> getArgs() {
        List<String> names = new ArrayList<String>();
        for (Entity o : Minecraft.getMinecraft().theWorld.playerEntities) {
            names.add(o.getName());
        }
        names.add("list");
        names.add("clear");
        return names;
    }

    private void whitelist() {
        ToggleChat.whitelist.clear();
        sendChatMessage("Cleared your whitelist!");
    }

    private void whitelist(String userName) {
        userName = userName != null ? userName : "boomboompower";
        if (whitelistContains(userName)) {
            sendChatMessage("Removed " + EnumChatFormatting.GOLD + userName + EnumChatFormatting.GRAY + " from the whitelist!");
            removeFromWhitelist(userName);
        } else {
            sendChatMessage("Added " + EnumChatFormatting.GOLD + userName + EnumChatFormatting.GRAY + " to the whitelist!");
            ToggleChat.whitelist.add(userName);
        }
    }



    private void sendChatMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "T" + EnumChatFormatting.BLUE + "C" + EnumChatFormatting.DARK_GRAY + " > " + EnumChatFormatting.GRAY + message));
    }

    private boolean whitelistContains(String message) {
        boolean contains = false;

        for (String s : ToggleChat.whitelist) {
            if (s.equalsIgnoreCase(message)) {
                contains = true;
                break;
            }
        }

        return contains;
    }

    private void removeFromWhitelist(String message) {
        for (String s : ToggleChat.whitelist) {
            if (s.equalsIgnoreCase(message)) {
                ToggleChat.whitelist.remove(message);
                break;
            }
        }
    }
}
