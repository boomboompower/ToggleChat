/*
 *     Copyright (C) 2019 boomboompower
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

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.config.ConfigLoader;
import me.boomboompower.togglechat.toggles.ToggleBase;
import me.boomboompower.togglechat.utils.ChatColor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.List;

/**
 * ModernGui, a nicer looking Gui
 *
 * @author boomboompower
 * @version 3.0
 */
public abstract class ModernGui extends GuiScreen {

    protected final ConfigLoader configLoader = ToggleChatMod.getInstance().getConfigLoader();
    
    protected final Minecraft mc = Minecraft.getMinecraft();
    protected final FontRenderer fontRendererObj = this.mc.fontRendererObj;
    protected final ToggleChatMod mod = ToggleChatMod.getInstance();

    protected List<ModernButton> buttonList = Lists.newArrayList();
    protected List<ModernTextBox> textList = Lists.newArrayList();

    private ModernButton selectedButton;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!this.buttonList.isEmpty()) {
            for (ModernButton button : this.buttonList) {
                button.drawButton(this.mc, mouseX, mouseY);
            }
        }

        if (!this.labelList.isEmpty()) {
            for (GuiLabel label : this.labelList) {
                label.drawLabel(this.mc, mouseX, mouseY);
            }
        }

        if (!this.textList.isEmpty()) {
            for (ModernTextBox text : this.textList) {
                text.drawTextBox();
            }
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
            for (ModernButton button : this.buttonList) {
                if (button.mousePressed(this.mc, mouseX, mouseY)) {
                    this.selectedButton = button;
                    button.playPressSound(this.mc.getSoundHandler());
                    this.buttonPressed(button);
                }
            }
        }

        if (mouseButton == 1) {
            for (ModernButton button : this.buttonList) {
                if (button != null) {
                    if (button.mousePressed(this.mc, mouseX, mouseY)) {
                        this.rightClicked(button);
                    }
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
        Gui.drawRect(0, 0, this.width, this.height, new Color(2, 2, 2, 120).getRGB());
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
    public final void sendChatMessage(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(ChatColor.AQUA + "T" + ChatColor.BLUE + "C" + ChatColor.DARK_GRAY + " > " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg)));
    }
    
    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        // Required for compatibility
        super.mc = mc;
        
        this.itemRender = mc.getRenderItem();
        this.width = width;
        this.height = height;
        
        this.textList.clear();
        this.buttonList.clear();
        
        initGui();
    }
    
    public void buttonPressed(ModernButton button) {
    }

    public void rightClicked(ModernButton button) {

    }

    public final void checkHover(int firstPosition) {
        if (this.buttonList.isEmpty()) {
            return;
        }

        for (ModernButton button : this.buttonList) {
            if (button == null) continue;

            if (button.isHovered() && button.hasButtonData()) {
                ToggleBase toggleBase = button.getButtonData();

                if (!toggleBase.hasDescription()) continue;

                final int[] position = {firstPosition};
                toggleBase.getDescription().forEach(text -> {
                    drawCenteredString(ChatColor.translateAlternateColorCodes(text), this.width / 2 + 150, position[0], Color.WHITE.getRGB());
                    position[0] += 10;
                });
            }
        }
    }

    /**
     * Draws multiple lines on the screen
     *
     * @param startingX
     * @param startingY
     * @param separation
     * @param lines
     */
    public void writeInformation(int startingX, int startingY, int separation, String... lines) {
        for (String s : lines) {
            drawCenteredString(this.fontRendererObj, ChatColor.translateAlternateColorCodes('&', s), startingX, startingY, Color.WHITE.getRGB());
            startingY += separation;
        }
    }

    /**
     * Forge 1.7.10 compatibility. This is how old GUIs would need to be opened.
     */
    public final void display() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Allow the game to open the gui on the next client tick, whilst simultaneously
     * removing this gui from all subsequent ticks.
     *
     * @param event the next client tick event
     */
    @SubscribeEvent
    public final void onTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    /**
     * Simple formatting for a toggled string.
     *
     * @param in the status of the toggle
     * @return a formatted string containing the relevant status to the boolean
     */
    public static String getStatus(boolean in) {
        return in ? ChatColor.GREEN + "Enabled" : (ToggleChatMod.getInstance().getConfigLoader().isModernButton() ? ChatColor.RED : ChatColor.GRAY) + "Disabled";
    }
}
