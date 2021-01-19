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

package wtf.boomy.togglechat.gui.custom;

import wtf.boomy.togglechat.ToggleChatMod;
import wtf.boomy.togglechat.utils.uis.ModernGui;
import wtf.boomy.togglechat.toggles.custom.ToggleCondition;

import java.awt.Color;
import java.util.LinkedList;

/**
 * This class is the base gui for creating a new custom toggle. Its just a user-friendly
 * interface for toggles and such and isn't essential for the creation of new toggles
 */
public class CreateCustomUI extends ModernGui {
    
    private final ModernGui previous;
    
    private String name;
    private final LinkedList<ToggleCondition> conditionList = new LinkedList<>();
    
    public CreateCustomUI(ModernGui previous) {
        this.previous = previous;
    }
    
    @Override
    public void initGui() {
    
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        
        drawCenteredString("This page is still in progress!", this.width / 2, this.height / 2 - 4, Color.WHITE.getRGB());
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.previous);
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }
    
    @Override
    public void onGuiClosed() {
        ToggleChatMod.getInstance().getConfigLoader().saveCustomToggles();
    }
}
