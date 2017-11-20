/*
 *     Copyright (C) 2017 boomboompower
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

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.ToggleBase;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class TypePartyInvites extends ToggleBase {

    private Pattern expiredPattern = Pattern.compile("The party invite (?<where>\\S{1,4}) (?<rank>\\[.+] )?(?<player>\\S{1,16}) has expired.");
    public Pattern invitePattern = Pattern.compile("(?<rank>\\[.+] )?(?<player>\\S{1,16}) has invited you to join their party!");
    private Pattern otherInvitePattern = Pattern.compile("(?<inviteerank>\\[.+] )?(?<invitee>\\S{1,16}) invited (?<rank>\\[.+] )?(?<player>\\S{1,16}) to the party! They have 60 seconds to accept.");

    private boolean showPartyInvites = true;

    @Override
    public String getName() {
        return "Party invites";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.expiredPattern.matcher(message).matches() || this.invitePattern.matcher(message).matches() || this.otherInvitePattern.matcher(message).matches();
    }

    @Override
    public boolean isEnabled() {
        return this.showPartyInvites;
    }

    @Override
    public void setToggled(boolean enabled) {
        this.showPartyInvites = enabled;
    }

    @Override
    public void onClick(ModernButton button) {
        this.showPartyInvites = !this.showPartyInvites;
        button.setText(String.format(getDisplayName(), ModernGui.getStatus(isEnabled())));
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
                "Toggles the ability to see",
                "party invites from",
                "other players.",
                "",
                "This goes well with",
                "separators toggled"
        );
    }

    private boolean containsIgnoreCase(String message, String contains) {
        return Pattern.compile(Pattern.quote(contains), Pattern.CASE_INSENSITIVE).matcher(message).find();
    }
}
