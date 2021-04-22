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

import wtf.boomy.togglechat.toggles.Categories;
import wtf.boomy.togglechat.toggles.ToggleBase;

import java.util.regex.Pattern;

/**
 * #BringBackSpecChat
 */
public class TypeSpectator extends ToggleBase {

    private final Pattern spectatorPattern = Pattern
            .compile("\\[SPECTATOR] (?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");

    @Override
    public String getName() {
        return "Spectator";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.spectatorPattern.matcher(message).matches();
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Toggles all spectator",
                "chat messages",
                "",
                "Message format",
                "&7[SPECTATOR] &7Player&r: Hi",
                "&7[SPECTATOR] &a[VIP] Player&r: Hi",
                "&7[SPECTATOR] &b[MVP] Player&r: Hi",
                "",
                "Useful to ignore",
                "post-game chat",
                "messages"
        };
    }
    
    @Override
    public Categories getCategory() {
        return Categories.CHAT;
    }
}
