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

package me.boomboompower.all.togglechat;

import me.boomboompower.all.togglechat.utils.GlobalUtils;
import me.boomboompower.all.togglechat.utils.Writer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class Options {

    private static final String ENABLED = EnumChatFormatting.GREEN + "Enabled";
    private static final String DISABLED = EnumChatFormatting.RED + "Disabled";

    public static boolean showUHC = true;
    public static boolean showSpec = true;
    public static boolean showTeam = true;
    public static boolean showJoin = true;
    public static boolean showLeave = true;
    public static boolean showGuild = true;
    public static boolean showParty = true;
    public static boolean showShout = true;
    public static boolean showHousing = true;
    public static boolean showColored = true;
    public static boolean showMessage = true;
    public static boolean showPartyInv = true;
    public static boolean showFriendReqs = true;
    public static boolean showSeparators = true;

//    public static boolean ignoreUHC = false;
//    public static boolean ignoreSpec = false;
//    public static boolean ignoreTeam = false;
//    public static boolean ignoreJoin = false;
//    public static boolean ignoreLeave = false;
//    public static boolean ignoreGuild = false;
//    public static boolean ignoreParty = false;
//    public static boolean ignoreShout = false;
//    public static boolean ignoreHousing = false;
//    public static boolean ignoreColored = false;
//    public static boolean ignoreMessage = false;
//    public static boolean ignorePartyInv = false;
//    public static boolean ignoreFriendReqs = false;
//    public static boolean ignoreSeparators = false;

    private static Options instance;

    public Options() {
        instance = this;

        doTutorialCheck();
    }

    public static Options getInstance() {
        return instance;
    }

    public void setup(ConfigType type, List<String> lines) {
        switch (type) {
            case MAIN_OPTIONS:
                try {
                    Options.showTeam = Boolean.parseBoolean(lines.get(0));
                    Options.showJoin = Boolean.parseBoolean(lines.get(1));
                    Options.showLeave = Boolean.parseBoolean(lines.get(2));
                    Options.showGuild = Boolean.parseBoolean(lines.get(3));
                    Options.showParty = Boolean.parseBoolean(lines.get(4));
                    Options.showShout = Boolean.parseBoolean(lines.get(5));
                    Options.showMessage = Boolean.parseBoolean(lines.get(6));

                    Options.showUHC = Boolean.parseBoolean(lines.get(7));
                    Options.showPartyInv = Boolean.parseBoolean(lines.get(8));
                    Options.showFriendReqs = Boolean.parseBoolean(lines.get(9));

                    Options.showSpec = Boolean.parseBoolean(lines.get(10));
                    Options.showColored = Boolean.parseBoolean(lines.get(11));

                    Options.showHousing = Boolean.parseBoolean(lines.get(12));
                    Options.showColored = Boolean.parseBoolean(lines.get(13));
                } catch (Exception ex) {
                    GlobalUtils.log("Failed to setup all main option values. Rewriting!");
                    Writer.execute();
                    GlobalUtils.log("------------------- ERROR -------------------");
                    ex.printStackTrace();
                    GlobalUtils.log("------------------- ERROR -------------------");
                    GlobalUtils.log("The error and its details are shown above.");
                }
                break;
//            case WHITELIST_OPTIONS:
//                try {
//                    Options.ignoreUHC = Boolean.parseBoolean(lines.get(0));
//                    Options.ignoreSpec = Boolean.parseBoolean(lines.get(1));
//                    Options.ignoreTeam = Boolean.parseBoolean(lines.get(2));
//                    Options.ignoreJoin = Boolean.parseBoolean(lines.get(3));
//                    Options.ignoreLeave = Boolean.parseBoolean(lines.get(4));
//                    Options.ignoreGuild = Boolean.parseBoolean(lines.get(5));
//                    Options.ignoreParty = Boolean.parseBoolean(lines.get(6));
//                    Options.ignoreShout = Boolean.parseBoolean(lines.get(7));
//                    Options.ignoreHousing = Boolean.parseBoolean(lines.get(8));
//                    Options.ignoreColored = Boolean.parseBoolean(lines.get(9));
//                    Options.ignoreMessage = Boolean.parseBoolean(lines.get(10));
//                    Options.ignorePartyInv = Boolean.parseBoolean(lines.get(11));
//                    Options.ignoreFriendReqs = Boolean.parseBoolean(lines.get(12));
//                    Options.ignoreSeparators = Boolean.parseBoolean(lines.get(13));
//                }
//                catch (Exception ex) {
//                    GlobalUtils.log("Failed to setup whitelist ignoring values. Rewriting!");
//                    Writer.execute(false, true);
//                    GlobalUtils.log("------------------- ERROR -------------------");
//                    ex.printStackTrace();
//                    GlobalUtils.log("------------------- ERROR -------------------");
//                    GlobalUtils.log("The error and its details are shown above.");
//                }
//                break;
        }
    }

    public boolean toggle(ToggleType type) {
        switch (type) {
            // Which chat should be toggled
            case CHAT_UHC:
                return (showUHC = !showUHC);
            case CHAT_TEAM:
                return (showTeam = !showTeam);
            case CHAT_JOIN:
                return (showJoin = !showJoin);
            case CHAT_LEAVE:
                return (showLeave = !showLeave);
            case CHAT_GUILD:
                return (showGuild = !showGuild);
            case CHAT_PARTY:
                return (showParty = !showParty);
            case CHAT_SHOUT:
                return (showShout = !showShout);
            case CHAT_MESSAGE:
                return (showMessage = !showMessage);
            case CHAT_HOUSING:
                return (showHousing = !showHousing);
            case CHAT_PARTYINV:
                return (showPartyInv = !showPartyInv);
            case CHAT_FRIENDREQ:
                return (showFriendReqs = !showFriendReqs);
            case CHAT_SPECTATOR:
                return (showSpec = !showSpec);
            case CHAT_SEPARATOR:
                return (showSeparators = !showSeparators);
            case CHAT_COLORED_TEAM:
                return (showColored = !showColored);

//            // Whitelist chat ignore list.
//            case WHITELIST_UHC:
//                return (ignoreUHC = !ignoreUHC);
//            case WHITELIST_TEAM:
//                return (ignoreTeam = !ignoreTeam);
//            case WHITELIST_JOIN:
//                return (ignoreJoin = !ignoreJoin);
//            case WHITELIST_LEAVE:
//                return (ignoreLeave = !ignoreLeave);
//            case WHITELIST_GUILD:
//                return (ignoreGuild = !ignoreGuild);
//            case WHITELIST_PARTY:
//                return (ignoreParty = !ignoreParty);
//            case WHITELIST_SHOUT:
//                return (ignoreShout = !ignoreShout);
//            case WHITELIST_MESSAGE:
//                return (ignoreMessage = !ignoreMessage);
//            case WHITELIST_HOUSING:
//                return (ignoreHousing = !ignoreHousing);
//            case WHITELIST_PARTYINV:
//                return (ignorePartyInv = !ignorePartyInv);
//            case WHITELIST_FRIENDREQ:
//                return (ignoreFriendReqs = !ignoreFriendReqs);
//            case WHITELIST_SPECTATOR:
//                return (ignoreSpec = !ignoreSpec);
//            case WHITELIST_SEPARATOR:
//                return (ignoreSeparators = !ignoreSeparators);
//            case WHITELIST_COLORED_TEAM:
//                return (ignoreColored = !ignoreColored);

            // Should never happen
            default:
                return true;
        }
    }

    public void updateButton(GuiButton button, ToggleType type) {
        String prefix;
        boolean enabled;

        switch (type) {
            case CHAT_UHC:
                prefix = "UHC: ";
                enabled = showUHC;
                break;
            case CHAT_TEAM:
                prefix = "Team: ";
                enabled = showTeam;
                break;
            case CHAT_JOIN:
                prefix = "Join: ";
                enabled = showJoin;
                break;
            case CHAT_LEAVE:
                prefix = "Leave: ";
                enabled = showLeave;
                break;
            case CHAT_GUILD:
                prefix = "Guild: ";
                enabled = showGuild;
                break;
            case CHAT_PARTY:
                prefix = "Party: ";
                enabled = showParty;
                break;
            case CHAT_SHOUT:
                prefix = "Shout: ";
                enabled = showShout;
                break;
            case CHAT_MESSAGE:
                prefix = "Messages: ";
                enabled = showMessage;
                break;
            case CHAT_HOUSING:
                prefix = "Housing: ";
                enabled = showHousing;
                break;
            case CHAT_PARTYINV:
                prefix = "Party invites: ";
                enabled = showPartyInv;
                break;
            case CHAT_FRIENDREQ:
                prefix = "Friend requests: ";
                enabled = showFriendReqs;
                break;
            case CHAT_SPECTATOR:
                prefix = "Spectator: ";
                enabled = showSpec;
                break;
            case CHAT_SEPARATOR:
                prefix = "Separator: ";
                enabled = showSeparators;
                break;
            case CHAT_COLORED_TEAM:
                prefix = "Colored team: ";
                enabled = showColored;
                break;
            default:
                prefix = String.format("Unknown (ID:%s): ", button.id);
                enabled = false;
                break;
        }

        button.displayString = prefix + (enabled ? ENABLED : DISABLED);
    }

    private void doTutorialCheck() {
        try {
            Class.forName("me.boomboompower.all.togglechat.gui.tutorial.TutorialGui");
        } catch (Exception ex) {
            ToggleChat.tutorialEnabled = false;
        }
    }

    public enum ConfigType {
        MAIN_OPTIONS(),
        WHITELIST_OPTIONS();

        ConfigType() {}
    }

    public enum ToggleType {
        CHAT_UHC(),
        CHAT_TEAM(),
        CHAT_JOIN(),
        CHAT_LEAVE(),
        CHAT_GUILD(),
        CHAT_PARTY(),
        CHAT_SHOUT(),
        CHAT_MESSAGE(),
        CHAT_HOUSING(),
        CHAT_PARTYINV(),
        CHAT_FRIENDREQ(),
        CHAT_SPECTATOR(),
        CHAT_SEPARATOR(),
        CHAT_COLORED_TEAM(),

//        WHITELIST_UHC(),
//        WHITELIST_TEAM(),
//        WHITELIST_JOIN(),
//        WHITELIST_LEAVE(),
//        WHITELIST_GUILD(),
//        WHITELIST_PARTY(),
//        WHITELIST_SHOUT(),
//        WHITELIST_MESSAGE(),
//        WHITELIST_HOUSING(),
//        WHITELIST_PARTYINV(),
//        WHITELIST_FRIENDREQ(),
//        WHITELIST_SPECTATOR(),
//        WHITELIST_SEPARATOR(),
//        WHITELIST_COLORED_TEAM()
        ;

        ToggleType() {}
    }
}
