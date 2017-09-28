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

import java.util.LinkedList;

public class TypeSpecial extends ToggleBase {

    private boolean showSpecial = true;

    @Override
    public String getName() {
        return "Special";
    }

    @Override
    public String getDisplayName() {
        return "UHC/Bedwars: %s";
    }

    @Override
    public boolean shouldToggle(String message) {
        boolean isSpecial = false;

        char[] chars = message.toCharArray();

        if (chars.length > 3) {
            if (chars[0] == '[' && (chars[3] == ']' || chars[4] == ']')) {
                if (Character.isDigit(chars[1])) {
                    if (Character.isDefined(chars[2]) || Character.isDefined(chars[3])) {
                        isSpecial = true;
                    }
                }
            }
        }
        return isSpecial;
    }

    @Override
    public boolean isEnabled() {
        return this.showSpecial;
    }

    @Override
    public void setToggled(boolean enabled) {
        this.showSpecial = enabled;
    }

    @Override
    public void onClick(ModernButton button) {
        this.showSpecial = !this.showSpecial;
        button.setText(String.format(getDisplayName(), isEnabled() ? ModernGui.ENABLED : ModernGui.DISABLED));
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
                "Toggles all uhc",
                "or bedwars chat",
                "",
                "Message format",
                "&6[1\u272A] &7Player&r: Hi",
                "&6[2\u272A] &a[VIP] Player&r: Hi",
                "&6[3\u272A] &b[MVP] Player&r: Hi"
        );
    }
}
