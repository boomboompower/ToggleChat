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
import wtf.boomy.togglechat.gui.selector.UITheme;
import wtf.boomy.togglechat.toggles.Categories;
import wtf.boomy.togglechat.toggles.ICustomSaver;
import wtf.boomy.togglechat.toggles.ToggleBase;
import wtf.boomy.togglechat.toggles.custom.interpreter.ToggleInterpreter;
import wtf.boomy.togglechat.toggles.sorting.SortType;
import wtf.boomy.togglechat.utils.BetterJsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigLoader {

    private final Logger logger = LogManager.getLogger("ToggleChat - Config");
    
    // All the places we'll save and load at
    private final List<Object> locations = new ArrayList<>();

    private final File toggleFile;
    private final File modernGuiFile;

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
    
    @SaveableField(saveId = "theme")
    private UITheme uiTheme = UITheme.UNKNOWN;
    
    private final ToggleChatMod mod;
    private final ToggleInterpreter toggleInterpreter;

    public ConfigLoader(ToggleChatMod mod, File directory) {
        this.mod = mod;

        if (!directory.exists()) {
            directory.mkdirs();
        }

        this.toggleFile = new File(directory, "options.nn");
        this.modernGuiFile = new File(directory, "modern.nn");
        this.toggleInterpreter = new ToggleInterpreter(mod, new File(directory, "custom"));

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

//    public void saveCustomToggles() {
//        if (this.customToggleDir == null) {
//            return;
//        }
//
//        if (!exists(this.customToggleDir)) {
//            this.customToggleDir.mkdirs();
//        }
//
//        for (Map.Entry<String, ToggleBase> entry : this.mod.getToggleHandler().getToggles().entrySet()) {
//            if (entry.getValue() instanceof TypeCustom) {
//                TypeCustom base = (TypeCustom) entry.getValue();
//
//                if (base._getConditions().isEmpty()) {
//                    continue;
//                }
//
//                saveSingle(base);
//            }
//        }
//    }

//    public void saveSingle(TypeCustom base) {
//        if (!base.isDirty()) {
//            return;
//        }
//
//        try {
//            File file = new File(this.customToggleDir, base.getName().toLowerCase() + ".txt");
//            FileWriter writer = new FileWriter(file);
//
//            if (!base._getComments().isEmpty()) {
//                for (String s : base._getComments()) {
//                    writer.append(s).append(System.lineSeparator());
//                }
//            } else {
//                writeDefaultValues(writer);
//            }
//
//            writer.append("").append(System.lineSeparator());
//
//            for (ToggleCondition condition : base._getConditions()) {
//                if (!(condition instanceof ConditionEmpty)) {
//                    writer.append(base.getName()).append(" : ").append(condition.getConditionType().getDisplayText()).append("(").append(condition.getText()).append(")").append(System.lineSeparator());
//                }
//            }
//            writer.close();
//        } catch (Exception ex) {
//            this.logger.error("Failed to save custom toggle: \"{}\"!", base.getName(), ex);
//        }
//
//        base.clean();
//    }

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
    
    public ToggleInterpreter getToggleInterpreter() {
        return this.toggleInterpreter;
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
    
    public UITheme getUITheme() {
        return this.uiTheme;
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
    
    public void setUITheme(UITheme designTypes) {
        this.uiTheme = designTypes;
    }
}
