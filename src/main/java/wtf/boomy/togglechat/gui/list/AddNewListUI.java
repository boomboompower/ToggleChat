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

package wtf.boomy.togglechat.gui.list;

import wtf.boomy.togglechat.utils.uis.ToggleChatModernUI;
import wtf.boomy.togglechat.utils.uis.components.ButtonComponent;
import wtf.boomy.togglechat.utils.uis.ModernGui;
import wtf.boomy.togglechat.utils.ChatColor;

import java.awt.Color;

public class AddNewListUI extends ToggleChatModernUI {

    private ButtonComponent next;

    private ModernGui previousScreen;

    private boolean pageInvalid;
    
    private int pageNumber;
    private int pages;

    public AddNewListUI(ModernGui previous, int pageNumber) {
        this.previousScreen = previous;
        this.pageNumber = pageNumber;
        this.pageInvalid = false;
    }

    @Override
    public void onGuiOpen() {
        if (this.mod.getConfigLoader().getWhitelist().size() > 0) {
            registerElement(new ButtonComponent(0, this.width / 2 - 200, this.height / 2 + 80, 150, 20, "Back"));
            registerElement(this.next = new ButtonComponent(1, this.width / 2 + 50, this.height / 2 + 80, 150, 20, "Next"));
        } else {
            registerElement(new ButtonComponent(0, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "Back"));
        }
    }
    
    @Override
    public void preRender(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
    }
    
    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        drawCenteredString(this.fontRendererObj, "Whitelist Entries", this.width / 2, this.height / 2 - 105, Color.WHITE.getRGB());
        
        if (this.pageInvalid) {
            writeInformation(this.width / 2, this.height / 2 - 50, 20, "There are no entries on the whitelist!", "Insert some words then return to this page!");
            
            return;
        }
    
        if (this.mod.getConfigLoader().getWhitelist().size() > 0 && !this.pageInvalid) {
            drawRect(this.width / 2 - 60, this.height / 2 - 80, this.width / 2 + 60, this.height / 2 + 60, new Color(105, 105, 105, 75).getRGB());
        
            drawHorizontalLine(this.width / 2 - 60, width / 2 + 60, this.height / 2 - 80, Color.WHITE.getRGB());
            drawHorizontalLine(this.width / 2 - 60, width / 2 + 60, this.height / 2 + 60, Color.WHITE.getRGB());
        
            drawVerticalLine(this.width / 2 - 60, this.height / 2 - 80, this.height / 2 + 60, Color.WHITE.getRGB());
            drawVerticalLine(this.width / 2 + 60, this.height / 2 - 80, this.height / 2 + 60, Color.WHITE.getRGB());
        }
    
        if (this.mod.getConfigLoader().getWhitelist().size() > 0) {
        
            int totalEntries = this.mod.getConfigLoader().getWhitelist().size();
        
            this.pages = (int) Math.ceil((double) this.mod.getConfigLoader().getWhitelist().size() / 10D);
        
            if (this.pageNumber < 1 || this.pageNumber > this.pages) {
                writeInformation(this.width / 2, this.height / 2 - 40, 20, String.format(ChatColor.RED + "Invalid page number (%s)", (ChatColor.DARK_RED + String.valueOf(pageNumber) + ChatColor.RED)));
                this.pageInvalid = true;
                
                return;
            }
        
            this.pageInvalid = false;
            this.next.setEnabled(this.pageNumber != this.pages); // Next
        
            drawCenteredString(this.fontRendererObj, String.format("Page %s/%s", (this.pageNumber), this.pages), this.width / 2, this.height / 2 - 95, Color.WHITE.getRGB());
            drawCenteredString(this.fontRendererObj, String.format("There is a total of %s %s on the whitelist!", ChatColor.GOLD + String.valueOf(totalEntries), (totalEntries > 1 ? "entries" : "entry") + ChatColor.RESET), this.width / 2, this.height / 2 + 65, Color.WHITE.getRGB());
        
            final int[] position = {this.height / 2 - 73};
        
            this.mod.getConfigLoader().getWhitelist().stream().skip((this.pageNumber - 1) * 10L).limit(10).forEach(word -> {
                drawCenteredString(this.fontRendererObj, word, this.width / 2, position[0], Color.WHITE.getRGB());
                position[0] += 13;
            });
        
            return;
        }
    
        this.pageInvalid = true;
    }
    
    @Override
    public void postRender(float partialTicks) {
    
    }
    
    @Override
    public void buttonPressed(ButtonComponent button) {
        switch (button.getId()) {
            case 0:
                if (this.pageNumber > 1) {
                    new AddNewListUI(this, this.pageNumber--);
                } else {
                    this.mc.displayGuiScreen(previousScreen);
                }
                break;
            case 1:
                new AddNewListUI(this, this.pageNumber++);
                break;
        }
    }
    
    @Override
    public void onScrollUp() {
        if (this.pageInvalid) {
            return;
        }
        
        if (this.pageNumber > 1) {
            new AddNewListUI(this, this.pageNumber--).display();
        }
    }
    
    @Override
    public void onScrollDown() {
        if (this.pageInvalid) {
            return;
        }
        
        if (this.pageNumber != this.pages) {
            new AddNewListUI(this, this.pageNumber++).display();
        }
    }
}
