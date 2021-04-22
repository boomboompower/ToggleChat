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
 * A condition to check if a character at a specific index is a letter
 *
 * E.g toggle any messages which have a 'X' at the 7th letter.
 */
public class ConditionIsLetter extends ToggleCondition {
    
    private int index;
    
    /**
     * Constructor to check if the value at a specific index is something.
     */
    public ConditionIsLetter(String index) {
        super(index);
        
        this.index = Integer.parseInt(index);
    }

    @Override
    public boolean shouldToggle(String input) {
        if (input.length() < this.index) {
            return false;
        }
        
        return Character.isLetter(input.charAt(this.index));
    }
    
    @Override
    public JsonObject serialize() {
        // Retrieve the value from our parent.
        JsonObject object = super.serialize();
    
        // Remove the string property
        object.remove("condition");
        
        // Add our custom property!
        object.addProperty("condition", this.index);
    
        return object;
    }
    
    @Override
    public ConditionType getConditionType() {
        return ConditionType.CHARACTERAT;
    }
}
