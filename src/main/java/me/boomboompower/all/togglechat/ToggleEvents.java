/*
 *     Copyright (C) 2017 boomboompower
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

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ToggleEvents {

    public ToggleEvents() {}

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onChatReceive(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        boolean cancelled = false;
        if (message == null) return;
        if (!containsWhitelisted(message)) {
            if (isChat(ChatType.GUILD, message) && !Options.showGuild) {
                cancelled = true;
            }
            if (isChat(ChatType.PARTY, message) && !Options.showParty) {
                cancelled = true;
            }
            if (isChat(ChatType.JOIN, message) && !Options.showJoin) {
                cancelled = true;
            }
            if (isChat(ChatType.LEAVE, message) && !Options.showLeave) {
                cancelled = true;
            }
            if (isChat(ChatType.SPECTATOR, message) && !Options.showSpec) {
                cancelled = true;
            }
            if (isChat(ChatType.SHOUT, message) && !Options.showShout) {
                cancelled = true;
            }
            if (isChat(ChatType.COLORED, message) && !Options.showColored) {
                cancelled = true;
            }
            if (isChat(ChatType.TEAM, message) && !Options.showTeam) {
                cancelled = true;
            }
            if (isChat(ChatType.MESSAGE, message) && !Options.showMessage) {
                cancelled = true;
            }
            if (isChat(ChatType.HOUSING, message) && !Options.showHousing) {
                cancelled = true;
            }
            if (isChat(ChatType.UHC, message) && !Options.showUHC) {
                cancelled = true;
            }
            if (isChat(ChatType.SEPARATORS, message) && !Options.showSeparators) {
                cancelled = true;
            }
            if (isChat(ChatType.PARTYINVITE, message) && !Options.showPartyInv) {
                cancelled = true;
            }
            if (isChat(ChatType.FRIENDREQUEST, message) && !Options.showFriendReqs) {
                cancelled = true;
            }
        }
        event.setCanceled(cancelled);
    }

    public void sendChatMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "T" + EnumChatFormatting.BLUE + "C" + EnumChatFormatting.DARK_GRAY + " > " + EnumChatFormatting.GRAY + message));
    }

    private boolean containsWhitelisted(String message) {
        final boolean[] contains = {false};
        ToggleChat.whitelist.forEach(s -> {
        if (ToggleChat.containsIgnoreCase(message, s)) {
            contains[0] = true;
        }});
        return contains[0];
    }

    private boolean isChat(ChatType type, String message) {
        switch (type) {
            case UHC:
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
            case TEAM:
                return message.startsWith("[TEAM] ");
            case JOIN:
                return message.endsWith(" joined.");
            case LEAVE:
                return message.endsWith(" left.");
            case GUILD:
                return message.startsWith("Guild > ") || message.startsWith("G > ");
            case PARTY:
                return message.startsWith("Party > ") || message.startsWith("P > ");
            case SHOUT:
                return message.startsWith("[SHOUT] ");
            case COLORED:
                return message.startsWith("[BLUE] ") || message.startsWith("[YELLOW] ") || message.startsWith("[GREEN] ") || message.startsWith("[RED] ") || message.startsWith("[WHITE] ") || message.startsWith("[PURPLE] ");
            case HOUSING:
                return message.startsWith("[OWNER] ") || message.startsWith("[CO-OWNER] ") || message.startsWith("[RES] ");
            case MESSAGE:
                return message.startsWith("To ") || message.startsWith("From ");
            case SPECTATOR:
                return message.startsWith("[SPECTATOR] ");
            case SEPARATORS:
                return message.equalsIgnoreCase("-----------------------------------------------------");
            case PARTYINVITE:
                return ToggleChat.containsIgnoreCase(message, "has invited you to join ") || ToggleChat.containsIgnoreCase(message, "60 seconds to accept") || (EnumChatFormatting.getTextWithoutFormattingCodes(message).contains("The party invite from ") && message.endsWith(" has expired."));
            case FRIENDREQUEST:
                return ToggleChat.containsIgnoreCase(message, "Friend request from ") || (message.contains("Click one") && message.contains("[ACCEPT]") && message.contains("[DENY]"));
            default:
                return true;
        }
    }

    @SubscribeEvent
    public void onSk1erChat(ClientChatReceivedEvent event) {
        if (event.message == null || event.message.getUnformattedText().isEmpty()) return;

        if (event.message.getFormattedText().startsWith("§aG >")) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().deleteChatLine(Minecraft.getMinecraft().ingameGUI.getChatGUI().getLineCount());
        }
    }

    public enum ChatType {
        UHC(),
        NONE(),
        TEAM(),
        JOIN(),
        LEAVE(),
        GUILD(),
        PARTY(),
        SHOUT(),
        COLORED(),
        HOUSING(),
        MESSAGE(),
        SPECTATOR(),
        SEPARATORS(),
        PARTYINVITE(),
        FRIENDREQUEST();

        ChatType() {}
    }
}