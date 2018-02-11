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

package me.boomboompower.togglechat.gui.whitelist;

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.gui.modern.ModernTextBox;
import me.boomboompower.togglechat.gui.togglechat.MainGui;
import me.boomboompower.togglechat.toggles.dummy.ToggleDummyMessage;

import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.text.Collator;

public class WhitelistMainGui extends ModernGui {

    private ModernButton add;
    private ModernButton remove;
    private ModernButton clear;

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

        this.buttonList.add(this.add = new ModernButton(1, this.width / 2 - 75, this.height / 2 - 22, 150, 20, "Add").setButtonData(new ToggleDummyMessage(
                "Adds a word to",
                "the whitelist",
                "",
                "Messages containing",
                "this word will not be",
                "toggled"
        )));
        this.buttonList.add(this.remove = new ModernButton(2, this.width / 2 - 75, this.height / 2 + 2, 150, 20, "Remove").setButtonData(new ToggleDummyMessage(
                "Removes a word from",
                "the whitelist",
                "",
                "Messages containing",
                "this will no longer",
                "be ignored"
        )));
        this.buttonList.add(this.clear = new ModernButton(3, this.width / 2 - 75, this.height / 2 + 26, 150, 20, "Clear").setButtonData(new ToggleDummyMessage(
                "Clears every word",
                "from the whitelist",
                "",
                "This action cannot be",
                "undone, use at your",
                "own peril"
        )));
        this.buttonList.add(new ModernButton(4, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "List").setButtonData(new ToggleDummyMessage(
                "Shows a list of all",
                "word entries in the",
                "whitelist",
                "",
                "Also has a page system",
                "for extra fanciness"
        )));

        this.buttonList.add(new ModernButton(10, 5, this.height - 25, 90, 20, "Back"));

        this.text.setText(this.input);
        this.text.setFocused(true);
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, "Whitelist", this.width / 2, this.height / 2 - 82, Color.WHITE.getRGB());

        this.add.setEnabled(!this.text.getText().isEmpty() && !whitelistContains(this.text.getText()));
        this.remove.setEnabled(!this.text.getText().isEmpty() && whitelistContains(this.text.getText()));
        this.clear.setEnabled(!ToggleChatMod.getInstance().getWhitelist().isEmpty());

        super.drawScreen(x, y, ticks);

        checkHover(this.height / 2 - 15);
    }

    @Override
    protected void keyTyped(char c, int key) {
        if (key == 1) {
            this.mc.displayGuiScreen(null);
        } else if (key == 28) {
            if (this.text.getText().isEmpty()) {
                sendChatMessage("No name given!");
            } else if (whitelistContains(this.text.getText())) {
                sendChatMessage("The whitelist already contained &6" + this.text.getText() + "&7!");
            } else {
                ToggleChatMod.getInstance().getWhitelist().add(this.text.getText());
                sendChatMessage("Added &6" + this.text.getText() + "&7 to the whitelist!");
                this.text.setText("");
                ToggleChatMod.getInstance().getWhitelist().sort(Collator.getInstance());
            }
        } else if (Character.isLetterOrDigit(c) || c == '_' || key == 14 || isCool(key) || isCooler(c)) { // Sorry to anyone who originally used other things
            this.text.textboxKeyTyped(c, key);
        }
    }

    @Override
    public void updateScreen() {
        this.text.updateCursorCounter();
    }

    @Override
    public void buttonPressed(ModernButton button) {
        switch (button.getId()) {
            case 1:
                if (this.text.getText().isEmpty()) {
                    sendChatMessage("No name given!");
                } else if (whitelistContains(this.text.getText())) {
                    sendChatMessage("The whitelist already contained &6" + this.text.getText() + "&7!");
                } else {
                    ToggleChatMod.getInstance().getWhitelist().add(this.text.getText());
                    sendChatMessage("Added &6" + this.text.getText() + "&7 to the whitelist!");
                    this.text.setText("");
                    ToggleChatMod.getInstance().getWhitelist().sort(Collator.getInstance());
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
                    this.text.setText("");
                    ToggleChatMod.getInstance().getWhitelist().sort(Collator.getInstance());
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
    
    private boolean whitelistContains(String word) {
        for (String whitelistWord : ToggleChatMod.getInstance().getWhitelist()) {
            if (whitelistWord.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    private void removeFromWhitelist(String word) {
        for (String whitelistWord : ToggleChatMod.getInstance().getWhitelist()) {
            if (whitelistWord.equalsIgnoreCase(word)) {
                ToggleChatMod.getInstance().getWhitelist().remove(whitelistWord);
                break;
            }
        }
    }

    private boolean isCool(int keyCode) {
        return isKeyComboCtrlA(keyCode) || isKeyComboCtrlC(keyCode) || isKeyComboCtrlX(keyCode) || isKeyComboCtrlV(keyCode);
    }

    private boolean isCooler(char key) {
        return key == '[' || key == ']';
    }
}
