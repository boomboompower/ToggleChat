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

package me.boomboompower.togglechat.gui.togglechat;

import java.io.IOException;

import me.boomboompower.togglechat.gui.custom.CustomToggleMain;
import me.boomboompower.togglechat.gui.custom.CustomToggleModify;
import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.gui.modern.gui.ModernConfigGui;
import me.boomboompower.togglechat.gui.whitelist.WhitelistMainGui;
import me.boomboompower.togglechat.toggles.ToggleBase;
import me.boomboompower.togglechat.toggles.custom.ICustomToggle;
import me.boomboompower.togglechat.toggles.custom.TypeCustom;
import me.boomboompower.togglechat.toggles.dummy.ToggleDummyMessage;

import java.awt.*;
import org.lwjgl.input.Mouse;

public class MainGui extends ModernGui {
    
    //        - 99
    //        - 75
    //        - 51
    //        - 27
    //        - 3
    //        + 21
    //        + 45
    //        + 69
    
    private boolean nobuttons = false;
    private boolean changed = false;
    
    private int pages;
    private int pageNumber;
    
    public MainGui(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    @Override
    public void initGui() {
        if (ToggleBase.getToggles().values().size() > 0) {
            this.nobuttons = false;
        
            this.pages = (int) Math.ceil((double) ToggleBase.getToggles().size() / 7D);
        
            if (this.pageNumber < 1 || this.pageNumber > pages) {
                this.pageNumber = 1;
            }
        
            final int[] position = {this.height / 2 - 75};
        
            ToggleBase.getToggles().values().stream().skip((this.pageNumber - 1) * 7).limit(7)
                .forEach(baseType -> {
                    ModernButton button = new ModernButton(0, baseType.getIdString(),
                        this.width / 2 - 75, position[0], 150, 20,
                        String.format(baseType.getDisplayName(), getStatus(baseType.isEnabled())))
                        .setButtonData(baseType);
                    if (baseType instanceof ICustomToggle) {
                        button = button.setEnabledColor(new Color(100, 88, 192, 75)).setDisabledColor(new Color(67, 67, 133, 75));
                    }
                    this.buttonList.add(button);
                    position[0] += 24;
                });
        
            this.buttonList.add(new ModernButton(1, "inbuilt_whitelist", 5, this.height - 25, 90, 20, "Whitelist"));
            this.buttonList.add(new ModernButton(2, "inbuilt_back", this.width - 114, this.height - 25, 50, 20, "\u21E6").setEnabled(this.pageNumber > 1));
            this.buttonList.add(new ModernButton(3, "inbuilt_next", this.width - 60, this.height - 25, 50, 20, "\u21E8").setEnabled(this.pageNumber != pages));
            this.buttonList.add(new ModernButton(4, "inbuilt_theme", 5, this.height - 49, 90, 20, "Theme Modifier")
                .setButtonData(
                    // Let them know what this button does
                    new ToggleDummyMessage(
                        "Opens the glorious",
                        "&bTheme Modifier&r,",
                        "allowing nearly full",
                        "customization for the",
                        "look of the mod"
                    )
                ));
        
            if (this.mod.getWebsiteUtils().isFlagged()) {
                this.buttonList.add(new ModernButton(5, this.width - 114, this.height - 49, 104, 20,
                    "Custom Toggles").setEnabledColor(new Color(100, 88, 192, 75))
                    .setDisabledColor(new Color(67, 67, 133, 75)).setButtonData(
                        new ToggleDummyMessage(
                            "Allows you to add",
                            "your own custom",
                            "toggles to the mod",
                            "",
                            "This feature is still",
                            "in &cbeta&r and may be",
                            "changed at any time",
                            "",
                            "Brought to you by",
                            "&6OrangeMarshall"
                        )
                    ));
            }
            return;
        }
        this.nobuttons = true;
    }
    
    public void drawScreen(int x, int y, float ticks) {
        drawDefaultBackground();
        
        if (this.nobuttons) {
            drawCenteredString(this.fontRendererObj, "An issue occured whilst loading ToggleChat!", this.width / 2, this.height / 2 - 50, Color.WHITE.getRGB());
            drawCenteredString(this.fontRendererObj, "Buttons have not loaded correctly", this.width / 2, this.height / 2 - 30, Color.WHITE.getRGB());
            drawCenteredString(this.fontRendererObj, "Please reinstall the mod!", this.width / 2, this.height / 2, Color.WHITE.getRGB());
            return;
        } else {
            drawCenteredString(this.fontRendererObj, String.format("Page %s/%s", (this.pageNumber),
                (int) Math.ceil((double) ToggleBase.getToggles().size() / 7D)), this.width / 2,
                this.height / 2 - 94, Color.WHITE.getRGB());
        }
        
        super.drawScreen(x, y, ticks);
        
        checkHover(this.height / 2 - 75);
    }
    
    @Override
    public void buttonPressed(ModernButton button) {
        switch (button.getId()) {
            case 1:
                new WhitelistMainGui().display();
                return;
            case 2:
                this.mc.displayGuiScreen(new MainGui(this.pageNumber - 1));
                return;
            case 3:
                this.mc.displayGuiScreen(new MainGui(this.pageNumber + 1));
                return;
            case 4:
                this.mc.displayGuiScreen(new ModernConfigGui(this));
                return;
            case 5:
                this.mc.displayGuiScreen(new CustomToggleMain());
                return;
        }
        
        // Make sure the id is 0 to prevent other buttons being pressed
        if (button.getId() == 0) {
            for (ToggleBase base : ToggleBase.getToggles().values()) {
                if (base.getIdString().equals(button.getButtonId())) {
                    base.setEnabled(!base.isEnabled());
                    button
                        .setText(String.format(base.getDisplayName(), getStatus(base.isEnabled())));
                    this.changed = true;
                    break;
                }
            }
        }
    }
    
    @Override
    public void rightClicked(ModernButton button) {
        if (this.mod.getWebsiteUtils().isFlagged()) {
            for (ToggleBase base : ToggleBase.getToggles().values()) {
                if (base instanceof TypeCustom) {
                    TypeCustom custom = (TypeCustom) base;
                    if (custom.getIdString().equals(button.getButtonId())) {
                        this.mc.displayGuiScreen(new CustomToggleModify(this, custom));
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    public void onGuiClosed() {
        if (this.changed) {
            this.mod.getConfigLoader().saveToggles();
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        
        if (this.nobuttons) {
            return;
        }
    
        int i = Mouse.getEventDWheel();
    
        if (i < 0 && this.pageNumber > 1) {
            this.mc.displayGuiScreen(new MainGui(this.pageNumber - 1));
        } else if (i > 0 && this.pageNumber != this.pages) {
            this.mc.displayGuiScreen(new MainGui(this.pageNumber + 1));
        }
    }
}
