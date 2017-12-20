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
public class ConditionStartsWith extends ToggleCondition {

    public ConditionStartsWith(String input) {
        super(input);
    }

    @Override
    public String getSaveIdentifier() {
        return "startsWith";
    }

    @Override
    public Boolean apply(String input) {
        return input.startsWith(getText());
    }
}
