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
package me.boomboompower.togglechat.gui.utils;

import me.boomboompower.togglechat.utils.ChatColor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.Random;

/*
 * An easier way to create messages on the screen, does not default to the center of the screen.
 * It can be accomplished by using "Minecraft#displayWidth / 2" or "Gui#width / 2"
 */
public class CenterStringBuilder {

    private FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    private Color color = Color.WHITE;

    private String message = "";

    private int x = 0;
    private int y = 0;

    /*
     * Default constructor, initiaizes the default variables.
     */
    public CenterStringBuilder(String message, int x, int y) {
        this.message = message;
        this.x = x;
        this.y = y;
    }

    /*
     * Setter for the fontRenderer field, defaults to Minecraft#fontRendererObj
     */
    public void setFontRenderer(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }

    /*
     * Getter for the fontRenderer field, returns the current fontRenderer
     */
    public FontRenderer getFontRender() {
        return this.fontRenderer;
    }

    /*
     * Returning itself so people can do "new CenterStringBuilder(args...).randomColor();"
     * and it will be valid for a method requiring a CenterStringBuilder field.
     *
     * Inputs a random color before the given message.
     */
    public CenterStringBuilder randomColor() {
        EnumChatFormatting[] colors = EnumChatFormatting.values();
        int i = new Random().nextInt(colors.length);
        message = colors[i].toString() + message;

        return get();
    }

    /*
     * Returning itself so people can do "new CenterStringBuilder(args...).translate();"
     * and it will be valid for a method requiring a CenterStringBuilder field.
     *
     * Replaces all '&' characters with the color-code symbols.
     */
    public CenterStringBuilder translateCodes() {
        this.message = ChatColor.translateAlternateColorCodes('&', this.message);

        return get();
    }

    /*
     * Returning itself so people can do "new CenterStringBuilder(args...).setColor(Color color);"
     * and it will be valid for a method requiring a CenterStringBuilder field.
     *
     * Setter for the color field, changes the base message color (Color class)
     */
    public CenterStringBuilder setColor(Color color) {
        this.color = color;

        return get();
    }

    /*
     * Getter for the color field, returns the message color (Color class)
     */
    public Color getColor() {
        return this.color;
    }

    /*
     * Getter for the message field, returns the current String message. (Possibly modified from previous methods)
     */
    public String getMessage() {
        return this.message;
    }

    /*
     * Gets the position on the screen for the x coordinate
     */
    public int getX() {
        return this.x;
    }

    /*
     * Gets the position on the screen for the y coordinate
     */
    public int getY() {
        return this.y;
    }

    /*
     * Returns itself.
     */
    public CenterStringBuilder get() {
        return this;
    }
}
