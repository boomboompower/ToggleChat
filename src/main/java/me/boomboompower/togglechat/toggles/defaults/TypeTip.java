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

public class TypeTip extends ToggleBase {

    private Pattern manyPlayersTipPattern = Pattern.compile("You tipped (?<players>\\d+) players!");
    private Pattern gamesTipPattern = Pattern.compile("You tipped (?<players>\\d+) in (?<games>\\d+) different games!");
    private Pattern onePlayerTipPattern = Pattern.compile("You tipped (?<player>.+) in (?<game>.+)!");
    private Pattern selfTipPattern = Pattern.compile("You were tipped by (?<players>.+) in the last minute!");

    @Setter
    @Getter
    private boolean enabled = true;
    
    @Override
    public String getName() { return "Tip_Messages"; }

    @Override
    public String getDisplayName() { return "Tip Messages: %s"; }
    
    @Override
    public boolean shouldToggle(String message) {
        return this.manyPlayersTipPattern.matcher(message).matches() ||
                this.gamesTipPattern.matcher(message).matches() ||
                this.onePlayerTipPattern.matcher(message).matches() ||
                this.selfTipPattern.matcher(message).matches();
    }
    
    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
            "Toggles all Tip messages",
            "",
            "Such as:",
            "&aYou tipped 4 players!",
            "&aYou tipped SirNapkin1334 in Duels!",
            "&7(not all shown)",
            "",
            "This is good if you",
            "use the AutoTip mod",
            "or MVP++ AutoTip."
        );
    }
}