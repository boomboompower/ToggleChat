/*
 *     Copyright (C) 2019 boomboompower
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

public class TypeShout extends ToggleBase {
    
    private Pattern shoutPattern = Pattern
        .compile("\\[SHOUT] (?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");
    
    @Setter
    @Getter
    private boolean enabled = true;
    
    @Override
    public String getName() {
        return "Shout";
    }
    
    @Override
    public boolean shouldToggle(String message) {
        return this.shoutPattern.matcher(message).matches();
    }
    
    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
            "Toggles all shout",
            "chat messages",
            "",
            "Message format",
            "&6[SHOUT] &7Player&r: Hello",
            "&6[SHOUT] &a[VIP] Player&r: Hello",
            "&6[SHOUT] &b[MVP] Player&r: Hello",
            "",
            "Good for large minigames",
            "such as Mega Skywars"
        );
    }
}
