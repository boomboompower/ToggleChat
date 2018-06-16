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
import me.boomboompower.togglechat.gui.modern.ModernTextBox;
import me.boomboompower.togglechat.toggles.custom.TypeCustom;
import me.boomboompower.togglechat.utils.ChatColor;

import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * A menu that allows users to test predefined custom toggles with the given arguments.
 *
 * This menu is designed to be simple and not overload the user with information.
 */
public class CustomToggleTest extends ModernGui {

    private ModernTextBox text;

    private TypeCustom custom;
    
    private ModernGui previous;

    public CustomToggleTest(ModernGui previous, TypeCustom customIn) {
        this.previous = previous;
        this.custom = customIn;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        
        this.buttonList.add(new ModernButton(0, 5, this.height - 25, 75, 20, "Back"));
        
        this.textList.add(this.text = new ModernTextBox(this.width / 2 - 150, this.height / 2 - 10, 300, 20));

        this.text.setForceOldTheme(true);
        this.text.setMaxStringLength(1000);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

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

        super.drawScreen(mouseX, mouseY, partialTicks);
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
    public void updateScreen() {
        this.text.updateCursorCounter();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
}
