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

public class TypeAutoQuest extends ToggleBase {
    private boolean enabled = true;

    @Override
    public String getName() {
        return "auto_quest";
    }

    @Override
    public String getDisplayName() {
        return "Auto Quest: %s";
    }

    @Override
    public boolean shouldToggle(String message) {
        return message.startsWith("Automatically activated: ");
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
                "Removes messages that",
                "notify you about quests",
                "automatically started",
                "(feature only works with",
                "&b[MVP&c+&b] &frank)",
                "",
                "Such as:",
                "&aAutomatically activated:",
                "&6Weekly Quest: Break 25 beds"
        );
    }
    
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
