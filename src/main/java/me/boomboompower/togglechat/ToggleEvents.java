/*
 *     Copyright (C) 2018 boomboompower
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
import me.boomboompower.togglechat.toggles.custom.TypeCustom;
import me.boomboompower.togglechat.toggles.defaults.TypeMessageSeparator;
import me.boomboompower.togglechat.utils.ChatColor;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Pattern;

/**
 * The ToggleChat chat handler, this is the core of all the chat-logic. Actually semi-decent code
 *
 * @author boomboompower
 * @version 4.0
 */
public class ToggleEvents {
    
    private final ToggleChatMod mod;
    
    public ToggleEvents(ToggleChatMod mod) {
        this.mod = mod;
    }
    
    @SubscribeEvent(priority = EventPriority.LOW) // We use the low priority to grab things first
    public void onChatReceive(ClientChatReceivedEvent event) {
        // Strip the message of any colors for improved detectability
        String unformattedText = ChatColor.stripColor(event.message.getUnformattedText());
        
        // The formatted message for a few of the custom toggles
        String formattedText = event.message.getFormattedText();
        
        try {
            // Check if the message contains something from
            // The whitelist, if it doesn't, continue!
            if (!containsWhitelisted(unformattedText)) {
                
                // Loop through all the toggles
                for (ToggleBase type : ToggleBase.getToggles().values()) {
                    // We don't want an issue with one toggle bringing
                    // the whole toggle system crashing down in flames.
                    try {
                        // The text we want to input into the shouldToggle method.
                        String input = type.useFormattedMessage() ? formattedText : unformattedText;
                        
                        // If the toggle should toggle the specified message and
                        // the toggle is not enabled (this message is turned off)
                        // don't send the message to the player & stop looping
                        if (!type.isEnabled() && type.shouldToggle(input)) {
                            if (type instanceof TypeMessageSeparator && formattedText.contains("\n")) { // Test for newlines
                                event.message = new ChatComponentText(((TypeMessageSeparator) type).editMessage(formattedText));
                            } else {
                                event.setCanceled(true);
                            }
                            break;
                        }
                    } catch (Exception ex) {
                        printSmartEx(type, ex);
                    }
                }
            }
        } catch (Exception e1) {
            printSmartEx(null, e1);
        }
    }
    
    private boolean containsWhitelisted(String message) {
        final boolean[] contains = {false};
        this.mod.getConfigLoader().getWhitelist().forEach(s -> {
            if (containsIgnoreCase(message, s)) {
                contains[0] = true;
            }
        });
        return contains[0];
    }
    
    private boolean containsIgnoreCase(String message, String contains) {
        return Pattern.compile(Pattern.quote(contains), Pattern.CASE_INSENSITIVE).matcher(message)
            .find();
    }
    
    private void printSmartEx(ToggleBase baseIn, Exception ex) {
        String message = "";
        
        if (baseIn != null) {
            if (baseIn instanceof TypeCustom) {
                message = "[" + ((TypeCustom) baseIn)._getName() + ":" + ((TypeCustom) baseIn)
                    ._getConditions().size() + " ] ";
            } else {
                message = "[" + baseIn.getName() + " ] ";
            }
        } else {
            message += "[Unknown] ";
        }
        
        if (baseIn != null && ex != null) {
            message += "An issue was encountered \"" + ex.getClass().getSimpleName() + "\" ";
            
            if (ex.getMessage() != null) {
                message += ex.getMessage() + ", ";
            }
            if (ex.getCause() != null) {
                message += ex.getCause();
            }
        } else {
            message += "An unknown issue was encountered, please remove all other ToggleChat addons before reporting this issue!";
        }
        
        System.err.print(message);
    }
}