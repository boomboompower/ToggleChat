/*
 *     Copyright (C) 2018 boomboompower
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

package me.boomboompower.togglechat.gui.custom;

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.custom.ToggleCondition;

import java.awt.*;
import java.util.ArrayList;

public class CustomToggleCreate extends ModernGui {
    
    private ModernGui previous;
    
    private String name;
    private ArrayList<ToggleCondition> conditionList = new ArrayList<>();
    
    public CustomToggleCreate(ModernGui previous) {
        this.previous = previous;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
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
