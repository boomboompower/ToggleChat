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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import wtf.boomy.togglechat.utils.options.SimpleCallback;
import wtf.boomy.togglechat.utils.uis.ModernGui;
import wtf.boomy.togglechat.utils.uis.faces.InteractiveUIElement;

import java.awt.Color;

/**
 * A BoxedElement is essentially a canvas which on its own does nothing,
 * which is why it requires an implementation before it can be used.
 * It purpose is to display and allow  content to be interacted with.
 *
 * As of writing this, the BoxedElement is a type of {@link InteractiveUIElement} meaning
 * it will respond to user input, such as mouse hovers, mouse clicks etc.
 *
 * @author boomboompower
 * @since 3.1.37
 */
public abstract class BoxedElement implements InteractiveUIElement {
    
    // Typical fields for any element.
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    // The callback for when this is clicked
    private final SimpleCallback<Void> onLeftClicked;
    
    // The background colour
    private final int boxedBackgroundColour = new Color(50, 50, 50, 150).getRGB();
    // The outline colour
    private final int boxedOutlineColour = new Color(160, 160, 255, 220).getRGB();
    
    /**
     * The standard constructor for a boxed element
     *
     * @param xPos the starting x position of the element
     * @param yPos the starting y position of the element
     * @param width the width of the element
     * @param height the height of the element
     * @param onLeftClicked the callback for when this is clicked (can be null)
     */
    public BoxedElement(int xPos, int yPos, int width, int height, SimpleCallback<Void> onLeftClicked) {
        this.x = xPos;
        this.y = yPos;
        
        this.width = width;
        this.height = height;
        
        this.onLeftClicked = onLeftClicked;
    }
    
    /**
     * Returns the display name for this option. This
     * will be rendered underneath the box
     *
     * @return the display name for this boxed element.
     */
    public abstract String getDisplayName();
    
    /**
     * Commands the underlying box to draw what it can!
     *
     * @param hovered true if we are currently hovered
     * @param partialTicks the partial ticks (time since the last render).
     */
    public abstract void drawBoxInner(boolean hovered, float partialTicks);
    
    @Override
    public void render(int mouseX, int mouseY, float yTranslation, float partialTicks) {
        boolean hovered = isInside(mouseX, mouseY, yTranslation);
    
        // Draws the background of the element
        ModernGui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.boxedBackgroundColour);
        
        // Draws the 2px wide outline of the element
        // TODO condense this into 4 draw calls instead of 8
        ModernGui.drawRectangleOutline(this.x, this.y, this.x + this.width, this.y + this.height, hovered ? this.boxedOutlineColour : Color.WHITE.getRGB());
        ModernGui.drawRectangleOutline(this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, hovered ? this.boxedOutlineColour : Color.WHITE.getRGB());
        
        try {
            // Draws the box!
            drawBoxInner(hovered, partialTicks);
        } catch (IllegalArgumentException ex) {
            // If an error occurs (likely from the "Color" class, render it).
            drawCenteredString("Error drawing element", this.x + (this.width / 2), this.y + (this.height / 2) - 10);
            drawCenteredString(ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage(), this.x + (this.width / 2), this.y + (this.height / 2) + 10);
        }
    
        // Renders the corresponding display name for this element.
        drawCenteredString(getDisplayName(), this.x + (this.width / 2), this.y + this.height + 10);
    
    }
    
    /**
     * A reimplementation of the standard drawCenteredString method. It's too good to go without.
     *
     * @param string the string to draw
     * @param x the x position to draw the string at
     * @param y the y position to draw the string at
     */
    protected void drawCenteredString(String string, int x, int y) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    
        // Draw based on the x position and calculated width.
        fontRenderer.drawString(string, x - (fontRenderer.getStringWidth(string) / 2), y, Color.WHITE.getRGB());
    }
    
    @Override
    public boolean isInside(int mouseX, int mouseY, float yTranslation) {
        // Standard check to see if the mouse is in the box.
        // This does not currently support y translations.
        return mouseX >= this.x && mouseX <= this.x + this.width &&
                mouseY >= this.y && mouseY <= this.y + this.height;
    }
    
    @Override
    public void onLeftClick(int mouseX, int mouseY, float yTranslation) {
        // If the callback is not null then we will trigger it here
        if (this.onLeftClicked != null) {
            this.onLeftClicked.run(null);
        }
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
    public boolean isEnabled() {
        return true;
    }
    
    @Override
    public boolean isTranslatable() {
        // We don't want these elements to be translatable
        // even though it would be trivial to add this support.
        return false;
    }
}
