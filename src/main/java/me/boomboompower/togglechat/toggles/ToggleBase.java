/*
 *     Copyright (C) 2017 boomboompower
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

package me.boomboompower.togglechat.toggles;

import net.minecraft.client.gui.GuiButton;

/**
 * A fairly old interface to configure and check Toggles
 */
public interface ToggleBase {

    /**
     * Gets the base name of the toggle. Used in saving & loading
     *
     * @return the toggle base name
     */
    public String getName();

    /**
     * Gets the id, external loaders cannot use ids below 17
     *
     * @return the toggle's id
     */
    public int getId();

    /**
     * Determines if the message triggers the toggle check
     *
     * @param message Message input that will be tested
     * @return true if the message should be toggled
     */
    public boolean isMessage(String message);

    /**
     * Checks to see if the given toggle is on/off
     *
     * @return true if the message should be shown
     */
    public boolean isEnabled();

    /**
     * Called when the button is clicked. Should be
     *      used to toggle on/off or do something else
     *
     * @param button the button that was clicked
     */
    public void onClick(GuiButton button);

    /**
     * Unrequired method to toggle on/off. Used in loading
     *
     * @param enabled if the message should be enabled
     */
    public default void setEnabled(boolean enabled) {}

    /**
     * Unrequired method to get the displayname of the button
     *      Can be changed but this is the recommended format
     *
     * @return the displayName of the button
     */
    default String getDisplayName() {
        return getName() + ": %s";
    }
}
