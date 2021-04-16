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

import wtf.boomy.togglechat.utils.options.SimpleCallback;
import wtf.boomy.togglechat.utils.uis.ModernGui;
import wtf.boomy.togglechat.utils.uis.faces.InteractiveUIElement;

import java.awt.Color;

/**
 * This is a simple implementation of a checkbox using my ModernUI element system
 *
 * @author boomboompower
 * @since 3.1.37
 */
public class CheckboxComponent implements InteractiveUIElement {
    
    private boolean enabled = true;
    private boolean checked;
    private boolean hovered;
    
    private int x;
    private int y;
    
    private int width;
    private int height;
    
    private final SimpleCallback<CheckboxComponent> callback;
    
    public CheckboxComponent(int xPos, int yPos, int width, int height, boolean checked) {
        this(xPos, yPos, width, height, checked, null);
    }
    
    public CheckboxComponent(int xPos, int yPos, int width, int height, boolean checked, SimpleCallback<CheckboxComponent> callback) {
        this.x = xPos;
        this.y = yPos;
        
        this.width = width;
        this.height = height;
        
        this.checked = checked;
        this.callback = callback;
    }
    
    @Override
    public void onLeftClick(int mouseX, int mouseY, float yTranslation) {
        if (this.callback != null) {
            // Sweet!
            this.callback.run(this);
        }
    }
    
    @Override
    public boolean isInside(int mouseX, int mouseY, float yTranslation) {
        float yPosition = this.y + yTranslation;
        
        // Simple check, see if the x position of the mouse is inside of our box.
        // And similarly check if the y position is also in our box.
        return mouseX >= this.x && mouseX <= this.x + this.width &&
                mouseY >= yPosition && mouseY <= yPosition + this.height;
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
        this.hovered = isInside(mouseX, mouseY, yTranslation);
        
        int color = new Color(255, 255, 255, 200).getRGB();
        
        if (this.hovered) {
            color = new Color(255, 255, 255, 255).getRGB();
        }
    
        ModernGui.drawRectangleOutline(this.x, this.y, this.x + this.width, this.y + this.height, color);
        
        // The checkbox should be checked!
        if (this.checked) {
            ModernGui.drawRect(this.x + 2, this.y + 2, this.x + this.width - 1, this.y + this.height - 1, color);
        }
    }
    
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public CheckboxComponent setChecked(boolean checked) {
        this.checked = checked;
        
        return this;
    }
    
    public boolean isChecked() {
        return this.checked;
    }
    
    public boolean isHovered() {
        return this.hovered;
    }
}