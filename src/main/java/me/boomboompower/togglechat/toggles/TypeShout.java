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

public class TypeShout implements ToggleBase {

    private boolean showShout = true;

    @Override
    public String getName() {
        return "Shout";
    }

    @Override
    public int getId() {
        return 6;
    }

    @Override
    public boolean isMessage(String message) {
        return message.startsWith("[SHOUT] ");
    }

    @Override
    public boolean isEnabled() {
        return this.showShout;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.showShout = enabled;
    }

    @Override
    public void onClick(GuiButton button) {
        this.showShout = !this.showShout;
        button.displayString = String.format(getDisplayName(), isEnabled() ? GuiUtils.ENABLED : GuiUtils.DISABLED);
    }
}
