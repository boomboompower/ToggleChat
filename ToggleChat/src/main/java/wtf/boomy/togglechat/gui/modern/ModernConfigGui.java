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

package wtf.boomy.togglechat.gui.modern;

import wtf.boomy.mods.modernui.uis.ChatColor;
import wtf.boomy.mods.modernui.uis.ModernGui;
import wtf.boomy.togglechat.utils.uis.ToggleChatModernUI;
import wtf.boomy.togglechat.utils.uis.components.tc.ToggleChatButtonComponent;

public class ModernConfigGui extends ToggleChatModernUI {

    private final ModernGui previous;

    private boolean modified;

    public ModernConfigGui(ModernGui previous) {
        this.previous = previous;
        this.modified = false;
    }

    @Override
    public void onGuiOpen() {
        // Blur settings, turn this on for worse performance but with hotter UIs
        registerElement(new ToggleChatButtonComponent(1, this.width / 2 - 75, this.height / 2 - 34, 150, 20, "Blur: " + getStatus(this.configLoader.isModernBlur()), button -> {
            this.modified = true;
    
            this.configLoader.setModernBlur(!this.configLoader.isModernBlur());
            button.setText("Blur: " + getStatus(this.configLoader.isModernBlur()));
            this.mod.getBlurModHandler().reloadBlur(this.mc.currentScreen);
        }).setButtonData(
                "Toggles Gaussian bluring", "&aOn&r or &cOff&r", "on all our menus", "", "Created by &6tterrag1098", "for the BlurMC mod"
        ).setDrawingModern(this.modernButton));
        
        // Toggles the style of the buttons for the mod, between one which renders the texture
        // pack style and the other which is literally just a transparent rectangle for a background.
        registerElement(new ToggleChatButtonComponent(2, this.width / 2 - 75, this.height / 2 - 10, 150, 20, "Buttons: " + getClassic(this.configLoader.isModernButton()), button -> {
            this.modified = true;
    
            this.configLoader.setModernButton(!this.configLoader.isModernButton());
            button.setText("Buttons: " + getClassic(this.configLoader.isModernButton()));
            
            display();
        }).setButtonData(
                // Button editing
                "Changes the button", "theme to either", "&6Modern&r or &bClassic", "", "&6Modern&r is see-through", "&bClassic&r is texture based"
        ).setDrawingModern(this.modernButton));
        
        // Toggles the style of the text boxes in the mod, between the one which appears when you're in
        // the command block and once more one which is just a rectangle with extra steps.
        registerElement(new ToggleChatButtonComponent(3, this.width / 2 - 75, this.height / 2 + 14, 150, 20, "Textbox: " + getClassic(this.configLoader.isModernTextbox()), button -> {
            this.modified = true;
    
            this.configLoader.setModernTextbox(!this.configLoader.isModernTextbox());
            button.setText("Textbox: " + getClassic(this.configLoader.isModernTextbox()));
            
            display();
        }).setButtonData(
                // Textbox editing
                "Changes the textbox", "theme to either", "&6Modern&r or &bClassic"
        ).setDrawingModern(this.modernButton));
        
        // Standard button to return to the previous page.
        registerElement(new ToggleChatButtonComponent(4, 5, this.height - 25, 90, 20, "Back", button -> this.mc.displayGuiScreen(this.previous)).setDrawingModern(this.modernButton));
    }
    
    @Override
    public void preRender(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
    }
    
    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        writeInformation(this.width / 2, this.height / 2 + 40, 12,
                "&6Modern&r is our custom theme",
                "featuring cleaner interfaces and game bluring",
                "",
                "&bClassic&r is the default game",
                "theme, usually based on texturepacks"
        );
    }
    
    @Override
    public void postRender(float partialTicks) {
        checkHover(this.height / 2 - 75);
    }

    @Override
    public void onGuiClose() {
        if (!this.modified) {
            return;
        }

        this.configLoader.saveModernUtils();
    }

    private String getClassic(boolean config) {
        return (config ? ChatColor.AQUA + "Classic" : ChatColor.GOLD + "Modern");
    }
}
