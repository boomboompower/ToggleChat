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
import net.minecraft.client.gui.FontRenderer;
import wtf.boomy.togglechat.utils.ChatColor;
import wtf.boomy.togglechat.utils.options.SimpleCallback;

import java.awt.Color;

/**
 * An extension of the checkbox element to render a string to the screen when the checkbox
 * is being hovered by the client. This is a bit of a hack however it's a lot better than
 * using the dummy toggle as a renderer.
 *
 * @author boomboompower
 * @since 3.1.37
 */
public class CheckboxTextExtensionComponent extends CheckboxComponent {
    
    private final int renderX;
    private final int renderY;
    private final int lineSeparation;
    
    private final String[] toRender;
    
    public CheckboxTextExtensionComponent(int xPos, int yPos, int width, int height, boolean checked, SimpleCallback<CheckboxComponent> callback, int renderX, int renderY, int lineSeparation, String... textToRender) {
        super(xPos, yPos, width, height, checked, callback);
        
        this.toRender = textToRender;
        
        this.lineSeparation = lineSeparation;
        this.renderX = renderX;
        this.renderY = renderY;
    }
    
    @Override
    public void render(int mouseX, int mouseY, float yTranslation, float partialTicks) {
        super.render(mouseX, mouseY, yTranslation, partialTicks);
        
        // Use our preexisting check!
        if (!this.isHovered()) {
            return;
        }
    
        FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;
        int renderingYPosition = (int) (this.renderY - ((this.toRender.length / 2) * this.lineSeparation) - yTranslation);
    
        for (String line : this.toRender) {
            if (line.isEmpty()) {
                renderingYPosition += this.lineSeparation;
                
                continue;
            }
            
            // Colour the text!
            line = ChatColor.translateAlternateColorCodes(line);
            
            renderer.drawString(line, this.renderX, renderingYPosition, Color.WHITE.getRGB(), false);
            
            renderingYPosition += this.lineSeparation;
        }
    }
}
