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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.io.IOException;

//        - 94
//        - 70
//        - 46
//        - 22
//        + 2
//        + 26
//        + 50
//        + 74

public class WhitelistGui {

    public static class WhitelistMain extends GuiScreen {

        private CustomGuiTextBox text;
        private String input = "";

        public WhitelistMain() {
            this("");
        }

        public WhitelistMain(String input) {
            this.input = input;
        }

        @Override
        public void initGui() {
            text = new CustomGuiTextBox(0, this.fontRendererObj, this.width / 2 - 75, this.height / 2 - 58, 150, 20);
            this.buttonList.add(new GuiButton(1, this.width / 2 - 75, this.height / 2 - 22, 150, 20, "Add"));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 75, this.height / 2 + 2, 150, 20, "Remove"));
            this.buttonList.add(new GuiButton(3, this.width / 2 - 75, this.height / 2 + 26, 150, 20, "Clear"));
            this.buttonList.add(new GuiButton(4, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "List"));

            this.buttonList.add(new GuiButton(5, 5, this.height - 25, 50, 20, "Back"));
            this.buttonList.add(new GuiButton(6, this.width / 2 - 75, this.height - 25, 150, 20, "Settings"));

            text.setText(input);
            text.setMaxStringLength(16);
            text.setFocused(true);
        }

        public void display() {
            FMLCommonHandler.instance().bus().register(this);
        }

        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            FMLCommonHandler.instance().bus().unregister(this);
            Minecraft.getMinecraft().displayGuiScreen(this);
        }

        @Override
        public void drawScreen(int x, int y, float ticks) {
            drawDefaultBackground();
            text.drawTextBox();
            drawCenteredString(this.fontRendererObj, "Whitelist", this.width / 2, this.height / 2 - 82, Color.WHITE.getRGB());
            super.drawScreen(x, y, ticks);
        }

        @Override
        protected void keyTyped(char c, int key) throws IOException {
            super.keyTyped(c, key);
            text.textboxKeyTyped(c, key);
        }

        @Override
        protected void mouseClicked(int x, int y, int btn) throws IOException {
            super.mouseClicked(x, y, btn);
            text.mouseClicked(x, y, btn);
        }

        @Override
        public void updateScreen() {
            super.updateScreen();
            text.updateCursorCounter();
        }

        @Override
        protected void actionPerformed(GuiButton button) {
            switch (button.id) {
                case 0:
                    // Dont need to do anything
                    break;
                case 1:
                    if (text.getText().isEmpty()) {
                        sendChatMessage("No name given!");
                    } else if (!whitelistContains(text.getText())) {
                        ToggleChat.whitelist.add(text.getText());
                        sendChatMessage("Added " + goldify(text.getText()) + " to the whitelist!");
                    } else {
                        sendChatMessage("The whitelist already contained " + goldify(text.getText()) + "!");
                    }
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    break;
                case 2:
                    if (text.getText().isEmpty()) {
                        sendChatMessage("No name given!");
                    } else if (!whitelistContains(text.getText())) {
                        sendChatMessage("The whitelist does not contain " + goldify(text.getText()) + "!");
                    } else {
                        removeFromWhitelist(text.getText());
                        sendChatMessage("Removed " + goldify(text.getText()) + " from the whitelist!");
                    }
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    break;
                case 3:
                    if (!ToggleChat.whitelist.isEmpty()) {
                        ToggleChat.whitelist.clear();
                        sendChatMessage("Cleared the whitelist!");
                    } else {
                        sendChatMessage("The whitelist is already empty!");
                    }
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    break;
                case 4:
                    displayWhitelist();
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    break;
                case 5:
                    new ToggleGui.Settings_1().display();
                    break;
                case 6:
                    new WhitelistSettings().display();
                    break;
            }
        }

        @Override
        public void onGuiClosed() {
            Writer.execute(true);
        }

        @Override
        public boolean doesGuiPauseGame() {
            return false;
        }

        @Override
        public void sendChatMessage(String message) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "T" + EnumChatFormatting.BLUE + "C" + EnumChatFormatting.DARK_GRAY + " > " + EnumChatFormatting.GRAY + message));
        }

        private String goldify(String name) {
            return EnumChatFormatting.GOLD + name + EnumChatFormatting.GRAY;
        }

        private boolean whitelistContains(String message) {
            boolean contains = false;

            for (String s: ToggleChat.whitelist) {
                if (s.equalsIgnoreCase(message)) {
                    contains = true;
                    break;
                }
            }

            return contains;
        }

        private void removeFromWhitelist(String username) {
            for (String s: ToggleChat.whitelist) {
                if (s.equalsIgnoreCase(username)) {
                    ToggleChat.whitelist.remove(s);
                    break;
                }
            }
        }

        private void displayWhitelist() {
            if (ToggleChat.whitelist.size() > 0) {
                sendChatMessage("Displaying " + goldify(String.valueOf(ToggleChat.whitelist.size())) + (ToggleChat.whitelist.size() == 1 ? " entry" : " entries") + " from the whitelist!");
                for (String word: ToggleChat.whitelist) {
                    sendChatMessage(EnumChatFormatting.GOLD + "\u25CF " + word);
                }
            } else {
                sendChatMessage(EnumChatFormatting.RED + "There is nothing on your whitelist!");
            }
        }
    }

    public static class WhitelistSettings extends GuiScreen {

        public WhitelistSettings() {
        }

        @Override
        public void initGui() {
            this.buttonList.add(new GuiButton(0, this.width / 2 - 150, this.height / 2 - 70, 150, 20, "Team: " + (Options.showTeam ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(1, this.width / 2 - 150, this.height / 2 - 46, 150, 20, "Join: " + (Options.showJoin ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 150, this.height / 2 - 22, 150, 20, "Leave: " + (Options.showLeave ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(3, this.width / 2 - 150, this.height / 2 + 2, 150, 20, "Guild: " + (Options.showGuild ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(4, this.width / 2 - 150, this.height / 2 + 26, 150, 20, "Party: " + (Options.showParty ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(5, this.width / 2 - 150, this.height / 2 + 50, 150, 20, "Shout: " + (Options.showShout ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(6, this.width / 2 - 150, this.height / 2 + 74, 150, 20, "Message: " + (Options.showTeam ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));

            this.buttonList.add(new GuiButton(7, this.width / 2 + 4, this.height / 2 - 70, 150, 20, "UHC: " + (Options.showJoin ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(8, this.width / 2 + 4, this.height / 2 - 46, 150, 20, "Party invites: " + (Options.showLeave ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(9, this.width / 2 + 4, this.height / 2 - 22, 150, 20, "Friend requests: " + (Options.showGuild ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(10, this.width / 2 + 4, this.height / 2 + 2, 150, 20, "Spectator: " + (Options.showParty ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(11, this.width / 2 + 4, this.height / 2 + 26, 150, 20, "Colored team: " + (Options.showShout ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(12, this.width / 2 + 4, this.height / 2 + 50, 150, 20, "Housing: " + (Options.showHousing ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));
            this.buttonList.add(new GuiButton(13, this.width / 2 + 4, this.height / 2 + 74, 150, 20, "Separators: " + (Options.showSeparators ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled")));


            this.buttonList.add(new GuiButton(14, 5, this.height - 25, 50, 20, "Back"));
        }

        public void display() {
            FMLCommonHandler.instance().bus().register(this);
        }

        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            FMLCommonHandler.instance().bus().unregister(this);
            Minecraft.getMinecraft().displayGuiScreen(this);
        }

        @Override
        public void drawScreen(int x, int y, float ticks) {
            drawDefaultBackground();
            drawCenteredString(this.fontRendererObj, "Whitelist Settings", this.width / 2, this.height / 2 - 82, Color.WHITE.getRGB());
            drawCenteredString(this.fontRendererObj, "This feature is experimental and may be removed at any time!", this.width / 2, this.height / 2 + 95, Color.WHITE.getRGB());
            super.drawScreen(x, y, ticks);
        }

        @Override
        protected void keyTyped(char c, int key) throws IOException {
            super.keyTyped(c, key);
        }

        @Override
        protected void mouseClicked(int x, int y, int btn) throws IOException {
            super.mouseClicked(x, y, btn);
        }

        @Override
        public void updateScreen() {
            super.updateScreen();
        }

        @Override
        protected void actionPerformed(GuiButton button) {
            switch (button.id) {
                case 0:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_TEAM);
                    this.buttonList.get(button.id).displayString = "Team: " + (Options.ignoreTeam ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 1:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_JOIN);
                    this.buttonList.get(button.id).displayString = "Join: " + (Options.ignoreJoin ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 2:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_LEAVE);
                    this.buttonList.get(button.id).displayString = "Leave: " + (Options.ignoreLeave ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 3:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_GUILD);
                    this.buttonList.get(button.id).displayString = "Guild: " + (Options.ignoreGuild ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 4:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_PARTY);
                    this.buttonList.get(button.id).displayString = "Party: " + (Options.ignoreParty ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 5:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_SHOUT);
                    this.buttonList.get(button.id).displayString = "Shout: " + (Options.ignoreShout ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 6:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_MESSAGE);
                    this.buttonList.get(button.id).displayString = "Message: " + (Options.ignoreMessage ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 7:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_UHC);
                    this.buttonList.get(button.id).displayString = "UHC: " + (Options.ignoreUHC ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 8:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_PARTYINV);
                    this.buttonList.get(button.id).displayString = "Party invites: " + (Options.ignorePartyInv ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 9:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_FRIENDREQ);
                    this.buttonList.get(button.id).displayString = "Friend requests: " + (Options.ignoreFriendReqs ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 10:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_SPECTATOR);
                    this.buttonList.get(button.id).displayString = "Spectator: " + (Options.ignoreSpec ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 11:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_COLORED_TEAM);
                    this.buttonList.get(button.id).displayString = "Colored team: " + (Options.ignoreColored ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 12:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_HOUSING);
                    this.buttonList.get(button.id).displayString = "Housing: " + (Options.ignoreHousing ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 13:
                    Options.getInstance().toggle(Options.ToggleType.WHITELIST_SEPARATOR);
                    this.buttonList.get(button.id).displayString = "Separators: " + (Options.ignoreSeparators ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled");
                    break;
                case 14:
                    new WhitelistMain().display();
                    break;
            }
        }

        @Override
        public void onGuiClosed() {
            Writer.execute(false, true);
        }

        @Override
        public boolean doesGuiPauseGame() {
            return false;
        }

        @Override
        public void sendChatMessage(String message) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "T" + EnumChatFormatting.BLUE + "C" + EnumChatFormatting.DARK_GRAY + " > " + EnumChatFormatting.GRAY + message));
        }
    }
}