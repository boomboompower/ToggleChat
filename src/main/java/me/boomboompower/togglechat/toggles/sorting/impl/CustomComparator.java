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

package me.boomboompower.togglechat.toggles.sorting.impl;

import me.boomboompower.togglechat.toggles.ToggleBase;
import me.boomboompower.togglechat.toggles.custom.ICustomToggle;
import me.boomboompower.togglechat.toggles.sorting.ToggleComparator;

/**
 * Sort by:
 * -> Custom Toggles
 * -> String width
 * <p>
 * If they are both custom toggles or neither is a custom toggle, display size will be used
 */
public class CustomComparator extends ToggleComparator {

    @Override
    public int compare(ToggleBase firstIn, ToggleBase secondIn) {
        boolean isFirstCustom = firstIn instanceof ICustomToggle;
        boolean isSecondCustom = secondIn instanceof ICustomToggle;

        if (isFirstCustom && !isSecondCustom) {
            return -1;
        } else if (!isFirstCustom && isSecondCustom) {
            return 0;
        } else {
            return compareDefault(firstIn, secondIn);
        }
    }
}
