package me.boomboompower.togglechat.gui.modern.gui;

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.config.ConfigLoader;
import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.ToggleBase;
import me.boomboompower.togglechat.toggles.dummy.ToggleDummyMessage;
import me.boomboompower.togglechat.utils.ChatColor;

import org.apache.commons.lang3.text.WordUtils;

public class ModernConfigGui extends ModernGui {

    private static final ConfigLoader configLoader = ToggleChatMod.getInstance().getConfigLoader();

    @Override
    public void initGui() {
        this.buttonList.add(new ModernButton(1, "blur", this.width / 2 - 75, this.height / 2 - 32, 150, 20, "Blur: " + getStatus(configLoader.isModernBlur())).setButtonData(
                // Blur settings
                new ToggleDummyMessage("Toggles Gaussian bluring", "&aOn&r or &cOff&r", "on all our menus", "", "Created by &6tterrag1098", "for the BlurMC mod")
        ));
        this.buttonList.add(new ModernButton(2, "button", this.width / 2 - 75, this.height / 2 - 10, 150, 20, "Buttons: " + getClassic(configLoader.isModernButton())).setButtonData(
                // Button editing
                new ToggleDummyMessage("Changes the button", "theme to either", "&6Modern&r or &bClassic", "", "&6Modern&r is see-through", "&bClassic&r is texture based")
        ));
        this.buttonList.add(new ModernButton(3, "button", this.width / 2 - 75, this.height / 2 + 12, 150, 20, "Textbox: " + getClassic(configLoader.isModernButton())).setButtonData(
                // Textbox editing
                new ToggleDummyMessage("Changes the textbox", "theme to either", "&6Modern&r or &bClassic")
        ));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);

        writeInformation(this.width / 2, this.height / 2 + 40, 12,
                "&6Modern&r is our custom theme",
                "featuring cleaner interfaces and game bluring",
                "",
                "&bClassic&r is the default game",
                "theme, usually based on texturepacks"
        );

        checkHover(this.height / 2 - 75);
    }

    @Override
    public void buttonPressed(ModernButton button) {


        switch (button.getId()) {
            case 1:
                configLoader.setModernBlur(!configLoader.isModernBlur());
                button.setText("Blur: " + getStatus(configLoader.isModernBlur()));
                ToggleChatMod.getInstance().getBlurModHandler().reload();
                break;
            case 2:
                configLoader.setModernButton(!configLoader.isModernButton());
                button.setText("Buttons: " + getClassic(configLoader.isModernButton()));
                break;
            case 3:
                configLoader.setModernTextbox(!configLoader.isModernTextbox());
                button.setText("Textbox: " + getClassic(configLoader.isModernTextbox()));
                break;
        }
    }

    private String nameFormat(ToggleBase base) {
        return base.capitalizeName() ? WordUtils.capitalizeFully(base.getDisplayName().toLowerCase()) : base.getDisplayName();
    }

    private String getClassic(boolean config) {
        return (config ? ChatColor.AQUA + "Classic" : ChatColor.GOLD + "Modern");
    }
}
