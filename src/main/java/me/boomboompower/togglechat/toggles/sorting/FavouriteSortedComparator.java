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

import me.boomboompower.togglechat.toggles.ToggleBase;

/**
 * Sort by:
 * -> Favourites
 * -> String width (Default)
 * <p>
 * If they are both favourites or neither is a favourite, display size will be used
 */
public class FavouriteSortedComparator extends ToggleComparator {

    @Override
    public int compare(ToggleBase firstIn, ToggleBase secondIn) {
        if (firstIn.isFavourite() && !secondIn.isFavourite()) {
            return -1;
        } else if (!firstIn.isFavourite() && secondIn.isFavourite()) {
            return 0;
        } else {
            return compareDefault(firstIn, secondIn);
        }
    }
}
