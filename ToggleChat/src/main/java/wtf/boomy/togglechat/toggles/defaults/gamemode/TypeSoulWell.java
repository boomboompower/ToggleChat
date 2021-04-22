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

package wtf.boomy.togglechat.toggles.defaults.gamemode;

import wtf.boomy.togglechat.toggles.Categories;
import wtf.boomy.togglechat.toggles.ToggleBase;

import java.util.regex.Pattern;

public class TypeSoulWell extends ToggleBase {

    private final Pattern soulPattern = Pattern
            .compile("(?<player>\\S{1,16}) has found (?<message>.*) in the Soul Well!");

    @Override
    public String getName() {
        return "Soul";
    }

    @Override
    public String getDisplayName() {
        return "Soul Well: %s";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.soulPattern.matcher(message).matches();
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Turns off perk and kit",
                "messages for skywars",
                "",
                "&bSk1er &7has found &6Slime",
                "&6Cage &7in the &bSoul Well&r!",
                "",
                "Good for recording",
                "in a skywars lobby"
        };
    }
    
    @Override
    public Categories getCategory() {
        return Categories.GAMES;
    }
}
