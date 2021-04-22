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

package wtf.boomy.togglechat.toggles.custom;

import com.google.gson.JsonObject;

/**
 * A class used to store all the internal properties of a toggle condition. Basically multiple conditions make up one toggle.
 *
 * @author boomboompower,
 * @version 1.0
 */
public abstract class ToggleCondition {

    private final String text;

    public ToggleCondition(String input) {
        this.text = input;
    }
    
    /**
     * Returns true if the incoming message should be toggled by this condition
     *
     * @param incoming the incoming text
     * @return true if the message should toggle this text.
     */
    public abstract boolean shouldToggle(String incoming);
    
    /**
     * Returns the condition type associated with this condition.
     *
     * @return the condition type associated with this condition.
     */
    public abstract ConditionType getConditionType();
    
    /**
     * Called when this condition is being saved, converts this object into JSON for loading later!
     *
     * @return the json object representing this condition for when it's saved
     */
    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        object.addProperty("type", getConditionType().getDisplayText());
        object.addProperty("condition", getText());
        
        return object;
    }

    /**
     * The text that was used to create this condition
     *
     * @return the text
     */
    public final String getText() {
        return this.text;
    }

    /**
     * Makes sure the string is not null or empty
     *
     * @param input the string
     * @return true if the string is null or empty
     */
    protected boolean isEmpty(String input) {
        return input == null || input.isEmpty();
    }

    // Use our text
    @Override
    public String toString() {
        return getText();
    }
}
