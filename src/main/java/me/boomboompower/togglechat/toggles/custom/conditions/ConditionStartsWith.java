package me.boomboompower.togglechat.toggles.custom.conditions;

import me.boomboompower.togglechat.toggles.custom.ToggleCondition;

public class ConditionStartsWith extends ToggleCondition {

    public ConditionStartsWith(String input) {
        super(input);
    }

    @Override
    public Boolean apply(String input) {
        return input.startsWith(getText());
    }
}
