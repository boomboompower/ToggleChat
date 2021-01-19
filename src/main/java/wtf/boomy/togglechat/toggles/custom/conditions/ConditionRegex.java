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

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * This code was created by OrangeMarshall and used with permission by boomboompower.
 * <p>
 * Full credit to OrangeMarshall
 *
 * @author OrangeMarshall
 */
public class ConditionRegex extends ToggleCondition {

    private static final String REGEX = "https://regex101.com";

    private Pattern pattern;

    public ConditionRegex(String input) {
        super(input);
        try {
            this.pattern = Pattern.compile(input);
        } catch (PatternSyntaxException ex) {
            System.out.println(String
                    .format("[ToggleCondition] Invalid Regex: \"%s\", try using %s to fix it!", input,
                            ConditionRegex.REGEX));
            this.pattern = null;
        }
    }

    @Override
    public Boolean apply(String input) {
        if (isEmpty(input) || this.pattern == null) {
            return false;
        }

        return this.pattern.matcher(input).matches();
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.REGEX;
    }
}
