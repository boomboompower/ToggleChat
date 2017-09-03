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

package me.boomboompower.togglechat.gui.togglechat;

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.utils.GuiUtils;
import me.boomboompower.togglechat.gui.whitelist.WhitelistMainGui;
import me.boomboompower.togglechat.toggles.ToggleBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class MainGui extends GuiScreen {

    //        - 99
    //        - 75
    //        - 51
    //        - 27
    //        - 3
    //        + 21
    //        + 45
    //        + 69

    private boolean nobuttons = false;

    private int pageNumber;
    private Minecraft mc;

    public MainGui(int pageNumber) {
        this.pageNumber = pageNumber;

        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        createDefaultButtons();
    }

    private void createDefaultButtons() {
        buttonList.clear();

        setupPage();

        if (ToggleChatMod.getInstance().isTutorialEnabled()) this.buttonList.add(new ModernButton(0, "Inbuilt_Tutorial", this.width / 2 - 70, this.height - 25, 140, 20, "Tutorial"));
    }

    private void setupPage() {
        if (ToggleBase.getToggles().values().size() > 0) {
            this.nobuttons = false;

            int pages = (int) Math.ceil((double) ToggleBase.getToggles().size() / 7D);

            if (this.pageNumber < 1 || this.pageNumber > pages) {
                this.pageNumber = 1;
            }

            final int[] position = {this.height / 2 - 75};

            ToggleBase.getToggles().values().stream().skip((pageNumber - 1) * 7).limit(7).forEach(baseType -> {
                this.buttonList.add(new ModernButton(0, baseType.getName().toLowerCase().replace(" ", "_"), this.width / 2 - 75, position[0], 150, 20, String.format(baseType.getDisplayName(), (baseType.isEnabled() ? GuiUtils.ENABLED : GuiUtils.DISABLED))));
                position[0] += 24;
            });

            ModernButton back;
            ModernButton next;

            this.buttonList.add(new ModernButton(1, "inbuilt_whitelist", 5, this.height - 25, 75, 20, "Whitelist"));
            this.buttonList.add(back = new ModernButton(2, "inbuilt_back", this.width - 135, this.height - 25, 65, 20, "Back"));
            this.buttonList.add(next = new ModernButton(3, "inbuilt_next", this.width - 70, this.height - 25, 65, 20, "Next"));

            back.enabled = pageNumber > 1;
            next.enabled = pageNumber != pages; // Next

            return;
        }
        this.nobuttons = true;
    }

    public void drawScreen(int x, int y, float ticks) {
        super.drawDefaultBackground();

        if (this.nobuttons) {
            drawCenteredString(this.fontRendererObj, "There are no buttons loaded!", this.width / 2, this.height / 2 - 50, Color.WHITE.getRGB());
            drawCenteredString(this.fontRendererObj, "Buttons must've not loaded correctly", this.width / 2, this.height / 2 - 30, Color.WHITE.getRGB());
            drawCenteredString(this.fontRendererObj, "Please contact boomboompower!", this.width / 2, this.height / 2, Color.WHITE.getRGB());
        } else {
            drawCenteredString(this.fontRendererObj, String.format("Page %s/%s", (this.pageNumber), (int) Math.ceil((double) ToggleBase.getToggles().size() / 7D)), this.width / 2, this.height / 2 - 94, Color.WHITE.getRGB());
        }

        super.drawScreen(x, y, ticks);
    }

    protected void keyTyped(char c, int key) {
        if (key == 1) {
            this.mc.displayGuiScreen(null);
        }
    }

    public void updateScreen() {
        super.updateScreen();
    }

    protected void actionPerformed(GuiButton button) {
        int id = ((ModernButton) button).id;

        switch (id) {
            case 1:
                new WhitelistMainGui().display();
                break;
            case 2:
                new MainGui(this.pageNumber--);
                createDefaultButtons();
                break;
            case 3:
                new MainGui(this.pageNumber++);
                createDefaultButtons();
                break;
        }
        if (ToggleChatMod.getInstance().isTutorialEnabled()) {
            try {
                switch (id) {
                    case 0:
                        new me.boomboompower.togglechat.tutorial.MainTutorialGui(this, 0).display();
                        break;
                }
            } catch (Exception ex) {}
        }
        for (ToggleBase base : ToggleBase.getToggles().values()) {
            if (base.getName().toLowerCase().replace(" ", "_").equals(((ModernButton) button).buttonIdName)) {
                base.onClick((ModernButton) button);
                break;
            }
        }
    }

    @Override
    public void onGuiClosed() {
        ToggleChatMod.getInstance().getConfigLoader().saveToggles();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void display() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void ClientTickEvent(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }
}
