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

package me.boomboompower.togglechat.toggles;

import me.boomboompower.togglechat.Options;
import me.boomboompower.togglechat.gui.utils.GuiUtils;
import me.boomboompower.togglechat.utils.ChatColor;

import net.minecraft.client.gui.GuiButton;

public class TypeTeam implements ToggleBase {

    @Override
    public String getName() {
        return "Team";
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public boolean isMessage(String message) {
        return message.startsWith(ChatColor.BLUE + "[TEAM] ");
    }

    @Override
    public boolean isEnabled() {
        return Options.showTeam;
    }

    @Override
    public void onClick(GuiButton button) {
        Options.showTeam = !Options.showTeam;
        button.displayString = String.format(getDisplayName(), isEnabled() ? GuiUtils.ENABLED : GuiUtils.DISABLED);
    }

    @Override
    public boolean useUnformattedMessage() {
        return false;
    }
}