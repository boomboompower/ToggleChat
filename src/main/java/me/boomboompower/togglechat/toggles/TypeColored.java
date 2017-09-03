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

package me.boomboompower.togglechat.toggles;

import me.boomboompower.togglechat.gui.utils.GuiUtils;

import net.minecraft.client.gui.GuiButton;

public class TypeColored implements ToggleBase {

    private boolean showColored = true;

    @Override
    public String getName() {
        return "Colored team";
    }

    @Override
    public int getId() {
        return 12;
    }

    @Override
    public boolean isMessage(String message) {
        return message.startsWith("[BLUE] ") ||
                message.startsWith("[YELLOW] ") ||
                message.startsWith("[GREEN] ") ||
                message.startsWith("[RED] ") ||
                message.startsWith("[WHITE] ") ||
                message.startsWith("[PURPLE] "
        );
    }

    @Override
    public boolean isEnabled() {
        return this.showColored;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.showColored = enabled;
    }

    @Override
    public void onClick(GuiButton button) {
        this.showColored = !this.showColored;
        button.displayString = String.format(getDisplayName(), isEnabled() ? GuiUtils.ENABLED : GuiUtils.DISABLED);
    }
}
