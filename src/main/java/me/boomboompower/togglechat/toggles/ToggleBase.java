/*
 *     Copyright (C) 2020 Isophene
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;
import lombok.Setter;

import me.boomboompower.togglechat.toggles.custom.TypeCustom;
import me.boomboompower.togglechat.toggles.sorting.impl.ToggleBaseComparator;
import me.boomboompower.togglechat.toggles.defaults.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Created to replace the old ToggleBase class, supports custom toggles.
 * <p>
 * The best class for static abuse and other non-object-oriented-programming methods
 *
 * @author boomboompower
 * @version 2.4
 */
public abstract class ToggleBase {

    private static final ToggleBaseComparator comparator = new ToggleBaseComparator();

    /* Name | ToggleBase */
    private static final LinkedHashMap<String, ToggleBase> toggles = new LinkedHashMap<>();

    /* Name | TypeCustom */
    private static final LinkedHashMap<String, ToggleBase> custom = new LinkedHashMap<>();

    @Getter
    @Setter
    private boolean favourite;

    /**
     * Default constructor for ToggleBase
     */
    public ToggleBase() {
    }

    /**
     * Adds the developers own ToggleBase
     *
     * @param toggleBase the developers toggle
     */
    public static void addToggle(ToggleBase toggleBase) {
        if (toggleBase != null && toggleBase.getName() != null) {
            if (toggleBase instanceof TypeCustom) {
                custom.put(toggleBase.getIdString(), toggleBase);

                // We want to reorder every time a custom one is added, so things added later on will reorder it
                sortMap(custom);
            } else {
                toggles.put(toggleBase.getIdString(), toggleBase);
            }
        }
    }

    /**
     * Clears all the normal toggles and orders them by the size of their unformatted display text.
     * Used at startup
     */
    public static void remake() {
        toggles.clear();
        addToggle(new TypeAds());
        addToggle(new TypePit());
        addToggle(new TypeTip());
        addToggle(new TypeEasy());
        addToggle(new TypeGexp());
        addToggle(new TypeTeam());
        addToggle(new TypeGuild());
        addToggle(new TypeParty());
        addToggle(new TypeShout());
        addToggle(new TypeGlobal());
        addToggle(new TypeColored());
        addToggle(new TypeHousing());
        addToggle(new TypeSpecial());
        addToggle(new TypeMessages());
        addToggle(new TypeSoulWell());
        addToggle(new TypeWatchdog());
        addToggle(new TypeGuildJoin());
        addToggle(new TypeLobbyJoin());
        addToggle(new TypeSpectator());
        addToggle(new TypeFriendJoin());
        addToggle(new TypeGuildLeave());
        addToggle(new TypeMysteryBox());
        addToggle(new TypeBuildBattle());
        addToggle(new TypeFriendLeave());
        addToggle(new TypePartyInvites());
        addToggle(new TypeFriendRequests());
        addToggle(new TypeMessageSeparator());

        sortMap(toggles);
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
     * Checks to see if the registered parsers contains a parser with the given name.
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
     * Checks to see if the given chat is enabled
     *
     * @return true if the player wants to see the given chat
     */
    public abstract boolean isEnabled();

    /**
     * Sets the message to be toggled or not. Is used in toggle loading
     *
     * @param enabled used in loading to set the toggled enabled/disabled
     */
    public abstract void setEnabled(boolean enabled);

    /**
     * Gets the description of the specified toggle, this will show up in the main toggle gui
     *
     * @return description of the toggle, can be null
     */
    public abstract LinkedList<String> getDescription();


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
        return getDescription() != null && !getDescription().isEmpty();
    }

    /**
     * The id that will be used for this toggle globally, used as an identifier
     *
     * @return the id of this toggle
     */
    public final String getIdString() {
        return getName().toLowerCase().replace(" ", "_");
    }

    @Override
    public final String toString() {
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
     * Creates a new temporary HashMap for toggles. this is to prevent HashMap.clear
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
     * @param <T>   the class of the objects in the list
     * @return a list view of the specified array
     */
    @SafeVarargs
    public final <T> LinkedList<T> asLinked(T... entry) {
        return new LinkedList<>(Arrays.asList(entry));
    }

    public static void inheritFavourites(ArrayList<String> favourites) {
        for (String f : favourites) {
            ToggleBase toggle = getToggle(f);

            if (toggle != null) {
                toggle.setFavourite(true);
            }
        }
    }

    /**
     * Checks if the message contains something without being case-sensitive
     *
     * @param message  The message to check
     * @param contains the contents
     * @return true if it contains it
     */
    public final boolean containsIgnoreCase(String message, String contains) {
        return Pattern.compile(Pattern.quote(contains), Pattern.CASE_INSENSITIVE).matcher(message)
                .find();
    }

    /**
     * Used to sort by the displayname of the toggle, so the gui looks neat without having to do it
     * manually
     *
     * @param map the map to sort
     */
    private static void sortMap(LinkedHashMap<String, ToggleBase> map) {
        List<Entry<String, ToggleBase>> list = new LinkedList<>(map.entrySet());

        list.sort(comparator);

        Map<String, ToggleBase> sortedMap = new LinkedHashMap<>();

        for (Entry<String, ToggleBase> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        map.clear();
        map.putAll(sortedMap);
    }
}
