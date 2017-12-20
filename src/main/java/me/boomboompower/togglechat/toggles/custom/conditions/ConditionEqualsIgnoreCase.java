package me.boomboompower.togglechat.toggles.custom.conditions;

import me.boomboompower.togglechat.toggles.custom.ToggleCondition;

/**
 * This code was created by OrangeMarshall and used
 *          with permission by boomboompower.
 *
 * Full credit to OrangeMarshall
 *
 * @author OrangeMarshall
 */
public class ConditionEqualsIgnoreCase extends ToggleCondition {

    public ConditionEqualsIgnoreCase(String input) {
        super(input);
    }

    @Override
    public String getSaveIdentifier() {
        return "equalsIgnoreCase";
    }

    @Override
    public Boolean apply(String input) {
        return input.equalsIgnoreCase(getText());
    }
}
