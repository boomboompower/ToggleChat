/*
 *     Copyright (C) 2017 boomboompower
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

package me.boomboompower.togglechat.gui.whitelist;

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.gui.modern.ModernTextBox;
import me.boomboompower.togglechat.gui.togglechat.MainGui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import org.lwjgl.input.Keyboard;

import java.awt.*;

public class WhitelistMainGui extends ModernGui {

    private ModernTextBox text;
    private String input = "";

    public WhitelistMainGui() {
        this("");
    }

    public WhitelistMainGui(String input) {
        this.input = input;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);

        this.textList.clear();
        this.buttonList.clear();

        this.textList.add(this.text = new ModernTextBox(this.width / 2 - 75, this.height / 2 - 58, 150, 20));

        this.buttonList.add(new ModernButton(1, this.width / 2 - 75, this.height / 2 - 22, 150, 20, "Add"));
        this.buttonList.add(new ModernButton(2, this.width / 2 - 75, this.height / 2 + 2, 150, 20, "Remove"));
        this.buttonList.add(new ModernButton(3, this.width / 2 - 75, this.height / 2 + 26, 150, 20, "Clear"));
        this.buttonList.add(new ModernButton(4, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "List"));

        this.buttonList.add(new ModernButton(10, 5, this.height - 25, 75, 20, "Back"));

        this.text.setText(this.input);
        this.text.setFocused(true);
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, "Whitelist", this.width / 2, this.height / 2 - 82, Color.WHITE.getRGB());
        super.drawScreen(x, y, ticks);
    }

    @Override
    protected void keyTyped(char c, int key) {
        if (key == 1) {
            this.mc.displayGuiScreen(null);
        } else if (Character.isLetterOrDigit(c) || c == '_' || key == 14 || isCool(key)) { // Sorry to anyone who originally used other things
            this.text.textboxKeyTyped(c, key);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.text.updateCursorCounter();
    }

    @Override
    public void buttonPressed(ModernButton button) {
        switch (button.getId()) {
            case 1:
                if (this.text.getText().isEmpty()) {
                    sendChatMessage("No name given!");
                } else if (!whitelistContains(this.text.getText())) {
                    ToggleChatMod.getInstance().getWhitelist().add(this.text.getText());
                    sendChatMessage("Added &6" + this.text.getText() + "&7 to the whitelist!");
                } else {
                    sendChatMessage("The whitelist already contained &6" + this.text.getText() + "&7!");
                }
                break;
            case 2:
                if (this.text.getText().isEmpty()) {
                    sendChatMessage("No name given!");
                } else if (!whitelistContains(this.text.getText())) {
                    sendChatMessage("The whitelist does not contain &6" + this.text.getText() + "&7!");
                } else {
                    removeFromWhitelist(this.text.getText());
                    sendChatMessage("Removed &6" + this.text.getText() + "&7 from the whitelist!");
                }
                break;
            case 3:
                if (!ToggleChatMod.getInstance().getWhitelist().isEmpty()) {
                    new WhitelistClearConfirmationGui(this).display();
                } else {
                    sendChatMessage("The whitelist is already empty!");
                }
                break;
            case 4:
                new WhitelistEntryGui(this, 1).display();
                break;
            case 10:
                new MainGui(1).display();
                break;
        }
    }

    @Override
    public void onGuiClosed() {
        ToggleChatMod.getInstance().getConfigLoader().saveWhitelist();
        Keyboard.enableRepeatEvents(false);
    }
    
    private boolean whitelistContains(String message) {
        boolean contains = false;

        for (String s : ToggleChatMod.getInstance().getWhitelist()) {
            if (s.equalsIgnoreCase(message)) {
                contains = true;
                break;
            }
        }

        return contains;
    }

    private void removeFromWhitelist(String username) {
        for (String s: ToggleChatMod.getInstance().getWhitelist()) {
            if (s.equalsIgnoreCase(username)) {
                ToggleChatMod.getInstance().getWhitelist().remove(s);
                break;
            }
        }
    }

    public void display() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    private boolean isCool(int keyCode) {
        return isKeyComboCtrlA(keyCode) || isKeyComboCtrlC(keyCode) || isKeyComboCtrlX(keyCode) || isKeyComboCtrlV(keyCode);
    }
}
