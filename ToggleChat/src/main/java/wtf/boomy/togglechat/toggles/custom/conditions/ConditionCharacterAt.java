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

package wtf.boomy.togglechat.toggles.custom.conditions;

import com.google.gson.JsonObject;
import wtf.boomy.togglechat.toggles.custom.ConditionType;
import wtf.boomy.togglechat.toggles.custom.ToggleCondition;

/**
 * A condition to check if the character at a specific index of a string matches an expected value.
 *
 * E.g toggle any messages which have a 'T' at the 4th letter.
 */
public class ConditionCharacterAt extends ToggleCondition {
    
    private char character;
    private int index;
    
    /**
     * Constructor to check if the value at a specific index is something.
     */
    public ConditionCharacterAt(String character, int index) {
        super(String.valueOf(character.charAt(0)));
        
        this.character = character.charAt(0);
        this.index = index;
    }

    @Override
    public boolean shouldToggle(String input) {
        if (input.length() < this.index) {
            return false;
        }
        
        return input.charAt(this.index) == this.character;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.CHARACTERAT;
    }
    
    @Override
    public JsonObject serialize() {
        // Retrieve the value from our parent.
        JsonObject object = super.serialize();
        
        // Add our custom property!
        object.addProperty("charIndex", this.index);
        
        return object;
    }
}
