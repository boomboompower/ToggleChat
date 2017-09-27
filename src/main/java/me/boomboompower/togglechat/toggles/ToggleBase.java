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

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.toggles.defaults.*;

import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * Created to replace the old ToggleBase class
 *      because it was really trash
 *
 * @author boomboompower
 */
public abstract class ToggleBase {

    /* Name | ToggleBase */
    private static final LinkedHashMap<String, ToggleBase> toggles = new LinkedHashMap<>();

    /**
     * Default constructor for ToggleBase
     */
    public ToggleBase() {
    }

    /**
     * Run through all bases and check if the
     *      given text should be toggled
     *
     * @param input text to test
     * @return the formatted text
     */
    public static boolean isEnabled(String input) {
        for (ToggleBase parser : toggles.values()) {
            if (parser.shouldToggle(input)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the developers own ToggleBase
     *
     * @param toggleBase the developers toggle
     */
    public static void addToggle(ToggleBase toggleBase) {
        if (toggleBase != null && toggleBase.getName() != null) toggles.put(toggleBase.getName().toLowerCase().replace(" ", "_"), toggleBase);
    }

    /**
     * Clears all toggles and readds default ones
     */
    public static void remake() {
        toggles.clear();
        toggles.put("special", new TypeSpecial());
        toggles.put("team", new TypeTeam());
        toggles.put("join", new TypeJoin());
        toggles.put("leave", new TypeLeave());
        toggles.put("guild", new TypeGuild());
        toggles.put("party", new TypeParty());
        toggles.put("shout", new TypeShout());
        toggles.put("housing", new TypeHousing());
        toggles.put("global", new TypeGlobal());
        toggles.put("colored_team", new TypeColored());
        toggles.put("messages", new TypeMessages());
        toggles.put("party_invites", new TypePartyInvites());
        toggles.put("spectator", new TypeSpectator());
        toggles.put("friend_requests", new TypeFriendRequests());
        toggles.put("separators", new TypeMessageSeparator());
    }

    /**
     * Gets a toggle by the given name, may return null
     *
     * @param name the toggle's name
     * @return a ToggleBase instance if found, or else null
     */
    public static ToggleBase getToggle(String name) {
        return toggles.getOrDefault(name, null);
    }

    /**
     * Checks to see if the registered parsers contains a parser
     *      with the given name.
     *
     * @param name The toggle's name to test for
     * @return true if it is registered
     */
    public static boolean hasToggle(String name) {
        return toggles.containsKey(name);
    }

    /**
     * Returns the name of the specified ToggleBase
     *
     * @return the name of the specified ToggleBase, cannot be null
     */
    public abstract String getName();

    /**
     * Checks the given text to see if it should be toggled
     *
     * @param message message to test
     * @return true if the message matches the toggle test
     */
    public abstract boolean shouldToggle(final String message);

    /**
     * Checks to see if the given chat is enabled
     *
     * @return true if the player wants to see the given chat
     */
    public abstract boolean isEnabled();

    /**
     * Sets the message to be toggled or not. Is used in
     *      toggle loading
     *
     * @param isToggled used in loading to set the toggled enabled/disabled
     */
    public abstract void setToggled(boolean isToggled);

    /**
     * Called when the button is first clicked. Toggle logic should
     *      go here. Such as turning your setting on/off
     *
     * @param button The button that was pressed
     */
    public abstract void onClick(ModernButton button);

    /**
     * Gets the display format for the button.
     *      Will be formatted when loaded
     *
     * @return The button display format
     */
    public String getDisplayName() {
        return getName() + ": %s";
    }

    @Override
    public String toString() {
        return "MessageBase{parsers=" + Arrays.toString(toggles.keySet().toArray()) + '}';
    }

    /**
     * Creates a new temporary HashMap for toggles.
     *      this is to prevent HashMap.clear
     *
     * @return The toggle list
     */
    public static LinkedHashMap<String, ToggleBase> getToggles() {
        LinkedHashMap<String, ToggleBase> newInput = new LinkedHashMap<>();
        toggles.forEach(newInput::put);
        return newInput;
    }
}
