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

    private static final String WHITELIST_CMD = "/whitelist ";

    //        - 94
    //        - 70
    //        - 46
    //        - 22
    //        + 2
    //        + 26
    //        + 50
    //        + 74

    public static class Settings_1 extends GuiScreen {

        public void initGui() {

            this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height / 2 - 70, 150, 20, "Team: " + (ToggleChat.showTeam ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(1, this.width / 2 - 75, this.height / 2 - 46, 150, 20, "Join: " + (ToggleChat.showJoin ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 75, this.height / 2 - 22, 150, 20, "Leave: " + (ToggleChat.showLeave ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(3, this.width / 2 - 75, this.height / 2 + 2, 150, 20, "Guild: " + (ToggleChat.showGuild ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(4, this.width / 2 - 75, this.height / 2 + 26, 150, 20, "Party: " + (ToggleChat.showParty ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(5, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "Shout: " + (ToggleChat.showShout ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));

            // Register other buttons
            this.buttonList.add(new GuiButton(6, 5, this.height - 25, 50, 20, "Whitelist"));
            this.buttonList.add(new GuiButton(7, this.width - 55, this.height - 25, 50, 20, "Next"));
        }

        public void display() {
            FMLCommonHandler.instance().bus().register(this);
        }

        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            FMLCommonHandler.instance().bus().unregister(this);
            Minecraft.getMinecraft().displayGuiScreen(this);
        }

        public void drawScreen(int x, int y, float ticks) {
            super.drawDefaultBackground();
            drawCenteredString(this.fontRendererObj, "Page 1", this.width / 2, this.height / 2 - 94, Color.WHITE.getRGB());
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
            switch (button.id) {
                case 0:
                    ToggleChat.instance().toggleTeamChat();
                    this.buttonList.get(0).displayString = "Team: " + (ToggleChat.showTeam ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 1:
                    ToggleChat.instance().toggleJoinChat();
                    this.buttonList.get(1).displayString = "Join: " + (ToggleChat.showJoin ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 2:
                    ToggleChat.instance().toggleLeaveChat();
                    this.buttonList.get(3).displayString = "Leave: " + (ToggleChat.showLeave ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 3:
                    ToggleChat.instance().toggleGuildChat();
                    this.buttonList.get(4).displayString = "Guild: " + (ToggleChat.showGuild ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 4:
                    ToggleChat.instance().togglePartyChat();
                    this.buttonList.get(5).displayString = "Party: " + (ToggleChat.showParty ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 5:
                    ToggleChat.instance().toggleShoutChat();
                    this.buttonList.get(6).displayString = "Shout: " + (ToggleChat.showShout ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 6:
                    new WhitelistGui().display();
                    break;
                case 7:
                    mc.displayGuiScreen(new Settings_2());
                    break;
            }
        }

        public void onGuiClosed() {
            Writer.execute(false);
        }

        public boolean doesGuiPauseGame() {
            return false;
        }
    }

    public static class Settings_2 extends GuiScreen {

        public void initGui() {
            // Now the toggles

            this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height / 2 - 70, 150, 20, "Messages: " + (ToggleChat.showMessage ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(1, this.width / 2 - 75, this.height / 2 - 46, 150, 20, "UHC: " + (ToggleChat.showUHC ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 75, this.height / 2 - 22, 150, 20, "Friend requests: " + (ToggleChat.showFriendReqs ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(3, this.width / 2 - 75, this.height / 2 + 2, 150, 20, "Party invites: " + (ToggleChat.showPartyInv ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(4, this.width / 2 - 75, this.height / 2 + 26, 150, 20, "Spectator: " + (ToggleChat.showSpec ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(5, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "Colored team: " + (ToggleChat.showColored ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));

            // Register other buttons
            this.buttonList.add(new GuiButton(6, 5, this.height - 25, 50, 20, "Whitelist"));
            this.buttonList.add(new GuiButton(7, this.width - 55, this.height - 25, 50, 20, "Back"));
        }

        public void display() {
            FMLCommonHandler.instance().bus().register(this);
        }

        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            FMLCommonHandler.instance().bus().unregister(this);
            Minecraft.getMinecraft().displayGuiScreen(this);
        }

        public void drawScreen(int x, int y, float ticks) {
            super.drawDefaultBackground();
            drawCenteredString(this.fontRendererObj, "Page 2", this.width / 2, this.height / 2 - 94, Color.WHITE.getRGB());
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
            switch (button.id) {
                case 0:
                    ToggleChat.instance().toggleMessages();
                    this.buttonList.get(0).displayString = "Messages: " + (ToggleChat.showMessage ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 1:
                    ToggleChat.instance().toggleUHCChat();
                    this.buttonList.get(1).displayString = "UHC: " + (ToggleChat.showUHC ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 2:
                    ToggleChat.instance().toggleFriendReqs();
                    this.buttonList.get(2).displayString = "Friend requests: " + (ToggleChat.showFriendReqs ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 3:
                    ToggleChat.instance().togglePartyInv();
                    this.buttonList.get(3).displayString = "Party invites: " + (ToggleChat.showPartyInv ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 4:
                    ToggleChat.instance().toggleSpecChat();
                    this.buttonList.get(4).displayString = "Spectator: " + (ToggleChat.showSpec ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 5:
                    ToggleChat.instance().toggleColored();
                    this.buttonList.get(5).displayString = "Colored team: " + (ToggleChat.showColored ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 6:
                    new WhitelistGui().display();
                    break;
                case 7:
                    mc.displayGuiScreen(new Settings_1());
                    break;
            }
        }

        public void onGuiClosed() {
            Writer.execute(false);
        }

        public boolean doesGuiPauseGame() {
            return false;
        }
    }

}
