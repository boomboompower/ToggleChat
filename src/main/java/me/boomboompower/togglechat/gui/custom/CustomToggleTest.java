package me.boomboompower.togglechat.gui.custom;

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.custom.TypeCustom;
import me.boomboompower.togglechat.utils.ChatColor;
import net.minecraft.client.gui.GuiScreen;
import org.apache.commons.lang3.text.WordUtils;

import java.awt.*;

public class CustomToggleTest extends ModernGui {

    private GuiScreen previous;
    private TypeCustom custom;

    public CustomToggleTest(GuiScreen previous, TypeCustom customIn) {
        this.previous = previous;

        this.custom = customIn;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        drawCenteredString(this.fontRendererObj, String.format("Testing %s", ChatColor.GOLD + this.custom._getName()), this.width / 2, this.height / 2 - 115, Color.WHITE.getRGB());

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
