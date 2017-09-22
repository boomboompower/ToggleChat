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

package me.boomboompower.togglechat.toggles.defaults;

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.ToggleBase;

public class TypeColored extends ToggleBase {

    private boolean showColored = true;

    @Override
    public String getName() {
        return "Colored team";
    }

    @Override
    public boolean shouldToggle(String message) {
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
    public void setToggled(boolean enabled) {
        this.showColored = enabled;
    }

    @Override
    public void onClick(ModernButton button) {
        this.showColored = !this.showColored;
        button.setText(String.format(getDisplayName(), isEnabled() ? ModernGui.ENABLED : ModernGui.DISABLED));
    }
}
