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

package me.boomboompower.togglechat.gui.whitelist;

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernTextBox;
import me.boomboompower.togglechat.gui.togglechat.MainGui;
import me.boomboompower.togglechat.utils.ChatColor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class WhitelistMainGui extends GuiScreen {

    private ModernTextBox text;
    private String input = "";
    private Minecraft mc;

    public WhitelistMainGui() {
        this("");
    }

    public WhitelistMainGui(String input) {
        this.input = input;

        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);

        this.text = new ModernTextBox(0, this.width / 2 - 75, this.height / 2 - 58, 150, 20);
        this.buttonList.add(new ModernButton(1, this.width / 2 - 75, this.height / 2 - 22, 150, 20, "Add"));
        this.buttonList.add(new ModernButton(2, this.width / 2 - 75, this.height / 2 + 2, 150, 20, "Remove"));
        this.buttonList.add(new ModernButton(3, this.width / 2 - 75, this.height / 2 + 26, 150, 20, "Clear"));
        this.buttonList.add(new ModernButton(4, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "List"));

        if (ToggleChatMod.getInstance().isTutorialEnabled()) this.buttonList.add(new ModernButton(9, this.width / 2 - 70, this.height - 25, 140, 20, "Tutorial"));
        this.buttonList.add(new ModernButton(10, 5, this.height - 25, 75, 20, "Back"));

        this.text.setText(input);
        this.text.setMaxStringLength(16);
        this.text.setFocused(true);
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        drawGuiBackground();
        this.text.drawTextBox();
        drawCenteredString(this.fontRendererObj, "Whitelist", this.width / 2, this.height / 2 - 82, Color.WHITE.getRGB());
        super.drawScreen(x, y, ticks);
    }

    @Override
    protected void keyTyped(char c, int key) throws IOException {
        if (key == 1) {
            this.mc.displayGuiScreen(null);
        } else if (Character.isLetterOrDigit(c) || c == '_' || key == 14) { // Sorry to anyone who originally used other things
            this.text.textboxKeyTyped(c, key);
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int btn) throws IOException {
        super.mouseClicked(x, y, btn);
        this.text.mouseClicked(x, y, btn);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.text.updateCursorCounter();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        boolean dontClose = false;

        switch (((ModernButton) button).id) {
            case 1:
                if (this.text.getText().isEmpty()) {
                    sendChatMessage("No name given!");
                } else if (!whitelistContains(this.text.getText())) {
                    ToggleChatMod.getInstance().getWhitelist().add(this.text.getText());
                    sendChatMessage("Added &6" + this.text.getText() + "&7 to the whitelist!");
                } else {
                    sendChatMessage("The whitelist already contained &6" + this.text.getText() + "&7!");
                }
                break;
            case 2:
                if (this.text.getText().isEmpty()) {
                    sendChatMessage("No name given!");
                } else if (!whitelistContains(this.text.getText())) {
                    sendChatMessage("The whitelist does not contain &6" + this.text.getText() + "&7!");
                } else {
                    removeFromWhitelist(this.text.getText());
                    sendChatMessage("Removed &6" + this.text.getText() + "&7 from the whitelist!");
                }
                break;
            case 3:
                if (!ToggleChatMod.getInstance().getWhitelist().isEmpty()) {
                    dontClose = true;
                    new WhitelistClearConfirmationGui(this).display();
                } else {
                    sendChatMessage("The whitelist is already empty!");
                }
                break;
            case 4:
                dontClose = true;
                new WhitelistEntryGui(this, 1).display();
                break;
            case 10:
                dontClose = true;
                new MainGui(1).display();
                break;
        }

        if (ToggleChatMod.getInstance().isTutorialEnabled()) {
            try {
                switch (((ModernButton) button).id) {
                    case 9:
                        dontClose = true;
                        new me.boomboompower.togglechat.tutorial.WhitelistTutorialGui(this, 0).display();
                        break;
                }
            } catch (Exception ex) {}
        }

        if (!dontClose) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void onGuiClosed() {
        ToggleChatMod.getInstance().getConfigLoader().saveWhitelist();
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void sendChatMessage(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(ChatColor.AQUA + "T" + ChatColor.BLUE + "C" + ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg)));
    }
    
    private boolean whitelistContains(String message) {
        boolean contains = false;

        for (String s : ToggleChatMod.getInstance().getWhitelist()) {
            if (s.equalsIgnoreCase(message)) {
                contains = true;
                break;
            }
        }

        return contains;
    }

    private void removeFromWhitelist(String username) {
        for (String s: ToggleChatMod.getInstance().getWhitelist()) {
            if (s.equalsIgnoreCase(username)) {
                ToggleChatMod.getInstance().getWhitelist().remove(s);
                break;
            }
        }
    }

    public void display() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    public void drawGuiBackground() {
        long lastPress = System.currentTimeMillis();
        int color = Math.min(255, (int) (2L * (System.currentTimeMillis() - lastPress)));
        drawRect(0, 0, width, height, 2013265920 + (color << 16) + (color << 8) + color);
    }
}
