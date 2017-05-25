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
import me.boomboompower.all.togglechat.utils.Writer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
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

        private int pageNumber;
        private Minecraft mc;

        public ToggleChatMainGui(int pageNumber) {
            this.pageNumber = pageNumber;

            this.mc = Minecraft.getMinecraft();
        }

        public void initGui() {
            createButtons();
        }

        private void createButtons() {
            buttonList.clear();
            if (pageNumber == 0) {
                this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height / 2 - 75, 150, 20, "Team: " + (Options.showTeam ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
                this.buttonList.add(new GuiButton(1, this.width / 2 - 75, this.height / 2 - 51, 150, 20, "Join: " + (Options.showJoin ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
                this.buttonList.add(new GuiButton(2, this.width / 2 - 75, this.height / 2 - 27, 150, 20, "Leave: " + (Options.showLeave ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
                this.buttonList.add(new GuiButton(3, this.width / 2 - 75, this.height / 2 - 3, 150, 20, "Guild: " + (Options.showGuild ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
                this.buttonList.add(new GuiButton(4, this.width / 2 - 75, this.height / 2 + 21, 150, 20, "Party: " + (Options.showParty ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
                this.buttonList.add(new GuiButton(5, this.width / 2 - 75, this.height / 2 + 45, 150, 20, "Shout: " + (Options.showShout ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
                this.buttonList.add(new GuiButton(6, this.width / 2 - 75, this.height / 2 + 69, 150, 20, "Messages: " + (Options.showMessage ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            } else {
                this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height / 2 - 75, 150, 20, "UHC: " + (Options.showUHC ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
                this.buttonList.add(new GuiButton(1, this.width / 2 - 75, this.height / 2 - 51, 150, 20, "Friend requests: " + (Options.showFriendReqs ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
                this.buttonList.add(new GuiButton(2, this.width / 2 - 75, this.height / 2 - 27, 150, 20, "Party invites: " + (Options.showPartyInv ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
                this.buttonList.add(new GuiButton(3, this.width / 2 - 75, this.height / 2 - 3, 150, 20, "Spectator: " + (Options.showSpec ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
                this.buttonList.add(new GuiButton(4, this.width / 2 - 75, this.height / 2 + 21, 150, 20, "Colored team: " + (Options.showColored ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
                this.buttonList.add(new GuiButton(5, this.width / 2 - 75, this.height / 2 + 45, 150, 20, "Housing: " + (Options.showHousing ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
                this.buttonList.add(new GuiButton(6, this.width / 2 - 75, this.height / 2 + 69, 150, 20, "Separators: " + (Options.showSeparators ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            }

            // Register other buttons
            this.buttonList.add(new GuiButton(7, 5, this.height - 25, 75, 20, "Whitelist"));
            this.buttonList.add(new GuiButton(8, this.width - 135, this.height - 25, 65, 20, "Back"));
            this.buttonList.add(new GuiButton(9, this.width - 70, this.height - 25, 65, 20, "Next"));

            if (ToggleChat.tutorialEnabled) this.buttonList.add(new GuiButton(10, this.width / 2 - 70, this.height - 25, 140, 20, "Tutorial"));
        }

        public void drawScreen(int x, int y, float ticks) {
            super.drawDefaultBackground();
            drawCenteredString(this.fontRendererObj, String.format("Page %s", (pageNumber + 1)), this.width / 2, this.height / 2 - 94, Color.WHITE.getRGB());

            buttonList.get(8).enabled = pageNumber == 1; // Back
            buttonList.get(9).enabled = pageNumber == 0; // Next

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
            if (pageNumber == 0) {
                switch (button.id) {
                    case 0:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_TEAM);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_TEAM);
                        break;
                    case 1:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_JOIN);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_JOIN);
                        break;
                    case 2:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_LEAVE);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_LEAVE);
                        break;
                    case 3:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_GUILD);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_GUILD);
                        break;
                    case 4:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_PARTY);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_PARTY);
                        break;
                    case 5:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_SHOUT);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_SHOUT);
                        break;
                    case 6:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_MESSAGE);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_MESSAGE);
                        break;
                }
            } else {
                switch (button.id) {
                    case 0:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_UHC);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_UHC);
                        break;
                    case 1:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_FRIENDREQ);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_FRIENDREQ);
                        break;
                    case 2:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_PARTYINV);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_PARTYINV);
                        break;
                    case 3:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_SPECTATOR);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_SPECTATOR);
                        break;
                    case 4:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_COLORED_TEAM);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_COLORED_TEAM);
                        break;
                    case 5:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_HOUSING);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_HOUSING);
                        break;
                    case 6:
                        Options.getInstance().toggle(Options.ToggleType.CHAT_SEPARATOR);
                        Options.getInstance().updateButton(button, Options.ToggleType.CHAT_SEPARATOR);
                        break;
                }
            }

            switch (button.id) {
                case 7:
                    new WhitelistGui.WhitelistMainGui().display();
                    break;
                case 8:
                    if (pageNumber > 0) {
                        new ToggleChatMainGui(pageNumber--);
                        createButtons();
                    } else {
                        mc.displayGuiScreen(null);
                    }
                    break;
                case 9:
                    new ToggleChatMainGui(pageNumber++);
                    createButtons();
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
        }

        public void onGuiClosed() {
            Writer.execute(false);
        }

        public boolean doesGuiPauseGame() {
            return false;
        }

        public void display() {
            FMLCommonHandler.instance().bus().register(this);
        }

        @SubscribeEvent
        public void ClientTickEvent(TickEvent.ClientTickEvent event) {
            FMLCommonHandler.instance().bus().unregister(this);
            mc.displayGuiScreen(this);
        }
    }
}