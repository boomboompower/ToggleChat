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

package wtf.boomy.togglechat.toggles.defaults;

import wtf.boomy.togglechat.toggles.ToggleBase;

import java.util.LinkedList;

public class TypeSkyblockLevelUp extends ToggleBase {

    @Override
    public String getName() {
        return "Separators";
    }

    @Override
    public boolean shouldToggle(String message) {
        return (message.startsWith("COLLECTION") || message.startsWith("SKILL")) && message.contains("LEVEL UP");
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
                "Toggles all SkyBlock",
                "levelup messages",
                "",
                "Toggle format",
                "&b&lSKILL LEVEL UP &dFarming &8II&7➜&dIII",
                "&6&lCOLLECTION LEVEL UP &eFarming &8III&e➜&dIV"
        );
    }
}
