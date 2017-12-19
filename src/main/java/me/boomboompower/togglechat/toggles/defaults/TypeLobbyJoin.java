package me.boomboompower.togglechat.toggles.defaults;

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.ToggleBase;

import java.util.LinkedList;

public class TypeLobbyJoin extends ToggleBase {

    private boolean enabled = true;

    @Override
    public String getName() {
        return "lobby_join";
    }

    @Override
    public String getDisplayName() {
        return "Lobby join: %s";
    }

    @Override
    public boolean shouldToggle(String message) {
        return message.endsWith("joined the lobby!") || (message.contains("joined the lobby") && message.startsWith(" >>>"));
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void setToggled(boolean isToggled) {
        this.enabled = isToggled;
    }

    @Override
    public void onClick(ModernButton button) {
        this.enabled = !this.enabled;
        button.setText(String.format(getDisplayName(), ModernGui.getStatus(isEnabled())));
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
                "Removes all MVP+",
                "lobby join messages",
                "",
                "Such as:",
                "&b[MVP&c+&b] I &6joined the lobby!"
        );
    }
}
