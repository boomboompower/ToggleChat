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

import wtf.boomy.togglechat.utils.uis.ToggleChatModernUI;
import wtf.boomy.togglechat.utils.uis.impl.ModernButton;
import wtf.boomy.togglechat.utils.uis.ModernGui;
import wtf.boomy.togglechat.utils.uis.impl.ModernTextBox;
import wtf.boomy.togglechat.toggles.custom.TypeCustom;
import wtf.boomy.togglechat.utils.ChatColor;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

import java.awt.Color;

/**
 * A menu that allows users to test predefined custom toggles with the given arguments.
 *
 * This menu is designed to be simple and not overload the user with information.
 */
public class TestCustomUI extends ToggleChatModernUI {

    private ModernTextBox text;

    private final TypeCustom custom;
    
    private final ModernGui previous;

    public TestCustomUI(ModernGui previous, TypeCustom customIn) {
        this.previous = previous;
        this.custom = customIn;
    }

    @Override
    public void onGuiOpen() {
        Keyboard.enableRepeatEvents(true);
        
        registerElement(new ModernButton(0, 5, this.height - 25, 75, 20, "Back"));
        
        registerElement(this.text = new ModernTextBox(1, this.width / 2 - 150, this.height / 2 - 10, 300, 20));

        this.text.setMaxStringLength(1000);
    }
    
    @Override
    public void preRender(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
    }
    
    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        drawCenteredString(this.fontRendererObj, String.format("Testing %s", ChatColor.GOLD + this.custom._getName()), this.width / 2, this.height / 2 - 85, Color.WHITE.getRGB());
    
        double upSize = 1.5;
    
        GlStateManager.pushMatrix();
        GlStateManager.scale(upSize, upSize, 0);
        if (this.text.getText().isEmpty()) {
            drawCenteredString(this.fontRendererObj, "Write in the box below to test your custom toggle!", (int) ((this.width / 2) / upSize), (int) ((this.height / 2 - 50) / upSize), Color.WHITE.getRGB());
        } else if (this.custom.shouldToggle(this.text.getText())) {
            drawCenteredString(this.fontRendererObj, ChatColor.GREEN + "Will be toggled", (int) ((this.width / 2) / upSize), (int) ((this.height / 2 - 50) / upSize), Color.WHITE.getRGB());
        } else {
            drawCenteredString(this.fontRendererObj, ChatColor.RED + "Will not be toggled", (int) ((this.width / 2) / upSize), (int) ((this.height / 2 - 50) / upSize), Color.WHITE.getRGB());
        }
        GlStateManager.popMatrix();
    }
    
    @Override
    public void buttonPressed(ModernButton button) {
        if (button.getId() == 0) {
            this.mc.displayGuiScreen(this.previous);
        }
    }

    @Override
    public void onGuiClose() {
        Keyboard.enableRepeatEvents(false);
    }
}
