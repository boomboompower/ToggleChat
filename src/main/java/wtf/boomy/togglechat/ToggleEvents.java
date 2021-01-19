/*
 *     Copyright (C) 2021 boomboompower
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

package wtf.boomy.togglechat;

import wtf.boomy.togglechat.toggles.ToggleBase;
import wtf.boomy.togglechat.toggles.custom.TypeCustom;
import wtf.boomy.togglechat.toggles.defaults.TypeMessageSeparator;
import wtf.boomy.togglechat.utils.ChatColor;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
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
                                modifySeparators((TypeMessageSeparator) type, event.message, formattedText);
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

    /**
     * A hack to modify the separators in a {@link IChatComponent} by modifying sibling classes if possible
     *
     * @param toggle the TypeMessageSeparator toggle
     * @param componentIn the component to modify
     * @param formattedText the formattedText (just for an existance check)
     */
    private void modifySeparators(TypeMessageSeparator toggle, IChatComponent componentIn, String formattedText) {
        if (!formattedText.contains("▬▬") && !formattedText.contains("---")) {
            return;
        }

        // The first line of a ChatComponent is completely separate to the actual component
        if (componentIn instanceof ChatComponentText) {
            ChatComponentText original = (ChatComponentText) componentIn;

            String firstLineText = original.getUnformattedTextForChat();

            if (firstLineText.contains("---") || firstLineText.contains("▬")) {
                // Create a modified component with styling stripped.
                ChatComponentText newText = new ChatComponentText(toggle.editMessage(firstLineText));

                // Apply style
                newText.setChatStyle(original.getChatStyle());

                // Keep any styling on this first piece of text
                newText.getSiblings().addAll(componentIn.getSiblings());

                // Set the component to our new component
                componentIn = newText;
            }
        }

        if (componentIn.getSiblings().size() > 0) {
            // Loop through each sibling
            for (int i = 0; i < componentIn.getSiblings().size(); i++) {
                IChatComponent child = componentIn.getSiblings().get(i);

                if (!(child instanceof ChatComponentText)) {
                    continue;
                }

                String formattedChildText = child.getFormattedText();

                // If this does not have anything worth replacing we'll ignore it.
                if (!formattedChildText.contains("---") && !formattedChildText.contains("▬")) {
                    continue;
                }

                // Replace the contents of the message if required.
                String fixed = toggle.editMessage(formattedChildText);

                // If the text has not been modified there is no point replacing the message
                if (fixed.equals(formattedChildText)) {
                    continue;
                }

                // Create a new message from the replaced content
                ChatComponentText newComponent = new ChatComponentText(fixed);

                // Add the old style of the child onto this new component (keeps Chat events working)
                newComponent.setChatStyle(child.getChatStyle());

                // Replace the sibling in the component with our new message.
                componentIn.getSiblings().set(i, newComponent);
            }
        }
    }

    /**
     * Check to see if a given message contains a whitelisted user/name/phrase
     *
     * @param message the message to check (case insensitive)
     * @return true if the message contains a whitelisted phrase
     */
    private boolean containsWhitelisted(String message) {
        final boolean[] contains = {false};
        this.mod.getConfigLoader().getWhitelist().forEach(s -> {
            if (containsIgnoreCase(message, s)) {
                contains[0] = true;
            }
        });
        return contains[0];
    }

    /**
     * Case insensitive matcher.
     *
     * @param message the message to search
     * @param contains what to search for
     * @return true if the message contains the phrase
     */
    private boolean containsIgnoreCase(String message, String contains) {
        return Pattern.compile(Pattern.quote(contains), Pattern.CASE_INSENSITIVE).matcher(message).find();
    }

    /**
     * Logs an error to console. Handles most debugging logic
     *
     * @param baseIn the toggleBase where the error occurred
     * @param ex the exception which was printed.
     */
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