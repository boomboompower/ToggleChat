/*
 *     Copyright (C) 2018 boomboompower
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

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.ToggleBase;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class TypeFriendRequests extends ToggleBase {

    private Pattern friendPattern = Pattern.compile(
            "----------------------------------------------------\n" +
            "Friend request from (?<rank>\\[.+] )?(?<player>\\S{1,16})\n" +
            "\\[ACCEPT] - \\[DENY] - \\[IGNORE]\n" +
            "----------------------------------------------------");

    // This is used for expiry messages
    private Pattern oldPattern = Pattern.compile(Pattern.quote("Friend request from "), Pattern.CASE_INSENSITIVE);

    @Setter
    @Getter
    private boolean enabled = true;

    @Override
    public String getName() {
        return "Friend requests";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.oldPattern.matcher(message).find() || this.friendPattern.matcher(message).matches();
    }

    @Override
    public void onClick(ModernButton button) {
        this.enabled = !this.enabled;
        button.setText(String.format(getDisplayName(), ModernGui.getStatus(isEnabled())));
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
                "Toggles the ability to see",
                "new friend requests from",
                "other players.",
                "",
                "It can be useful if you",
                "wish to keep friend",
                "requests open, but don\'t",
                "want to see notifications"
        );
    }
}
