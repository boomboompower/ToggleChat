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

import wtf.boomy.togglechat.utils.uis.impl.ModernButton;
import wtf.boomy.togglechat.utils.uis.ModernGui;
import wtf.boomy.togglechat.toggles.custom.TypeCustom;
import wtf.boomy.togglechat.utils.ChatColor;

import java.awt.Color;

public class ModifyCustomUI extends CustomUI {
    
    private final TypeCustom custom;
    
    private final ModernGui previous;
    
    public ModifyCustomUI(ModernGui previous, TypeCustom customIn) {
        this.previous = previous;
        this.custom = customIn;
    }
    
    @Override
    public void onGuiOpen() {
        generateScreen();

        registerElement(new ModernButton(0, 5, this.height - 25, 75, 20, "Back", (button) -> {
            this.mc.displayGuiScreen(this.previous);
        }));
    }
    
    @Override
    public void preRender(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
    }
    
    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        drawCenteredString(this.mc.fontRendererObj, "Modifying " + ChatColor.GOLD + this.custom._getName(), this.width / 2, 13, new Color(255, 255, 255).getRGB());
    
        drawCenteredString(this.mc.fontRendererObj, "Click on the toggle you would like to modify!", this.width / 2, this.height - 17, new Color(255, 255, 255).getRGB());
    }
    
    @Override
    public void onGuiClose() {
        //ToggleChatMod.getInstance().getConfigLoader().saveCustomToggles();
    }
    
    @Override
    public TypeCustom getCustomToggle() {
        return this.custom;
    }

    private void generateScreen() {


        this.custom._getConditions();
    }
}
