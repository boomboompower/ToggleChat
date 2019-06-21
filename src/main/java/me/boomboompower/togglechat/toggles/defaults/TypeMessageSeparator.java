/*
 *     Copyright (C) 2019 boomboompower
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

package me.boomboompower.togglechat.toggles.defaults;

import lombok.Getter;
import lombok.Setter;

import me.boomboompower.togglechat.toggles.ToggleBase;

import java.util.LinkedList;

public class TypeMessageSeparator extends ToggleBase {
    
    @Setter
    @Getter
    private boolean enabled = true;
    
    @Override
    public String getName() {
        return "Separators";
    }
    
    @Override
    public boolean shouldToggle(String message) {
        return message.contains("-----------");
    }
    
    /**
     * Custom method for this class only, allows the message to be stripped of all color codes
     *
     * @param formattedText the text to strip
     * @return the final text
     */
    public String editMessage(String formattedText) {
        if (formattedText.contains("▬▬")) {
            formattedText = formattedText
                .replace("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬", "")
                .replace("▬▬", "");
            return formattedText;
        }
        if (formattedText.contains("---")) {
            formattedText = formattedText
                .replace("----------------------------------------------------\n", "");
            return formattedText.replace("--\n", "").replace("\n--", "").replace("--", "");
        }
        return formattedText;
    }
    
    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
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
        );
    }
}
