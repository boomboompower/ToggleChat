/*
 *     Copyright (C) 2021 boomboompower, SirNapkin1334
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

package wtf.boomy.togglechat.toggles.defaults.qol;

import wtf.boomy.togglechat.toggles.Categories;
import wtf.boomy.togglechat.toggles.ToggleBase;

import java.util.regex.Pattern;

public class TypeWatchdog extends ToggleBase {

    private final Pattern watchdogOnePattern = Pattern.compile("\\[WATCHDOG ANNOUNCEMENT]");
    private final Pattern watchdogTwoPattern = Pattern.compile("Watchdog has banned (?<autoBans>.+) players in the last 7 days\\.");
    private final Pattern watchdogThreePattern = Pattern.compile("Staff have banned an additional (?<staffBans>.+) in the last 7 days\\.");
    private final Pattern watchdogFourPattern = Pattern.compile("Blacklisted modifications are a bannable offense!");

    // SirNapkin1334

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
    public String[] getDescription() {
        return new String[] {
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
                "so you don't miss",
                "important messages"
        };
    }
    
    @Override
    public Categories getCategory() {
        return Categories.QOL;
    }
}
