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

import wtf.boomy.togglechat.toggles.custom.ToggleCondition;

/**
 * This code was created by OrangeMarshall and used with permission by boomboompower.
 * <p>
 * Full credit to OrangeMarshall
 *
 * @author OrangeMarshall
 */
public class ConditionContains extends ToggleCondition {

    private int matchCount = 1;

    public ConditionContains(String input) {
        super(input);
    }

    public ConditionContains(String input, int matchCount) {
        super(input);

        this.matchCount = Math.max(1, matchCount);
    }

    @Override
    public Boolean apply(String input) {
        return countMatches(input, getText()) == this.matchCount;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.CONTAINS;
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
