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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.Random;

public class CenterStringBuilder {

    private FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    private Color color = Color.WHITE;

    private String message;

    private int x;
    private int y;

    public CenterStringBuilder(String message, int x, int y) {
        this.message = message;
        this.x = x;
        this.y = y;
    }

    public void setFontRenderer(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }

    public FontRenderer getFontRender() {
        return this.fontRenderer;
    }

    public CenterStringBuilder randomColor() {
        EnumChatFormatting[] colors = EnumChatFormatting.values();
        int i = new Random().nextInt(colors.length);
        message = colors[i].toString() + message;

        return get();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    /*
     * Returning itself so people can do "new CenterStringBuilder(args...).translate();"
     * and it will be valid for a method requiring a CenterStringBuilder field.
     */
    public CenterStringBuilder translateCodes() {
        this.message = GlobalUtils.translateAlternateColorCodes('&', this.message);

        return get();
    }

    public String getMessage() {
        return this.message;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public CenterStringBuilder get() {
        return this;
    }
}
