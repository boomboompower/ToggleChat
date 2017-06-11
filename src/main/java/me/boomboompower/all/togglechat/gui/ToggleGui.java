/*
 *     Copyright (C) 2016 boomboompower
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
package me.boomboompower.all.togglechat.gui;

import me.boomboompower.all.togglechat.Options;
import me.boomboompower.all.togglechat.ToggleChat;
import me.boomboompower.all.togglechat.gui.utils.GuiUtils;
import me.boomboompower.all.togglechat.loading.ToggleTypes;
import me.boomboompower.all.togglechat.utils.Writer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class ToggleGui {

    //        - 99
    //        - 75
    //        - 51
    //        - 27
    //        - 3
    //        + 21
    //        + 45
    //        + 69

    public static class ToggleChatMainGui extends GuiScreen {

        private GuiButton whitelist;
        private GuiButton back;
        private GuiButton next;

        private boolean nobuttons = false;

        private int pageNumber;
        private Minecraft mc;

        public ToggleChatMainGui(int pageNumber) {
            this.pageNumber = 50;

            this.mc = Minecraft.getMinecraft();
        }

        @Override
        public void initGui() {
            createDefaultButtons();
        }

        private void createDefaultButtons() {
            buttonList.clear();

            setupPage();

            if (ToggleChat.tutorialEnabled) this.buttonList.add(new GuiButton(10, this.width / 2 - 70, this.height - 25, 140, 20, "Tutorial"));
        }

        private void setupPage() {
            if (Options.getInstance().getBaseTypes().size() > 0) {
                nobuttons = false;

                int pages = (int) Math.ceil((double) Options.getInstance().getBaseTypes().size() / 7D);

                if (pageNumber < 1 || pageNumber > pages) {
                    pageNumber = 1;
                }

                final int[] buttonId = {0};
                final int[] position = {this.height / 2 - 75};

                Options.getInstance().getBaseTypes().values().stream().skip((pageNumber - 1) * 7).limit(7).forEach(baseType -> {
                    this.buttonList.add(new GuiButton(baseType.getId(), this.width / 2 - 75, position[0], 150, 20, String.format(baseType.getDisplayName(), (baseType.isEnabled() ? GuiUtils.ENABLED : GuiUtils.DISABLED))));
                    position[0] += 24;
                });

                this.buttonList.add(this.whitelist = new GuiButton(7, 5, this.height - 25, 75, 20, "Whitelist"));
                this.buttonList.add(this.back = new GuiButton(8, this.width - 135, this.height - 25, 65, 20, "Back"));
                this.buttonList.add(this.next = new GuiButton(9, this.width - 70, this.height - 25, 65, 20, "Next"));

                back.enabled = pageNumber > 1;
                next.enabled = pageNumber != pages; // Next

                return;
            }
            nobuttons = true;
        }

        public void drawScreen(int x, int y, float ticks) {
            super.drawDefaultBackground();

            if (nobuttons) {
                drawCenteredString(this.fontRendererObj, "There are no buttons loaded!", this.width / 2, this.height / 2 - 50, Color.WHITE.getRGB());
                drawCenteredString(this.fontRendererObj, "Buttons must've not loaded correctly", this.width / 2, this.height / 2 - 30, Color.WHITE.getRGB());
                drawCenteredString(this.fontRendererObj, "Please contact boomboompower!", this.width / 2, this.height / 2, Color.WHITE.getRGB());
            } else {
                drawCenteredString(this.fontRendererObj, String.format("Page %s/%s", (pageNumber), (int) Math.ceil((double) Options.getInstance().getBaseTypes().size() / 7D)), this.width / 2, this.height / 2 - 94, Color.WHITE.getRGB());
            }

            super.drawScreen(x, y, ticks);
        }

        protected void keyTyped(char c, int key) {
            if (key == 1) {
                mc.displayGuiScreen(null);
            }
        }

        public void updateScreen() {
            super.updateScreen();
        }

        protected void actionPerformed(GuiButton button) {
            if (button.id == 7 || button.id == 8 || button.id == 9 || button.id == 10) {
                switch (button.id) {
                    case 7:
                        new WhitelistGui.WhitelistMainGui().display();
                        break;
                    case 8:
                        new ToggleChatMainGui(pageNumber--);
                        createDefaultButtons();
                        break;
                    case 9:
                        new ToggleChatMainGui(pageNumber++);
                        createDefaultButtons();
                        break;
                }
                if (ToggleChat.tutorialEnabled) {
                    try {
                        switch (button.id) {
                            case 10:
                                new me.boomboompower.all.togglechat.tutorial.TutorialGui.MainToggleTutorialGui(this, 0).display();
                                break;
                        }
                    } catch (Exception ex) {}
                }
            } else {
                for (ToggleTypes.ToggleBase base : Options.getInstance().getBaseTypes().values()) {
                    if (base.getId() == button.id) {
                        base.onClick(button);
                        break;
                    }
                }
            }
        }

        public void onGuiClosed() {
            Writer.execute(false);
        }

        public boolean doesGuiPauseGame() {
            return false;
        }

        public void display() {
            MinecraftForge.EVENT_BUS.register(this);
        }

        @SubscribeEvent
        public void ClientTickEvent(TickEvent.ClientTickEvent event) {
            MinecraftForge.EVENT_BUS.unregister(this);
            mc.displayGuiScreen(this);
        }
    }
}