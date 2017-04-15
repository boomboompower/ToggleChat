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
            if (message.startsWith("Guild > ") && !ToggleChat.showGuild) {
                cancelled = true;
            }
            if (message.startsWith("Party > ") && !ToggleChat.showParty) {
                cancelled = true;
            }
            if (message.endsWith(" joined.") && !ToggleChat.showJoin) {
                cancelled = true;
            }
            if (isLeave(message) && !ToggleChat.showLeave) {
                cancelled = true;
            }
            if (message.startsWith("[SPECTATOR] ") && !ToggleChat.showSpec) {
                cancelled = true;
            }
            if (message.startsWith("[SHOUT] ") && !ToggleChat.showShout) {
                cancelled = true;
            }
            if (message.startsWith("[TEAM] ") && !ToggleChat.showTeam) {
                cancelled = true;
            }
            if ((message.startsWith("To ") || message.startsWith("From ")) && !ToggleChat.showMessage) {
                cancelled = true;
            }
            if (isUHC(message) && !ToggleChat.showUHC) {
                cancelled = true;
            }
            if (isPartyInv(message) && !ToggleChat.showPartyInv) {
                cancelled = true;
            }
            if (isFriendReq(message) && !ToggleChat.showFriendReqs) {
                cancelled = true;
            }
            if (isColoredChat(message) && !ToggleChat.showColored) {
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

    private boolean isFriendReq(String message) {
        return ToggleChat.containsIgnoreCase(message, "Friend request from ") || (message.contains("Click one") && message.contains("[ACCEPT]") && message.contains("[DENY]"));
    }

    private boolean isLeave(String message) {
        return message.equalsIgnoreCase("-----------------------------------------------------") || message.endsWith(" left.");
    }

    private boolean isPartyInv(String message) {
        return ToggleChat.containsIgnoreCase(message, "has invited you to join ") || ToggleChat.containsIgnoreCase(message, "60 seconds to accept") || (EnumChatFormatting.getTextWithoutFormattingCodes(message).contains("The party invite from ") && message.endsWith(" has expired."));
    }

    private boolean isColoredChat(String message) {
        return message.startsWith("[BLUE] ") || message.startsWith("[YELLOW] ") || message.startsWith("[GREEN] ") || message.startsWith("[RED] ") || message.startsWith("[WHITE] ") || message.startsWith("[PURPLE] ");
    }

    private boolean isUHC(String message) {
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
}