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
 * Compare by fontrender length, rather than relying on a linked list
 */
public class ToggleComparator implements Comparator<ToggleBase> {

    private final ToggleChatMod mode = ToggleChatMod.getInstance();
    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public int compare(ToggleBase firstIn, ToggleBase secondIn) {
        return compareDefault(firstIn, secondIn);
    }

    public int compareDefault(ToggleBase firstIn, ToggleBase secondIn) {
        Integer first = this.mc.fontRendererObj.getStringWidth(firstIn.getDisplayName());
        Integer second = this.mc.fontRendererObj.getStringWidth(secondIn.getDisplayName());

        return first.compareTo(second);
    }
}
