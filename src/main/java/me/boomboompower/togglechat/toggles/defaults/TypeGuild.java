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

public class TypeGuild extends ToggleBase {

    public Pattern guildPattern = Pattern.compile("Guild > (?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");
    public Pattern shortGuildPattern = Pattern.compile("G > (?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");

    private boolean showGuild = true;

    @Override
    public String getName() {
        return "Guild";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.guildPattern.matcher(message).matches() || this.shortGuildPattern.matcher(message).matches();
    }

    @Override
    public boolean isEnabled() {
        return this.showGuild;
    }

    @Override
    public void setToggled(boolean enabled) {
        this.showGuild = enabled;
    }

    @Override
    public void onClick(ModernButton button) {
        this.showGuild = !this.showGuild;
        button.setText(String.format(getDisplayName(), isEnabled() ? ModernGui.ENABLED : ModernGui.DISABLED));
    }
}
