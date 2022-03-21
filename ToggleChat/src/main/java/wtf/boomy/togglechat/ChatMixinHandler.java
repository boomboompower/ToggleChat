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

import net.minecraft.util.IChatComponent;
import net.minecraft.util.Tuple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wtf.boomy.mods.modernui.uis.ChatColor;
import wtf.boomy.togglechat.toggles.ToggleBase;
import wtf.boomy.togglechat.toggles.defaults.qol.TypeMessageSeparator;

import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * The ToggleChat chat mixin handler.
 *
 * @author boomboompower
 * @version 4.1
 */
public class ChatMixinHandler {

    private final Logger logger = LogManager.getLogger("ToggleChat - ChatMixinHandler");

    // Improve performance. Use already compiled patterns if available.
    private final HashMap<String, Pattern> compiledPatterns = new HashMap<>();

    private final ToggleChatMod mod;

    public ChatMixinHandler(ToggleChatMod mod) {
        this.mod = mod;
    }

    /**
     * Handles the chat message provided.
     *
     * @return Whether to cancel the message.
     * @author boomboompower
     */
    public Tuple<Boolean, IChatComponent> handleChat(IChatComponent message) {
        // Retrieve the raw text from the TextComponent then filter it again
        // through our ChatColor class to remove all remaining color codes.
        String strippedText = ChatColor.stripColor(message.getUnformattedText());

        // The formatted message for a few of the custom toggles
        String formattedText = message.getFormattedText();

        try {
            // Check if the message contains something from
            // The unblocked list, if it doesn't, continue!
            if (!containsUnblocked(strippedText)) {
                // Loop through all the toggles
                for (ToggleBase type : mod.getToggleHandler().getToggles().values()) {
                    // We don't want an issue with one toggle bringing
                    // the whole toggle system crashing down in flames.
                    try {
                        // The text we want to input into the shouldToggle method.
                        String input = type.useFormattedMessage() ? formattedText : strippedText;

                        // If the toggle should toggle the specified message and
                        // the toggle is not enabled (this message is turned off)
                        // don't send the message to the player & stop looping
                        if (!type.isEnabled() && type.shouldToggle(input)) {
                            if (type instanceof TypeMessageSeparator) {
                                return new Tuple<>(false, ((TypeMessageSeparator) type).removeSeparators(message));
                            } else {
                                return new Tuple<>(true, message);
                            }
                        }
                    } catch (Exception ex) {
                        logger.error("An error occurred while checking a message against a toggle {}", type, ex);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("An error occurred whilst comparing a message with the allow list.", ex);
        }
        return new Tuple<>(false, message);
    }

    /**
     * Check to see if a given message contains a unblocked user/name/phrase
     *
     * @param message the message to check (case insensitive)
     * @return true if the message contains a unblocked phrase
     */
    private boolean containsUnblocked(String message) {
        // Use Java 8 streams to find any matches with our requirements
        // this is better than the previous implementation since anyMatch is self-terminating
        // meaning it will return immediately as soon as one of the criteria is met.
        return mod.getConfigLoader().getWhitelist().stream().anyMatch(s -> containsIgnoreCase(message, s));
    }

    /**
     * Case insensitive matcher.
     * <p>
     * Caches the compiled patterns for later use (to save on performance).
     *
     * @param message  the message to search
     * @param contains what to search for
     * @return true if the message contains the phrase
     */
    private boolean containsIgnoreCase(String message, String contains) {
        Pattern using = compiledPatterns.get(contains);

        if (using == null) {
            try {
                // We want to compile a case-insensitive pattern for later use
                using = Pattern.compile(Pattern.quote(contains), Pattern.CASE_INSENSITIVE);

                // Add the pattern to the precompiled pattern list.
                compiledPatterns.put(contains, using);
            } catch (PatternSyntaxException ex) {
                // An error occurred when making the pattern. This is likely due to weird syntax
                // when converting the raw message into a quoted pattern.
                logger.error("Failed to compile a pattern using {}", contains, ex);
            }
        }

        // If we've reached this stage it's likely that the previous
        // pattern resulted in a syntax error. Just assume the message
        // does not actually contain the allowed message to prevent
        // a game crash.
        if (using == null) {
            return false;
        }

        // Identify if the message contains the text using Matcher#find() instead of Matcher#matches()
        // since find() detects the pattern in a message, but doesn't require the entire message to follow
        // the regex we compiled previously.
        return using.matcher(message).find();
    }
}