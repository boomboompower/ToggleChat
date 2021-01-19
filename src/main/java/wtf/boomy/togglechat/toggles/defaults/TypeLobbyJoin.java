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
import java.util.regex.Pattern;

public class TypeLobbyJoin extends ToggleBase {
    
    private final Pattern lobbyJoinPattern = Pattern.compile("( >>> )??\\[MVP(\\+|\\+\\+)] \\S{1,16} (sled into|joined) the lobby!( <<<)??");
    
    private boolean enabled = true;

        @Override
    public String getName() {
        return "lobby_join";
    }

    @Override
    public String getDisplayName() {
        return "Lobby join: %s";
    }

    @Override
    public boolean shouldToggle(String message) {
        if (!message.contains("the lobby")) {
            return false;
        }
        
        return this.lobbyJoinPattern.matcher(message).matches();
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
                "Removes all &bMVP&c+",
                "and &6MVP&c++&r lobby join",
                "messages",
                "",
                "Such as:",
                "&b[MVP&c+&b] I &6joined the lobby!",
                "",
                "It also removes the &6MVP&c++",
                "join messages to make",
                "lobby chat more readable"
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
