package me.boomboompower.togglechat.toggles.custom;

import lombok.Getter;
import lombok.Setter;

import me.boomboompower.togglechat.toggles.ToggleBase;

import java.util.*;

/**
 * A custom Toggle, has all appropriate methods for user customization
 *
 * @author boomboompower
 * @version 1.0
 */
public class TypeCustom extends ToggleBase implements ICustomToggle {
    
    @Getter
    @Setter
    private boolean enabled = true; /** Enabled/Disabled */
    
    private String togglename; // The name of the toggle
    private List<ToggleCondition> conditions; // The conditions of the toggle
    
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
    
    /**
     * The real name of the toggle, will never be null
     *
     * @return the name of the toggle
     */
    public String _getName() {
        return this.togglename != null ? this.togglename : "Unknown";
    }
    
    /**
     * The conditions of this toggle
     *
     * @return the conditions
     */
    public List<ToggleCondition> _getConditions() {
        return this.conditions;
    }
    
    /**
     * Adds a condition to this toggle
     *
     * @param condition the condition to add
     */
    public void _addCondition(ToggleCondition condition) {
        this.conditions.add(condition);
    }
    
    /**
     * Converts an array of conditions into one list
     *
     * @param conditions the conditions to add
     * @return the list version of the conditions
     */
    private List<ToggleCondition> asList(ToggleCondition... conditions) {
        return new ArrayList<>(Arrays.asList(conditions));
    }
}
