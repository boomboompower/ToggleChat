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

public class TypeWatchdog extends ToggleBase {
    
    private Pattern watchdogOnePattern = Pattern.compile("\\[WATCHDOG ANNOUNCEMENT\\]");
    private Pattern watchdogTwoPattern = Pattern.compile("Watchdog has banned (?<autoBans>.+) players in the last 7 days\\.");
    private Pattern watchdogThreePattern = Pattern.compile("Staff have banned an additional (?<staffBans>.+) in the last 7 days\\.");
    private Pattern watchdogFourPattern = Pattern.compile("Blacklisted modifications are a bannable offense!");

    @Setter
    @Getter
    private boolean enabled = true;
    
    @Override
    public String getName() {
        return "Watchdog";
    }

    @Override
    public String getDisplayName() {
        return "Watchdog: %s";
    }
    
    @Override
    public boolean shouldToggle(String message) {
        return this.watchdogOnePattern.matcher(message).matches() ||
                this.watchdogTwoPattern.matcher(message).matches() ||
                this.watchdogThreePattern.matcher(message).matches() ||
                this.watchdogFourPattern.matcher(message).matches();
    }
    
    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
            "Toggles Watchdog",
            "Announcements that",
            "give ban counts",
            "",
            "&7(Note that this currently",
            "&7does not remove the leading",
            "&7and trailing blank chat",
            "&7messages)",
            "",
            "This cleans up the chat",
            "whilst you are afk",
            "so you don\'t miss",
            "important messages"
        );
    }
}
