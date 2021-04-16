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

package wtf.boomy.togglechat.toggles;

/**
 * Represents the different categories supported by this mod. Another mode for filtering
 * the results in the toggle list.
 *
 * @since 3.1.44
 */
public enum Categories {
    
    ALL("All", "Displays all toggles"),
    CHAT("General", "Displays general toggles"),
    CUSTOM("Custom", "Displays custom toggles"),
    FRIENDS("Friends", "Displays friend toggles"),
    GAMES("Games", "Displays game toggles"),
    GUILDS("Guilds", "Displays guild toggles"),
    PARTIES("Parties", "Displays party toggles"),
    QOL("Quality Of Life", "Displays quality-of-life toggles"),
    OTHER("Other", "Displays everything else");
    
    private final String displayName;
    private final String description;
    
    Categories(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public Categories getNextMode() {
        int next = ordinal() + 1;
        
        if (next >= values().length) {
            next = 0;
        }
        
        return values()[next];
    }
}
