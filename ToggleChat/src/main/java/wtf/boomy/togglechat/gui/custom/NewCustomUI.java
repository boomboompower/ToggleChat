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

import wtf.boomy.mods.modernui.uis.ChatColor;
import wtf.boomy.mods.modernui.uis.ModernGui;
import wtf.boomy.mods.modernui.uis.components.ButtonComponent;
import wtf.boomy.mods.modernui.uis.components.LabelComponent;
import wtf.boomy.mods.modernui.uis.components.ScrollComponent;
import wtf.boomy.mods.modernui.uis.components.TextBoxComponent;
import wtf.boomy.togglechat.ToggleChatMod;
import wtf.boomy.togglechat.toggles.ToggleBase;
import wtf.boomy.togglechat.toggles.custom.ConditionType;
import wtf.boomy.togglechat.toggles.custom.CustomToggle;
import wtf.boomy.togglechat.toggles.custom.ToggleCondition;
import wtf.boomy.togglechat.toggles.custom.conditions.*;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;

/**
 * A layout for the custom toggle menu
 *
 * @author boomboompower
 * @since 3.1.39
 */
public class NewCustomUI extends ModernGui {
    
    // The backdrop colour is the colour we use behind the left panel
    private final int backdropColour = new Color(0, 0, 0, 100).getRGB();
    // The line colour is the colour of the vertical line which is rendered 1 third of the viewport from the left.
    private final int lineColour = new Color(200, 200, 200, 200).getRGB();
    
    // Stores the cached backdrop width (so we don't have to do as many calcs per frame).
    private int backDropWidth = 0;
    // Stores the final Y position of the elements drawn on the left of the screen
    private int finalYPos;
    
    // If this is true then the config will be overwritten when the UI closes.
    private boolean changesMade = false;
    
    // We want to store the active scrolling component
    // so we can retrieve it's scrolling values :)
    private ScrollComponent scrollComponent = null;
    
    // The previous GUI
    private ModernGui previousGUI;
    
    // Currently editing
    private CustomToggle customToggle;
    private ToggleCondition customCondition;
    
    // Condition editing
    private ToggleCondition condition;
    
    public NewCustomUI(ModernGui previous) {
        this.previousGUI = previous;
    }
    
