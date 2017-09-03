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

package me.boomboompower.togglechat.gui.utils;

import me.boomboompower.togglechat.utils.ChatColor;

import net.minecraft.client.gui.FontRenderer;

/*
 * An attempt at creating an easier way to do things in GUI's
 *
 * Initially these methods were only used in the WhitelistGui class,
 * however they were moved here so other GUI classes could use them.
 */
public class GuiUtils {

    public static final String ENABLED = ChatColor.GREEN + "Enabled";
    public static final String DISABLED = ChatColor.RED + "Disabled";

    /*
     * Default constructor, no need to create an instance of this class
     */
    private GuiUtils() {
    }

    /*
     * Base method for writing on the screen.
     *
     * fontRender - The fontRender to write onto
     * text - The message to be written onto the screen
     * x - the x coordinate of the text
     * y - the y coordinate of the text
     * color - the color of the message
     */
    public static void drawCenteredString(FontRenderer fontRenderer, String text, int x, int y, int color) {
        fontRenderer.drawString(text, (float) (x - fontRenderer.getStringWidth(text) / 2), (float) y, color, false);
    }

    /*
     * An additional way of using the drawCenteredString
     */
    public static void drawCentered(CenterStringBuilder builder) {
        drawCenteredString(builder.getFontRender(), builder.translateCodes().getMessage(), builder.getX(), builder.getY(), builder.getColor().getRGB());
    }

    /*
     * Optional method for writeInformation(...) so that the color variable is not essential.
     *
     * Write multiple lines on the screen at once, separated by the separation variable.
     * This is done by using a simple loop through the lines variable, and each time another
     * String is found, it will be written to the screen and the y will have the separation amount added.
     */
    public static void writeInformation(int startingX, int startingY, int separation, String... lines) {
        writeInformation(startingX, startingY, separation, ChatColor.WHITE, lines);
    }

    /*
     * Default method for writeInformation(...) with the color variable present
     *
     * Write multiple lines on the screen at once, separated by the separation variable.
     * This is done by using a simple loop through the lines variable, and each time another
     * String is found, it will be written to the screen and the y will have the separation amount added.
     */
    public static void writeInformation(int startingX, int startingY, int separation, ChatColor color, String... lines) {
        for (String s : lines) {
            drawCentered(new CenterStringBuilder(color + s, startingX, startingY));
            startingY += separation;
        }
    }
}
