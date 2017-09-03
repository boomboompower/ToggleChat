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

package me.boomboompower.togglechat.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.toggles.ToggleBase;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class ConfigLoader {

    private File toggleFile;
    private File whitelistFile;

    private JsonObject configJson;

    public ConfigLoader(String directory) {
        File e = new File(directory);
        if (!e.exists()) {
            e.mkdirs();
        }

        this.toggleFile = new File(directory + "options.nn");
        this.whitelistFile = new File(directory + "whitelist.nn");
    }
    public void loadToggles() {
        if (exists(this.toggleFile)) {
            try {
                FileReader fileReader = new FileReader(this.toggleFile);
                BufferedReader reader = new BufferedReader(fileReader);
                StringBuilder builder = new StringBuilder();

                String current;
                while ((current = reader.readLine()) != null) {
                    builder.append(current);
                }
                this.configJson = new JsonParser().parse(builder.toString()).getAsJsonObject();
            } catch (Exception ex) {
                log("Could not read toggles properly, saving.");
                saveToggles();
            }

            if (ToggleBase.hasToggle("uhc")) ToggleBase.getToggle("uhc").setToggled(configJson.has("showUHC") && configJson.get("showUHC").getAsBoolean());
            if (ToggleBase.hasToggle("team")) ToggleBase.getToggle("team").setToggled(configJson.has("showTeam") && configJson.get("showTeam").getAsBoolean());
            if (ToggleBase.hasToggle("join")) ToggleBase.getToggle("join").setToggled(configJson.has("showJoin") && configJson.get("showJoin").getAsBoolean());
            if (ToggleBase.hasToggle("leave")) ToggleBase.getToggle("leave").setToggled(configJson.has("showLeave") && configJson.get("showLeave").getAsBoolean());
            if (ToggleBase.hasToggle("guild")) ToggleBase.getToggle("guild").setToggled(configJson.has("showGuild") && configJson.get("showGuild").getAsBoolean());
            if (ToggleBase.hasToggle("party")) ToggleBase.getToggle("party").setToggled(configJson.has("showParty") && configJson.get("showParty").getAsBoolean());
            if (ToggleBase.hasToggle("shout")) ToggleBase.getToggle("shout").setToggled(configJson.has("showShout") && configJson.get("showShout").getAsBoolean());
            if (ToggleBase.hasToggle("housing")) ToggleBase.getToggle("housing").setToggled(configJson.has("showHousing") && configJson.get("showHousing").getAsBoolean());
            if (ToggleBase.hasToggle("global")) ToggleBase.getToggle("global").setToggled(configJson.has("showGlobal") && configJson.get("showGlobal").getAsBoolean());
            if (ToggleBase.hasToggle("colored_team")) ToggleBase.getToggle("colored_team").setToggled(configJson.has("showColoredTeam") && configJson.get("showColoredTeam").getAsBoolean());
            if (ToggleBase.hasToggle("messages")) ToggleBase.getToggle("messages").setToggled(configJson.has("showMessages") && configJson.get("showMessages").getAsBoolean());
            if (ToggleBase.hasToggle("party_invites")) ToggleBase.getToggle("party_invites").setToggled(configJson.has("showPartyInvites") && configJson.get("showPartyInvites").getAsBoolean());
            if (ToggleBase.hasToggle("spectator")) ToggleBase.getToggle("spectator").setToggled(configJson.has("showSpectatorChat") && configJson.get("showSpectatorChat").getAsBoolean());
            if (ToggleBase.hasToggle("friend_requests")) ToggleBase.getToggle("friend_requests").setToggled(configJson.has("showFriendRequests") && configJson.get("showFriendRequests").getAsBoolean());
            if (ToggleBase.hasToggle("separators")) ToggleBase.getToggle("separators").setToggled(configJson.has("showSeparators") && configJson.get("showSeparators").getAsBoolean());

        } else {
            saveToggles();
        }
    }

    public void saveToggles() {
        this.configJson = new JsonObject();
        try {
            this.toggleFile.createNewFile();
            FileWriter writer = new FileWriter(this.toggleFile);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            if (ToggleBase.hasToggle("uhc")) configJson.addProperty("showUHC", ToggleBase.getToggle("uhc").isEnabled());
            if (ToggleBase.hasToggle("team")) configJson.addProperty("showTeam", ToggleBase.getToggle("team").isEnabled());
            if (ToggleBase.hasToggle("join")) configJson.addProperty("showJoin", ToggleBase.getToggle("join").isEnabled());
            if (ToggleBase.hasToggle("leave")) configJson.addProperty("showLeave", ToggleBase.getToggle("leave").isEnabled());
            if (ToggleBase.hasToggle("guild")) configJson.addProperty("showGuild", ToggleBase.getToggle("guild").isEnabled());
            if (ToggleBase.hasToggle("party")) configJson.addProperty("showParty", ToggleBase.getToggle("party").isEnabled());
            if (ToggleBase.hasToggle("shout")) configJson.addProperty("showShout", ToggleBase.getToggle("shout").isEnabled());
            if (ToggleBase.hasToggle("housing")) configJson.addProperty("showHousing", ToggleBase.getToggle("housing").isEnabled());
            if (ToggleBase.hasToggle("global")) configJson.addProperty("showGlobal", ToggleBase.getToggle("global").isEnabled());
            if (ToggleBase.hasToggle("colored_team")) configJson.addProperty("showColoredTeam", ToggleBase.getToggle("colored_team").isEnabled());
            if (ToggleBase.hasToggle("messages")) configJson.addProperty("showMessages", ToggleBase.getToggle("messages").isEnabled());
            if (ToggleBase.hasToggle("party_invites")) configJson.addProperty("showPartyInvites", ToggleBase.getToggle("party_invites").isEnabled());
            if (ToggleBase.hasToggle("spectator")) configJson.addProperty("showSpectatorChat", ToggleBase.getToggle("spectator").isEnabled());
            if (ToggleBase.hasToggle("friend_requests")) configJson.addProperty("showFriendRequests", ToggleBase.getToggle("friend_requests").isEnabled());
            if (ToggleBase.hasToggle("separators")) configJson.addProperty("showSeparators", ToggleBase.getToggle("separators").isEnabled());

            bufferedWriter.write(this.configJson.toString());
            bufferedWriter.close();
            writer.close();
        } catch (Exception ex) {
            log("Could not save toggles.");
            ex.printStackTrace();
        }
    }

    public void loadWhitelist() {
        try {
            if (exists(this.whitelistFile)) {
                BufferedReader reader = new BufferedReader(new FileReader(this.whitelistFile));

                for (String s : reader.lines().collect(Collectors.toList())) {
                    if (s != null && s.toCharArray().length <= 16 && !s.contains(" ")) { // We don't want to load something that is over 16 characters, or has spaces in it!
                        ToggleChatMod.getInstance().getWhitelist().add(s);
                    }
                }
            }
        } catch (Exception ex) {
            saveWhitelist();
        }
    }

    public void saveWhitelist() {
       try {
           FileWriter e = new FileWriter(this.whitelistFile);

           for (String s : ToggleChatMod.getInstance().getWhitelist()) {
               e.write(s + System.lineSeparator());
           }

           e.close();
       } catch (Exception ex) {
           log("Could not save whitelist.");
           ex.printStackTrace();
       }
    }

    public boolean exists(File file) {
        return Files.exists(Paths.get(file.getPath()));
    }

    public File getToggleFile() {
        return this.toggleFile;
    }

    public File getWhitelistFile() {
        return this.whitelistFile;
    }

    protected void log(String message, Object... replace) {
        System.out.println(String.format("[ConfigLoader] " + message, replace));
    }
}
