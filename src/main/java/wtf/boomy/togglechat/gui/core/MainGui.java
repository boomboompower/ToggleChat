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

package wtf.boomy.togglechat.gui.core;

import wtf.boomy.togglechat.gui.list.ViewListUI;
import wtf.boomy.togglechat.toggles.Categories;
import wtf.boomy.togglechat.toggles.ToggleBase;
import wtf.boomy.togglechat.toggles.custom.ICustomToggle;
import wtf.boomy.togglechat.toggles.dummy.ToggleDummyMessage;
import wtf.boomy.togglechat.utils.ChatColor;
import wtf.boomy.togglechat.utils.uis.ToggleChatModernUI;
import wtf.boomy.togglechat.utils.uis.gui.ModernConfigGui;
import wtf.boomy.togglechat.utils.uis.impl.ModernButton;
import wtf.boomy.togglechat.utils.uis.impl.ModernTextBox;
import wtf.boomy.togglechat.utils.uis.impl.tc.ToggleChatButton;

import java.awt.Color;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainGui extends ToggleChatModernUI {

    //        - 99
    //        - 75
    //        - 51
    //        - 27
    //        - 3
    //        + 21
    //        + 45
    //        + 69

    private boolean favouritesChanged = false;
    private boolean nothingFound = false;
    private boolean nothingFoundFilter = false;
    private boolean changed = false;

    private int pageNumber;
    private int pagesTotal;
    
//    private ModernTextBox searchBox;

    public MainGui(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public void onGuiOpen() {
        this.nothingFound = false;
        this.nothingFoundFilter = false;
        
        Map<String, ToggleBase> toggles = this.mod.getToggleHandler().getToggles();
    
        // Default to 1. Kind of redundant but oh well.
        this.pagesTotal = 1;
        
        // No toggles were found. Use a different render method!
        if (toggles.values().size() == 0) {
            this.nothingFound = true;
            
            return;
        }
        
        // Only run this if we've actually registered the toggles.
        final int[] position = {this.height / 2 - 75};
    
        final Categories filter = this.configLoader.getCategoryFilter();
        Comparator<ToggleBase> sorter = this.configLoader.getSortType().getSorter();
    
        List<ToggleBase> collectedToggles = toggles.values().stream().sorted(sorter)
                .filter(p -> filter == Categories.ALL || filter == p.getCategory()) // Filter by category!
                .collect(Collectors.toList());
    
        // Figure out how many pages are actually available.
        this.pagesTotal = (int) Math.ceil((double) collectedToggles.size() / 7D);
    
        // If there are no toggles at this point
        // it's probably because of the filter.
        if (collectedToggles.size() == 0) {
            this.nothingFoundFilter = true;
        } else {
            // Choose the page information!
            collectedToggles = collectedToggles.stream()
                    .skip((this.pageNumber - 1) * 7L) // Skip for the different pages
                    .limit(7).collect(Collectors.toList());
        }
    
        // Go back to the first page if out of bounds.
        if (this.pageNumber < 1 || this.pageNumber > this.pagesTotal) {
            this.pageNumber = 1;
        }
    
        // So basically we are iterating through the toggles. Skipping the amount in pages, then limiting how many are shown
        // on each page so we don't show too many at once. This also sorts the buttons by the currently defined sorting mode.
        // Limit the results
        collectedToggles.forEach(baseType -> { // Run the Remote Code Exe... just kidding!
            // Format the display name of the button with its current toggle status.
            String displayName = String.format(baseType.getDisplayName(), getStatus(baseType.isEnabled()));
        
            // Construct the button instance. Use ID 0 to prevent clashes (legacy). Use the callback since it's always unique.
            ToggleChatButton button = new ToggleChatButton(0, this.width / 2 - 75, position[0], 150, 20, displayName, btn -> {
                // Buttons which don't have data shouldn't be toggled.
                if (btn.hasButtonData()) {
                    ToggleBase base = btn.getButtonData();
                
                    base.setEnabled(!base.isEnabled());
                    btn.setText(String.format(base.getDisplayName(), getStatus(base.isEnabled())));
                
                    this.changed = true;
                }
            }).setButtonData(baseType);
        
            // Change the colour for custom toggles.
            if (baseType instanceof ICustomToggle) {
                button = (ToggleChatButton) button.setEnabledColor(new Color(100, 88, 192, 75)).setDisabledColor(new Color(67, 67, 133, 75));
            }
        
            // Favourite it if needed :)
            button.setFavourite(baseType.isFavourite());
        
            // Register the button!
            registerElement(button);
        
            position[0] += 24;
        });
    
        // Opens the list menu
        registerElement(new ToggleChatButton(1, this.width - 114, this.height - 49, 104, 20, "Whitelist", button -> new ViewListUI().display()));
    
        // Switches the current category filter
        registerElement(new ToggleChatButton(2, 5, this.height - 49, 100, 20, "Category: " + ChatColor.AQUA + this.configLoader.getCategoryFilter().getDisplayName(), button -> {
            // Choose the next node
            this.configLoader.setCategoryFilter(this.configLoader.getCategoryFilter().getNextMode());
    
            // Save our settings.
            this.mod.getConfigLoader().saveModernUtils();
            
            // Reset to the first page
            this.mc.displayGuiScreen(new MainGui(1));
        }).setButtonData("Filters the list", "of toggles into", "their respective", "categories.", " ", "Current Mode:", this.configLoader.getCategoryFilter().getDescription()));
        
        // Displays the previous page in the toggle list. Tries to disable itself if there is no prior page.
        registerElement(new ToggleChatButton(3, this.width - 114, this.height - 25, 50, 20, "\u21E6", button -> this.mc.displayGuiScreen(new MainGui(this.pageNumber - 1))).setEnabled(this.pageNumber > 1));
    
        // Displays the next page in the toggle list. Reminder this will skip the 7 * page entries as defined above.
        registerElement(new ToggleChatButton(4, this.width - 60, this.height - 25, 50, 20, "\u21E8", button -> this.mc.displayGuiScreen(new MainGui(this.pageNumber + 1))).setEnabled(this.pageNumber != this.pagesTotal));
    
        // Opens the Theme modifier menu
        registerElement(new ToggleChatButton(5, 5, 5, 20, 20, "\u2699", button -> this.mc.displayGuiScreen(new ModernConfigGui(this))).setButtonData(
                // Let them know what this button does
                "Opens the",
                "&bTheme Modifier&r,",
                "containing options which",
                "customization the",
                "look of the mod"
        ));
    
        // The string for the sorting button.
        String sortingString = "Sort: " + ChatColor.AQUA + this.configLoader.getSortType().getDisplayName();
    
        // Create the sorting button, when it's pressed it'll just reopen the current page.
        registerElement(new ToggleChatButton(6, 5, this.height - 25, 100, 20, sortingString, button -> {
            // Go to the next sorting type!!!
            this.mod.getConfigLoader().setSortType(this.mod.getConfigLoader().getSortType().getNextSortType());
        
            // Save our settings.
            this.mod.getConfigLoader().saveModernUtils();
        
            // Open our page again
            this.mc.displayGuiScreen(new MainGui(this.pageNumber));
        }).setButtonData(getSortingMessage()));
        
//        TODO implement search box!
//        registerElement(this.searchBox = new ModernTextBox(-1, this.width / 2 - 50, this.height - 30, 100, 20));
    }
    
    @Override
    public void preRender(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
    }
    
    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        // If no buttons have been registered we'll let the user know.
        if (this.nothingFoundFilter) {
            drawCenteredString(this.fontRendererObj, "Your filter does not have any toggles!", this.width / 2, this.height / 2 - 50, Color.WHITE.getRGB());
            drawCenteredString(this.fontRendererObj, "Try changing the filter selection", this.width / 2, this.height / 2 - 30, Color.WHITE.getRGB());
            drawCenteredString(this.fontRendererObj, "Or install more toggles matching the " + ChatColor.AQUA + this.configLoader.getCategoryFilter().getDisplayName() + ChatColor.RESET + " category!", this.width / 2, this.height / 2, Color.WHITE.getRGB());
        } else if (this.nothingFound) {
            drawCenteredString(this.fontRendererObj, "An issue occurred whilst loading ToggleChat!", this.width / 2, this.height / 2 - 50, Color.WHITE.getRGB());
            drawCenteredString(this.fontRendererObj, "Buttons have not loaded correctly", this.width / 2, this.height / 2 - 30, Color.WHITE.getRGB());
            drawCenteredString(this.fontRendererObj, "Please reinstall the mod!", this.width / 2, this.height / 2, Color.WHITE.getRGB());
        } else {
            drawCenteredString(this.fontRendererObj, "Page " + this.pageNumber + "/" + this.pagesTotal, this.width / 2, this.height / 2 - 94, Color.WHITE.getRGB());
        }
    }
    
    @Override
    public void postRender(float partialTicks) {
        if (this.nothingFound || this.nothingFoundFilter) return;
    
        // Render the descriptions of the toggles if possible.
        checkHover(this.height / 2 - 75);
    }

    @Override
    public void rightClicked(ModernButton modernButton) {
        if (!(modernButton instanceof ToggleChatButton)) {
            return;
        }
        
        ToggleChatButton button = (ToggleChatButton) modernButton;
        
        if (!button.hasButtonData()) {
            return;
        }

        ToggleBase base = button.getButtonData();
        
        // Patch for non-toggle buttons.
        if (base instanceof ToggleDummyMessage) {
            return;
        }

        // Mark both the button and the base as a favourite .-.
        base.setFavourite(!base.isFavourite());
        button.setFavourite(base.isFavourite());

        this.favouritesChanged = true;
    }

    @Override
    public void onGuiClose() {
        // Save the toggles if something has been changed.
        // this will also run if someone clicks a toggle to disable it, then clicks it again.
        if (this.changed) {
            this.mod.getConfigLoader().saveToggles();
        }

        // Only run if the favourite list has been modified.
        if (this.favouritesChanged) {
            // Clear all the favourites for the mod
            this.mod.getConfigLoader().getFavourites().clear();
    
            // Iterate through each base and add it to the favourite list if
            // it has the respective favourite flag. This can probably be optimized.
            for (ToggleBase base : this.mod.getToggleHandler().getToggles().values()) {
                if (base.isFavourite()) {
                    this.mod.getConfigLoader().getFavourites().add(base.getIdString());
                }
            }

            // Save the modern utils (includes favourites for some reason).
            this.mod.getConfigLoader().saveModernUtils();
        }
    }
    
    @Override
    public void onScrollUp() {
        if (this.nothingFound) {
            return;
        }
        
        // If the current page is not the final page
        // then allow them to go to the page after this one.
        if (this.pageNumber != this.pagesTotal) {
            this.mc.displayGuiScreen(new MainGui(this.pageNumber + 1));
        }
    }
    
    @Override
    public void onScrollDown() {
        if (this.nothingFound) {
            return;
        }
        
        // If the page is not the first page then allow them to
        // go back to the page before the current one.
        if (this.pageNumber > 1) {
            this.mc.displayGuiScreen(new MainGui(this.pageNumber - 1));
        }
    }
    
    /**
     * Creates the description of the dummy message with all the sorting info.
     */
    private ToggleDummyMessage getSortingMessage() {
        ToggleDummyMessage dummyMessage = new ToggleDummyMessage();
        
        dummyMessage.appendLine("Changes the sorting");
        dummyMessage.appendLine("of the toggles so some");
        dummyMessage.appendLine("are easier to find");
        dummyMessage.appendLine(" ");
        
        // Get the current sorting types description.
        String desc = this.mod.getConfigLoader().getSortType().getDescription();

        // Null should always be ignored!
        if (desc != null) {
            // Add the lines to the description. If the description contains newline
            // characters then split against the character and add each individual line.
            // If not, then just append the string as one line.
            if (desc.contains("\n")) {
                for (String line : desc.split("\n")) {
                    dummyMessage.appendLine(line);
                }
            } else {
                dummyMessage.appendLine(desc);
            }
        }
        
        return dummyMessage;
    }
}
