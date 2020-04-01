/*
 *     Copyright (C) 2020 Isophene
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

package me.boomboompower.togglechat.gui.modern.text;

import java.awt.Color;

import lombok.Getter;
import lombok.Setter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

/**
 * A simple class to render strings using the Minecraft FontRenderer.
 * <br />
 * Includes various functionality tweaks as well (such as hover colors)
 *
 * @author boomboompower
 */
public class ModernTextDrawable {

    private FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private int x;

    @Getter
    @Setter
    private int y;

    @Getter
    @Setter
    private int color;

    @Getter
    @Setter
    private int hoverColor;

    @Getter
    @Setter
    private boolean centered;

    @Setter
    @Getter
    private ModernTextClickable click;

    /**
     * The constructor of the drawable
     *
     * @param text the initial text to be rendered
     */
    public ModernTextDrawable(String text) {
        this(0, 0, text, false, Color.WHITE.getRGB(), Color.ORANGE.getRGB());
    }

    /**
     * The constructor of the drawable
     *
     * @param x the initial x postion of the text
     * @param y the initial y postion of the text
     * @param text the initial text to be rendered
     */
    public ModernTextDrawable(int x, int y, String text) {
        this(x, y, text, false, Color.WHITE.getRGB(), Color.ORANGE.getRGB());
    }

    /**
     * The constructor of the drawable
     *
     * @param x the initial x postion of the text
     * @param y the initial y postion of the text
     * @param text the initial text to be rendered
     * @param centered true if text rendering will take into account the width of the text
     */
    public ModernTextDrawable(int x, int y, String text, boolean centered) {
        this(x, y, text, centered, Color.WHITE.getRGB(), Color.ORANGE.getRGB());
    }

    /**
     * The constructor of the drawable
     *
     * @param x the initial x postion of the text
     * @param y the initial y postion of the text
     * @param text the initial text to be rendered
     * @param textColor the color of the text while inactive
     */
    public ModernTextDrawable(int x, int y, String text, int textColor) {
        this(x, y, text, false, textColor, Color.ORANGE.getRGB());
    }

    /**
     * The constructor of the drawable
     *
     * @param x the initial x postion of the text
     * @param y the initial y postion of the text
     * @param text the initial text to be rendered
     * @param centered true if text rendering will take into account the width of the text
     * @param textColor the color of the text while inactive
     */
    public ModernTextDrawable(int x, int y, String text, boolean centered, int textColor) {
        this(x, y, text, centered, textColor, Color.ORANGE.getRGB());
    }

    /**
     * The constructor of the drawable
     *
     * @param x the initial x postion of the text
     * @param y the initial y postion of the text
     * @param text the initial text to be rendered
     * @param textColor the color of the text while inactive
     * @param hoverColor the color of the text whilst the mouse is over it
     */
    public ModernTextDrawable(int x, int y, String text, int textColor, int hoverColor) {
        this(x, y, text, false, textColor, hoverColor);
    }

    /**
     * The constructor of the drawable
     *
     * @param x the initial x postion of the text
     * @param y the initial y postion of the text
     * @param text the initial text to be rendered
     * @param centered true if text rendering will take into account the width of the text
     * @param textColor the color of the text while inactive
     * @param hoverColor the color of the text whilst the mouse is over it
     */
    public ModernTextDrawable(int x, int y, String text, boolean centered, int textColor, int hoverColor) {
        this.x = x < 0 ? 0 : x;
        this.y = y < 0 ? 0 : y;
        this.text = text == null ? "" : text;
        this.color = textColor;
        this.hoverColor = textColor;
        this.centered = centered;
    }

    /**
     * Renders the text without any mouse interactions.
     */
    public void render() {
        String txt = this.text;

        if (txt == null) {
            txt = "";
        }

        GlStateManager.pushMatrix();

        if (this.centered) {
            this.fontRenderer.drawString(txt, (float) (this.x - getWidth() / 2), (float) this.y, this.color, false);
        } else {
            this.fontRenderer.drawString(txt, this.x, this.y, this.color, false);
        }

        GlStateManager.popMatrix();
    }

    /**
     * Renders the text whilst taking mouse interactions into account.
     *
     * @param mouseX the x position of the mouse
     * @param mouseY the y position of the mouse
     */
    public void render(int mouseX, int mouseY) {
        String txt = this.text;
        int color = this.color;

        if (txt == null) {
            txt = "";
        }

        if (isMouseOver(mouseX, mouseY)) {
            color = this.hoverColor;
        }

        GlStateManager.pushMatrix();

        if (this.centered) {
            this.fontRenderer.drawString(txt, (float) (this.x - getWidth() / 2), (float) this.y, color, false);
        } else {
            this.fontRenderer.drawString(txt, this.x, this.y, color, false);
        }

        GlStateManager.popMatrix();
    }

    /**
     * Uses the coordinate of the mouse to determine if the mouse is within the text boundaries
     *
     * @param mouseX the x position of the mouse
     * @param mouseY the y position of the mouse
     *
     * @return true if the mouse is inside the text region
     */
    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseY >= this.y && mouseX <= this.x + getWidth() && mouseY <= this.y + getHeight();
    }

    /**
     * Retrieves the width of the string in {@link #getText()} based on the FontRenderer
     *
     * @return the width of the string, or 0 if null or empty
     */
    public int getWidth() {
        if (this.text == null || this.text.trim().length()== 0) {
            return 0;
        }

        return this.fontRenderer.getStringWidth(this.text);
    }

    /**
     * Returns the height of the text
     *
     * @return the height of the text
     */
    public int getHeight() {
        return 8;
    }
}
