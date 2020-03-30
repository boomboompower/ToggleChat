/*
 *     Copyright (C) 2020 SirNapkin1334
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

import lombok.Getter;
import lombok.Setter;
import me.boomboompower.togglechat.toggles.ToggleBase;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class TypeGexp extends ToggleBase {
    
    private Pattern genericGexpPattern = Pattern.compile("You earned (?<xp>\\d+) GEXP from playing (?<game>.+)!");
    private Pattern pitGexpPattern = Pattern.compile("GUILD XP! Earned \\+(?<xp>\\d+) GEXP past (?<minutes>\\d+) minutes");
    
    @Setter
    @Getter
    private boolean enabled = true;
    
    @Override
    public String getName() { return "GEXP_Messages"; }

    @Override
    public String getDisplayName() { return "GEXP Messages: %s"; }
    
    @Override
    public boolean shouldToggle(String message) {
        return this.genericGexpPattern.matcher(message).matches() ||
                this.pitGexpPattern.matcher(message).matches();
    }
    
    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
            "Toggles all GEXP",
            "earning messages",
            "",
            "Such as:",
            "&aYou earned &2200 GEXP&a",
            "&aEarned &2+418 GEXP&a past 5 minutes",
            "",
            "This cleans up the chat",
            "in Skyblock and The Pit,",
            "and is good for AFKing."
        );
    }
}