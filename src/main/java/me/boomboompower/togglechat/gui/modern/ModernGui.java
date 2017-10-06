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

package me.boomboompower.togglechat.gui.modern;

import com.google.common.collect.Lists;

import me.boomboompower.togglechat.toggles.ToggleBase;
import me.boomboompower.togglechat.utils.ChatColor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.List;

public abstract class ModernGui extends GuiScreen {

    protected final Minecraft mc = Minecraft.getMinecraft();
    protected final FontRenderer fontRendererObj = this.mc.fontRendererObj;

    public static final String ENABLED = ChatColor.GREEN + "Enabled";
    public static final String DISABLED = ChatColor.GRAY + "Disabled";

    protected List<GuiLabel> labelList = Lists.newArrayList();
    protected List<GuiButton> buttonList = Lists.newArrayList();
    protected List<ModernTextBox> textList = Lists.newArrayList();

    private GuiButton selectedButton;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (GuiButton button : this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY);
        }

        for (GuiLabel label : this.labelList) {
            label.drawLabel(this.mc, mouseX, mouseY);
        }

        for (ModernTextBox text : this.textList) {
            text.drawTextBox();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);

            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        } else {
            for (ModernTextBox text : textList) {
                text.textboxKeyTyped(typedChar, keyCode);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < this.buttonList.size(); ++i) {
                GuiButton guibutton = this.buttonList.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
                    net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
                    if (FMLCommonHandler.instance().bus().post(event)) break;
                    guibutton = event.button;
                    this.selectedButton = guibutton;
                    guibutton.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                    if (this.equals(this.mc.currentScreen))
                        FMLCommonHandler.instance().bus().post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.button, this.buttonList));
                }
            }
        }
        for (ModernTextBox text : this.textList) {
            text.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.selectedButton != null && state == 0) {
            this.selectedButton.mouseReleased(mouseX, mouseY);
            this.selectedButton = null;
        }
    }

    @Override
    public final void drawDefaultBackground() {
        Gui.drawRect(0, 0, this.width, this.height, 2013265920 + (2 << 16) + (2 << 8) + 2);
    }

    @Override
    public final void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color, false);
    }

    public final void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color, boolean shadow) {
        fontRendererIn.drawString(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color, shadow);
    }

    public final void drawCenteredString(String text, int x, int y, int color) {
        drawCenteredString(this.fontRendererObj, text, x, y, color, false);
    }

    public final void drawCenteredString(String text, int x, int y, int color, boolean shadow) {
        drawCenteredString(this.fontRendererObj, text, x, y, color, shadow);
    }

    @Override
    public final void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, (float) x, (float) y, color, false);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof ModernButton) {
            buttonPressed((ModernButton) button);
        }
    }

    @Override
    public final void sendChatMessage(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(ChatColor.AQUA + "T" + ChatColor.BLUE + "C" + ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg)));
    }

    public void buttonPressed(ModernButton button) {
    }

    public final void checkHover(int firstPosition) {
        for (GuiButton old : this.buttonList) {
            if (!(old instanceof ModernButton)) return;
            ModernButton button = (ModernButton) old;

            if (button.isMouseOver() && button.hasButtonData()) {
                ToggleBase toggleBase = button.getButtonData();

                if (!toggleBase.hasDescription()) return;

                final int[] position = {firstPosition};
                toggleBase.getDescription().forEach(text -> {
                    drawCenteredString(ChatColor.translateAlternateColorCodes(text), this.width / 2 + 150, position[0], Color.WHITE.getRGB());
                    position[0] += 10;
                });
            }
        }
    }

    public void writeInformation(int startingX, int startingY, int separation, String... lines) {
        for (String s : lines) {
            drawCenteredString(this.fontRendererObj, ChatColor.translateAlternateColorCodes('&', s), startingX, startingY, Color.WHITE.getRGB());
            startingY += separation;
        }
    }

    public static boolean isAltKeyDown()
    {
        return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
    }

    public static boolean isKeyComboCtrlX(int keyID)
    {
        return keyID == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlV(int keyID)
    {
        return keyID == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlC(int keyID)
    {
        return keyID == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlA(int keyID)
    {
        return keyID == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }
}
