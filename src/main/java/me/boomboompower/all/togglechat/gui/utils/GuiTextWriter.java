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

package me.boomboompower.all.togglechat.gui.utils;

import me.boomboompower.all.togglechat.utils.GlobalUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;

/**
 * Created to assist with writing things on GUI's
 *
 * @author boomboompower
 */
public class GuiTextWriter {

    private GuiScreen gui;

    public GuiTextWriter(GuiScreen screen) {
        this.gui = screen;
    }

    public void drawCenteredMessage(CenterStringBuilder builder) {
        drawMessage(builder.getX(), builder.getY(), builder.translateCodes().getMessage());
    }

    public void drawCenteredMessage(int x, int y, String... message) {
        drawMessage(x, y, 10, EnumChatFormatting.WHITE, message);
    }

    public void drawCenteredMessage(int x, int y, int separation, String... message) {
        drawCenteredMessage(x, y, separation, EnumChatFormatting.WHITE, message);
    }

    public void drawCenteredMessage(int x, int y, int separation, EnumChatFormatting color, String... messages) {
        for (String message : messages) {
            gui.drawCenteredString(gui.mc.fontRendererObj, color + message, x, y, Color.WHITE.getRGB());
            y += separation;
        }
    }

    public void drawMessage(int x, int y, String... messages) {
        drawMessage(x, y, 10, EnumChatFormatting.WHITE, messages);
    }

    public void drawMessage(int x, int y, int separation, String... messages) {
        drawMessage(x, y, separation, EnumChatFormatting.WHITE, messages);
    }

    public void drawMessage(int x, int y, int separation, EnumChatFormatting color, String... messages) {
        for (String message : messages) {
            gui.drawString(gui.mc.fontRendererObj, color + message, x, y, Color.WHITE.getRGB());
            y += separation;
        }
    }

    public String getClipboard() {
        return GuiScreen.getClipboardString();
    }

    public void setClipboard(String message) {
        GuiScreen.setClipboardString(message);
    }
}
