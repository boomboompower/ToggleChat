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

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.gui.modern.scrollable.ModernGuiToggleList;
import me.boomboompower.togglechat.toggles.custom.TypeCustom;
import me.boomboompower.togglechat.utils.ChatColor;

import java.awt.Color;
import java.io.IOException;

public class CustomToggleModify extends ICustomToggleGui {
    
    private ModernGuiToggleList toggleList;
    
    private TypeCustom custom;
    
    private ModernGui previous;
    
    public CustomToggleModify(ModernGui previous, TypeCustom customIn) {
        this.previous = previous;
        this.custom = customIn;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new ModernButton(0, 5, this.height - 25, 75, 20, "Back"));
        
        this.toggleList = new ModernGuiToggleList(this);
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        
        this.toggleList.drawScreen(mouseX, mouseY, partialTicks);
        
        drawCenteredString("Modifying " + ChatColor.GOLD + this.custom._getName(), this.width / 2, 13, new Color(255, 255, 255).getRGB());
        
        drawCenteredString("Click on the toggle you would like to modify!", this.width / 2, this.height - 17, new Color(255, 255, 255).getRGB());
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        
        this.toggleList.handleMouseInput();
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.toggleList.mouseClicked(mouseX, mouseY, mouseButton)) {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (state != 0 || !this.toggleList.mouseReleased(mouseX, mouseY, state)) {
            super.mouseReleased(mouseX, mouseY, state);
        }
    }
    
    @Override
    public void onGuiClosed() {
        //ToggleChatMod.getInstance().getConfigLoader().saveCustomToggles();
    }
    
    @Override
    public void buttonPressed(ModernButton button) {
        switch (button.getId()) {
            case 0:
                this.mc.displayGuiScreen(this.previous);
                break;
        }
    }
    
    @Override
    public TypeCustom getCustomToggle() {
        return this.custom;
    }
}
