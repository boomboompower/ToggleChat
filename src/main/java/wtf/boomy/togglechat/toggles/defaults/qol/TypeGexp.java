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

public class TypeGexp extends ToggleBase {

    private final Pattern genericGexpPattern = Pattern.compile("You earned (?<xp>\\d+) GEXP from playing (?<game>.+)!");
    private final Pattern pitGexpPattern = Pattern.compile("GUILD XP! Earned \\+(?<xp>\\d+) GEXP past (?<minutes>\\d+) minutes");

    @Override
    public String getName() {
        return "GEXP_Messages";
    }

    @Override
    public String getDisplayName() {
        return "GEXP Messages: %s";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.genericGexpPattern.matcher(message).matches() ||
                this.pitGexpPattern.matcher(message).matches();
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Toggles all GEXP",
                "earning messages",
                "",
                "Such as:",
                "&aYou earned &2200 GEXP&a...",
                "&aEarned &2+418 GEXP&a...",
                "",
                "This cleans up the chat",
                "in Skyblock and The Pit,",
                "and is good for AFKing"
        };
    }
    
    @Override
    public Categories getCategory() {
        return Categories.QOL;
    }
}