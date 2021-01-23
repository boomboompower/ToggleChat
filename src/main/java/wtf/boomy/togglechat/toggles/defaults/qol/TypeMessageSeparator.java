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

package wtf.boomy.togglechat.toggles.defaults.qol;

import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import wtf.boomy.togglechat.toggles.ToggleBase;

import java.util.regex.Pattern;

public class TypeMessageSeparator extends ToggleBase {

    private final Pattern separatorPattern = Pattern.compile("([-▬]+)\\n??");
    
    @Override
    public String getName() {
        return "Separators";
    }

    @Override
    public boolean shouldToggle(String message) {
        if (this.separatorPattern.matcher(message).matches()) {
            return true;
        }
    
        return message.contains("------");
    }
    
    @Override
    public String[] getDescription() {
        return new String[] {
                "Toggles all messages",
                "that contain a lot",
                "of separators",
                "",
                "Checks for message",
                "separators that look",
                "like this",
                "-----------------",
                "",
                "Less lines = more fun"
        };
    }
    
    /**
     * Removes separator siblings from a chat component. This only runs in a single pass, meaning nestled siblings will not be scanned,
     * and therefore nestled separators will not be removed from messages.
     *
     * The first component (the parent) is replaced with an empty string if it matches the separator pattern,
     * so it's worth noting that occasionally the component being supplied to the method is not always the one coming
     * out again.
     *
     * Using {@link IChatComponent#iterator()} is not recommended as it creates a copy.
     * If using an iterator for the chat components, then use {@link IChatComponent#getSiblings().iterator()}
     * instead since this will make the entries in the sibling list actually modifiable.
     *
     * @param component the component to strip of its separators.
     * @return the component for the chat message. This will only be a new component if the
     * parent chat component is a text component and matches the separator requirement in {@link #doesPatternMatch(IChatComponent)}
     */
    public IChatComponent removeSeparators(IChatComponent component) {
        // In the vanilla game everything is part of the style.
        if (!(component instanceof ChatComponentStyle)) return component;
        
        // We only really want to modify text components.
        if (component instanceof ChatComponentText) {
            // Check it for a pattern match
            if (doesPatternMatch(component)) {
                // Construct a new TextComponent to replace the other one
                ChatComponentText newText = new ChatComponentText("");
                
                // Set the chat style and the siblings to the former component
                // so it follows the same format and has the same chat colors.
                newText.setChatStyle(component.getChatStyle());
                newText.getSiblings().addAll(component.getSiblings());
                
                // Discard the old component.
                component = newText;
            }
        }
        
        // Remove all siblings if their pattern matches.
        component.getSiblings().removeIf(this::doesPatternMatch);
        
        return component;
    }
    
    /**
     * Checks if a component matches our separator pattern.
     *
     * @param in the chat message to test.
     * @return true if the pattern matches and this message should be detected as a separator.
     */
    private boolean doesPatternMatch(IChatComponent in) {
        if (in == null) return false;
        
        String text = in.getUnformattedTextForChat();
        
        return this.separatorPattern.matcher(text).matches();
    }
}
