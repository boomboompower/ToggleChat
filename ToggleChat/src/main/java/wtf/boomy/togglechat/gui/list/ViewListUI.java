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

import org.lwjgl.input.Keyboard;

import wtf.boomy.mods.modernui.uis.ModernGui;
import wtf.boomy.mods.modernui.uis.components.ButtonComponent;
import wtf.boomy.mods.modernui.uis.components.TextBoxComponent;
import wtf.boomy.togglechat.utils.uis.ToggleChatModernUI;
import wtf.boomy.togglechat.utils.uis.components.tc.ToggleChatButtonComponent;

import java.awt.Color;
import java.text.Collator;

public class ViewListUI extends ToggleChatModernUI {

    private ButtonComponent add;
    private ButtonComponent remove;
    private ButtonComponent clear;

    private TextBoxComponent text;
    private String input = "";

    private ModernGui previousUI;
    
    public ViewListUI() {
        this("");
    }
    
    public ViewListUI(ModernGui previousUI) {
        this.previousUI = previousUI;
    }

    public ViewListUI(String input) {
        this.input = input;
    }

    @Override
    public void onGuiOpen() {
        Keyboard.enableRepeatEvents(true);
    
        registerElement(this.text = new TextBoxComponent(-1, this.width / 2 - 75, this.height / 2 - 58, 150, 20));

        registerElement(this.add = new ToggleChatButtonComponent(1, this.width / 2 - 75, this.height / 2 - 22, 150, 20, "Add").setButtonData(
                "Adds a word to",
                "the whitelist",
                "",
                "Messages containing",
                "this word will not be",
                "toggled"
        ));
        registerElement(this.remove = new ToggleChatButtonComponent(2, this.width / 2 - 75, this.height / 2 + 2, 150, 20, "Remove").setButtonData(
                "Removes a word from",
                "the whitelist",
                "",
                "Messages containing",
                "this will no longer",
                "be ignored"
        ));
        registerElement(this.clear = new ToggleChatButtonComponent(3, this.width / 2 - 75, this.height / 2 + 26, 150, 20, "Clear").setButtonData(
                "Clears every word",
                "from the whitelist",
                "",
                "This action cannot be",
                "undone, use at your",
                "own peril"
        ));
        registerElement(new ToggleChatButtonComponent(4, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "List").setButtonData(
                "Shows a list of all",
                "word entries in the",
                "whitelist",
                "",
                "Also has a page system",
                "for extra fanciness"
        ));

        registerElement(new ToggleChatButtonComponent(10, 5, this.height - 25, 90, 20, "Back"));

        this.text.setText(this.input);
        this.text.setFocused(true);
    }
    
    @Override
    public void preRender(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
    
        drawCenteredString(this.fontRendererObj, "Whitelist", this.width / 2, this.height / 2 - 82, Color.WHITE.getRGB());
    }
    
    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        this.add.setEnabled(!this.text.getText().isEmpty() && !whitelistContains(this.text.getText()));
        this.remove.setEnabled(!this.text.getText().isEmpty() && whitelistContains(this.text.getText()));
        this.clear.setEnabled(!this.mod.getConfigLoader().getWhitelist().isEmpty());
    }
    
    @Override
    public void postRender(float partialTicks) {
        checkHover(this.height / 2 - 15);
    }
    
    @Override
    public boolean onKeyTyped(int keyCode, char keyCharacter) {
        // Ignore other logic.
        if (keyCode == 1) {
            return false;
        }
        
        // Handles enter keypress.
        if (keyCode == 28) {
            if (this.text.getText().isEmpty()) {
                sendChatMessage("No name given!");
            } else if (whitelistContains(this.text.getText())) {
                sendChatMessage("The whitelist already contained &6" + this.text.getText() + "&7!");
            } else {
                this.mod.getConfigLoader().getWhitelist().add(this.text.getText());
                sendChatMessage("Added &6" + this.text.getText() + "&7 to the whitelist!");
                this.text.setText("");
                this.mod.getConfigLoader().getWhitelist().sort(Collator.getInstance());
            }
            
            return true;
        }
    
        // Sorry to anyone who originally used other things
        if (Character.isLetterOrDigit(keyCharacter) || keyCharacter == '_' || keyCode == 14 || isCool(keyCode) || isCooler(keyCharacter)) {
            this.text.onKeyTyped(keyCharacter, keyCode);
            
            return true;
        }
        
        return false;
    }

    @Override
    public void buttonPressed(ButtonComponent button) {
        switch (button.getId()) {
            case 1:
                if (this.text.getText().isEmpty()) {
                    sendChatMessage("No name given!");
                } else if (whitelistContains(this.text.getText())) {
                    sendChatMessage("The whitelist already contained &6" + this.text.getText() + "&7!");
                } else {
                    this.mod.getConfigLoader().getWhitelist().add(this.text.getText());
                    sendChatMessage("Added &6" + this.text.getText() + "&7 to the whitelist!");
                    this.text.setText("");
                    this.mod.getConfigLoader().getWhitelist().sort(Collator.getInstance());
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
                    this.mod.getConfigLoader().getWhitelist().sort(Collator.getInstance());
                }
                break;
            case 3:
                if (!this.mod.getConfigLoader().getWhitelist().isEmpty()) {
                    new ClearListUI(this).display();
                } else {
                    sendChatMessage("The whitelist is already empty!");
                }
                break;
            case 4:
                new AddNewListUI(this, 1).display();
                break;
            case 10:
                this.previousUI.display();
                break;
        }
    }

    @Override
    public void onGuiClose() {
        this.mod.getConfigLoader().saveModernUtils();
        Keyboard.enableRepeatEvents(false);
    }
    
    private boolean whitelistContains(String word) {
        for (String whitelistWord : this.mod.getConfigLoader().getWhitelist()) {
            if (whitelistWord.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    private void removeFromWhitelist(String word) {
        for (String whitelistWord : this.mod.getConfigLoader().getWhitelist()) {
            if (whitelistWord.equalsIgnoreCase(word)) {
                this.mod.getConfigLoader().getWhitelist().remove(whitelistWord);
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
