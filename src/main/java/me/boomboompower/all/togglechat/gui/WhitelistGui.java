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
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//        - 94
//        - 70
//        - 46
//        - 22
//        + 2
//        + 26
//        + 50
//        + 74

public class WhitelistGui extends GuiScreen {

    private CustomGuiTextBox text;

    @Override
    public void initGui() {
        text = new CustomGuiTextBox(0, this.fontRendererObj, this.width / 2 - 75, this.height / 2 - 58, 150, 20);
        this.buttonList.add(new CustomGuiButton(1, this.width / 2 - 75, this.height / 2 - 22, 150, 20, "Add"));
        this.buttonList.add(new CustomGuiButton(2, this.width / 2 - 75, this.height / 2 + 2, 150, 20, "Remove"));
        this.buttonList.add(new CustomGuiButton(3, this.width / 2 - 75, this.height / 2 + 26, 150, 20, "Clear"));
        this.buttonList.add(new CustomGuiButton(4, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "List"));

        this.buttonList.add(new CustomGuiButton(5, 5, this.height - 25, 50, 20, "Back"));

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
                displayWhitelist(new String[] {"0"});
                Minecraft.getMinecraft().displayGuiScreen(null);
                break;
            case 5:
                new ToggleGui.Settings_1().display();
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

        for (String s : ToggleChat.whitelist) {
            if (s.equalsIgnoreCase(message)) {
                contains = true;
                break;
            }
        }

        return contains;
    }

    private void removeFromWhitelist(String username) {
        for (String s : ToggleChat.whitelist) {
            if (s.equalsIgnoreCase(username)) {
                ToggleChat.whitelist.remove(s);
                break;
            }
        }
    }

    private void displayWhitelist(String[] args) {
        if (ToggleChat.whitelist.size() > 0) {
            int page = 1;
            int pages = (int) Math.ceil(ToggleChat.whitelist.size() / 7D);

            if (args.length > 1) {
                try {
                    page = Integer.parseInt(args[1]);
                } catch (NumberFormatException var21) {
                    page = -1;
                }
            }

            if (page >= 1 && page <= pages) {
                sendChatMessage(EnumChatFormatting.WHITE + "Whitelist " + EnumChatFormatting.GRAY + "[Page " + page + " of " + pages + "]" + EnumChatFormatting.WHITE + ":");
                ToggleChat.whitelist.stream().skip((long) (page - 1) * 7).limit(7L).forEach(word -> sendChatMessage(EnumChatFormatting.GOLD + "\u25CF " + word));
            } else {
                sendChatMessage(EnumChatFormatting.RED + "Unknown page number!");
            }
        } else {
            sendChatMessage(EnumChatFormatting.RED + "There is nothing on your whitelist!");
        }
    }
}
