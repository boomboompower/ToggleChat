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
 * The legacy boxed element. This represents the legacy menu's design.
 *
 * This system makes use of pagination (unlike its counterpart which uses vertical scrolling).
 * Honestly, this design was a lot better when there were only a few toggles, however once more
 * were added things started getting crowded and it became harder to find toggles which is why
 * the new one was created. This one still has its uses though.
 *
 * @author boomboompower
 * @since 3.1.37
 */
public class LegacyMenuBoxedElement extends BoxedElement {
    
    // Stores the colour so we aren't creating a new instance every frame.
    private final int buttonColour = new Color(200, 200, 200, 200).getRGB();
    
    public LegacyMenuBoxedElement(int xPos, int yPos, int width, int height, SimpleCallback<Void> onLeftClicked) {
        super(xPos, yPos, width, height, onLeftClicked);
    }
    
    @Override
    public String getDisplayName() {
        return "Legacy Menu";
    }
    
    @Override
    public void drawBoxInner(boolean hovered, float partialTicks) {
        int halfWidth = this.width / 2;
        int halfHeight = this.height / 2;
        
        // The 7 toggle buttons
        ModernGui.drawRect(this.x + halfWidth - 15, this.y + halfHeight - 15, this.x + halfWidth + 15, this.y + halfHeight - 13, this.buttonColour);
        ModernGui.drawRect(this.x + halfWidth - 15, this.y + halfHeight - 10, this.x + halfWidth + 15, this.y + halfHeight - 8, this.buttonColour);
        ModernGui.drawRect(this.x + halfWidth - 15, this.y + halfHeight - 5, this.x + halfWidth + 15, this.y + halfHeight - 3, this.buttonColour);
        ModernGui.drawRect(this.x + halfWidth - 15, this.y + halfHeight, this.x + halfWidth + 15, this.y + halfHeight + 2, this.buttonColour);
        ModernGui.drawRect(this.x + halfWidth - 15, this.y + halfHeight + 5, this.x + halfWidth + 15, this.y + halfHeight + 7, this.buttonColour);
        ModernGui.drawRect(this.x + halfWidth - 15, this.y + halfHeight + 10, this.x + halfWidth + 15, this.y + halfHeight + 12, this.buttonColour);
        ModernGui.drawRect(this.x + halfWidth - 15, this.y + halfHeight + 15, this.x + halfWidth + 15, this.y + halfHeight + 17, this.buttonColour);
        
        // The modern settings button
        ModernGui.drawRect(this.x + 4, this.y + 4, this.x + 8, this.y + 8, this.buttonColour);
    
        // Sorting and category buttons
        ModernGui.drawRect(this.x + 5, this.y + this.height - 10, this.x + 20, this.y + this.height - 8, this.buttonColour);
        ModernGui.drawRect(this.x + 5, this.y + this.height - 6, this.x + 20, this.y + this.height - 4, this.buttonColour);
    
        // Allow List Button
        ModernGui.drawRect(this.x + this.width - 21, this.y + this.height - 10, this.x + this.width - 5, this.y + this.height - 8, this.buttonColour);
        
        // Left & Right navigation
        ModernGui.drawRect(this.x + this.width - 12, this.y + this.height - 6, this.x + this.width - 5, this.y + this.height - 4, this.buttonColour);
        ModernGui.drawRect(this.x + this.width - 21, this.y + this.height - 6, this.x + this.width - 14, this.y + this.height - 4, this.buttonColour);
    }
    
}
