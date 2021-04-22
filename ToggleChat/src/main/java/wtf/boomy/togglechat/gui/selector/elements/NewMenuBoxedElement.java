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

package wtf.boomy.togglechat.gui.selector.elements;

import wtf.boomy.mods.modernui.threads.SimpleCallback;
import wtf.boomy.mods.modernui.uis.ModernGui;

import java.awt.Color;

/**
 * A {@link BoxedElement} responsible for showing a layout of the new ToggleChat UI
 *
 * Features a more compact layout compared to it's counterpart. Uses vertical scrolling
 * instead of pagination (like the legacy UI uses), which is designed for user friendliness.
 *
 * @author boomboompower
 * @since 3.1.37
 */
public class NewMenuBoxedElement extends BoxedElement {
    
    private final int backgroundColour = new Color(0, 0, 0, 100).getRGB();
    private final int foregroundColour = new Color(200, 200, 200, 200).getRGB();
    
    public NewMenuBoxedElement(int xPos, int yPos, int width, int height, SimpleCallback<Void> onLeftClicked) {
        super(xPos, yPos, width, height, onLeftClicked);
    }
    
    @Override
    public String getDisplayName() {
        return "New Menu";
    }
    
    @Override
    public void drawBoxInner(boolean hovered, float partialTicks) {
        int thirdWidth = this.width / 3;
        
        // Background colour
        ModernGui.drawRect(this.x + 1, this.y + 1, this.x + thirdWidth + 3, this.y + this.height, this.backgroundColour);
        
        // Group 1 toggles
        ModernGui.drawRect(this.x + 4, this.y + 9, this.x + 12, this.y + 10, this.foregroundColour);
        
        ModernGui.drawRect(this.x + 5, this.y + 13, this.x + 32, this.y + 14, this.foregroundColour);
        ModernGui.drawRect(this.x + 5, this.y + 17, this.x + 24, this.y + 18, this.foregroundColour);
        ModernGui.drawRect(this.x + 5, this.y + 21, this.x + 36, this.y + 22, this.foregroundColour);
        ModernGui.drawRect(this.x + 5, this.y + 25, this.x + 23, this.y + 26, this.foregroundColour);
    
        // Group 2 toggles
        ModernGui.drawRect(this.x + 4, this.y + 33, this.x + 14, this.y + 34, this.foregroundColour);
        ModernGui.drawRect(this.x + 5, this.y + 37, this.x + 22, this.y + 38, this.foregroundColour);
        ModernGui.drawRect(this.x + 5, this.y + 41, this.x + 33, this.y + 42, this.foregroundColour);
        ModernGui.drawRect(this.x + 5, this.y + 45, this.x + 25, this.y + 46, this.foregroundColour);
        ModernGui.drawRect(this.x + 5, this.y + 49, this.x + 28, this.y + 50, this.foregroundColour);
    
        // Group 3 toggles
        ModernGui.drawRect(this.x + 4, this.y + 57, this.x + 10, this.y + 58, this.foregroundColour);
        ModernGui.drawRect(this.x + 5, this.y + 61, this.x + 33, this.y + 62, this.foregroundColour);
        ModernGui.drawRect(this.x + 5, this.y + 65, this.x + 23, this.y + 66, this.foregroundColour);
        ModernGui.drawRect(this.x + 5, this.y + 69, this.x + 22, this.y + 70, this.foregroundColour);
        ModernGui.drawRect(this.x + 5, this.y + 73, this.x + 32, this.y + 74, this.foregroundColour);
        
        // Vertical separator
        ModernGui.drawRect(this.x + thirdWidth + 3, this.y + 1, this.x + thirdWidth + 4, this.y + this.height, this.foregroundColour);
    
        // Bottom Buttons
        ModernGui.drawRect(this.x + this.width - 23, this.y + this.height - 15, this.x + this.width - 10, this.y + this.height - 13, this.foregroundColour);
        ModernGui.drawRect(this.x + this.width - 23, this.y + this.height - 11, this.x + this.width - 10, this.y + this.height - 9, this.foregroundColour);
        ModernGui.drawRect(this.x + this.width - 23, this.y + this.height - 7, this.x + this.width - 10, this.y + this.height - 5, this.foregroundColour);
        
        // Scrollbar
        ModernGui.drawRect(this.x + this.width - 5, this.y + 3, this.x + this.width - 3, this.y + this.height - 3, this.foregroundColour);
        ModernGui.drawRect(this.x + this.width - 5, this.y + 8, this.x + this.width - 3, this.y + 15, this.backgroundColour);
        
        // Draw the hovered information
        ModernGui.drawRect(this.x + thirdWidth + 10, this.y + (this.height / 2) - 6, this.x + thirdWidth + 16, this.y + (this.height / 2) - 5, this.foregroundColour);
        ModernGui.drawRect(this.x + thirdWidth + 10, this.y + (this.height / 2) - 3, this.x + thirdWidth + 27, this.y + (this.height / 2) - 2, this.foregroundColour);
        ModernGui.drawRect(this.x + thirdWidth + 10, this.y + (this.height / 2), this.x + thirdWidth + 34, this.y + (this.height / 2) + 1, this.foregroundColour);
        ModernGui.drawRect(this.x + thirdWidth + 10, this.y + (this.height / 2) + 3, this.x + thirdWidth + 23, this.y + (this.height / 2) + 4, this.foregroundColour);
        ModernGui.drawRect(this.x + thirdWidth + 10, this.y + (this.height / 2) + 6, this.x + thirdWidth + 31, this.y + (this.height / 2) + 7, this.foregroundColour);
    }
    
}
