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

package me.boomboompower.togglechat.gui.togglechat;

import java.io.IOException;

import java.util.Comparator;

import me.boomboompower.togglechat.gui.custom.CustomToggleMain;
import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.gui.modern.gui.ModernConfigGui;
import me.boomboompower.togglechat.gui.whitelist.WhitelistMainGui;
import me.boomboompower.togglechat.toggles.ToggleBase;
import me.boomboompower.togglechat.toggles.custom.ICustomToggle;
import me.boomboompower.togglechat.toggles.dummy.ToggleDummyMessage;
import me.boomboompower.togglechat.toggles.sorting.SortType;
import me.boomboompower.togglechat.utils.ChatColor;

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

    private boolean favouritesChanged = false;
    private boolean nobuttons = false;
    private boolean changed = false;

    private int pages;
    private int pageNumber;

    private boolean favouring;
    private int favouriteCount;

    private static SortType sortType = SortType.getCurrentSortType();

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

            Comparator<ToggleBase> sorter = sortType.getSorter();

            ToggleBase.getToggles().values().stream().sorted(sorter).skip((this.pageNumber - 1) * 7).limit(7)
                    .forEach(baseType -> {
                        ModernButton button = new ModernButton(0, baseType.getIdString(),
                                this.width / 2 - 75, position[0], 150, 20,
                                String.format(baseType.getDisplayName(), getStatus(baseType.isEnabled())))
                                .setButtonData(baseType);
                        if (baseType instanceof ICustomToggle) {
                            button = button.setEnabledColor(new Color(100, 88, 192, 75)).setDisabledColor(new Color(67, 67, 133, 75));
                        }

                        button.setFavourite(baseType.isFavourite());

                        this.buttonList.add(button);
                        position[0] += 24;
                    });

            this.buttonList.add(new ModernButton(1, "inbuilt_whitelist", 5, this.height - 49, 90, 20, "Whitelist"));
            this.buttonList.add(new ModernButton(2, "inbuilt_back", this.width - 114, this.height - 25, 50, 20, "\u21E6").setEnabled(this.pageNumber > 1));
            this.buttonList.add(new ModernButton(3, "inbuilt_next", this.width - 60, this.height - 25, 50, 20, "\u21E8").setEnabled(this.pageNumber != pages));
            this.buttonList.add(new ModernButton(4, "inbuilt_theme", 5, 5, 20, 20, "\u2699")
                    .setButtonData(
                            // Let them know what this button does
                            new ToggleDummyMessage(
                                    "Opens the",
                                    "&bTheme Modifier&r,",
                                    "containing options which",
                                    "customization the",
                                    "look of the mod"
                            )
                    ));

            String sort_string = "Sort: " + ChatColor.AQUA + sortType.getDisplayName();

            this.buttonList.add(new ModernButton(5, "inbuilt_sort", 5, this.height - 25, 90, 20, sort_string).setButtonData(
                    // Let them know what this button does
                    new ToggleDummyMessage(
                            "Changes the sorting",
                            "the toggles so some,",
                            "are easier to find"
                    )
            ));

            this.buttonList.add(new ModernButton(6, this.width - 114, this.height - 49, 104, 20,
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
            return;
        }
        this.nobuttons = true;
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        drawDefaultBackground();

        if (this.favouring) {
            this.favouriteCount += 2;

            if (this.favouriteCount > 2550) {
                this.favouriteCount = 2550;

                this.favouring = false;
            }
        } else if (this.favouriteCount > 0) {
            this.favouriteCount -= 1;
        }

        if (this.favouriteCount > 0) {
            Color favouriteColor = new Color(255, 170, 0, this.favouriteCount / 10);

            drawString(this.fontRendererObj, "Favourite Added", this.width - this.fontRendererObj.getStringWidth("Favourite Added") - 10, 10, favouriteColor.getRGB());
        }

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
                sortType = SortType.getNextSortType();

                this.mc.displayGuiScreen(new MainGui(this.pageNumber));
                return;
            case 6:
                this.mc.displayGuiScreen(new CustomToggleMain());
                return;
        }

        // Make sure the id is 0 to prevent other buttons being pressed
        if (button.getId() == 0) {
            if (button.hasButtonData()) {
                ToggleBase base = button.getButtonData();

                base.setEnabled(!base.isEnabled());
                button.setText(String.format(base.getDisplayName(), getStatus(base.isEnabled())));

                this.changed = true;
            }
        }
    }

    @Override
    public void rightClicked(ModernButton button) {
        if (!button.hasButtonData()) {
            return;
        }

        ToggleBase base = button.getButtonData();

        base.setFavourite(!base.isFavourite());
        button.setFavourite(base.isFavourite());

        this.favouritesChanged = true;

//            if (base instanceof TypeCustom) {
//                TypeCustom custom = (TypeCustom) base;
//                if (custom.getIdString().equals(button.getButtonId())) {
//                    this.mc.displayGuiScreen(new CustomToggleModify(this, custom));
//                }
//            }
    }

    @Override
    public void onGuiClosed() {
        if (this.changed) {
            this.mod.getConfigLoader().saveToggles();
        }

        if (this.favouritesChanged) {
            this.mod.getConfigLoader().getFavourites().clear();

            for (ToggleBase t : ToggleBase.getToggles().values()) {
                if (t.isFavourite()) {
                    this.mod.getConfigLoader().getFavourites().add(t.getIdString());
                }
            }

            this.mod.getConfigLoader().saveModernUtils();
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
