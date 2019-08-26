/*
 *     Copyright (C) 2019 boomboompower
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
import java.util.Map.Entry;

import me.boomboompower.togglechat.toggles.ToggleBase;

import org.apache.commons.lang3.text.WordUtils;

public enum SortType {

    NORMAL,
    FAVOURITES;

    private final String displayName;
    private final Comparator<Entry<String, ToggleBase>> sorter;

    SortType() {
        this(null, null);
    }

    SortType(Comparator<Entry<String, ToggleBase>> sorter) {
        this(null, sorter);
    }

    SortType(String displayName, Comparator<Entry<String, ToggleBase>> sorter) {
        this.displayName = displayName == null ? WordUtils.capitalize(name().toLowerCase()) : displayName;
        this.sorter = sorter == null ? new ToggleBaseComparator() : sorter;
    }

    public Comparator<Entry<String, ToggleBase>> getSorter() {
        return this.sorter;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
