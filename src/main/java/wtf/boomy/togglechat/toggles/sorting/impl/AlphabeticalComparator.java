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

package wtf.boomy.togglechat.toggles.sorting.impl;

import wtf.boomy.togglechat.toggles.ToggleBase;
import wtf.boomy.togglechat.toggles.sorting.ToggleComparator;

/**
 * Sort by:
 * -> Alphabetical
 * <p>
 * If the words are the same alphabetically, then 0 will be returned as it means they are the same word
 */
public class AlphabeticalComparator extends ToggleComparator {

    private boolean inverse = false;

    @Override
    public int compare(ToggleBase firstIn, ToggleBase secondIn) {
        int trackedCompare = firstIn.getName().compareToIgnoreCase(secondIn.getName());

        if (this.inverse && trackedCompare != 0) {
            return -trackedCompare;
        }

        return trackedCompare;
    }

    /**
     * Inverse this Comparator
     *
     * @return a comparator which is inversed.
     */
    public AlphabeticalComparator inverse() {
        this.inverse = !this.inverse;

        return this;
    }

}
