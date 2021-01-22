/*
 *     Copyright (C) 2021 boomboompower
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

package wtf.boomy.togglechat.toggles.defaults;

import wtf.boomy.togglechat.toggles.ToggleBase;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class TypeHousing extends ToggleBase {

    private final Pattern worldJoinPattern = Pattern
            .compile("(?<rank>\\[.+] )?(?<player>\\S{1,16}) (?<action>.*) the world\\.");

    @Override
    public String getName() {
        return "Housing";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.worldJoinPattern.matcher(message).matches() || (message.startsWith("[OWNER] ")
                || message.startsWith("[CO-OWNER] ") || message.startsWith("[RES] "));
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
                "Toggles all Housing chat",
                "messages that start with",
                "the following",
                "",
                "&6[OWNER]",
                "&6[CO-OWNER]",
                "&6[RES]",
                "",
                "Also toggles housing join",
                "and leave messages",
                "",
                "Build peacefully and",
                "without hassle,",
                "as if you were in a",
                "zen garden"
        );
    }
}