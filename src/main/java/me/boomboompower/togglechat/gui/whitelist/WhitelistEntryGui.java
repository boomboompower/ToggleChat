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
import me.boomboompower.togglechat.gui.utils.CenterStringBuilder;
import me.boomboompower.togglechat.gui.utils.GuiUtils;
import me.boomboompower.togglechat.utils.ChatColor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.io.IOException;

public class WhitelistEntryGui extends GuiScreen {

    private ModernButton next;

    private GuiScreen previousScreen;

    private boolean pageInvalid;
    private int pageNumber;
    private Minecraft mc;

    public WhitelistEntryGui(GuiScreen previous, int pageNumber) {
        this.previousScreen = previous;
        this.pageNumber = pageNumber;
        this.pageInvalid = false;

        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        makeButtons();
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        drawGuiBackground();
        drawCenteredString(this.fontRendererObj, "Whitelist Entries", this.width / 2, this.height / 2 - 105, Color.WHITE.getRGB());

        drawBox();

        setupPage();

        super.drawScreen(x, y, ticks);
    }

    private void makeButtons() {
        if (ToggleChatMod.getInstance().getWhitelist().size() > 0) {
            this.buttonList.add(new ModernButton(0, this.width / 2 - 200, this.height / 2 + 80, 150, 20, "Back"));
            this.buttonList.add(this.next = new ModernButton(1, this.width / 2 + 50, this.height / 2 + 80, 150, 20, "Next"));
        } else {
            this.buttonList.add(new ModernButton(0, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "Back"));
        }
    }

    private void setupPage() {
        if (ToggleChatMod.getInstance().getWhitelist().size() > 0) {

            int totalEntries = ToggleChatMod.getInstance().getWhitelist().size();
            int pages = (int) Math.ceil((double) ToggleChatMod.getInstance().getWhitelist().size() / 10D);

            if (this.pageNumber < 1 || this.pageNumber > pages) {
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 40, 20, ChatColor.RED, String.format("Invalid page number (%s)", (ChatColor.DARK_RED + String.valueOf(pageNumber) + ChatColor.RED)));
                this.pageInvalid = true;
                return;
            }

            this.pageInvalid = false;
            this.next.enabled = pageNumber != pages; // Next

            GuiUtils.drawCentered(new CenterStringBuilder(String.format("Page %s/%s", (this.pageNumber), pages), this.width / 2, this.height / 2 - 95));
            GuiUtils.drawCentered(new CenterStringBuilder(String.format("There is a total of %s %s on the whitelist!", ChatColor.GOLD + String.valueOf(totalEntries), (totalEntries > 1 ? "entries" : "entry") + ChatColor.RESET), this.width / 2, this.height / 2 + 65));

            final int[] position = {this.height / 2 - 73};

            ToggleChatMod.getInstance().getWhitelist().stream().skip((this.pageNumber - 1) * 10).limit(10).forEach(word -> {
                GuiUtils.drawCentered(new CenterStringBuilder(word, this.width / 2, position[0]));
                position[0] += 13;
            });

            return;
        }

        GuiUtils.writeInformation(this.width / 2, this.height / 2 - 50, 20, "There are no entries on the whitelist!", "Insert some words then return to this page!");
    }

    private void drawBox() {
        if (ToggleChatMod.getInstance().getWhitelist().size() > 0 && !pageInvalid) {
            drawRect(this.width / 2 - 60, this.height / 2 - 80, this.width / 2 + 60, this.height / 2 + 60, new Color(105, 105, 105, 75).getRGB());

            drawHorizontalLine(this.width / 2 - 60, width / 2 + 60, this.height / 2 - 80, Color.WHITE.getRGB());
            drawHorizontalLine(this.width / 2 - 60, width / 2 + 60, this.height / 2 + 60, Color.WHITE.getRGB());

            drawVerticalLine(this.width / 2 - 60, this.height / 2 - 80, this.height / 2 + 60, Color.WHITE.getRGB());
            drawVerticalLine(this.width / 2 + 60, this.height / 2 - 80, this.height / 2 + 60, Color.WHITE.getRGB());
        }
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
                if (this.pageNumber > 1) {
                    new WhitelistEntryGui(this, this.pageNumber--);
                } else {
                    this.mc.displayGuiScreen(previousScreen);
                }
                break;
            case 1:
                new WhitelistEntryGui(this, this.pageNumber++);
                break;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
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
