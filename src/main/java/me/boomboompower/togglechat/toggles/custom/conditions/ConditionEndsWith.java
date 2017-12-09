package me.boomboompower.togglechat.toggles.custom.conditions;

import me.boomboompower.togglechat.toggles.custom.ToggleCondition;

public class ConditionEndsWith extends ToggleCondition {

    public ConditionEndsWith(String input) {
        super(input);
    }

    @Override
    public Boolean apply(String input) {
        return input.endsWith(getText());
    }
}
