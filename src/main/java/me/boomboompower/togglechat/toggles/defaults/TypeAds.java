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

package me.boomboompower.togglechat.toggles.defaults;

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.ToggleBase;
import me.boomboompower.togglechat.utils.ChatColor;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeAds extends ToggleBase {

    private Pattern networkBoosterPattern = Pattern.compile("\nBuying a (?<game>.*) Network Booster activates (?<coinboost>.*) for (?<count>.*) players & supports the server!\nClick to browse Network Boosters! (?<thing>.) (?<site>.*)\n");
    private Pattern mysteryPattern = Pattern.compile("\nMystery Boxes contain tons of awesome collectibles! Unlock Housing items, find legendary Pets and more!\nClick to browse Mystery Boxes! (?<symbol>.) (?<site>.*)\n");
    private Pattern mediaPattern = Pattern.compile("\nSee all the posts shared by Hypixel on (?<name>.*)!\nLike the Hypixel page! (?<special>.) (?<link>.*)\n");

    public boolean showAds = true;

    @Override
    public String getName() {
        return "Ads";
    }

    @Override
    public String getDisplayName() {
        return "Ads: %s";
    }

    @Override
    public boolean shouldToggle(String message) {
        return this.networkBoosterPattern.matcher(message).matches() || this.mysteryPattern.matcher(message).matches();
    }

    @Override
    public boolean isEnabled() {
        return this.showAds;
    }

    @Override
    public void setToggled(boolean enabled) {
        this.showAds = enabled;
    }

    @Override
    public void onClick(ModernButton button) {
        this.showAds = !this.showAds;
        button.setText(String.format(getDisplayName(), ModernGui.getStatus(isEnabled())));
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
                "Toggles all server chat",
                "advertisements such as",
                "things prompting the",
                "store page",
                "",
                "This cleans up the chat",
                "whilst you are afk",
                "so you don\'t miss",
                "important messages"
        );
    }
}
