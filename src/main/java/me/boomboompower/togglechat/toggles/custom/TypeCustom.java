package me.boomboompower.togglechat.toggles.custom;

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.ToggleBase;

import java.util.*;

public class TypeCustom extends ToggleBase implements ICustomToggle {

    private boolean showCustom = true;

    private String togglename;
    private List<ToggleCondition> conditions;

    public TypeCustom(String name) {
        this.togglename = name;
        this.conditions = new ArrayList<>();
    }

    public TypeCustom(String name, ToggleCondition condition) {
        this.togglename = name;
        this.conditions = asList(condition);
    }

    public TypeCustom(String name, List<ToggleCondition> condition) {
        this.togglename = name;
        this.conditions = condition;
    }

    @Override
    public String getName() {
        return this.togglename;
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
        return asLinked(
                "A custom toggle",
                "",
                "&b" + _getName(),
                "",
                "This feature is still",
                "in &cbeta&r and may be",
                "modified at any time!",
                "",
                "Custom toggles were",
                "created with help from",
                "&6OrangeMarshall"
        );
    }

    private String _getName() {
        return this.togglename != null ? this.togglename : "Unknown";
    }

    public List<ToggleCondition> _getConditions() {
        return this.conditions;
    }

    public void _addCondition(ToggleCondition condition) {
        this.conditions.add(condition);
    }

    private List<ToggleCondition> asList(ToggleCondition... conditions) {
        List<ToggleCondition> easy = new ArrayList<>();
        easy.addAll(Arrays.asList(conditions));
        return easy;
    }
}
