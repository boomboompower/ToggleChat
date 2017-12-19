package me.boomboompower.togglechat.gui.togglechat;

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.defaults.TypeGlobal;

public class GlobalToggleGui extends ModernGui {

    //        - 99
    //        - 75
    //        - 51
    //        - 27
    //        - 3
    //        + 21
    //        + 45
    //        + 69

    private TypeGlobal hook;

    private ModernButton non;
    private ModernButton vip;
    private ModernButton vipPlus;
    private ModernButton mvp;
    private ModernButton mvpPlus;
    private ModernButton mvpPlusPlus;
    private ModernButton youtube;

    public GlobalToggleGui(TypeGlobal input) {
        this.hook = input;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();

        this.buttonList.add(new ModernButton(0, this.width / 2 - 75, this.height / 2 - 75, 150, 20, "No rank: " + ModernGui.getStatus(this.hook.showNon)));
        this.buttonList.add(new ModernButton(1, this.width / 2 - 75, this.height / 2 - 51, 150, 20, "VIP: " + ModernGui.getStatus(this.hook.showVip)));
        this.buttonList.add(new ModernButton(2, this.width / 2 - 75, this.height / 2 - 27, 150, 20, "VIP+: " + ModernGui.getStatus(this.hook.showVipPlus)));
        this.buttonList.add(new ModernButton(3, this.width / 2 - 75, this.height / 2 - 3, 150, 20, "MVP: " + ModernGui.getStatus(this.hook.showMvp)));
        this.buttonList.add(new ModernButton(4, this.width / 2 - 75, this.height / 2 + 21, 150, 20, "MVP+: " + ModernGui.getStatus(this.hook.showMvpPlus)));
        this.buttonList.add(new ModernButton(5, this.width / 2 - 75, this.height / 2 + 45, 150, 20, "MVP++: " + ModernGui.getStatus(this.hook.showMvpPlusPlus)));
        this.buttonList.add(new ModernButton(6, this.width / 2 - 75, this.height / 2 + 69, 150, 20, "Youtube: " + ModernGui.getStatus(this.hook.showYoutube)));

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void buttonPressed(ModernButton button) {
        switch (button.getId()) {
            case 0:
                this.hook.showNon = !this.hook.showNon;
                button.setText("No rank: " + ModernGui.getStatus(this.hook.showNon));
                break;
            case 1:
                this.hook.showVip = !this.hook.showVip;
                button.setText("VIP: " + ModernGui.getStatus(this.hook.showVip));
                break;
            case 2:
                this.hook.showVipPlus = !this.hook.showVipPlus;
                button.setText("VIP+: " + ModernGui.getStatus(this.hook.showVipPlus));
                break;
            case 3:
                this.hook.showMvp = !this.hook.showMvp;
                button.setText("MVP: " + ModernGui.getStatus(this.hook.showMvp));
                break;
            case 4:
                this.hook.showMvpPlus = !this.hook.showMvpPlus;
                button.setText("MVP+: " + ModernGui.getStatus(this.hook.showMvpPlus));
                break;
            case 5:
                this.hook.showMvpPlusPlus = !this.hook.showMvpPlusPlus;
                button.setText("MVP++: " + ModernGui.getStatus(this.hook.showMvpPlusPlus));
                break;
            case 6:
                this.hook.showYoutube = !this.hook.showYoutube;
                button.setText("Youtube: " + ModernGui.getStatus(this.hook.showYoutube));
                break;
        }
    }
}
