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

package wtf.boomy.togglechat.commands.impl;

import wtf.boomy.mods.modernui.uis.ModernGui;
import wtf.boomy.togglechat.ToggleChatMod;
import wtf.boomy.togglechat.commands.ModCommand;
import wtf.boomy.togglechat.gui.core.MainGui;
import wtf.boomy.togglechat.gui.redesign.NewMainUI;
import wtf.boomy.togglechat.gui.selector.DesignSelectorMenu;
import wtf.boomy.togglechat.gui.selector.UITheme;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

/**
 * The core ToggleCommand command
 */
public class ToggleCommand extends ModCommand {
    
    // Cached main menu, saves memory and options.
    private ModernGui mainMenu = null;
    
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
        UITheme theme = this.mod.getConfigLoader().getUITheme();
        
        // Display the cached menu.
        switch (theme) {
            case UNKNOWN:
                new DesignSelectorMenu().display();
                break;
            case NEW:
            case LEGACY:
                getMenu(theme).display();
        }
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
    private ModernGui getMenu(UITheme theme) {
        // The user has changed their preference.
        if (this.mainMenu != null && this.mainMenu.getClass() != theme.getThemeClass()) {
            this.mainMenu = null;
        }
    
        // Create a new cached menu if none exists
        if (this.mainMenu == null) {
            switch (theme) {
                case LEGACY:
                    this.mainMenu = new MainGui(1);
                    break;
                case NEW:
                    this.mainMenu = new NewMainUI();
                    break;
            }
        }
        
        // Return the cached menu
        return this.mainMenu;
    }
}
