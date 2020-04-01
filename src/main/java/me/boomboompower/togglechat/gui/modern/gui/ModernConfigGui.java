/*
 *     Copyright (C) 2020 Isophene
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

package me.boomboompower.togglechat.gui.modern.gui;

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.dummy.ToggleDummyMessage;
import me.boomboompower.togglechat.utils.ChatColor;

public class ModernConfigGui extends ModernGui {

    private ModernGui previous;

    private boolean modified;

    public ModernConfigGui(ModernGui previous) {
        this.previous = previous;
        this.modified = false;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new ModernButton(1, "blur", this.width / 2 - 75, this.height / 2 - 34, 150, 20, "Blur: " + getStatus(configLoader.isModernBlur())).setButtonData(
                // Blur settings
                new ToggleDummyMessage("Toggles Gaussian bluring", "&aOn&r or &cOff&r", "on all our menus", "", "Created by &6tterrag1098", "for the BlurMC mod")
        ));
        this.buttonList.add(new ModernButton(2, "button", this.width / 2 - 75, this.height / 2 - 10, 150, 20, "Buttons: " + getClassic(configLoader.isModernButton())).setButtonData(
                // Button editing
                new ToggleDummyMessage("Changes the button", "theme to either", "&6Modern&r or &bClassic", "", "&6Modern&r is see-through", "&bClassic&r is texture based")
        ));
        this.buttonList.add(new ModernButton(3, "button", this.width / 2 - 75, this.height / 2 + 14, 150, 20, "Textbox: " + getClassic(configLoader.isModernTextbox())).setButtonData(
                // Textbox editing
                new ToggleDummyMessage("Changes the textbox", "theme to either", "&6Modern&r or &bClassic")
        ));
        this.buttonList.add(new ModernButton(4, 5, this.height - 25, 90, 20, "Back"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);

        writeInformation(this.width / 2, this.height / 2 + 40, 12,
                "&6Modern&r is our custom theme",
                "featuring cleaner interfaces and game bluring",
                "",
                "&bClassic&r is the default game",
                "theme, usually based on texturepacks"
        );

        checkHover(this.height / 2 - 75);
    }

    @Override
    public void buttonPressed(ModernButton button) {
        switch (button.getId()) {
            case 1:
                this.modified = true;

                this.configLoader.setModernBlur(!this.configLoader.isModernBlur());
                button.setText("Blur: " + getStatus(this.configLoader.isModernBlur()));
                this.mod.getBlurModHandler().reload();
                break;
            case 2:
                this.modified = true;

                this.configLoader.setModernButton(!this.configLoader.isModernButton());
                button.setText("Buttons: " + getClassic(this.configLoader.isModernButton()));
                break;
            case 3:
                this.modified = true;

                this.configLoader.setModernTextbox(!this.configLoader.isModernTextbox());
                button.setText("Textbox: " + getClassic(this.configLoader.isModernTextbox()));
                break;
            case 4:
                this.mc.displayGuiScreen(this.previous);
                break;
        }
    }

    @Override
    public void onGuiClosed() {
        if (!this.modified) {
            return;
        }

        this.configLoader.saveModernUtils();
    }

    private String getClassic(boolean config) {
        return (config ? ChatColor.AQUA + "Classic" : ChatColor.GOLD + "Modern");
    }
}
