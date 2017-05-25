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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiUtils {

    public static void drawCenteredString(FontRenderer fontRenderer, String text, int x, int y, int color) {
        fontRenderer.drawStringWithShadow(text, (float) (x - fontRenderer.getStringWidth(text) / 2), (float) y, color);
    }

    public static void drawCentered(CenterStringBuilder builder) {
        drawCenteredString(builder.getFontRender(), builder.translateCodes().getMessage(), builder.getX(), builder.getY(), builder.getColor().getRGB());
    }

    public static void writeInformation(int startingX, int startingY, int separation, String... lines) {
        writeInformation(startingX, startingY, separation, EnumChatFormatting.WHITE, lines);
    }

    public static void writeInformation(int startingX, int startingY, int separation, EnumChatFormatting color, String... lines) {
        for (String s : lines) {
            drawCentered(new CenterStringBuilder(color + s, startingX, startingY));
            startingY += separation;
        }
    }

    public static void display(GuiScreen gui) {
        Minecraft.getMinecraft().displayGuiScreen(gui);
    }
}
