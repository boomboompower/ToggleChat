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

import com.google.gson.JsonObject;

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.togglechat.GlobalToggleGui;
import me.boomboompower.togglechat.toggles.ICustomSaver;
import me.boomboompower.togglechat.toggles.ToggleBase;

import net.minecraft.client.Minecraft;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeGlobal extends ToggleBase implements ICustomSaver {

    private Pattern chatPattern = Pattern.compile("(?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");

    public boolean showNon = true;
    public boolean showVip = true;
    public boolean showVipPlus = true;
    public boolean showMvp = true;
    public boolean showMvpPlus = true;
    public boolean showMvpPlusPlus = true;
    public boolean showYoutube = true;

    @Override
    public String getName() {
        return "Global";
    }

    @Override
    public String getDisplayName() {
        return "Global Chat";
    }

    @Override
    public boolean shouldToggle(String message) {
        if (message.contains(":") && message.contains("distance") && message.endsWith("}")) {
            return false; // Fix skywars debug being toggled
        }

        if (ToggleBase.hasToggle("team") && ToggleBase.getToggle("team").isEnabled() && message.startsWith("[TEAM]")) {
            return false;
        }

        Matcher matcher = this.chatPattern.matcher(message);

        return matcher.matches() && isRankToggled(matcher.group("rank")) && isNotOtherChat(message);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setToggled(boolean enabled) {
    }

    @Override
    public void onClick(ModernButton button) {
        Minecraft.getMinecraft().displayGuiScreen(new GlobalToggleGui(this));
    }

    @Override
    public boolean useDefaultSave() {
        return false;
    }

    @Override
    public void onSave(JsonObject config) {

    }

    @Override
    public boolean useDefaultLoad() {
        return false;
    }

    @Override
    public void onLoad(JsonObject config) {

    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
                "Turns all general player",
                "chat on or off",
                "",
                "These are the formats",
                "&7Player: Hi",
                "&a[VIP] Player&r: Hi",
                "&a[VIP&6+&a] Player&r: Hi",
                "&b[MVP] Player&r: Hi",
                "&b[MVP&c+&b] Player&r: Hi",
                "",
                "Useful to prevent spam",
                "or any unwanted chat",
                "messages"
        );
    }

    private boolean isNotOtherChat(String input) {
        return !input.startsWith("[TEAM] ") && !input.startsWith("[SHOUT] ") && !input.startsWith("[SPECTATOR] ");
    }

    private boolean isRankToggled(String rankInput) {
        if (rankInput == null) {
            return false;
        }

        switch (rankInput) {
            case "":
                return !this.showNon;
            case "[VIP] ":
                return !this.showVip;
            case "[VIP+] ":
                return !this.showVipPlus;
            case "[MVP] ":
                return !this.showMvp;
            case "[MVP+] ":
                return !this.showMvpPlus;
            case "[MVP++]":
                return !this.showMvpPlusPlus;
            case "[YOUTUBER] ":
                return !this.showYoutube;
        }

        return false;
    }
}
