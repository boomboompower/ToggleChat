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
import wtf.boomy.togglechat.utils.uis.ToggleChatModernUI;
import wtf.boomy.togglechat.utils.uis.impl.ModernButton;
import wtf.boomy.togglechat.toggles.ToggleBase;
import wtf.boomy.togglechat.toggles.custom.TypeCustom;
import wtf.boomy.togglechat.utils.ChatColor;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.text.WordUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ToggleCustomUI extends ToggleChatModernUI {
    
    private final ToggleChatMod mod;
    
    private ModernButton next;
    
    private final MainCustomUI.SelectType selectType;
    private TypeCustom hovered;
    
    private boolean hasCustomToggle;
    private boolean pageInvalid;
    
    private List<Map.Entry<String, ToggleBase>> renderingToggles = new ArrayList<>();
    
    private int pageNumber;
    private int pageTotal;
    private int entriesTotal;
    
    public ToggleCustomUI(ToggleChatMod mod, MainCustomUI.SelectType type) {
        this(mod, type, 1);
    }
    
    public ToggleCustomUI(ToggleChatMod mod, MainCustomUI.SelectType type, int pageNumber) {
        this.mod = mod;
        this.selectType = type;
        
        this.pageNumber = pageNumber;
        
        if (type == MainCustomUI.SelectType.CREATE) {
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public void onGuiOpen() {
        Stream<Map.Entry<String, ToggleBase>> filter = this.mod.getToggleHandler().getToggles().entrySet().stream().filter(e -> e.getValue() instanceof TypeCustom);
    
        this.entriesTotal = (int) filter.count();
        this.hasCustomToggle = this.entriesTotal > 0;
        this.pageTotal = (int) Math.ceil(this.entriesTotal / 7D);
        this.renderingToggles = filter.skip((this.pageNumber - 1) * 10L).limit(10).collect(Collectors.toList());
        
        if (this.hasCustomToggle && !this.pageInvalid) {
            registerElement(new ModernButton(0, this.width / 2 - 200, this.height / 2 + 80, 150, 20, "Back"));
            registerElement(this.next = new ModernButton(1, this.width / 2 + 50, this.height / 2 + 80, 150, 20, "Next"));
            
            this.next.setEnabled(false);
        } else {
            registerElement(new ModernButton(0, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "Back"));
        }
    }
    
    @Override
    public void preRender(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
    }
    
    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        drawCenteredString(this.fontRendererObj, String.format("Selecting a custom toggle to %s", ChatColor.GOLD + WordUtils.capitalizeFully(this.selectType.name().toLowerCase())), this.width / 2, this.height / 2 - 110, Color.WHITE.getRGB());
    
        if (this.hasCustomToggle) {
            if (!this.pageInvalid) {
                drawInvalidPage();
            } else {
                drawEntriesPage(mouseX, mouseY);
            }
        
            return;
        }
    
        writeInformation(this.width / 2, this.height / 2 - 50, 20, "You do not currently have", "any registered custom toggles!");
    
    }
    
    private void drawInvalidPage() {
        drawRect(this.width / 2 - 60, this.height / 2 - 80, this.width / 2 + 60, this.height / 2 + 60, new Color(105, 105, 105, 75).getRGB());
    
        drawHorizontalLine(this.width / 2 - 60, width / 2 + 60, this.height / 2 - 80, Color.WHITE.getRGB());
        drawHorizontalLine(this.width / 2 - 60, width / 2 + 60, this.height / 2 + 60, Color.WHITE.getRGB());
    
        drawVerticalLine(this.width / 2 - 60, this.height / 2 - 80, this.height / 2 + 60, Color.WHITE.getRGB());
        drawVerticalLine(this.width / 2 + 60, this.height / 2 - 80, this.height / 2 + 60, Color.WHITE.getRGB());
    }
    
    private void drawEntriesPage(int mouseX, int mouseY) {
        if (this.pageNumber < 1 || this.pageNumber > this.pageTotal) {
            writeInformation(this.width / 2, this.height / 2 - 40, 20, String
                    .format(ChatColor.RED + "Invalid page number (%s)",
                            (ChatColor.DARK_RED + String.valueOf(this.pageNumber) + ChatColor.RED)));
            this.pageInvalid = true;
            return;
        }
    
        this.pageInvalid = false;
        this.next.setEnabled(this.pageNumber < this.pageTotal); // Next
    
        drawCenteredString(this.fontRendererObj, String.format("Page %s/%s", (this.pageNumber), this.pageTotal), this.width / 2, this.height / 2 - 95, Color.WHITE.getRGB());
        drawCenteredString(this.fontRendererObj, String.format("You have a total of %s custom %s!", ChatColor.GOLD + String.valueOf(this.entriesTotal) + ChatColor.RESET, (this.entriesTotal > 1 ? "toggles" : "toggle")), this.width / 2, this.height / 2 + 65, Color.WHITE.getRGB());
    
        final int[] position = {this.height / 2 - 73};
    
        final boolean[] drawHover = {false};
    
        this.renderingToggles.forEach(entry -> {
            int xStart = (this.width / 2 - this.fontRendererObj.getStringWidth(((TypeCustom) entry.getValue())._getName()) / 2) - 2;
            int yStart = position[0] - 2;
            int xEnd = (xStart + this.fontRendererObj.getStringWidth(((TypeCustom) entry.getValue())._getName())) + 4;
            int yEnd = yStart + 13;
        
            if (mouseX >= xStart && mouseY >= yStart && mouseX < xEnd && mouseY < yEnd) {
                drawCenteredString(this.fontRendererObj, entry.getValue().getName(), this.width / 2, position[0], new Color(210, 210, 255).getRGB());
                drawHover[0] = true;
                this.hovered = (TypeCustom) entry.getValue();
            } else {
                drawCenteredString(this.fontRendererObj, ChatColor.WHITE + entry.getValue().getName(), this.width / 2, position[0], new Color(255, 255, 255, 255).getRGB());
            }
        
            position[0] += 13;
        });
    
        if (drawHover[0]) {
            drawHoveringText(Arrays.asList(ChatColor.WHITE + "Click to " + ChatColor.GOLD + WordUtils.capitalizeFully(this.selectType.name().toLowerCase()) + ChatColor.WHITE + " this", "custom toggle!" + ChatColor.RESET), mouseX, mouseY, this.fontRendererObj);
        } else {
            this.hovered = null;
        }
    }
    
    @Override
    public void buttonPressed(ModernButton button) {
        switch (button.getId()) {
            case 0:
                if (this.pageNumber > 1) {
                    new ToggleCustomUI(this.mod, this.selectType, this.pageNumber--);
                } else {
                    this.mc.displayGuiScreen(null);
                }
                break;
            case 1:
                new ToggleCustomUI(this.mod, this.selectType, this.pageNumber++);
                break;
        }
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.hovered != null) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            switch (this.selectType) {
                case TEST:
                    this.mc.displayGuiScreen(new TestCustomUI(this, this.hovered));
                    return;
                case MODIFY:
                    this.mc.displayGuiScreen(new ModifyCustomUI(this, this.hovered));
                    return;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
