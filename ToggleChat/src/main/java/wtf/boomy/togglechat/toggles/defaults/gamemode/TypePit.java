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

public class TypePit extends ToggleBase {

    private final Pattern pitPattern = Pattern
            .compile("(?<pitrank>\\[((XC|XL|L?X{0,4})(IX|IV|V?I{0,4})-)?\\d{0,3}])( ✬)? (?<rank>\\[.+?] )?(?<player>\\S{1,16}): (?<message>.*)");
    private final Pattern streakPattern =
            Pattern.compile("((BOUNTY|STREAK)! (((bump|of) (\\d+)g on (\\[((XC|XL|L?X{0,4})(IX|IV|V?I{0,3})-)?\\d{0,3}]) ([0-9A-Za-z_]{1,16}) for high streak)|(of \\d+ kil{2}s by (\\[((XC|XL|L?X{0,4})(IX|IV|V?I{0,3})-)?\\d{0,3}]) ([0-9A-Za-z_]{1,16}))))");
    
    @Override
    public String getName() {
        return "The Pit";
    }

    @Override
    public String getDisplayName() {
        return "The Pit: %s";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.pitPattern.matcher(message).matches() || this.streakPattern.matcher(message).matches();
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Toggles all pit",
                "chat from players and",
                "The bounty and kill",
                "streak messages",
                "",
                "Message format",
                "&7[1] Player&r: Hi",
                "&7[&5&b90&7] &a[VIP] Player&r: Hi",
                "&7[&b&l120&7] &b[MVP] Player&r: Hi"
        };
    }
    
    @Override
    public Categories getCategory() {
        return Categories.GAMES;
    }
}
