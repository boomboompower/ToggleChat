/*
 *     Copyright (C) 2021 boomboompower
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

package wtf.boomy.togglechat.gui.list;

import net.minecraft.client.gui.ChatLine;
import wtf.boomy.mods.modernui.uis.ModernGui;
import wtf.boomy.mods.modernui.uis.components.ButtonComponent;
import wtf.boomy.togglechat.mixin.GuiNewChatAccessor;
import wtf.boomy.togglechat.utils.uis.ToggleChatModernUI;
import net.minecraft.client.Minecraft;

public class ClearListUI extends ToggleChatModernUI {

    private ModernGui previousScreen;
    private Minecraft mc;

    public ClearListUI(ModernGui previous) {
        this.previousScreen = previous;

        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void onGuiOpen() {
        registerElement(new ButtonComponent(0, this.width / 2 - 200, this.height / 2 + 30, 150, 20, "Cancel").setDrawingModern(this.modernButton));
        registerElement(new ButtonComponent(1, this.width / 2 + 50, this.height / 2 + 30, 150, 20, "Confirm").setDrawingModern(this.modernButton));
    }
    
    @Override
    public void preRender(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
    }
    
    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        writeInformation(this.width / 2, this.height / 2 - 60, 15,
                String.format("Are you sure you wish to clear &6%s %s&r from your whitelist?", this.mod.getConfigLoader().getWhitelist().size(), (this.mod.getConfigLoader().getWhitelist().size() == 1 ? "entry" : "entries")),
                "This action cannot be undone, use at your own risk!"
        );
    }

    @Override
    public void buttonPressed(ButtonComponent button) {
        switch (button.getId()) {
            case 0:
                this.mc.displayGuiScreen(this.previousScreen);
                break;
            case 1:
                this.mod.getConfigLoader().getWhitelist().clear();
                sendChatMessage("Cleared the whitelist!");
                this.mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    public void onGuiClose() {
        this.mod.getConfigLoader().saveModernUtils();
        try {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().refreshChat();
        } catch (Exception e) {
            e.printStackTrace();
            ((GuiNewChatAccessor) Minecraft.getMinecraft().ingameGUI.getChatGUI()).getDrawnChatLines().clear();
            Minecraft.getMinecraft().ingameGUI.getChatGUI().resetScroll();

            for (int i = ((GuiNewChatAccessor) Minecraft.getMinecraft().ingameGUI.getChatGUI()).getChatLines().size() - 1; i >= 0; --i)
            {
                ChatLine chatline = ((GuiNewChatAccessor) Minecraft.getMinecraft().ingameGUI.getChatGUI()).getDrawnChatLines().get(i);
                ((GuiNewChatAccessor) Minecraft.getMinecraft().ingameGUI.getChatGUI()).invokeSetChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
            }
        }
    }
}
