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

package wtf.boomy.togglechat.toggles.defaults.otherchat;

import wtf.boomy.togglechat.toggles.ToggleBase;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class TypeTeam extends ToggleBase {

    private final Pattern teamPattern = Pattern
            .compile("\\[TEAM] (?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");

        @Override
    public String getName() {
        return "Team";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.teamPattern.matcher(message).matches();
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Toggles all",
                "incoming team messages",
                "",
                "Message format:",
                "&9[TEAM] &7Player&r: Hi",
                "&9[TEAM] &a[VIP] Player&r: Hi",
                "&9[TEAM] &b[MVP] Player&r: Hi",
                "",
                "Useful for large",
                "team games"
        };
    }
}