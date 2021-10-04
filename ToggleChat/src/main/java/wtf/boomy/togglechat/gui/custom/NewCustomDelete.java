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

import wtf.boomy.mods.modernui.uis.ChatColor;
import wtf.boomy.mods.modernui.uis.ModernGui;
import wtf.boomy.mods.modernui.uis.components.ButtonComponent;
import wtf.boomy.mods.modernui.uis.components.LabelComponent;
import wtf.boomy.togglechat.ToggleChatMod;
import wtf.boomy.togglechat.toggles.custom.CustomToggle;
import wtf.boomy.togglechat.toggles.custom.ToggleCondition;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class NewCustomDelete extends ModernGui {
    
    // The previous opened menu
    private final NewCustomUI previous;
    
    // the good stuff
    private final CustomToggle customToggle;
    private final ToggleCondition condition;
    
    // The stored delete button
    private ButtonComponent deleteButton = null;
    
    // Protection layer is how many times confirm has to be clicked
    private float protectionLayer = 5;
    
    public NewCustomDelete(NewCustomUI previous, CustomToggle customToggle, ToggleCondition condition) {
        if (previous == null) {
            throw new IllegalArgumentException("Previous menu cannot be null");
        }
        
        this.previous = previous;
        
        this.customToggle = customToggle;
        this.condition = condition;
        
        this.protectionLayer = ThreadLocalRandom.current().nextInt(3, 5);
    }
    
    @Override
    public void onGuiOpen() {
        // I'm at the point of just injecting this into buttons and stuff
        boolean buttonModern = ToggleChatMod.getInstance().getConfigLoader().isModernButton();
        boolean textboxModern = ToggleChatMod.getInstance().getConfigLoader().isModernTextbox();
    
        // We want to use half the font height to correctly position
        // most of the label's we'll draw onto the screen.
        int halfFontHeight = this.fontRendererObj.FONT_HEIGHT / 2;
        
        int currentYPos = this.height / 4 + halfFontHeight;
        
        String header = ChatColor.GOLD.toString() + ChatColor.BOLD + this.customToggle.getName();
        
        String labelConfirm = "Are you sure you'd like to delete the";
        String labelConfirm2 = "following condition from this toggle?";
        
        String type = "Type: " + this.condition.getConditionType().getDisplayText();
        String text = "Text: " + this.condition.getText();
        
        LabelComponent headerComponent = new LabelComponent(this.width / 2 - (this.fontRendererObj.getStringWidth(header) / 2), currentYPos, header);
        currentYPos += 30;
        
        LabelComponent labelConfirmOne = new LabelComponent(this.width / 2 - (this.fontRendererObj.getStringWidth(labelConfirm) / 2), currentYPos, labelConfirm);
        currentYPos += 14;
        LabelComponent labelConfirmTwo = new LabelComponent(this.width / 2 - (this.fontRendererObj.getStringWidth(labelConfirm2) / 2), currentYPos, labelConfirm2);
        currentYPos += 28;
        
        LabelComponent infoComponent = new LabelComponent(this.width / 2 - (this.fontRendererObj.getStringWidth(type) / 2), currentYPos, type);
        currentYPos += 15;
        LabelComponent textComponent = new LabelComponent(this.width / 2 - (this.fontRendererObj.getStringWidth(text) / 2), currentYPos, text);
        
        currentYPos = (this.height / 3) * 2 + halfFontHeight + 20;
        
        ButtonComponent cancelButton = new ButtonComponent(-1, this.width / 2 - 150, currentYPos, 120, 20, "Cancel", (button) -> {
            this.previous.display();
        }).setDrawingModern(textboxModern);
        
        this.deleteButton = new ButtonComponent(-1, this.width / 2 + 30, currentYPos, 120, 20, "Confirm", (button) -> {
            delete();
        }).setDrawingModern(buttonModern).setEnabled(false);
        
        registerElements(Arrays.asList(headerComponent, labelConfirmOne, labelConfirmTwo, infoComponent, textComponent, cancelButton, this.deleteButton));
    }
    
    private void delete() {
        this.customToggle._getConditions().remove(this.condition);
        this.customToggle.markDirty();
        
        this.previous.display();
    }
    
    @Override
    public void preRender(int mouseX, int mouseY, float partialTicks) {
        // Draw the default background as always.
        drawDefaultBackground();
    }
    
    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        this.protectionLayer -= (partialTicks / 20);
        
        if (this.protectionLayer <= 0) {
            this.protectionLayer = 0;
    
            this.deleteButton.setText("Delete");
            this.deleteButton.setEnabled(true);
        } else {
            this.deleteButton.setText("Delete [" + String.format("%.2f", this.protectionLayer) + "s]");
        }
    }
    
    @Override
    public void onGuiClose() {
        // Custom toggle was not modified
        if (!this.customToggle.isDirty()) {
            return;
        }
        
        // Save all the toggles
        ToggleChatMod.getInstance().getConfigLoader().getToggleInterpreter().saveCustomToggles();
    }
}
