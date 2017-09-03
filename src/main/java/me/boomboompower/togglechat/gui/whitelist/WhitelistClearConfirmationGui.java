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
import me.boomboompower.togglechat.gui.utils.GuiUtils;
import me.boomboompower.togglechat.utils.ChatColor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;

public class WhitelistClearConfirmationGui extends GuiScreen {

    private GuiScreen previousScreen;
    private Minecraft mc;

    public WhitelistClearConfirmationGui(GuiScreen previous) {
        this.previousScreen = previous;

        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        this.buttonList.add(new ModernButton(0, this.width / 2 - 200, this.height / 2 + 30, 150, 20, "Cancel"));
        this.buttonList.add(new ModernButton(1, this.width / 2 + 50, this.height / 2 + 30, 150, 20, "Confirm"));
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        drawDefaultBackground();

        GuiUtils.writeInformation(this.width / 2, this.height / 2 - 60, 15,
                String.format("Are you sure you wish to clear &6%s %s&r from your whitelist?", ToggleChatMod.getInstance().getWhitelist().size(), (ToggleChatMod.getInstance().getWhitelist().size() == 1 ? "entry" : "entries")),
                "This action cannot be undone, use at your own risk!"
        );

        super.drawScreen(x, y, ticks);
    }

    @Override
    protected void keyTyped(char c, int key) throws IOException {
        if (key == 1) {
            this.mc.displayGuiScreen(this.previousScreen);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (((ModernButton) button).id) {
            case 0:
                this.mc.displayGuiScreen(this.previousScreen);
                break;
            case 1:
                ToggleChatMod.getInstance().getWhitelist().clear();
                sendChatMessage("Cleared the whitelist!");
                this.mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    public void onGuiClosed() {
        ToggleChatMod.getInstance().getConfigLoader().saveWhitelist();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void sendChatMessage(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(ChatColor.AQUA + "T" + ChatColor.BLUE + "C" + ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg)));
    }

    public void display() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }
}
