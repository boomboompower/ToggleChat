package me.boomboompower.togglechat.toggles.custom.conditions;

import me.boomboompower.togglechat.toggles.custom.ToggleCondition;

public class ConditionEqualsIgnoreCase extends ToggleCondition {

    public ConditionEqualsIgnoreCase(String input) {
        super(input);
    }

    @Override
    public Boolean apply(String input) {
        return input.equalsIgnoreCase(getText());
    }
}
