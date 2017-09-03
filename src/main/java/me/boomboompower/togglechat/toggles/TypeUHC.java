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

public class TypeUHC implements ToggleBase {

    private boolean showUHC = true;

    @Override
    public String getName() {
        return "UHC";
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean isMessage(String message) {
        boolean isUHC = false;

        char[] chars = message.toCharArray();

        if (chars.length > 3) {
            if (chars[0] == '[' && (chars[3] == ']' || chars[4] == ']')) {
                if (Character.isDigit(chars[1])) {
                    if (Character.isDefined(chars[2]) || Character.isDefined(chars[3])) {
                        isUHC = true;
                    }
                }
            }
        }
        return isUHC;
    }

    @Override
    public boolean isEnabled() {
        return this.showUHC;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.showUHC = enabled;
    }

    @Override
    public void onClick(GuiButton button) {
        this.showUHC = !this.showUHC;
        button.displayString = String.format(getDisplayName(), isEnabled() ? GuiUtils.ENABLED : GuiUtils.DISABLED);
    }
}
