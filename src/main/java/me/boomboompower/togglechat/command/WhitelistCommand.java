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

package me.boomboompower.togglechat.command;

import me.boomboompower.togglechat.gui.whitelist.WhitelistMainGui;
import me.boomboompower.togglechat.utils.ChatColor;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class WhitelistCommand implements ICommand {

    private final List<String> aliases;

    public WhitelistCommand() {
        aliases = new ArrayList<>();
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
        return ChatColor.RED + "Usage: /whitelist <player>";
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            new WhitelistMainGui().display();
        } else {
            new WhitelistMainGui(args[0]).display();
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return args.length == 1 ? CommandBase.getListOfStringsMatchingLastWord(args, getArgs()) : new ArrayList<>();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

    /*
     * Get the names of all online players
     */
    private List<String> getArgs() {
        List<String> names = new ArrayList<>();
        for (Entity o : Minecraft.getMinecraft().theWorld.playerEntities) {
            names.add(ChatColor.stripColor(o.getName()));
        }
        return names;
    }
}
