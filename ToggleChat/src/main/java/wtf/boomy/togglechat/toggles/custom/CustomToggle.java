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

import wtf.boomy.togglechat.toggles.Categories;
import wtf.boomy.togglechat.toggles.ToggleBase;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * A custom Toggle, has all appropriate methods for user customization
 *
 * @author boomboompower
 * @version 1.0
 */
public class CustomToggle extends ToggleBase implements ICustomToggle {
    
    // If true, this will be saved on the next save request
    private boolean dirty;

    // The actual display name of the toggle
    private final String togglename;
    
    // The internal id of the toggle
    private final String identifier;
    
    // The conditions of the toggle
    private final List<ToggleCondition> conditions;
    
    // The description for this toggle to be displayed
    private String[] description;
    
    // The save file name
    private File saveFile;
    
    public CustomToggle(String name) {
        this.conditions = new ArrayList<>();
        this.identifier = UUID.randomUUID().toString();
        this.togglename = name;
        this.description = new String[] {
                "A custom toggle",
                "",
                "&b" + name,
                "",
                "This feature is still",
                "in &cbeta&r and may be",
                "modified at any time!"
        };
    }
    
    public CustomToggle(String name, String identifier, ToggleCondition condition) {
        //noinspection ArraysAsListWithZeroOrOneArgument
        this.conditions = Arrays.asList(condition);
        this.identifier = identifier;
        this.togglename = name;
        this.description = new String[] {
                "A custom toggle",
                "",
                "&b" + name,
                "",
                "This feature is still",
                "in &cbeta&r and may be",
                "modified at any time!"
        };
    }
    
    public CustomToggle(String name, String identifier, List<ToggleCondition> condition, List<String> comments) {
        this.togglename = name;
        this.identifier = identifier;
        this.conditions = condition;
        this.description = comments.toArray(new String[0]);
    }
    
    @Override
    public String getIdString() {
        return this.identifier;
    }
    
    @Override
    public String getName() {
        return this.togglename;
    }
    
    @Override
    public boolean shouldToggle(String message) {
        for (ToggleCondition condition : this.conditions) {
            if (condition.shouldToggle(message)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String[] getDescription() {
        return this.description;
    }
    
    @Override
    public Categories getCategory() {
        return Categories.CUSTOM;
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
        markDirty();

        this.conditions.add(condition);
    }
    
    /**
     * Sets the comments of the file
     *
     * @param comments the comments of the file
     */
    public CustomToggle _setDescription(List<String> comments) {
        markDirty();

        this.description = comments.toArray(new String[0]);
        
        return this;
    }
    
    /**
     * Sets this custom toggles save file
     *
     * @param file the file which this will be saved to
     * @return the {@link CustomToggle} instance.
     */
    public CustomToggle _setSaveFile(File file) {
        this.saveFile = file;
        
        return this;
    }
    
    /**
     * Returns the expected save file.
     *
     * @return the save file
     */
    public File _getSaveFile() {
        return this.saveFile;
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
    
    public boolean isDirty() {
        return this.dirty;
    }
}
