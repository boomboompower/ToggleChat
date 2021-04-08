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

package wtf.boomy.togglechat.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wtf.boomy.togglechat.ToggleChatMod;
import wtf.boomy.togglechat.toggles.Categories;
import wtf.boomy.togglechat.toggles.ICustomSaver;
import wtf.boomy.togglechat.toggles.ToggleBase;
import wtf.boomy.togglechat.toggles.custom.ToggleCondition;
import wtf.boomy.togglechat.toggles.custom.TypeCustom;
import wtf.boomy.togglechat.toggles.custom.conditions.ConditionEmpty;
import wtf.boomy.togglechat.toggles.custom.conditions.ConditionStartsWith;
import wtf.boomy.togglechat.toggles.sorting.SortType;
import wtf.boomy.togglechat.utils.BetterJsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigLoader {

    private final Logger logger = LogManager.getLogger("ToggleChat - Config");
    
    // All the places we'll save and load at
    private final List<Object> locations = new ArrayList<>();

    private final File toggleFile;
    private final File modernGuiFile;

    private File customToggleDir;

    private BetterJsonObject toggleJson = new BetterJsonObject();
    private BetterJsonObject modernJson = new BetterJsonObject();

    @SaveableField(saveId = "blur")
    private boolean modernBlur = true;

    @SaveableField(saveId = "button")
    private boolean modernButton = true;

    @SaveableField(saveId = "textbox")
    private boolean modernTextbox = true;

    @SaveableField(saveId = "favourites")
    private ArrayList<String> favourites = new ArrayList<>();
    
    @SaveableField(saveId = "whitelist")
    private final ArrayList<String> whitelist = new ArrayList<>();
    
    @SaveableField(saveId = "sortType")
    private SortType sortType = SortType.WIDTH;
    
    @SaveableField(saveId = "filter")
    private Categories categoryFilter = Categories.ALL;
    
    private final ToggleChatMod mod;

    public ConfigLoader(ToggleChatMod mod, File directory) {
        this.mod = mod;

        if (!directory.exists()) {
            directory.mkdirs();
        }

        this.toggleFile = new File(directory, "options.nn");
        this.modernGuiFile = new File(directory, "modern.nn");

        addToSaving(this); // M  o  d  e  r  n
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
                this.logger.error("Could not read toggles properly, saving.", ex);
                saveToggles();
            }

            for (ToggleBase base : this.mod.getToggleHandler().getToggles().values()) {
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

            for (ToggleBase base : this.mod.getToggleHandler().getToggles().values()) {
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
            this.logger.error("Could not save toggles.", ex);
        }
    }

    public void loadCustomToggles() {
        if (this.customToggleDir == null) {
            return;
        }

        if (!exists(this.customToggleDir)) {
            this.customToggleDir.mkdirs();

            try {
                File file = new File(this.customToggleDir, "mytoggle.txt");
                FileWriter writer = new FileWriter(file);

                writeDefaultValues(writer);

                writer.append("").append(System.lineSeparator());
                writer.append("MyToggle : startsWith([YOUTUBE] Sk1er)").append(System.lineSeparator());
                writer.close();
    
                this.mod.getToggleHandler().addToggle(new TypeCustom("MyToggle", new ConditionStartsWith("[YOUTUBE] Sk1er")));
            } catch (Exception ex) {
                this.logger.error("Failed to save custom toggles", ex);
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
                        this.logger.error("An issue occurred while loading \"{}\". Potentially corrupted?", file.getName(), ex);
                    }
                }
            }

            if (customs.isEmpty()) {
                // Custom toggles is empty, lets move on...
                return;
            }

            for (TypeCustom custom : customs) {
                custom.clean(); // Hasn't been modified. Doesn't need saving
    
                this.mod.getToggleHandler().addToggle(custom);
            }
        } catch (Exception ex) {
            this.logger.error("Failed to load your custom toggles!", ex);
        }
    }

    private void writeDefaultValues(FileWriter writer) throws IOException {
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

    public void saveCustomToggles() {
        if (this.customToggleDir == null) {
            return;
        }

        if (!exists(this.customToggleDir)) {
            this.customToggleDir.mkdirs();
        }

        for (Map.Entry<String, ToggleBase> entry : this.mod.getToggleHandler().getToggles().entrySet()) {
            if (entry.getValue() instanceof TypeCustom) {
                TypeCustom base = (TypeCustom) entry.getValue();

                if (base._getConditions().isEmpty()) {
                    continue;
                }

                saveSingle(base);
            }
        }
    }

    public void saveSingle(TypeCustom base) {
        if (!base.isDirty()) {
            return;
        }

        try {
            File file = new File(this.customToggleDir, base.getName().toLowerCase() + ".txt");
            FileWriter writer = new FileWriter(file);

            if (!base._getComments().isEmpty()) {
                for (String s : base._getComments()) {
                    writer.append(s).append(System.lineSeparator());
                }
            } else {
                writeDefaultValues(writer);
            }

            writer.append("").append(System.lineSeparator());

            for (ToggleCondition condition : base._getConditions()) {
                if (!(condition instanceof ConditionEmpty)) {
                    writer.append(base.getName()).append(" : ").append(condition.getConditionType().getDisplayText()).append("(").append(condition.getText()).append(")").append(System.lineSeparator());
                }
            }
            writer.close();
        } catch (Exception ex) {
            this.logger.error("Failed to save custom toggle: \"{}\"!", base.getName(), ex);
        }

        base.clean();
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
            this.logger.error(ex);

            failed = true;
        } finally {
            // Make sure it was read without errors
            if (!failed) {
                for (Object o : this.locations) {
                    Class<?> clazz = o.getClass();

                    for (Field f : clazz.getDeclaredFields()) {
                        if (f.isAnnotationPresent(SaveableField.class)) {
                            f.setAccessible(true);

                            try {
                                String saveId = f.getAnnotation(SaveableField.class).saveId();

                                if (saveId.trim().isEmpty()) {
                                    saveId = f.getName();
                                }

                                if (!this.modernJson.has(saveId)) {
                                    continue;
                                }

                                f.set(o, this.modernJson.getGsonData().fromJson(this.modernJson.get(saveId), f.getType()));
                            } catch (Exception ex) {
                                this.logger.error("Failed to load settings for field {} in {}", f.getName(), clazz.getName(), ex);
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
                if (f.isAnnotationPresent(SaveableField.class)) {
                    try {
                        if (f.get(o) == null) {
                            continue;
                        }

                        String saveId = f.getAnnotation(SaveableField.class).saveId();

                        if (saveId.isEmpty()) {
                            saveId = f.getName();
                        }

                        this.modernJson.getData().add(saveId, this.modernJson.getGsonData().toJsonTree(f.get(o), f.getType()));
                    } catch (Exception ex) {
                        this.logger.error("Failed to save settings for field {} in {}", f.getName(), clazz.getName(), ex);
                    }
                }
            }
        }
        this.modernJson.writeToFile(this.modernGuiFile);
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
                if (f.isAnnotationPresent(SaveableField.class)) {
                    this.locations.add(o);
                    break;
                }
            }
        }
    }

    public boolean exists(File file) {
        return Files.exists(Paths.get(file.getPath()));
    }
    
    public File getToggleFile() {
        return this.toggleFile;
    }
    
    public File getModernGuiFile() {
        return this.modernGuiFile;
    }
    
    public File getCustomToggleDir() {
        return this.customToggleDir;
    }
    
    public boolean isModernBlur() {
        return this.modernBlur;
    }
    
    public boolean isModernButton() {
        return this.modernButton;
    }
    
    public boolean isModernTextbox() {
        return this.modernTextbox;
    }
    
    public ArrayList<String> getFavourites() {
        return this.favourites;
    }
    
    public List<String> getWhitelist() {
        return this.whitelist;
    }
    
    public SortType getSortType() {
        return this.sortType;
    }
    
    public Categories getCategoryFilter() {
        return this.categoryFilter;
    }
    
    public void setModernBlur(boolean modernBlur) {
        this.modernBlur = modernBlur;
    }
    
    public void setModernButton(boolean modernButton) {
        this.modernButton = modernButton;
    }
    
    public void setModernTextbox(boolean modernTextbox) {
        this.modernTextbox = modernTextbox;
    }
    
    public void setFavourites(ArrayList<String> favourites) {
        this.favourites = favourites;
    }
    
    public void setSortType(SortType type) {
        this.sortType = type;
    }
    
    public void setCategoryFilter(Categories categoryFilter) {
        this.categoryFilter = categoryFilter;
    }
}
