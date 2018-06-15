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
    
    private LinkedList<ToggleCondition> conditions; // The conditions of the toggle
    private LinkedList<String> comments; // The comments displayed on the top of the file
    
    public TypeCustom(String name) {
        this(name, new LinkedList<>(), new LinkedList<>());
    }
    
    public TypeCustom(String name, ToggleCondition condition) {
        this.togglename = name;
        this.conditions = asList(condition);
        this.comments = new LinkedList<>();
    }
    
    public TypeCustom(String name, LinkedList<ToggleCondition> condition, LinkedList<String> comments) {
        this.togglename = name;
        this.conditions = condition;
        this.comments = comments;
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
    public LinkedList<ToggleCondition> _getConditions() {
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
     * Returns the comments that will be displayed on top of the file
     *
     * @return the comments on top of the file
     */
    public LinkedList<String> _getComments() {
        return this.comments;
    }
    
    /**
     * Sets the comments of the file
     *
     * @param comments the comments of the file
     */
    public TypeCustom _setComments(LinkedList<String> comments) {
        this.comments = comments;
        
        return this;
    }
    
    /**
     * Converts an array of conditions into one list
     *
     * @param conditions the conditions to add
     * @return the list version of the conditions
     */
    private LinkedList<ToggleCondition> asList(ToggleCondition... conditions) {
        return new LinkedList<>(Arrays.asList(conditions));
    }
}
