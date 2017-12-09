package me.boomboompower.togglechat.toggles.custom.conditions;

import me.boomboompower.togglechat.toggles.custom.ToggleCondition;

public class ConditionEmpty extends ToggleCondition {

    public ConditionEmpty() {
        super("");
    }

    @Override
    public Boolean apply(String input) {
        return false;
    }
}
