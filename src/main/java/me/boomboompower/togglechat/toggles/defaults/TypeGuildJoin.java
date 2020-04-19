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
import java.util.regex.Pattern;

public class TypeGuildJoin extends ToggleBase {

    private final Pattern joinPattern = Pattern.compile("Guild > (?<player>\\S{1,16})(\\s+)(joined\\.)");

    @Setter
    @Getter
    private boolean enabled = true;

    @Override
    public String getName() {
        return "Guild Join";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.joinPattern.matcher(message).matches();
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
                "&2Guild > &bPlayer &ejoined.",
                "",
                "This is good for",
                "people in a large",
                "guild"
        );
    }
}