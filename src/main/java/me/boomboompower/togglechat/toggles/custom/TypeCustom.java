package me.boomboompower.togglechat.toggles.custom;

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.ToggleBase;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TypeCustom extends ToggleBase implements ICustomToggle {

    private boolean showCustom = true;

    private String togglename;
    private List<ToggleCondition> conditions;

    public TypeCustom(String name, ToggleCondition condition) {
        this.togglename = name;
        this.conditions = Collections.singletonList(condition);
    }

    public TypeCustom(String name, List<ToggleCondition> condition) {
        this.togglename = name;
        this.conditions = condition;
    }

    @Override
    public String getName() {
        return togglename.toLowerCase();
    }

    @Override
    public boolean shouldToggle(String message) {
        for (ToggleCondition condition : this.conditions) {
            if (condition.apply(message)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.showCustom;
    }

    @Override
    public void setToggled(boolean isToggled) {
        this.showCustom = isToggled;
    }

    @Override
    public void onClick(ModernButton button) {
        this.showCustom = !this.showCustom;
        button.setText(String.format(getDisplayName(), ModernGui.getStatus(isEnabled())));
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked("A custom toggle", "", "&b" + _getName());
    }

    private String _getName() {
        return this.togglename != null ? this.togglename : "Unknown";
    }

    public List<ToggleCondition> _getCondition() {
        return this.conditions;
    }
}
