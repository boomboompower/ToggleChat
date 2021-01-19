/*
 *     Copyright (C) 2020 boomboompower
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

package wtf.boomy.togglechat.commands.impl;

import wtf.boomy.togglechat.ToggleChatMod;
import wtf.boomy.togglechat.commands.ModCommand;
import wtf.boomy.togglechat.gui.core.MainGui;
import wtf.boomy.togglechat.utils.ChatColor;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

/**
 * The core ToggleCommand command
 */
public class ToggleCommand extends ModCommand {
    
    // Cached main menu, saves memory and options.
    private MainGui mainMenu = null;
    
    public ToggleCommand(ToggleChatMod modIn) {
        super(modIn);
    }
    
    @Override
    public String getCommandName() {
        return "togglechat";
    }
    
    @Override
    public List<String> getAliases() {
        return Arrays.asList("tc", "chattoggle");
    }
    
    @Override
    public void onCommand(ICommandSender sender, String[] args) {
        MainGui menu = getMenu();
        
        // Something went wrong or an argument was incorrect.
        if (menu == null) {
            sendMessage(ChatColor.RED + "Invalid command arguments, try without arguments.");
            
            return;
        }
        
        menu.display();
    }
    
    @Override
    protected boolean shouldMultithreadCommand(String[] args) {
        return true;
    }
    
    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }
    
    /**
     * Gets the cached ToggleChat menu
     *
     * @return the cached ToggleChat menu if one exists, or a new one.
     */
    private MainGui getMenu() {
        // Check if a cached menu exists.
        if (this.mainMenu == null) {
            this.mainMenu = new MainGui(1);
        }
        
        return this.mainMenu;
    }
}
