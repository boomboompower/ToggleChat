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

import java.util.regex.Pattern;

public class TypeGlobal extends ToggleBase {

    private Pattern chatPattern = Pattern.compile("(?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");

    public boolean showGlobal = true;

    @Override
    public String getName() {
        return "Global";
    }

    @Override
    public String getDisplayName() {
        return "Global Chat: %s";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.chatPattern.matcher(message).matches() && isNotOtherChat(message);
    }

    @Override
    public boolean isEnabled() {
        return this.showGlobal;
    }

    @Override
    public void setToggled(boolean enabled) {
        this.showGlobal = enabled;
    }

    @Override
    public void onClick(ModernButton button) {
        this.showGlobal = !showGlobal;
        button.setText(String.format(getDisplayName(), isEnabled() ? ModernGui.ENABLED : ModernGui.DISABLED));
    }

    private boolean isNotOtherChat(String input) {
        return !input.startsWith("[TEAM] ") && !input.startsWith("[SHOUT] ") && !input.startsWith("[SPECTATOR] ") && (hasToggle("colored_team") && !getToggle("colored_team").shouldToggle(input));
    }
}
