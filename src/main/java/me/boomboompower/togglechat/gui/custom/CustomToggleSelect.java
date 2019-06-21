/*
 *     Copyright (C) 2019 boomboompower
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
import me.boomboompower.togglechat.toggles.ToggleBase;
import me.boomboompower.togglechat.toggles.custom.TypeCustom;
import me.boomboompower.togglechat.utils.ChatColor;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.text.WordUtils;

import java.awt.*;
import java.util.Arrays;

public class CustomToggleSelect extends ModernGui {
    
    private ModernButton next;
    
    private CustomToggleMain.SelectType selectType;
    private TypeCustom hovered;
    
    private boolean pageInvalid;
    private int pageNumber;
    
    public CustomToggleSelect(CustomToggleMain.SelectType type) {
        this(type, 1);
    }
    
    public CustomToggleSelect(CustomToggleMain.SelectType type, int pageNumber) {
        this.selectType = type;
        
        this.pageNumber = pageNumber;
        
        if (type == CustomToggleMain.SelectType.CREATE) {
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public void initGui() {
        if (ToggleBase.getToggles().entrySet().stream()
            .anyMatch(e -> e.getValue() instanceof TypeCustom) && !this.pageInvalid) {
            this.buttonList.add(
                new ModernButton(0, this.width / 2 - 200, this.height / 2 + 80, 150, 20, "Back"));
            this.buttonList.add(
                this.next = new ModernButton(1, this.width / 2 + 50, this.height / 2 + 80, 150, 20,
                    "Next"));
            
            this.next.setEnabled(false);
        } else {
            this.buttonList.add(
                new ModernButton(0, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "Back"));
        }
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float ticks) {
        drawDefaultBackground();
        
        drawCenteredString(this.fontRendererObj, String.format("Selecting a custom toggle to %s", ChatColor.GOLD + WordUtils.capitalizeFully(this.selectType.name().toLowerCase())), this.width / 2, this.height / 2 - 110, Color.WHITE.getRGB());
        
        super.drawScreen(mouseX, mouseY, ticks);
        
        if (ToggleBase.getToggles().entrySet().stream()
            .anyMatch(e -> e.getValue() instanceof TypeCustom) && !this.pageInvalid) {
            drawRect(this.width / 2 - 60, this.height / 2 - 80, this.width / 2 + 60,
                this.height / 2 + 60, new Color(105, 105, 105, 75).getRGB());
            
            drawHorizontalLine(this.width / 2 - 60, width / 2 + 60, this.height / 2 - 80, Color.WHITE.getRGB());
            drawHorizontalLine(this.width / 2 - 60, width / 2 + 60, this.height / 2 + 60, Color.WHITE.getRGB());
            
            drawVerticalLine(this.width / 2 - 60, this.height / 2 - 80, this.height / 2 + 60, Color.WHITE.getRGB());
            drawVerticalLine(this.width / 2 + 60, this.height / 2 - 80, this.height / 2 + 60, Color.WHITE.getRGB());
        }
        
        if (ToggleBase.getToggles().entrySet().stream().anyMatch(e -> e.getValue() instanceof TypeCustom)) {
            int totalEntries = (int) ToggleBase.getToggles().entrySet().stream().filter(e -> e.getValue() instanceof TypeCustom).count();
            int pages = (int) Math.ceil((double) ToggleBase.getToggles().entrySet().stream().filter(e -> e.getValue() instanceof TypeCustom).count() / 10D);
            
            if (this.pageNumber < 1 || this.pageNumber > pages) {
                writeInformation(this.width / 2, this.height / 2 - 40, 20, String
                    .format(ChatColor.RED + "Invalid page number (%s)",
                        (ChatColor.DARK_RED + String.valueOf(this.pageNumber) + ChatColor.RED)));
                this.pageInvalid = true;
                return;
            }
            
            this.pageInvalid = false;
            this.next.setEnabled(this.pageNumber < pages); // Next
            
            drawCenteredString(String.format("Page %s/%s", (this.pageNumber), pages), this.width / 2, this.height / 2 - 95, Color.WHITE.getRGB());
            drawCenteredString(String.format("You have a total of %s custom %s!", ChatColor.GOLD + String.valueOf(totalEntries) + ChatColor.RESET, (totalEntries > 1 ? "toggles" : "toggle")), this.width / 2, this.height / 2 + 65, Color.WHITE.getRGB());
            
            final int[] position = {this.height / 2 - 73};
            
            final boolean[] drawHover = {false};
            
            ToggleBase.getToggles().entrySet().stream().filter(e -> e.getValue() instanceof TypeCustom).skip((this.pageNumber - 1) * 10).limit(10).forEach(entry -> {
                int xStart = (this.width / 2 - this.fontRendererObj.getStringWidth(((TypeCustom) entry.getValue())._getName()) / 2) - 2;
                int yStart = position[0] - 2;
                int xEnd = (xStart + this.fontRendererObj.getStringWidth(((TypeCustom) entry.getValue())._getName())) + 4;
                int yEnd = yStart + 13;
                
                if (mouseX >= xStart && mouseY >= yStart && mouseX < xEnd && mouseY < yEnd) {
                    drawCenteredString(entry.getValue().getName(), this.width / 2, position[0], new Color(210, 210, 255).getRGB());
                    drawHover[0] = true;
                    this.hovered = (TypeCustom) entry.getValue();
                } else {
                    drawCenteredString(ChatColor.WHITE + entry.getValue().getName(), this.width / 2, position[0], new Color(255, 255, 255, 255).getRGB());
                }
                
                position[0] += 13;
            });
            
            if (drawHover[0]) {
                drawHoveringText(Arrays.asList(ChatColor.WHITE + "Click to " + ChatColor.GOLD + WordUtils.capitalizeFully(this.selectType.name().toLowerCase()) + ChatColor.WHITE + " this", "custom toggle!" + ChatColor.RESET), mouseX, mouseY, this.fontRendererObj);
            } else {
                this.hovered = null;
            }
            
            return;
        }
        
        writeInformation(this.width / 2, this.height / 2 - 50, 20, "You do not currently have", "any registered custom toggles!");
    }
    
    @Override
    public void buttonPressed(ModernButton button) {
        switch (button.getId()) {
            case 0:
                if (this.pageNumber > 1) {
                    new CustomToggleSelect(this.selectType, this.pageNumber--);
                } else {
                    this.mc.displayGuiScreen(null);
                }
                break;
            case 1:
                new CustomToggleSelect(this.selectType, this.pageNumber++);
                break;
        }
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.hovered != null) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            switch (this.selectType) {
                case TEST:
                    this.mc.displayGuiScreen(new CustomToggleTest(this, this.hovered));
                    return;
                case MODIFY:
                    this.mc.displayGuiScreen(new CustomToggleModify(this, this.hovered));
                    return;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
