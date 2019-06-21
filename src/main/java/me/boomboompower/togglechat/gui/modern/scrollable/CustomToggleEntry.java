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

package me.boomboompower.togglechat.gui.modern.scrollable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;

import java.awt.*;

public class CustomToggleEntry implements GuiListExtended.IGuiListEntry {
    
    private final Minecraft mc = Minecraft.getMinecraft();
    
    private final String labelText;
    private final int labelWidth;
    
    public CustomToggleEntry(String string) {
        this.labelText = string;
        this.labelWidth = mc.fontRendererObj.getStringWidth(this.labelText);
    }
    
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX,
        int mouseY, boolean isSelected) {
//        if (slotIndex == getSize()) {
//        }
        this.mc.fontRendererObj.drawString(this.labelText, mc.currentScreen.width / 2 - this.labelWidth / 2, y + slotHeight - this.mc.fontRendererObj.FONT_HEIGHT - 1, Color.WHITE.getRGB());
    }
    
    public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_,
        int p_148278_5_, int p_148278_6_) {
        return false;
    }
    
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX,
        int relativeY) {
    }
    
    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
    }
}