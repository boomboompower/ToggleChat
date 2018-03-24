/*
 *     Copyright (C) 2018 boomboompower
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

package me.boomboompower.togglechat.toggles.custom;

import me.boomboompower.togglechat.toggles.custom.conditions.*;

import java.util.List;

/**
 * A class used to load conditions, based of one something similar created by OrangeMarshall Used
 * with permission.
 *
 * @author boomboompower, OrangeMarshall
 * @version 1.0
 */
public abstract class ToggleCondition implements Function<String, Boolean> {
    
    private String name;
    private String text;
    
    public ToggleCondition(String input) {
        this.text = input;
    }
    
    /**
     * The text that will be used to identify this condition in the save files
     *
     * @return the save id
     */
    public abstract String getSaveIdentifier();
    
    /**
     * The text that was used to create this condition
     *
     * @return the text
     */
    public final String getText() {
        return this.text;
    }
    
    /**
     * Gets a ToggleCondition from the given line, will return a empty condition if invalid
     *
     * @param input the line input
     * @return a {@link ToggleCondition} based on the input of the line
     */
    public static ToggleCondition get(String input) {
        if (isEmpty(input)) {
            return new ConditionEmpty();
        }
        
        String condName = input.substring(0, input.indexOf("(")).toLowerCase();
        String[] arguments = input.substring(input.indexOf("(") + 1, input.lastIndexOf(")"))
            .split(",");
        try {
            switch (condName.toLowerCase()) {
                case "startswith":
                    return new ConditionStartsWith(arguments[0]);
                case "endswith":
                    return new ConditionEndsWith(arguments[0]);
                case "equals":
                    return new ConditionEquals(arguments[0]);
                case "equalsignorecase":
                    return new ConditionEqualsIgnoreCase(arguments[0]);
                case "contains":
                    if (arguments.length == 1) {
                        return new ConditionContains(arguments[0]);
                    }
                    return new ConditionContains(arguments[0], parseInt(arguments[1]));
                case "regex":
                    return new ConditionRegex(arguments[0]);
            }
        } catch (Exception ex) {
            System.err.println(String
                .format("[ToggleCondition] Failed to load custom toggle: Input = [ \"%s\" ]",
                    input));
        }
        // If there is an issue, we'll return nothing
        return new ConditionEmpty();
    }
    
    /**
     * Makes sure the string is not null or empty
     *
     * @param input the string
     * @return true if the string is null or empty
     */
    protected static boolean isEmpty(String input) {
        return input == null || input.isEmpty();
    }
    
    /**
     * Parses a number silently, no error will be thrown here
     *
     * @param input the input to parse
     * @return the number or 0 if failed
     */
    protected static int parseInt(String input) {
        try {
            if (!isEmpty(input)) {
                return Integer.parseInt(input);
            }
        } catch (Exception ex) {
        }
        return 1;
    }
    
    /**
     * Checks to see if the line give is a valid format and is supported by this version of
     * conditioning
     *
     * @param line the line to test
     * @return true if the line can be used
     */
    public static boolean isValidFormat(String line) {
        return !line.startsWith("//") && !line.isEmpty() && line.contains(" : ") && !(get(
            line.split(" : ")[1]) instanceof ConditionEmpty);
    }
    
    /**
     * A getter for the format name of the given line. This will return null if the line is not
     * in the valid format for the condition system
     *
     * @param line the line to get
     * @return the format name, or null if not applicable
     */
    public static String getFormatName(String line) {
        if (isValidFormat(line)) {
            return line.split(" : ")[0];
        } else {
            return null;
        }
    }
    
    // Use our text
    @Override
    public String toString() {
        return getText();
    }
}
