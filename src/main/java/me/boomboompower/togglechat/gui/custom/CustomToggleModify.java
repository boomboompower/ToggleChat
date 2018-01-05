package me.boomboompower.togglechat.gui.custom;

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.gui.modern.scrollable.ModernGuiToggleList;
import me.boomboompower.togglechat.toggles.custom.TypeCustom;
import me.boomboompower.togglechat.utils.ChatColor;
import net.minecraft.client.gui.GuiScreen;

import java.awt.Color;
import java.io.IOException;

public class CustomToggleModify extends IHaveScrollableData {

    private ModernGuiToggleList toggleList;

    private TypeCustom custom;

    public CustomToggleModify(TypeCustom customIn) {

        this.custom = customIn;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();

        this.toggleList = new ModernGuiToggleList(this);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.toggleList.drawScreen(mouseX, mouseY, partialTicks);

        drawCenteredString("Modifying " + ChatColor.GOLD + this.custom._getName(), this.width / 2, 13, new Color(255, 255, 255).getRGB());

        drawCenteredString("Click on the toggle you would like to modify!", this.width / 2, this.height - 17, new Color(255, 255, 255).getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.toggleList.handleMouseInput();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.toggleList.mouseClicked(mouseX, mouseY, mouseButton)) {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (state != 0 || !this.toggleList.mouseReleased(mouseX, mouseY, state)) {
            super.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    public void onGuiClosed() {
        ToggleChatMod.getInstance().getConfigLoader().saveCustomToggles();
    }

    @Override
    public TypeCustom getCustom() {
        return this.custom;
    }
}
