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

public class TypeFriendJoin extends ToggleBase {

    private final Pattern joinPattern = Pattern.compile("Friend > (?<player>\\S{1,16})(\\s+)(joined\\.)");
    private final Pattern legacyJoinPattern = Pattern.compile("(?<player>\\S{1,16})(\\s+)(joined\\.)");

    @Override
    public String getName() {
        return "Friend Join";
    }

    @Override
    public boolean shouldToggle(String message) {
        // Keeping legacy in case other servers use the old matcher
        return this.joinPattern.matcher(message).matches() ||
                this.legacyJoinPattern.matcher(message).matches();
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
                "Toggles all join",
                "notification messages",
                "or anything matching",
                "this format",
                "",
                "&ePlayer joined.",
                "&aFriend > &bPlayer &ejoined.",
                "",
                "This is good for",
                "people with a large",
                "friends list"
        );
    }
}