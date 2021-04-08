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

package wtf.boomy.togglechat.toggles.defaults.friends;

import wtf.boomy.togglechat.toggles.Categories;
import wtf.boomy.togglechat.toggles.ToggleBase;

import java.util.regex.Pattern;

public class TypeFriendLeave extends ToggleBase {

    private final Pattern leavePattern = Pattern.compile("Friend > (?<player>\\S{1,16})(\\s+)(left\\.)");
    private final Pattern legacyLeavePattern = Pattern.compile("(?<player>\\S{1,16})(\\s+)(left\\.)");

    @Override
    public String getName() {
        return "Friend Leave";
    }

    @Override
    public boolean shouldToggle(String message) {
        // keeping legacy in case other servers use the old matcher
        return this.leavePattern.matcher(message).matches() ||
                this.legacyLeavePattern.matcher(message).matches();
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Toggles all leave",
                "notification messages",
                "or anything matching",
                "this format",
                "",
                "&ePlayer left.",
                "&aFriend > &bPlayer &eleft.",
                "",
                "This is good for",
                "people with a large",
                "friends list"
        };
    }
    
    @Override
    public Categories getCategory() {
        return Categories.FRIENDS;
    }
}