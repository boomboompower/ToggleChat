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
package me.boomboompower.togglechat;

import me.boomboompower.togglechat.toggles.ToggleBase;
import me.boomboompower.togglechat.utils.ChatColor;
import me.boomboompower.togglechat.utils.GlobalUtils;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ToggleEvents {

    public ToggleEvents() {
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onChatReceive(ClientChatReceivedEvent event) {
        String formattedText = event.message.getFormattedText();
        String unformattedText = ChatColor.stripColor(event.message.getUnformattedText());
        try {
            if (!containsWhitelisted(unformattedText)) {
                for (ToggleBase type : Options.getInstance().getBaseTypes().values()) {
                    if (type.isMessage(type.useUnformattedMessage() ? unformattedText : formattedText) && !type.isEnabled()) {
                        event.setCanceled(true);
                        break;
                    }
                }
            }
        } catch (Exception e1) {
            GlobalUtils.log("Issue has been encountered: " + (e1.getMessage() != null ? e1.getMessage() : e1.getCause()));
        }
    }

    private boolean containsWhitelisted(String message) {
        final boolean[] contains = {false};
        ToggleChat.getInstance().getWhitelist().forEach(s -> {
        if (GlobalUtils.containsIgnoreCase(message, s)) {
            contains[0] = true;
        }});
        return contains[0];
    }
}