    @Override
    public void onGuiOpen() {
        boolean buttonModern = ToggleChatMod.getInstance().getConfigLoader().isModernButton();
        boolean textboxModern = ToggleChatMod.getInstance().getConfigLoader().isModernTextbox();
    
        // We want to use half the font height to correctly position
        // most of the label's we'll draw onto the screen.
        int halfFontHeight = this.fontRendererObj.FONT_HEIGHT / 2;
    
        // The backdrop width is 1 third of the current view window.
        this.backDropWidth = (this.width / 3);
    
        // Start of at y position 10 (which is where we will render the title).
        int yPos = 10;
    
        // Collect all the currently registered toggles for the mod.
        Collection<ToggleBase> toggles = this.customToggle == null ? ToggleChatMod.getInstance().getToggleHandler().getCustomToggles().values() : Collections.singleton(this.customToggle);
    
        // Displays the theme editor menu.
        if (this.previousGUI != null) {
            registerElement(new ButtonComponent(2, this.width - 105, this.height - 25, 75, 20, "Go Back", btn -> {
                this.previousGUI.display();
            }).setDrawingModern(buttonModern));
        }
        
        // Iterate through all the different category types for toggles.
        for (ToggleBase base : toggles) {
            if (!(base instanceof CustomToggle)) {
                continue;
            }
            
            CustomToggle customToggle = (CustomToggle) base;
            
            StringBuilder headerName = new StringBuilder();
            
            if (this.customToggle == base) {
                headerName.append(ChatColor.AQUA);
            } else {
                headerName.append("[");
                headerName.append(customToggle._getConditions().size());
                headerName.append("] ");
                
                headerName.append(ChatColor.YELLOW);
            }
            
            headerName.append(ChatColor.BOLD);
            headerName.append(customToggle.getName());
            
            String headerText = headerName.toString().trim();
            
            if (10 + this.fontRendererObj.getStringWidth(headerText) > this.backDropWidth - 35) {
                headerText = this.fontRendererObj.trimStringToWidth(headerText, this.backDropWidth - 45) + "...";
            }
            
            LabelComponent headerComponent = new LabelComponent(10, yPos + halfFontHeight, headerText);
            
            // Register a label containing the category name in bold.
            registerElement(headerComponent);
    
            if (this.customToggle != base) {
                registerElement(new ButtonComponent(-2, this.backDropWidth - 22, yPos, 16, 16, "\u2699", btn -> {
                    btn.setEnabled(false);
        
                    this.customToggle = customToggle;
        
                    display();
                }).setDrawingModern(buttonModern).enableTranslatable());
    
                yPos += 5;
            } else {
                registerElement(new ButtonComponent(-2, this.backDropWidth - 22, yPos, 16, 16, "\u2190", btn -> {
                    btn.setEnabled(true);
        
                    this.customToggle = null;
        
                    display();
                }).setDrawingModern(buttonModern).enableTranslatable());
    
                registerElement(new ButtonComponent(-1, this.backDropWidth - 40, yPos, 16, 16, "\u002B", btn -> {
                    btn.setEnabled(false);
        
                    String newHeaderText = ChatColor.AQUA.toString() + ChatColor.BOLD + customToggle.getName();
        
                    if (10 + this.fontRendererObj.getStringWidth(newHeaderText) > this.backDropWidth - 35) {
                        newHeaderText = this.fontRendererObj.trimStringToWidth(newHeaderText, this.backDropWidth - 45) + "...";
                    }
        
                    this.customToggle = customToggle;
        
                    headerComponent.setText(newHeaderText);
        
                    display();
                }).setDrawingModern(buttonModern).setEnabled(customToggle != this.customToggle).enableTranslatable());
    
                // Move down from the category name
                yPos += 20;
    
                for (ToggleCondition condition : customToggle._getConditions()) {
                    // Adds the label for the toggle based on the name
                    // TODO create a dedicated method in the toggle class containing
                    //      to separate the internal id for the toggle and the actual display name.
                    registerElement(new LabelComponent(20, yPos + halfFontHeight, condition.getText()));
                    
                    // Adds the button for toggling stuff
                    registerElement(new ButtonComponent(0, this.backDropWidth - 20, yPos + (halfFontHeight / 2), 12, 12, "\u270F", box -> {
                        // Indicate that the config
                        // will need to be saved.
                        this.changesMade = true;
    
                        editCondition(customToggle, condition);
                    }).setDrawingModern(buttonModern).setMessageLines(Collections.singletonList("Edit this")).enableTranslatable());
    
                    registerElement(new ButtonComponent(0, this.backDropWidth - 38, yPos + (halfFontHeight / 2), 12, 12, "\u2715", box -> {
                        deleteCondition(customToggle, condition);
                    }).setDrawingModern(buttonModern).setMessageLines(Collections.singletonList("Delete this")).enableTranslatable());
        
                    // Move down the window
                    yPos += 20;
                }
            }
        
            // Move further down the window.
            // This separates the categories from each other.
            yPos += 15;
        }
        
        TextBoxComponent textBox = new TextBoxComponent(1, this.width / 2 - this.width / 8, this.height - 44, this.width / 4, 20, "Toggle Name").setDrawingModern(textboxModern);
    
        if (this.customToggle == null) {
            registerElement(textBox);
    
            registerElement(new ButtonComponent(2, this.width / 2 + this.width / 8 + 5, this.height - 44, 20, 20, "+", button -> {
                if (textBox.getText().trim().isEmpty()) {
                    return;
                }
        
                // Tell the UI to save when it's closed
                this.changesMade = true;
        
                // Create a new custom toggle
                CustomToggle customToggle = new CustomToggle(textBox.getText().trim());
        
                // Needs to be saved regardless
                customToggle.markDirty();
        
                ToggleChatMod.getInstance().getToggleHandler().addToggle(customToggle);
        
                // Reopen the menu
                display();
            }).setDrawingModern(buttonModern));
        }
        
        this.finalYPos = yPos;
    
        // Adds the scrollbar which modifies the y translation on the page.
        registerElement(this.scrollComponent = new ScrollComponent(this.width - 20, 5, 12, this.height - 10, component -> {
            this.yTranslation = -(this.finalYPos - this.height) * this.scrollComponent.getCurrentScroll();
        }));
        
        if (this.customToggle != null) {
            addCustomToggleButtons(buttonModern, textboxModern, this.customCondition != null);
        }
    }
    
