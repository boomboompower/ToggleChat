/*
 *     Copyright (C) 2020 Isophene
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeGlobal extends ToggleBase {

    private final Pattern chatPattern = Pattern
            .compile("(?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");

    @Setter
    @Getter
    private boolean enabled = true;

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
        // A system to prevent accidentally toggling UHC or bedwars chat
        if (getToggle("special") != null) {
            ToggleBase base = getToggle("special");

            if (base.isEnabled() && base.shouldToggle(message)) {
                return false;
            }
        }

        Matcher matcher = this.chatPattern.matcher(message);

        return matcher.matches() && isNotOtherChat(matcher);
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
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
        );
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
