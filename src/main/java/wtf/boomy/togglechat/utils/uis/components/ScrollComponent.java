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
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import wtf.boomy.togglechat.utils.options.SimpleCallback;
import wtf.boomy.togglechat.utils.uis.ModernGui;
import wtf.boomy.togglechat.utils.uis.faces.InteractiveUIElement;

import java.awt.Color;

/**
 * A really bad vertical scrolling implementation based on the one in the creative inventory.
 *
 * @author boomboompower
 * @since 3.1.37
 */
public class ScrollComponent implements InteractiveUIElement {
    
    // Use the creative inventory scroll bar
    private static final ResourceLocation creativeInventoryTabs = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
    
    private final int x;
    private final int y;
    
    private final int width;
    private final int height;
    
    private float currentScroll;
    
    private boolean enabled = true;
    private boolean mouseDown = false;
    
    private final SimpleCallback<ScrollComponent> callback;
    
    public ScrollComponent(int xPos, int yPos, int width, int height) {
        this(xPos, yPos, width, height, null);
    }
    
    public ScrollComponent(int xPos, int yPos, int width, int height, SimpleCallback<ScrollComponent> scrollCallback) {
        this.x = xPos;
        this.y = yPos;
        
        this.width = width;
        this.height = height;
        
        this.callback = scrollCallback;
    }
    
    @Override
    public void onLeftClick(int mouseX, int mouseY, float yTranslation) {
        this.mouseDown = true;
    }
    
    @Override
    public void onMouseReleased(int mouseX, int mouseY, float yTranslation) {
        this.mouseDown = false;
    }
    
    @Override
    public boolean isInside(int mouseX, int mouseY, float yTranslation) {
        return mouseX >= this.x && mouseX <= this.x + this.width &&
                mouseY >= this.y && mouseY <= (float) this.y + this.height;
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
        if (this.mouseDown) {
            float currentScroll = this.currentScroll;
            
            this.currentScroll = ((float) (mouseY - this.y) - 7.5F) / ((float) (this.y + this.height) - 15.0F);
            this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);
    
            if (this.callback != null && currentScroll != this.currentScroll) {
                this.callback.run(this);
            }
        }
    
        Minecraft mc = Minecraft.getMinecraft();
        
        mc.getTextureManager().bindTexture(creativeInventoryTabs);
    
        ModernGui.drawRect(this.x - 3, this.y - 3, this.x + this.width + 2, this.y + this.height + 2, new Color(255, 255, 255, 50).getRGB());
        ModernGui.drawTexturedModalRectS(this.x, this.y + (int) ((float) (this.height - 15) * this.currentScroll), 232, 0, 12, 15);
        ModernGui.drawRectangleOutlineF(this.x - 3, this.y - 3, this.x + this.width + 2, this.y + this.height + 2, Color.WHITE.getRGB());
    }
    
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public ScrollComponent setEnabled(boolean enabled) {
        this.enabled = enabled;
        
        return this;
    }
    
    @Override
    public boolean isTranslatable() {
        return false;
    }
    
    /**
     * Returns the current scroll position as a value between 0 and 1.
     *
     * @return the current scroll between 0 and 1
     */
    public float getCurrentScroll() {
        return this.currentScroll;
    }
    
    /**
     * Triggered when a scroll has been detected and this should interpret it.
     *
     * Nothing will happen if the input is 0. The callback will be run if a value change is detected.
     *
     * @param i use a value > 0 when scrolling up and use a value < 0 when scrolling down.
     */
    public void onScroll(int i) {
        if (i == 0) return;
        
        float currentScroll = this.currentScroll;
        
        this.currentScroll = (float) ((double) this.currentScroll - (double) i / 10);
        this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);
        
        if (this.callback != null && currentScroll != this.currentScroll) {
            this.callback.run(this);
        }
    }
}
