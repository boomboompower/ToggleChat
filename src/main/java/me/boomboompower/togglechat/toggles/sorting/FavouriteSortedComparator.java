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

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.toggles.ToggleBase;

import net.minecraft.client.Minecraft;

/**
 * Sort by:
 *   -> Favourites
 *   -> String width
 *
 * If they are both favourites or neither is a favourite, display size will be used
 */
public class FavouriteSortedComparator implements Comparator<Entry<String, ToggleBase>> {

    private final ToggleChatMod mode = ToggleChatMod.getInstance();
    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public int compare(Entry<String, ToggleBase> firstIn, Entry<String, ToggleBase> secondIn) {
        ToggleBase first = firstIn.getValue();
        ToggleBase second = secondIn.getValue();

        if (first.isFavourite() && !second.isFavourite()) {
            return -1;
        } else if (!first.isFavourite() && second.isFavourite()) {
            return 0;
        } else {
            return compareWidth(first, second);
        }
    }

    /**
     * x = arg1
     * y = arg2
     *
     * equals 0 if x == y;
     * less than 0 if x < y
     * grater than 0 if x > y
     */
    private int compareWidth(ToggleBase first, ToggleBase second) {
        Integer width_first = this.mc.fontRendererObj.getStringWidth(first.getDisplayName());
        Integer width_second = this.mc.fontRendererObj.getStringWidth(second.getDisplayName());

        return width_first.compareTo(width_second);
    }
}
