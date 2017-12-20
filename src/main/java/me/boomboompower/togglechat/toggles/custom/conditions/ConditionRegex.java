package me.boomboompower.togglechat.toggles.custom.conditions;

import me.boomboompower.togglechat.toggles.custom.ToggleCondition;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * This code was created by OrangeMarshall and used
 *          with permission by boomboompower.
 *
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
        } catch (Exception ex) {
            System.out.println(String.format("[ToggleCondition] Invalid Regex: \"%s\", try using %s to fix it!", input, ConditionRegex.REGEX));
            this.pattern = null;
        }
    }

    @Override
    public String getSaveIdentifier() {
        return "regex";
    }

    @Override
    public Boolean apply(String input) {
        if (isEmpty(input) || this.pattern == null) {
            return false;
        }

        return this.pattern.matcher(input).matches();
    }
}
