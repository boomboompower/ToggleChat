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

import wtf.boomy.togglechat.toggles.Categories;
import wtf.boomy.togglechat.toggles.ToggleBase;

import java.util.regex.Pattern;

public class TypePlayerInformation extends ToggleBase {

    private final Pattern playerInfoHeader = Pattern.compile("\\[PLAYER INFORMATION]");
    private final Pattern playerInfoLineOne = Pattern.compile("(Found a rule breaker|Have a question)\\? (Our report system can help|Our help menu may have your answer)!");
    private final Pattern playerInfoLineTwo = Pattern.compile("(Type /report <name> and follow the prompts to report them|Type /help and look through our many helpful links)\\.");

    @Override
    public String getName() {
        return "Player Info";
    }

    @Override
    public String getDisplayName() {
        return "Player Info: %s";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.playerInfoHeader.matcher(message).matches() ||
                this.playerInfoLineOne.matcher(message).matches() ||
                this.playerInfoLineTwo.matcher(message).matches();
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Toggles Player",
                "information announcements",
                "",
                "&3[PLAYER INFORMATION]",
                "Found a rule breaker...",
                "Type &b/report <name> &rand..."
        };
    }
    
    @Override
    public Categories getCategory() {
        return Categories.QOL;
    }
}
