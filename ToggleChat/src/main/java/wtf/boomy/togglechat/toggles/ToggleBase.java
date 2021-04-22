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

package wtf.boomy.togglechat.toggles;

/**
 * Created to replace the old ToggleBase class, supports custom toggles.
 * <p>
 * The best class for static abuse and other non-object-oriented-programming methods
 *
 * @author boomboompower
 * @version 2.4
 */
public abstract class ToggleBase {
    
    private boolean enabled = true; // By default we want them enabled.
    private boolean favourite;

    /**
     * Default constructor for ToggleBase
     */
    public ToggleBase() {
    }

    /**
     * Returns the name of the specified ToggleBase
     *
     * @return the name of the specified ToggleBase, cannot be null
     */
    public abstract String getName();

    /**
     * Gets the display format for the button. Will be formatted when loaded
     * <p>
     * IMPORTANT: IF OVERRIDING DO NOT FORMAT YOUR MESSAGE HERE!!!! Leave it as something like
     * "MyToggle: %s" instead of directly figuring out the value, or the ordering will be wrong.
     *
     * @return The button display format
     */
    public String getDisplayName() {
        return getName() + ": %s";
    }

    /**
     * Checks the given text to see if it should be toggled
     *
     * @param message message to test
     * @return true if the message matches the toggle test
     */
    public abstract boolean shouldToggle(final String message);

    /**
     * Gets the description of the specified toggle, this will show up in the main toggle gui
     *
     * @return description of the toggle, can be null
     */
    public abstract String[] getDescription();
    
    /**
     * Returns the respective category this toggle should be defined under.
     * This is used for filtering purposes in the main UI.
     *
     * @return the category for this toggle.
     */
    public Categories getCategory() {
        return Categories.OTHER;
    }

    /**
     * Should the shouldToggle method use the formatted chat for the regular check?
     *
     * @return true if the formatted message should be used
     */
    public boolean useFormattedMessage() {
        return false;
    }

    /**
     * Confirms if the toggle has a description returns false if the description is null or empty
     *
     * @return true if the description is valid
     */
    public final boolean hasDescription() {
        return getDescription() != null && getDescription().length > 0;
    }

    /**
     * The id that will be used for this toggle globally, used as an identifier
     *
     * @return the id of this toggle
     */
    public String getIdString() {
        return getName().toLowerCase().replace(" ", "_");
    }
    
    /**
     * Checks to see if the given chat is enabled
     *
     * @return true if the player wants to see the given chat
     */
    public boolean isEnabled() {
        return this.enabled;
    }
    
    /**
     * Sets the message to be toggled or not. Is used in toggle loading
     *
     * @param enabled used in loading to set the toggled enabled/disabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * Is this toggle one of the users favourites? This is used for filtering
     *
     * @return return true if this is a favourite toggle
     */
    public boolean isFavourite() {
        return this.favourite;
    }
    
    /**
     * Sets the favourite status of this toggle.
     *
     * @param favourite true, if this is true a star will be rendered next to the toggle name
     */
    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
    
    @Override
    public final String toString() {
        return "ToggleBase{name = " + getName() + ", enabled = " + isEnabled() + ", favourite = " + isFavourite() + "}";
    }
}
