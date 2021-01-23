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

import wtf.boomy.togglechat.toggles.ToggleBase;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class TypeTip extends ToggleBase {

    private final Pattern manyPlayersTipPattern = Pattern.compile("You tipped (?<players>\\d+) players?!");
    private final Pattern gamesTipPattern = Pattern.compile("You tipped (?<players>\\d+) players? in (?<games>\\d+) different games?!");
    private final Pattern onePlayerTipPattern = Pattern.compile("You tipped (?<player>.+) in (?<game>.+)!");
    private final Pattern selfTipPattern = Pattern.compile("You were tipped by (?<players>\\d+) players? in the last minute!");

        @Override
    public String getName() {
        return "Tip_Messages";
    }

    @Override
    public String getDisplayName() {
        return "Tip Messages: %s";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.manyPlayersTipPattern.matcher(message).matches() ||
                this.gamesTipPattern.matcher(message).matches() ||
                this.onePlayerTipPattern.matcher(message).matches() ||
                this.selfTipPattern.matcher(message).matches();
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Toggles all Tip messages",
                "",
                "Such as:",
                "&aYou tipped 4 players!",
                "&aYou tipped SirNapkin1334 in Duels!",
                "&7(and more)",
                "",
                "This is good if you",
                "use the AutoTip mod",
                "or &6MVP&c++&r AutoTip"
        };
    }
}