    private void addCustomToggleButtons(boolean modernButton, boolean modernTextbox, boolean existing) {
        int middleX = (this.width / 3 * 2) - 10;
    
        final ConditionType[] type = {ConditionType.EMPTY};
        
        if (existing) {
            type[0] = this.customCondition.getConditionType();
        }
        
        String label = ChatColor.YELLOW.toString() + "Editing " + this.customToggle.getName();
    
        // Editing Label
        registerElement(new LabelComponent(middleX - (this.fontRendererObj.getStringWidth(label) / 2), 25, label).disableTranslatable());
        
        // Text box
        TextBoxComponent componentValue = new TextBoxComponent(-1, middleX - 75, 95, 150, 20, "Text Match").setEnabled(false).setDrawingModern(modernTextbox);
        
        ButtonComponent submitButton = new ButtonComponent(-2, middleX - 60, this.height / 2 + 50, 120, 20, "Submit", btn -> {
            ConditionType current = type[0];
    
            // No text
            if (componentValue.getText().trim().isEmpty()) {
                return;
            }
    
            String chosenText = componentValue.getText();
    
            if (existing) {
                this.customToggle._getConditions().remove(this.customCondition);
            }
    
            handleNewType(chosenText, current);
        }).setDrawingModern(modernButton).setEnabled(false);
        
        ButtonComponent typeButton = new ButtonComponent(69, middleX - 60, 50, 120, 20, type[0].getDisplayText(), btn -> {
            type[0] = type[0].next();
            
            ConditionType current = type[0];
            
            componentValue.setEnabled(current != ConditionType.EMPTY);
            componentValue.setMaxStringLength(current.isUsesIndex() ? 2 : 128);
            componentValue.setText(componentValue.getText());
            
            submitButton.setEnabled(current != ConditionType.EMPTY);
            
            btn.setText(current.getDisplayText());
        }).setDrawingModern(modernButton);
        
        if (existing) {
            ConditionType current = type[0];
            
            componentValue.setEnabled(current != ConditionType.EMPTY);
            componentValue.setMaxStringLength(current.isUsesIndex() ? 2 : 128);
            componentValue.setText(this.customCondition.getText());
    
            submitButton.setEnabled(current != ConditionType.EMPTY);
        }
        
        registerElement(componentValue);
        registerElement(typeButton);
        registerElement(submitButton);
        
        registerElement(new LabelComponent(middleX - 75, 83, "Matching Text").disableTranslatable());
    }
    
    private void handleNewType(String chosenText, ConditionType current) {
        if (current.isUsesIndex() && !isStringANumber(chosenText)) {
            System.err.println("Unable to handle new type, expected a index [as a number] but a number was not given!");
        
            return;
        }
        
        ToggleCondition condition = null;
        
        switch (current) {
            case ISLETTER:
                condition = new ConditionIsLetter(chosenText);
                break;
            case CHARACTERAT:
                condition = new ConditionCharacterAt(chosenText, 0);
                break;
            case ISNUMBER:
                condition = new ConditionIsNumber(chosenText);
                break;
            case REGEX:
                condition = new ConditionRegex(chosenText);
                break;
            case EQUALS:
                condition = new ConditionEquals(chosenText);
                break;
            case CONTAINS:
                condition = new ConditionContains(chosenText);
                break;
            case STARTSWITH:
                condition = new ConditionStartsWith(chosenText);
                break;
            case ENDSWITH:
                condition = new ConditionEndsWith(chosenText);
                break;
        }
        
        if (condition != null) {
            this.changesMade = true;
            
            this.customToggle._addCondition(condition);
            this.customToggle.markDirty();
            
            this.customToggle = null;
            
            display();
        }
    }
    
    private void deleteCondition(CustomToggle customToggle, ToggleCondition condition) {
        new NewCustomDelete(this, customToggle, condition).display();
    }
    
    private void editCondition(CustomToggle customToggle, ToggleCondition condition) {
        this.customCondition = condition;
        this.customToggle = customToggle;
        
        display();
    }
    
    @Override
    public void preRender(int mouseX, int mouseY, float partialTicks) {
        // Draw the default background as always.
        drawDefaultBackground();
        
        // Draw the left strip background and vertical line.
        ModernGui.drawRect(0, 0, this.backDropWidth, this.height, this.backdropColour);
        ModernGui.drawVerticalLine_(this.backDropWidth, 0, this.height, this.lineColour);
    }
    
    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
    
    }
    
    @Override
    public void onScrollUp() {
        // When we scroll up we want to tell the scroll
        // component that the mouse has moved up.
        if (this.scrollComponent != null) {
            this.scrollComponent.onScroll(1);
        }
    }
    
    @Override
    public void onScrollDown() {
        // Similar to above, when we scroll down we
        // want to send this to the scroll component so
        // it can dictate how much the scrollbar should
        // be moved, which will be sent back here and interpreted
        // as a yTranslation on this menu.
        if (this.scrollComponent != null) {
            this.scrollComponent.onScroll(-1);
        }
    }
    
    @Override
    public void onGuiClose() {
        // If no changes have been made just exit gracefully.
        if (!this.changesMade) {
            return;
        }
        
        // Save all the toggles
        ToggleChatMod.getInstance().getConfigLoader().getToggleInterpreter().saveCustomToggles();
    }
    
    /**
     * Returns true if the incoming string is a number
     *
     * @param input the input string
     * @return true if it's a number!
     */
    private boolean isStringANumber(String input) {
        try {
            Integer.parseInt(input);
            
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}