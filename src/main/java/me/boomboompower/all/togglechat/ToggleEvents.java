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

import me.boomboompower.all.togglechat.loading.ToggleTypes;
import me.boomboompower.all.togglechat.utils.GlobalUtils;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ToggleEvents {

    public ToggleEvents() {
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onChatReceive(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        try {
            if (!containsWhitelisted(message)) {
                for (ToggleTypes.ToggleBase type : Options.baseTypes.values()) {
                    if (type.isMessage(message) && !type.isEnabled()) {
                        event.setCanceled(true);
                        break;
                    }
                }
            }
        } catch (Exception e1) {
            GlobalUtils.log(e1.getMessage() != null ? e1.getMessage() : e1.getCause());
        }
    }

    private boolean containsWhitelisted(String message) {
        final boolean[] contains = {false};
        ToggleChat.whitelist.forEach(s -> {
        if (ToggleChat.containsIgnoreCase(message, s)) {
            contains[0] = true;
        }});
        return contains[0];
    }
}