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

import wtf.boomy.togglechat.ToggleChatMod;
import wtf.boomy.togglechat.toggles.Categories;
import wtf.boomy.togglechat.toggles.ToggleBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeGlobal extends ToggleBase {

    private final Pattern chatPattern = Pattern
            .compile("(?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");

    // Store the found toggle.
    private ToggleBase specialToggle;
    
    @Override
    public String getName() {
        return "Global";
    }

    @Override
    public String getDisplayName() {
        return "Global Chat: %s";
    }

    @Override
    public boolean shouldToggle(String message) {
        // Store the toggle
        if (this.specialToggle == null) {
            this.specialToggle = ToggleChatMod.getInstance().getToggleHandler().getToggle("special");
        }
    
        // A system to prevent accidentally toggling UHC or bedwars chat
        if (this.specialToggle != null && this.specialToggle.isEnabled() && this.specialToggle.shouldToggle(message)) {
            return false;
        }

        Matcher matcher = this.chatPattern.matcher(message);

        return matcher.matches() && isNotOtherChat(matcher);
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Turns all general player",
                "chat on or off",
                "",
                "These are the formats",
                "&7Player: Hi",
                "&a[VIP] Player&r: Hi",
                "&a[VIP&6+&a] Player&r: Hi",
                "&b[MVP] Player&r: Hi",
                "&b[MVP&c+&b] Player&r: Hi",
                "",
                "Useful to prevent spam",
                "or any unwanted chat",
                "messages"
        };
    }
    
    @Override
    public Categories getCategory() {
        return Categories.CHAT;
    }

    private boolean isNotOtherChat(Matcher input) {
        String rank;

        try {
            rank = input.group("rank");
        } catch (Exception ex) {
            return true;
        }

        switch (rank) {
            case "[TEAM] ":
            case "[SHOUT] ":
            case "[SPECTATOR] ":
                return false;
        }
        return true;
    }
}
