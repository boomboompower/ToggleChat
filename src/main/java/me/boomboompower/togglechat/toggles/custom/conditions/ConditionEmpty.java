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
public class ConditionEmpty extends ToggleCondition {

    public ConditionEmpty() {
        super("");
    }

    @Override
    public String getSaveIdentifier() {
        return "empty";
    }

    @Override
    public Boolean apply(String input) {
        return false;
    }
}
