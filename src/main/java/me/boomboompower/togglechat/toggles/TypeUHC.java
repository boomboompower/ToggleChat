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

import net.minecraft.client.gui.GuiButton;

public class TypeUHC implements ToggleBase {

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

        char[] charzz = message.toCharArray();

        if (charzz.length > 3) {
            if (charzz[0] == '[' && (charzz[3] == ']' || charzz[4] == ']')) {
                if (Character.isDigit(charzz[1])) {
                    if (Character.isDefined(charzz[2]) || Character.isDefined(charzz[3])) {
                        isUHC = true;
                    }
                }
            }
        }
        return isUHC;
    }

    @Override
    public boolean isEnabled() {
        return Options.showUHC;
    }

    @Override
    public void onClick(GuiButton button) {
        Options.showUHC = !Options.showUHC;
        button.displayString = String.format(getDisplayName(), isEnabled() ? GuiUtils.ENABLED : GuiUtils.DISABLED);
    }
}
