/*
 *     Copyright (C) 2016 boomboompower
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

import me.boomboompower.togglechat.Options;
import me.boomboompower.togglechat.ToggleChat;

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
        if (exists(toggleFile)) {
            try {
                FileReader fileReader = new FileReader(toggleFile);
                BufferedReader reader = new BufferedReader(fileReader);
                StringBuilder builder = new StringBuilder();

                String current;
                while ((current = reader.readLine()) != null) {
                    builder.append(current);
                }
                configJson = new JsonParser().parse(builder.toString()).getAsJsonObject();
            } catch (Exception ex) {
                log("Could not read toggles properly, saving.");
                saveToggles();
            }
            Options.showUHC = configJson.has("showUHC") && configJson.get("showUHC").getAsBoolean();
            Options.showTeam = configJson.has("showTeam") && configJson.get("showTeam").getAsBoolean();
            Options.showJoin = configJson.has("showJoin") && configJson.get("showJoin").getAsBoolean();
            Options.showLeave = configJson.has("showLeave") && configJson.get("showLeave").getAsBoolean();
            Options.showGuild = configJson.has("showGuild") && configJson.get("showGuild").getAsBoolean();
            Options.showParty = configJson.has("showParty") && configJson.get("showParty").getAsBoolean();
            Options.showShout = configJson.has("showShout") && configJson.get("showShout").getAsBoolean();
            Options.showHousing = configJson.has("showHousing") && configJson.get("showHousing").getAsBoolean();
            Options.showColored = configJson.has("showColored") && configJson.get("showColored").getAsBoolean();
            Options.showMessage = configJson.has("showMessage") && configJson.get("showMessage").getAsBoolean();
            Options.showPartyInv = configJson.has("showPartyInv") && configJson.get("showPartyInv").getAsBoolean();
            Options.showSpectator = configJson.has("showSpectator") && configJson.get("showSpectator").getAsBoolean();
            Options.showFriendReqs = configJson.has("showFriendReqs") && configJson.get("showFriendReqs").getAsBoolean();
            Options.showSeparators = configJson.has("showSeparators") && configJson.get("showSeparators").getAsBoolean();

        } else {
            saveToggles();
        }
    }

    public void saveToggles() {
        configJson = new JsonObject();
        try {
            toggleFile.createNewFile();
            FileWriter writer = new FileWriter(toggleFile);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            configJson.addProperty("showUHC", Options.showUHC);
            configJson.addProperty("showTeam", Options.showTeam);
            configJson.addProperty("showJoin", Options.showJoin);
            configJson.addProperty("showLeave", Options.showLeave);
            configJson.addProperty("showGuild", Options.showGuild);
            configJson.addProperty("showParty", Options.showParty);
            configJson.addProperty("showShout", Options.showShout);
            configJson.addProperty("showHousing", Options.showHousing);
            configJson.addProperty("showColored", Options.showColored);
            configJson.addProperty("showMessage", Options.showMessage);
            configJson.addProperty("showPartyInv", Options.showPartyInv);
            configJson.addProperty("showSpectator", Options.showSpectator);
            configJson.addProperty("showFriendReqs", Options.showFriendReqs);
            configJson.addProperty("showSeparators", Options.showSeparators);

            bufferedWriter.write(configJson.toString());
            bufferedWriter.close();
            writer.close();
        } catch (Exception ex) {
            log("Could not save toggles.");
            ex.printStackTrace();
        }
    }

    public void loadWhitelist() {
        try {
            if (exists(whitelistFile)) {
                BufferedReader reader = new BufferedReader(new FileReader(whitelistFile));

                for (String s : reader.lines().collect(Collectors.toList())) {
                    if (s != null && s.toCharArray().length <= 16 && !s.contains(" ")) { // We don't want to load something that is over 16 characters, or has spaces in it!
                        ToggleChat.getInstance().getWhitelist().add(s);
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

           for (String s : ToggleChat.getInstance().getWhitelist()) {
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
