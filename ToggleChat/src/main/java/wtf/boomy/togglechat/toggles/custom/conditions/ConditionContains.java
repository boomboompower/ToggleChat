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
 *
 */
public class ConditionContains extends ToggleCondition {
    
    // The expected match count.
    private int matchCount = 1;
    
    /**
     * Ctor which does not care about the match count.
     *
     * @param input the text to check for
     */
    public ConditionContains(String input) {
        super(input);
        
        this.matchCount = -1;
    }
    
    /**
     * Ctor which makes the condition check for n matches where n can be any quantity from 0 to infinity.
     *
     * If this value is negative then the check will not care about the quantity of matches.
     *
     * @param input the input to match against
     * @param matchCount the quantity of matches.
     */
    public ConditionContains(String input, int matchCount) {
        super(input);

        this.matchCount = Math.max(-1, matchCount);
    }

    @Override
    public boolean shouldToggle(String input) {
        // If there is no match count just use the default search
        if (this.matchCount < 0) {
            return input.contains(getText());
        }
        
        // Collect the count of matches
        int countedMatches = countMatches(input, getText());
        
        // Check if the count match matches our thing
        return countedMatches == this.matchCount;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.CONTAINS;
    }
    
    @Override
    public JsonObject serialize() {
        // Retrieve the value from our parent.
        JsonObject object = super.serialize();
        
        // Add our custom property!
        object.addProperty("matchCount", this.matchCount);
        
        return object;
    }
    
    /**
     * Counts the amount of matches in the string
     *
     * @param str the string to test
     * @param sub what to match
     * @return 0 if nothing, or the count
     */
    private int countMatches(String str, String sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }

        int count = 0;
        int idx = 0;

        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }
}
