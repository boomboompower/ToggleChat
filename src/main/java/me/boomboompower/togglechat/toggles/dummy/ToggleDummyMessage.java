package me.boomboompower.togglechat.toggles.dummy;

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.toggles.ToggleBase;

import java.util.LinkedList;

public class ToggleDummyMessage extends ToggleBase {

    private LinkedList<String> message;

    public ToggleDummyMessage(String... message) {
        this.message = asLinked(message);
    }

    @Override
    public String getName() {
        return "Dummy";
    }

    @Override
    public boolean shouldToggle(String message) {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setToggled(boolean isToggled) {
    }

    @Override
    public void onClick(ModernButton button) {
    }

    @Override
    public LinkedList<String> getDescription() {
        return this.message;
    }
}
