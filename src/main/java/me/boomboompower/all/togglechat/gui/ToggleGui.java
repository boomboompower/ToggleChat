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
import me.boomboompower.all.togglechat.command.TutorialGui;
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

            this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height / 2 - 70, 150, 20, "Team: " + (Options.showTeam ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(1, this.width / 2 - 75, this.height / 2 - 46, 150, 20, "Join: " + (Options.showJoin ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 75, this.height / 2 - 22, 150, 20, "Leave: " + (Options.showLeave ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(3, this.width / 2 - 75, this.height / 2 + 2, 150, 20, "Guild: " + (Options.showGuild ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(4, this.width / 2 - 75, this.height / 2 + 26, 150, 20, "Party: " + (Options.showParty ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(5, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "Shout: " + (Options.showShout ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(6, this.width / 2 - 75, this.height / 2 + 74, 150, 20, "Messages: " + (Options.showMessage ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));

            // Register tutorial buttons
            this.buttonList.add(new GuiButton(9, this.width / 2 - 75, this.height - 25, 150, 20, "Tutorial"));

            // Register other buttons
            this.buttonList.add(new GuiButton(10, 5, this.height - 25, 75, 20, "Whitelist"));
            this.buttonList.add(new GuiButton(11, this.width - 80, this.height - 25, 75, 20, "Next"));
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
                    Options.getInstance().toggle(Options.ToggleType.CHAT_TEAM);
                    button.displayString = "Team: " + (Options.showTeam ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 1:
                    Options.getInstance().toggle(Options.ToggleType.CHAT_JOIN);
                    button.displayString = "Join: " + (Options.showJoin ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 2:
                    Options.getInstance().toggle(Options.ToggleType.CHAT_LEAVE);
                    button.displayString = "Leave: " + (Options.showLeave ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 3:
                    Options.getInstance().toggle(Options.ToggleType.CHAT_GUILD);
                    button.displayString = "Guild: " + (Options.showGuild ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 4:
                    Options.getInstance().toggle(Options.ToggleType.CHAT_PARTY);
                    button.displayString = "Party: " + (Options.showParty ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 5:
                    Options.getInstance().toggle(Options.ToggleType.CHAT_SHOUT);
                    button.displayString = "Shout: " + (Options.showShout ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 6:
                    Options.getInstance().toggle(Options.ToggleType.CHAT_MESSAGE);
                    button.displayString = "Messages: " + (Options.showMessage ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 9:
                    new TutorialGui.MainToggleTutorial(this, 0).display();
                    break;
                case 10:
                    new WhitelistGui.WhitelistMain().display();
                    break;
                case 11:
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

            this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height / 2 - 70, 150, 20, "UHC: " + (Options.showUHC ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(1, this.width / 2 - 75, this.height / 2 - 46, 150, 20, "Friend requests: " + (Options.showFriendReqs ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 75, this.height / 2 - 22, 150, 20, "Party invites: " + (Options.showPartyInv ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(3, this.width / 2 - 75, this.height / 2 + 2, 150, 20, "Spectator: " + (Options.showSpec ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(4, this.width / 2 - 75, this.height / 2 + 26, 150, 20, "Colored team: " + (Options.showColored ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(5, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "Housing: " + (Options.showHousing ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(6, this.width / 2 - 75, this.height / 2 + 74, 150, 20, "Separators: " + (Options.showSeparators ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));

            // Register tutorial buttons
            this.buttonList.add(new GuiButton(9, this.width / 2 - 75, this.height - 25, 150, 20, "Tutorial"));

            // Register other buttons
            this.buttonList.add(new GuiButton(10, 5, this.height - 25, 75, 20, "Whitelist"));
            this.buttonList.add(new GuiButton(11, this.width - 80, this.height - 25, 75, 20, "Back"));
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
                    Options.getInstance().toggle(Options.ToggleType.CHAT_MESSAGE);
                    button.displayString = "UHC: " + (Options.showUHC ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 1:
                    Options.getInstance().toggle(Options.ToggleType.CHAT_FRIENDREQ);
                    button.displayString = "Friend requests: " + (Options.showFriendReqs ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 2:
                    Options.getInstance().toggle(Options.ToggleType.CHAT_PARTY);
                    button.displayString = "Party invites: " + (Options.showPartyInv ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 3:
                    Options.getInstance().toggle(Options.ToggleType.CHAT_SPECTATOR);
                    button.displayString = "Spectator: " + (Options.showSpec ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 4:
                    Options.getInstance().toggle(Options.ToggleType.CHAT_COLORED_TEAM);
                    button.displayString = "Colored team: " + (Options.showColored ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 5:
                    Options.getInstance().toggle(Options.ToggleType.CHAT_HOUSING);
                    button.displayString = "Housing: " + (Options.showColored ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 6:
                    Options.getInstance().toggle(Options.ToggleType.CHAT_SEPARATOR);
                    button.displayString = "Separators: " + (Options.showColored ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 9:
                    new TutorialGui.MainToggleTutorial(this, 0).display();
                    break;
                case 10:
                    new WhitelistGui.WhitelistMain().display();
                    break;
                case 11:
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