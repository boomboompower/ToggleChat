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

public class TypeBedwars extends ToggleBase {

    private final Pattern respawnPattern = Pattern.compile("You (have respawned|will respawn in \\d seconds|have respawned because you still have a bed|have been eliminated)!");
    private final Pattern purchasePattern = Pattern.compile("You(( purchased (.+?))|('ve already purchased this item!))");

    @Override
    public String getName() {
        return "Bedwars";
    }

    @Override
    public String getDisplayName() {
        return "Bedwars: %s";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.respawnPattern.matcher(message).matches() || this.purchasePattern.matcher(message).matches();
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Turns some bedwards chat",
                "messages on or off",
                "",
                "&cYou have been eliminated!",
                "&eYou have respawned because you still have a bed!",
                "&aYou purchased &6Golden Apple"
        };
    }
    
    @Override
    public Categories getCategory() {
        return Categories.GAMES;
    }
}
