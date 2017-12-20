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

public class TypeMessageSeparator extends ToggleBase {

    private boolean showSeparators = true;

    @Override
    public String getName() {
        return "Separators";
    }

    @Override
    public boolean shouldToggle(String message) {
        // Don't toggle it if its a chat message
        if (ToggleBase.hasToggle("global")) {
            ToggleBase globalToggle = ToggleBase.getToggle("global");
            if (globalToggle.isEnabled() && globalToggle.shouldToggle(message)) {
                return false;
            }
        }

        return message.contains("---------------------");
    }

    @Override
    public boolean isEnabled() {
        return this.showSeparators;
    }

    @Override
    public void setToggled(boolean enabled) {
        this.showSeparators = enabled;
    }

    @Override
    public void onClick(ModernButton button) {
        this.showSeparators = !this.showSeparators;
        button.setText(String.format(getDisplayName(), ModernGui.getStatus(isEnabled())));
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
                "Toggles all messages",
                "that contain a lot",
                "of separators",
                "",
                "Checks for message",
                "separators that look",
                "like this",
                "-----------------",
                "",
                "Less lines = more fun"
        );
    }
}
