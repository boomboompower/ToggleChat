package me.boomboompower.togglechat.toggles.custom.conditions;

import me.boomboompower.togglechat.toggles.custom.ToggleCondition;

public class ConditionEquals extends ToggleCondition {

    public ConditionEquals(String input) {
        super(input);
    }

    @Override
    public Boolean apply(String input) {
        return input.equals(getText());
    }
}
