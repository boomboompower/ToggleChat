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

import wtf.boomy.togglechat.utils.uis.ModernButton;
import wtf.boomy.togglechat.utils.uis.ModernGui;

import java.awt.Color;

/**
 * A hub for all custom toggles, linking all the "Custom Toggle" guis together in one place.
 */
public class MainCustomUI extends ModernGui {

    @Override
    public void initGui() {
        this.buttonList.add(new ModernButton(0, this.width / 2 - 75, this.height / 2 - 27, 150, 20, "Create a Custom Toggle"));
        this.buttonList.add(new ModernButton(1, this.width / 2 - 75, this.height / 2 - 3, 150, 20, "Modify a Custom Toggle"));
        this.buttonList.add(new ModernButton(2, this.width / 2 - 75, this.height / 2 + 21, 150, 20, "Test a Custom Toggle"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        drawCenteredString("The hub of all Custom Toggles", this.width / 2, this.height / 2 - 51, Color.WHITE.getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void buttonPressed(ModernButton button) {
        switch (button.getId()) {
            case 0:
                this.mc.displayGuiScreen(new CreateCustomUI(this));
                break;
            case 1:
                this.mc.displayGuiScreen(new ToggleCustomUI(this.mod, SelectType.MODIFY));
                break;
            case 2:
                this.mc.displayGuiScreen(new ToggleCustomUI(this.mod, SelectType.TEST));
                break;
        }
    }

    public enum SelectType {
        MODIFY,
        CREATE,
        TEST
    }
}
