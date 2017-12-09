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
import me.boomboompower.togglechat.toggles.custom.ToggleCondition;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class ConfigLoader {

    private File toggleFile;
    private File whitelistFile;
    private File customToggleDir;

    private JsonObject configJson;

    private boolean classicTheme = false;
    private boolean flagged = false;

    public ConfigLoader(String directory) {
        File e = new File(directory);

        if (!e.exists()) {
            e.mkdirs();
        }

        this.toggleFile = new File(directory + "options.nn");
        this.whitelistFile = new File(directory + "whitelist.nn");
        this.flagged = ToggleChatMod.getInstance().getWebsiteUtils().isFlagged();

        if (this.flagged) {
            this.customToggleDir = new File(directory, "custom");
        }
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

            for (ToggleBase base : ToggleBase.getToggles().values()) {
                base.setToggled(this.configJson.has("show" + base.getName().replace(" ", "_")) && this.configJson.get("show" + base.getName().replace(" ", "_")).getAsBoolean());
            }

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

            for (ToggleBase type : ToggleBase.getToggles().values()) {
                this.configJson.addProperty("show" + type.getName().replace(" ", "_"), type.isEnabled());
            }

            this.configJson.addProperty("classic", this.classicTheme);

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

    public void loadCustomToggles() {
        if (!this.flagged || this.customToggleDir == null) {
            return;
        }

        if (!exists(this.customToggleDir)) {
            this.customToggleDir.mkdirs();

            try {
                File file = new File(this.customToggleDir, "default.txt");
                FileWriter writer = new FileWriter(file);
                writer.append("// Format").append(System.lineSeparator());
                writer.append("// ToggleName : <Condition>").append(System.lineSeparator());
                writer.append("//").append(System.lineSeparator());
                writer.append("// Possible conditions").append(System.lineSeparator());
                writer.append("// startsWith(string)          Starts with \"string\"").append(System.lineSeparator());
                writer.append("// contains(string)            Contains \"string\"").append(System.lineSeparator());
                writer.append("// contains(string,4)          Contains \"string\" 4 times").append(System.lineSeparator());
                writer.append("// endsWith(string)            Ends with \"string\"").append(System.lineSeparator());
                writer.append("// equals(string)              Equals \"string\" case-sensitive").append(System.lineSeparator());
                writer.append("// equalsIgnoreCase(string)    Equals \"string\" not case-sensitive").append(System.lineSeparator());
                writer.append("").append(System.lineSeparator());
                writer.append("MyToggle : startsWith([YT] Sk1er)").append(System.lineSeparator());
                writer.close();
            } catch (Exception ex) {
            }
            return;
        }

        try {
            // noinspection ConstantConditions
            for (File file : this.customToggleDir.listFiles()) {
                if (file.getName().endsWith(".nn") && !file.isDirectory()) {
                    try {
                        FileReader fileReader = new FileReader(file);
                        BufferedReader reader = new BufferedReader(fileReader);
                        LinkedList<String> lines = new LinkedList<>();

                        for (String s : reader.lines().collect(Collectors.toList())) {
                            if (!s.isEmpty() && !s.startsWith("//")) {
                                lines.add(s);
                            }
                        }

                        if (lines.size() == 0) {
                            // We don't need to worry about an empty file.
                            continue;
                        }

                        for (String line : lines) {
                            if (ToggleCondition.isValidFormat(line)) {

                            }
                        }
                    } catch (Exception ex) {
                        log("Failed to load \"" + file.getName() + "\". Ignoring.");
                    }
                }
            }
        } catch (Exception ex) {
            log("Failed to load your custom toggles!");
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

    public boolean isClassicTheme() {
        return this.classicTheme;
    }

    public void setClassicTheme(boolean classicTheme) {
        this.classicTheme = classicTheme;
    }

    protected void log(String message, Object... replace) {
        System.out.println(String.format("[ConfigLoader] " + message, replace));
    }
}
