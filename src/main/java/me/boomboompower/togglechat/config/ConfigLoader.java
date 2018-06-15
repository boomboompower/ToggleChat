/*
 *     Copyright (C) 2018 boomboompower
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.toggles.ICustomSaver;
import me.boomboompower.togglechat.toggles.ToggleBase;
import me.boomboompower.togglechat.toggles.custom.ToggleCondition;
import me.boomboompower.togglechat.toggles.custom.TypeCustom;
import me.boomboompower.togglechat.toggles.custom.conditions.ConditionEmpty;
import me.boomboompower.togglechat.toggles.custom.conditions.ConditionStartsWith;
import me.boomboompower.togglechat.utils.BetterJsonObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigLoader {
    
    // All the places we'll save and load at
    private final List<Object> locations = new ArrayList<>();
    
    @Getter
    private File toggleFile;

    @Getter
    private File whitelistFile;

    @Getter
    private File modernGuiFile;

    @Getter
    private File customToggleDir;

    private BetterJsonObject toggleJson = new BetterJsonObject();
    private BetterJsonObject modernJson = new BetterJsonObject();

    @Getter
    private boolean flagged;

    @Setter
    @Getter
    @Memes(saveId = "blur")
    private boolean modernBlur = true;

    @Setter
    @Getter
    @Memes
    private boolean modernButton = true;

    @Setter
    @Getter
    @Memes
    private boolean modernTextbox = true;
    
    @Getter
    private LinkedList<String> whitelist = new LinkedList<>();

    public ConfigLoader(ToggleChatMod mod, String directory) {
        File e = new File(directory);

        if (!e.exists()) {
            e.mkdirs();
        }

        this.toggleFile = new File(directory + "options.nn");
        this.whitelistFile = new File(directory + "whitelist.nn");
        this.modernGuiFile = new File(directory + "modern.nn");
        this.flagged = mod.getWebsiteUtils().isFlagged();

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
                this.toggleJson = new BetterJsonObject(builder.toString());
            } catch (Exception ex) {
                log("Could not read toggles properly, saving.");
                saveToggles();
            }

            for (ToggleBase base : ToggleBase.getToggles().values()) {
                if (base instanceof ICustomSaver) {
                    ICustomSaver saver = (ICustomSaver) base;
                    if (!saver.useDefaultLoad()) {
                        saver.onLoad(this.toggleJson);
                        continue;
                    }
                }
                base.setEnabled(this.toggleJson.has("show" + base.getIdString()) && this.toggleJson.get("show" + base.getIdString()).getAsBoolean());
            }

        } else {
            saveToggles();
        }
    }

    public void saveToggles() {
        try {
            if (this.locations.isEmpty()) {
                return;
            }
            
            if (!this.toggleFile.getParentFile().exists()) {
                this.toggleFile.getParentFile().mkdirs();
            }

            this.toggleFile.createNewFile();
            FileWriter writer = new FileWriter(this.toggleFile);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (ToggleBase base : ToggleBase.getToggles().values()) {
                if (base instanceof ICustomSaver) {
                    ICustomSaver saver = (ICustomSaver) base;
                    if (!saver.useDefaultSave()) {
                        saver.onSave(this.toggleJson);
                        continue;
                    }
                }
                this.toggleJson.addProperty("show" + base.getIdString(), base.isEnabled());
            }

            this.toggleJson.writeToFile(this.toggleFile);
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
                        this.whitelist.add(s);
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

           for (String s : this.whitelist) {
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
                File file = new File(this.customToggleDir, "mytoggle.txt");
                FileWriter writer = new FileWriter(file);
                writer.append("// Format").append(System.lineSeparator());
                writer.append("// ToggleName : <Condition>").append(System.lineSeparator());
                writer.append("//").append(System.lineSeparator());
                writer.append("// This feature was created").append(System.lineSeparator());
                writer.append("// by OrangeMarshall!").append(System.lineSeparator());
                writer.append("//").append(System.lineSeparator());
                writer.append("// Possible conditions").append(System.lineSeparator());
                writer.append("// startsWith(string)          Starts with \"string\"").append(System.lineSeparator());
                writer.append("// contains(string)            Contains \"string\"").append(System.lineSeparator());
                writer.append("// contains(string,4)          Contains \"string\" 4 times").append(System.lineSeparator());
                writer.append("// endsWith(string)            Ends with \"string\"").append(System.lineSeparator());
                writer.append("// equals(string)              Equals \"string\" case-sensitive").append(System.lineSeparator());
                writer.append("// equalsIgnoreCase(string)    Equals \"string\" not case-sensitive").append(System.lineSeparator());
                writer.append("// regex(regex)                Regex matches the input").append(System.lineSeparator());
                writer.append("").append(System.lineSeparator());
                writer.append("MyToggle : startsWith([YOUTUBE] Sk1er)").append(System.lineSeparator());
                writer.close();

                ToggleBase.addToggle(new TypeCustom("MyToggle", new ConditionStartsWith("[YOUTUBE] Sk1er")));
            } catch (Exception ignored) {
            }
            return;
        }

        try {
            LinkedList<TypeCustom> customs = new LinkedList<>();

            // noinspection ConstantConditions
            for (File file : this.customToggleDir.listFiles()) {
                if (!file.isDirectory()) {
                    try {
                        FileReader fileReader = new FileReader(file);
                        BufferedReader reader = new BufferedReader(fileReader);
                        
                        LinkedList<String> comments = new LinkedList<>();
                        LinkedList<String> lines = new LinkedList<>();

                        for (String s : reader.lines().collect(Collectors.toList())) {
                            if (!s.isEmpty()) {
                                if (s.startsWith("//")) {
                                    comments.add(s);
                                } else {
                                    lines.add(s);
                                }
                            }
                        }

                        if (lines.isEmpty()) {
                            // We don't need to worry about an empty file.
                            continue;
                        }

                        for (String line : lines) {
                            if (ToggleCondition.isValidFormat(line)) {
                                ToggleCondition condition = ToggleCondition.get(line.split(" : ")[1]);
                                String name = ToggleCondition.getFormatName(line);

                                if (name == null || name.isEmpty()) {
                                    continue;
                                }

                                boolean added = false;

                                for (TypeCustom customer : customs) {
                                    if (name.equalsIgnoreCase(customer.getName())) {
                                        customer._addCondition(condition);
                                        added = true;
                                        break;
                                    }
                                }

                                if (!added) {
                                    customs.add(new TypeCustom(name, condition)._setComments(comments));
                                }
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        log("An issue occured while loading \"" + file.getName() + "\". Make sure it isn't corruputed!");
                    }
                }
            }

            if (customs.isEmpty()) {
                // Custom toggles is empty, lets move on...
                return;
            }

            for (TypeCustom custom : customs) {
                ToggleBase.addToggle(custom);
            }
        } catch (Exception ex) {
            log("Failed to load your custom toggles!");
            ex.printStackTrace();
        }
    }

    public void saveCustomToggles() {
        if (!this.flagged || this.customToggleDir == null) {
            return;
        }

        if (!exists(this.customToggleDir)) {
            this.customToggleDir.mkdirs();
        }

        for (Map.Entry<String, ToggleBase> entry : ToggleBase.getToggles().entrySet()) {
            if (entry.getValue() instanceof TypeCustom) {
                TypeCustom base = (TypeCustom) entry.getValue();

                if (base._getConditions().isEmpty()) {
                    continue;
                }

                saveSingle(base);
            }
        }
    }

    public void loadModernUtils() {
        if (!this.modernGuiFile.exists()) {
            saveModernUtils();
            return;
        }
        
        // Set to true if an exception occurs whilst loading
        boolean failed = false;
        
        try {
            FileReader fileReader = new FileReader(this.modernGuiFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder builder = new StringBuilder();
        
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        
            if (lines.isEmpty()) {
                return;
            }
        
            for (String s : lines) {
                builder.append(s);
            }
    
            this.modernJson = new BetterJsonObject(builder.toString().trim());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        
            failed = true;
        } finally {
            // Make sure it was read without errors
            if (!failed) {
                for (Object o : this.locations) {
                    Class<?> clazz = o.getClass();
                
                    for (Field f : clazz.getDeclaredFields()) {
                        if (f.isAnnotationPresent(Memes.class)) {
                            f.setAccessible(true);
                        
                            try {
                                String saveId = f.getAnnotation(Memes.class).saveId();
                            
                                if (saveId.trim().isEmpty()) {
                                    saveId = f.getName();
                                }
                            
                                if (!this.modernJson.has(saveId)) {
                                    continue;
                                }
                                
                                f.set(o, this.modernJson.getGsonData().fromJson(this.modernJson.get(saveId), f.getType()));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                System.err.println("Failed to load settings for field " + f.getName() + " in " + clazz.getName());
                            }
                        }
                    }
                }
            }
        }
    }

    public void saveModernUtils() {
        if (this.locations.isEmpty()) {
            return;
        }
    
        for (Object o : this.locations) {
            Class<?> clazz = o.getClass();
        
            for (Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Memes.class)) {
                    try {
                        if (f.get(o) == null) {
                            continue;
                        }
                    
                        String saveId = f.getAnnotation(Memes.class).saveId();
                    
                        if (saveId.isEmpty()) {
                            saveId = f.getName();
                        }
    
                        this.modernJson.getData().add(saveId, this.modernJson.getGsonData().toJsonTree(f.get(o), f.getType()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.err.println("Failed to save settings for field " + f.getName() + " in " + clazz.getName());
                    }
                }
            }
        }
        this.modernJson.writeToFile(this.modernGuiFile);
    }

    public void saveSingle(TypeCustom base) {
        try {
            File file = new File(this.customToggleDir, base.getName().toLowerCase() + ".txt");
            FileWriter writer = new FileWriter(file);
    
            if (!base._getComments().isEmpty()) {
                for (String s : base._getComments()) {
                    writer.append(s).append(System.lineSeparator());
                }
            } else {
                writer.append("// Format").append(System.lineSeparator());
                writer.append("// ToggleName : <Condition>").append(System.lineSeparator());
                writer.append("//").append(System.lineSeparator());
                writer.append("// This feature was created").append(System.lineSeparator());
                writer.append("// by OrangeMarshall!").append(System.lineSeparator());
                writer.append("//").append(System.lineSeparator());
                writer.append("// Possible conditions").append(System.lineSeparator());
                writer.append("// startsWith(string)          Starts with \"string\"").append(System.lineSeparator());
                writer.append("// contains(string)            Contains \"string\"").append(System.lineSeparator());
                writer.append("// contains(string,4)          Contains \"string\" 4 times").append(System.lineSeparator());
                writer.append("// endsWith(string)            Ends with \"string\"").append(System.lineSeparator());
                writer.append("// equals(string)              Equals \"string\" case-sensitive").append(System.lineSeparator());
                writer.append("// equalsIgnoreCase(string)    Equals \"string\" not case-sensitive").append(System.lineSeparator());
                writer.append("// regex(regex)                Regex matches the input").append(System.lineSeparator());
            }
    
            writer.append("").append(System.lineSeparator());

            for (ToggleCondition condition : base._getConditions()) {
                if (!(condition instanceof ConditionEmpty)) {
                    writer.append(base.getName()).append(" : ").append(condition.getSaveIdentifier()).append("(").append(condition.getText()).append(")").append(System.lineSeparator());
                }
            }
            writer.close();
        } catch (Exception ex) {
            log("Failed to save custom toggle: \"%s\"!", base.getName());
        }
    }
    
    public void addToSaving(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Save cannot be null");
        }
        
        // Test if the class actually has any fields
        if (o.getClass().getDeclaredFields().length > 0) {
            
            // Loop through all fields
            for (Field f : o.getClass().getDeclaredFields()) {
                
                // Check if a field has a save annotation, if so, add it to our saves
                if (f.isAnnotationPresent(Memes.class)) {
                    this.locations.add(o);
                    break;
                }
            }
        }
    }

    public boolean exists(File file) {
        return Files.exists(Paths.get(file.getPath()));
    }

    protected void log(String message, Object... replace) {
        System.out.println(String.format("[ConfigLoader] " + message, replace));
    }
}
