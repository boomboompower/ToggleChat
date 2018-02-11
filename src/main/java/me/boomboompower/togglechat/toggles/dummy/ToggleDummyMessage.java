package me.boomboompower.togglechat.toggles.dummy;

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.toggles.ToggleBase;

import java.util.LinkedList;

/**
 * A dummy ToggleBase instance used as a hack to display
 *      messages on the ModernGui screens.
 *
 * DO NOT REGISTER THIS IN THE {@link ToggleBase} CLASS!
 */
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
    public void setEnabled(boolean enabled) {
    }

    @Override
    public void onClick(ModernButton button) {
    }

    @Override
    public LinkedList<String> getDescription() {
        return this.message;
    }
}
