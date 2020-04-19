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

package me.boomboompower.togglechat.toggles.sorting;

import java.util.Comparator;

import me.boomboompower.togglechat.toggles.ToggleBase;
import me.boomboompower.togglechat.toggles.sorting.impl.AlphabeticalComparator;
import me.boomboompower.togglechat.toggles.sorting.impl.CustomComparator;
import me.boomboompower.togglechat.toggles.sorting.impl.FavouriteSortedComparator;

import org.apache.commons.lang3.text.WordUtils;

/**
 * A compilation of different types of {@link ToggleComparator}, used to sort the Toggles in the Toggle menu
 */
public enum SortType {

    WIDTH("Width", "Sorts by the width\nof the name", null),
    ALPHABETICAL("A-Z", "Sorts the toggles\nalphabetically (A-Z)", new AlphabeticalComparator()),
    INVERSE_ALPHABETICAL("Z-A", "Sorts the toggles\nunalphabetically (A-Z)", new AlphabeticalComparator().inverse()),
    CUSTOM("Custom", "Puts custom toggles\nfirst then sorts\nby width", new CustomComparator()),
    FAVOURITES("Favourite", "Puts favourite toggles\nfirst then sorts\nby width", new FavouriteSortedComparator());

    private final String displayName;
    private final String description;
    private final Comparator<ToggleBase> sorter;

    private static int chosenValue = 0;

    SortType() {
        this(null, null);
    }

    SortType(Comparator<ToggleBase> sorter) {
        this(null, sorter);
    }

    SortType(String displayName, Comparator<ToggleBase> sorter) {
        this(displayName, null, sorter);
    }

    SortType(String displayName, String description, Comparator<ToggleBase> sorter) {
        this.displayName = displayName == null ? WordUtils.capitalize(name().toLowerCase()) : displayName;
        this.description = description;
        this.sorter = sorter == null ? new ToggleComparator() : sorter;
    }

    public Comparator<ToggleBase> getSorter() {
        return this.sorter;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getDescription() {
        return this.description;
    }

    public static SortType getCurrentSortType() {
        return values()[chosenValue];
    }

    public static SortType getNextSortType() {
        int nextValue = chosenValue + 1;

        if (nextValue >= values().length) {
            nextValue = 0;
        }

        chosenValue = nextValue;

        return values()[nextValue];
    }
}
