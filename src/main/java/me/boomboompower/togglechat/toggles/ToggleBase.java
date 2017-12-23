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
import me.boomboompower.togglechat.toggles.custom.ICustomToggle;
import me.boomboompower.togglechat.toggles.custom.TypeCustom;
import me.boomboompower.togglechat.toggles.defaults.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Created to replace the old ToggleBase class
 *      because it was really trash
 *
 * @author boomboompower
 */
public abstract class ToggleBase {

    /* Name | ToggleBase */
    private static final LinkedHashMap<String, ToggleBase> toggles = new LinkedHashMap<>();

    /* Name | TypeCustom */
    private static final LinkedHashMap<String, TypeCustom> custom = new LinkedHashMap<>();

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
        for (TypeCustom custom : custom.values()) {
            if (custom.shouldToggle(input)) {
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
        if (toggleBase != null && toggleBase.getName() != null)  {
            if (toggleBase instanceof ICustomToggle && toggleBase instanceof TypeCustom) {
                custom.put(toggleBase.getName().toLowerCase().replace(" ", "_"), ((TypeCustom) toggleBase));
            } else {
                toggles.put(toggleBase.getName().toLowerCase().replace(" ", "_"), toggleBase);
            }
        }
    }

    /**
     * Clears all toggles and readds default ones
     */
    public static void remake() {
        toggles.clear();
        toggles.put("ads", new TypeAds());
        toggles.put("team", new TypeTeam());
        toggles.put("join", new TypeJoin());
        toggles.put("leave", new TypeLeave());
        toggles.put("guild", new TypeGuild());
        toggles.put("party", new TypeParty());
        toggles.put("shout", new TypeShout());
        toggles.put("soul", new TypeSoulWell());
        toggles.put("housing", new TypeHousing());
        toggles.put("messages", new TypeMessages());
        toggles.put("global", new TypeGlobal());
        toggles.put("ez_messages", new TypeEasy());
        toggles.put("special", new TypeSpecial());
        toggles.put("colored_team", new TypeColored());
        toggles.put("party_invites", new TypePartyInvites());
        toggles.put("build_battle", new TypeBuildBattle());
        toggles.put("mystery_box", new TypeMysteryBox());
        toggles.put("spectator", new TypeSpectator());
        toggles.put("lobby_join", new TypeLobbyJoin());
        toggles.put("separators", new TypeMessageSeparator());
        toggles.put("friend_requests", new TypeFriendRequests());
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
     * Gets the description of the specified toggle,
     *      this will show up in the main toggle gui
     *
     * @return description of the toggle, can be null
     */
    public abstract LinkedList<String> getDescription();

    /**
     * Confirms if the toggle has a description
     *      returns false if the description is null or empty
     *
     * @return true if the description is valid
     */
    public final boolean hasDescription() {
        return getDescription() != null && !getDescription().isEmpty();
    }

    /**
     * Gets the display format for the button.
     *      Will be formatted when loaded
     *
     * @return The button display format
     */
    public String getDisplayName() {
        return getName() + ": %s";
    }

    /**
     * Should each word in the display name
     *      be forcefully capitalized?
     *
     * @return true if it should be capitalized
     */
    public boolean capitalizeName() {
        return false;
    }

    /**
     * Should the shouldToggle method use the
     *      formatted chat for the regular check?
     *
     * @return true if the formatted message should
     *      be used
     */
    public boolean useFormattedMessage() {
        return false;
    }

    @Override
    public String toString() {
        String message = "ToggleBase{parsers = ";

        if (toggles.isEmpty()) {
            message += "[]";
        } else {
            message += Arrays.toString(toggles.keySet().toArray());
        }

        if (!custom.isEmpty()) {
            message += ", custom = " + Arrays.toString(custom.keySet().toArray());
        }

        return message + "}";
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
        custom.forEach(newInput::put);
        return newInput;
    }

    /**
     * Assistance in linked-list creation
     *
     * @param entry the array by which the list will be backed
     * @param <T> the class of the objects in the list
     * @return a list view of the specified array
     */
    @SafeVarargs
    public final <T> LinkedList<T> asLinked(T... entry) {
        LinkedList<T> list = new LinkedList<>();
        list.addAll(Arrays.asList(entry));
        return list;
    }

    /**
     * Checks if the message contains something without
     *      being case-sensitive
     *
     * @param message The message to check
     * @param contains the contents
     * @return true if it contains it
     */
    public final boolean containsIgnoreCase(String message, String contains) {
        return Pattern.compile(Pattern.quote(contains), Pattern.CASE_INSENSITIVE).matcher(message).find();
    }
}
