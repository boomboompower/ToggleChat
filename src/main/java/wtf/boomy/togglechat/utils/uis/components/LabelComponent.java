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

package wtf.boomy.togglechat.utils.uis.components;

import net.minecraft.client.Minecraft;
import wtf.boomy.togglechat.utils.uis.faces.ModernUIElement;

import java.awt.Color;

/**
 * Ah the label component, so primitive yet so fruitful. This component simply renders a string to the screen.
 * That's it. No shenanigans, no bullshit, just rendering text to the screen.
 *
 * The interesting thing is, text is way more performance heavy than simply drawing a quad. Use with care
 *
 * @author boomboompower
 * @since 3.1.27
 */
public class LabelComponent implements ModernUIElement {
    
    // Standard component variables
    private final int x;
    private final int y;
    
    private final int width;
    private final int colour;
    
    private final String text;
    
    /**
     * Takes the x and y positions for where to render the text. Similarly, the exact text for rendering can be chosen
     *
     * @param xPos the x position of the label
     * @param yPos the y position of the label
     * @param text the text to render to the screen
     */
    public LabelComponent(int xPos, int yPos, String text) {
        this(xPos, yPos, text, Color.WHITE.getRGB());
    }
    
    /**
     * Takes the x and y positions for where to render the text. Similarly, the exact text for rendering can be chosen, and so can it's colour.
     *
     * @param xPos the x position of the label
     * @param yPos the y position of the label
     * @param text the text to render to the screen
     * @param colour the color of the text.
     */
    public LabelComponent(int xPos, int yPos, String text, int colour) {
        if (text == null || text.isEmpty()) {
            // TODO screw them over by locking the addElement method after the init has been run :)))))))))))))))))))))
            throw new IllegalStateException("The text of a LabelComponent cannot be null or empty! You are wasting memory. Just register this later instead");
        }
        
        this.x = xPos;
        this.y = yPos;
        
        this.colour = colour;
        this.width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
        
        this.text = text;
    }
    
    @Override
    public int getX() {
        return this.x;
    }
    
    @Override
    public int getY() {
        return this.y;
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public void render(int mouseX, int mouseY, float yTranslation, float partialTicks) {
        // Literally just draw text to the screen, how epic is this!
        Minecraft.getMinecraft().fontRendererObj.drawString(this.text, this.x, this.y, this.colour, false);
    }
    
    @Override
    public boolean isEnabled() {
        return false;
    }
}
