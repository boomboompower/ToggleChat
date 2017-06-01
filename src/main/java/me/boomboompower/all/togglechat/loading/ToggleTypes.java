/*
 *     Copyright (C) 2016 boomboompower
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

package me.boomboompower.all.togglechat.loading;

import me.boomboompower.all.togglechat.Options;
import me.boomboompower.all.togglechat.ToggleChat;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;

public class ToggleTypes {

    private static final String ENABLED = EnumChatFormatting.GREEN + "Enabled";
    private static final String DISABLED = EnumChatFormatting.RED + "Disabled";

    private static ToggleTypes instance;

    public ToggleTypes() {
        instance = this;
    }

    public static ToggleTypes getInstance() {
        return instance;
    }

    public interface ToggleBase {

        public String getName();

        public int getId();

        public boolean isMessage(String message);

        public boolean isEnabled();

        public void onClick(GuiButton button);

        default String getDisplayName() {
            return getName() + ": %s";
        }

        default Boolean defaultValue() {
            return false;
        }
    }

    public static class TypeUHC implements ToggleBase {

        @Override
        public String getName() {
            return "UHC";
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public boolean isMessage(String message) {
            boolean isUHC = false;

            char[] charzz = message.toCharArray();

            if (charzz.length > 3) {
                if (charzz[0] == '[' && (charzz[3] == ']' || charzz[4] == ']')) {
                    if (Character.isDigit(charzz[1])) {
                        if (Character.isDefined(charzz[2]) || Character.isDefined(charzz[3])) {
                            isUHC = true;
                        }
                    }
                }
            }
            return isUHC;
        }

        @Override
        public boolean isEnabled() {
            return Options.showUHC;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showUHC = !Options.showUHC;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static class TypeTeam implements ToggleBase {

        @Override
        public String getName() {
            return "Team";
        }

        @Override
        public int getId() {
            return 1;
        }

        @Override
        public boolean isMessage(String message) {
            return message.startsWith("[TEAM] ");
        }

        @Override
        public boolean isEnabled() {
            return Options.showTeam;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showTeam = !Options.showTeam;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static class TypeJoin implements ToggleBase {

        @Override
        public String getName() {
            return "Join";
        }

        @Override
        public int getId() {
            return 2;
        }

        @Override
        public boolean isMessage(String message) {
            return message.endsWith(" joined.");
        }

        @Override
        public boolean isEnabled() {
            return Options.showJoin;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showJoin = !Options.showJoin;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static class TypeLeave implements ToggleBase {

        @Override
        public String getName() {
            return "Leave";
        }

        @Override
        public int getId() {
            return 3;
        }

        @Override
        public boolean isMessage(String message) {
            return message.endsWith(" left.");
        }

        @Override
        public boolean isEnabled() {
            return Options.showLeave;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showLeave = !Options.showLeave;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static class TypeGuild implements ToggleBase {

        @Override
        public String getName() {
            return "Guild";
        }

        @Override
        public int getId() {
            return 4;
        }

        @Override
        public boolean isMessage(String message) {
            return message.startsWith("Guild > ") || message.startsWith("G > ");
        }

        @Override
        public boolean isEnabled() {
            return Options.showGuild;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showGuild = !Options.showGuild;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static class TypeParty implements ToggleBase {

        @Override
        public String getName() {
            return "Party";
        }

        @Override
        public int getId() {
            return 5;
        }

        @Override
        public boolean isMessage(String message) {
            return message.startsWith("Party > ") || message.startsWith("P > ");
        }

        @Override
        public boolean isEnabled() {
            return Options.showParty;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showParty = !Options.showParty;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static class TypeShout implements ToggleBase {

        @Override
        public String getName() {
            return "Shout";
        }

        @Override
        public int getId() {
            return 6;
        }

        @Override
        public boolean isMessage(String message) {
            return message.startsWith("[SHOUT] ");
        }

        @Override
        public boolean isEnabled() {
            return Options.showShout;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showShout = !Options.showShout;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static class TypeHousing implements ToggleBase {

        @Override
        public String getName() {
            return "Housing";
        }

        @Override
        public int getId() {
            return 11;
        }

        @Override
        public boolean isMessage(String message) {
            return message.startsWith("[OWNER] ") || message.startsWith("[CO-OWNER] ") || message.startsWith("[RES] ");
        }

        @Override
        public boolean isEnabled() {
            return Options.showHousing;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showHousing = !Options.showHousing;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static class TypeColored implements ToggleBase {

        @Override
        public String getName() {
            return "Colored team";
        }

        @Override
        public int getId() {
            return 12;
        }

        @Override
        public boolean isMessage(String message) {
            return message.startsWith("[BLUE] ") || message.startsWith("[YELLOW] ") || message.startsWith("[GREEN] ") || message.startsWith("[RED] ") || message.startsWith("[WHITE] ") || message.startsWith("[PURPLE] ");
        }

        @Override
        public boolean isEnabled() {
            return Options.showColored;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showColored = !Options.showColored;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static class TypeMessage implements ToggleBase {

        @Override
        public String getName() {
            return "Messages";
        }

        @Override
        public int getId() {
            return 13;
        }

        @Override
        public boolean isMessage(String message) {
            return message.startsWith("To ") || message.startsWith("From ");
        }

        @Override
        public boolean isEnabled() {
            return Options.showMessage;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showMessage = !Options.showMessage;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static class TypePartyInvite implements ToggleBase {

        @Override
        public String getName() {
            return "Party invites";
        }

        @Override
        public int getId() {
            return 14;
        }

        @Override
        public boolean isMessage(String message) {
            return ToggleChat.containsIgnoreCase(message, "has invited you to join ") || ToggleChat.containsIgnoreCase(message, "60 seconds to accept") || (withoutColors(message).contains("The party invite from ") && withoutColors(message).endsWith(" has expired."));
        }

        @Override
        public boolean isEnabled() {
            return Options.showPartyInv;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showPartyInv = !Options.showPartyInv;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static class TypeSpectator implements ToggleBase {

        @Override
        public String getName() {
            return "Spectator";
        }

        @Override
        public int getId() {
            return 15;
        }

        @Override
        public boolean isMessage(String message) {
            return message.startsWith("[SPECTATOR] ");
        }

        @Override
        public boolean isEnabled() {
            return Options.showSpectator;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showSpectator = !Options.showSpectator;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static class TypeFriendRequest implements ToggleBase {

        @Override
        public String getName() {
            return "Friend requests";
        }

        @Override
        public int getId() {
            return 16;
        }

        @Override
        public boolean isMessage(String message) {
            return ToggleChat.containsIgnoreCase(message, "Friend request from ") || (message.contains("Click one") && message.contains("[ACCEPT]") && message.contains("[DENY]"));
        }

        @Override
        public boolean isEnabled() {
            return Options.showFriendReqs;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showFriendReqs = !Options.showFriendReqs;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static class TypeSeparators implements ToggleBase {

        @Override
        public String getName() {
            return "Separators";
        }

        @Override
        public int getId() {
            return 17;
        }

        @Override
        public boolean isMessage(String message) {
            return message.equalsIgnoreCase("-----------------------------------------------------");
        }

        @Override
        public boolean isEnabled() {
            return Options.showSeparators;
        }

        @Override
        public void onClick(GuiButton button) {
            Options.showSeparators = !Options.showSeparators;
            button.displayString = String.format(getDisplayName(), isEnabled() ? ENABLED : DISABLED);
        }
    }

    public static String withoutColors(String message) {
        return EnumChatFormatting.getTextWithoutFormattingCodes(message);
    }
}
