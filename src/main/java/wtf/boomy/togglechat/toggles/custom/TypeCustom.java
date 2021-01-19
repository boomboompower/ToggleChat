/*
 *     Copyright (C) 2021 boomboompower
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package wtf.boomy.togglechat.toggles.custom;

import wtf.boomy.togglechat.toggles.ToggleBase;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * A custom Toggle, has all appropriate methods for user customization
 *
 * @author boomboompower
 * @version 1.0
 */
public class TypeCustom extends ToggleBase implements ICustomToggle {
    
    private boolean enabled = true;

    private boolean dirty; // If true, this will be saved on the next save request

    private final String togglename; // The name of the toggle
    
    private final LinkedList<ToggleCondition> conditions; // The conditions of the toggle
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
        markDirty();

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
        markDirty();

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

    /**
     * Indicates that this toggle has been modified and requires saving.
     *
     * When this is true, the save file will be overwritten with the new contents.
     */
    public void markDirty() {
        this.dirty = true;
    }

    /**
     * When this toggle is saved, it will no longer require saving (until it is modified again)
     *
     * We'll disable the save trigger
     */
    public void clean() {
        this.dirty = false;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public boolean isDirty() {
        return this.dirty;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
