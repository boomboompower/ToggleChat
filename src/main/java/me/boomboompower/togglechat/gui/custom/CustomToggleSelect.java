package me.boomboompower.togglechat.gui.custom;

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.gui.modern.ModernGui;

import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

public class CustomToggleSelect extends ModernGui {

    private GuiScreen previous;
    private CustomToggleMain.SelectType selectType;

    public CustomToggleSelect(GuiScreen previous, CustomToggleMain.SelectType type) {
        this.previous = previous;
        this.selectType = type;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        drawCenteredString("This page is still in progress!", this.width / 2, this.height / 2 - 4, Color.WHITE.getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.previous);
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void onGuiClosed() {
        ToggleChatMod.getInstance().getConfigLoader().saveCustomToggles();
    }
}
