package wtf.boomy.togglechat.toggles.custom.interpreter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wtf.boomy.togglechat.ToggleChatMod;
import wtf.boomy.togglechat.toggles.ToggleBase;
import wtf.boomy.togglechat.toggles.custom.ConditionType;
import wtf.boomy.togglechat.toggles.custom.CustomToggle;
import wtf.boomy.togglechat.toggles.custom.ToggleCondition;
import wtf.boomy.togglechat.toggles.custom.conditions.ConditionCharacterAt;
import wtf.boomy.togglechat.toggles.custom.conditions.ConditionContains;
import wtf.boomy.togglechat.toggles.custom.conditions.ConditionEndsWith;
import wtf.boomy.togglechat.toggles.custom.conditions.ConditionEquals;
import wtf.boomy.togglechat.toggles.custom.conditions.ConditionIsLetter;
import wtf.boomy.togglechat.toggles.custom.conditions.ConditionIsNumber;
import wtf.boomy.togglechat.toggles.custom.conditions.ConditionRegex;
import wtf.boomy.togglechat.toggles.custom.conditions.ConditionStartsWith;
import wtf.boomy.togglechat.utils.BetterJsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A custom toggle interpreter using JSON instead of our custom file format.
 *
 * @author boomboompower
 * @since 3.0.39
 */
public final class ToggleInterpreter {
    
    private final Logger logger = LogManager.getLogger("ToggleChat - ToggleInterpreter");
    private final ToggleChatMod mod;
    
    private final File customToggleDir;
    
    public ToggleInterpreter(ToggleChatMod mod, File customToggleDir) {
        this.mod = mod;
        this.customToggleDir = customToggleDir;
        
        // Delete the file
        if (this.customToggleDir.isFile()) {
            if (!this.customToggleDir.delete()) {
                this.logger.warn("Failed to delete custom toggle directory. For some reason it was a file.");
            }
        }
    
        // Create the directory
        if (!this.customToggleDir.exists()) {
            if (!this.customToggleDir.mkdirs()) {
                this.logger.warn("Failed to create custom toggle directory.");
            }
        }
    }
    
    public void interpretFiles() {
        File[] files = this.customToggleDir.listFiles(f -> f.isFile() && f.getName().endsWith(".json"));
        
        // No files found in the directory?
        if (files == null || files.length == 0) {
            return;
        }
    
        // noinspection deprecation since this version of the game doesn't support the newer API
        JsonParser parser = new JsonParser();
        
        for (File f : files) {
            // Skip empty files.
            if (f.length() == 0) {
                continue;
            }
            
            FileReader reader = null;
            BufferedReader bufferedReader = null;
            
            try {
                reader = new FileReader(f);
                bufferedReader = new BufferedReader(reader);
    
                // Convert the text file into a String
                String lines = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
    
                // Empty file!
                if (lines.trim().length() == 0) {
                    continue;
                }
                
                // noinspection deprecation see message above for details
                JsonElement element = parser.parse(lines);
    
                if (element.isJsonObject()) {
                    // This file only contains one toggle (default behaviour).
                    loadElement(element);
                } else if (element.isJsonArray()) {
                    // If this file contains multiple toggles, we want to try load all the children :)
                    for (JsonElement child : element.getAsJsonArray()) {
                        loadElement(child);
                    }
                }
            } catch (JsonSyntaxException syntaxException) {
                this.logger.error("Failed to load custom toggle file {}. Json Syntax was wrong!", f.getName(), syntaxException);
            } catch (FileNotFoundException e) {
                this.logger.error("Failed to load custom toggle file {}, the file didn't exist?!?", f.getName());
            } finally {
                IOUtils.closeQuietly(bufferedReader);
                IOUtils.closeQuietly(reader);
            }
        }
    }
    
    /**
     * Attempts to load a single custom toggle
     *
     * @param element the element to load.
     */
    private void loadElement(JsonElement element) {
        // At this stage we only accept incoming objects. Not arrays or primatives.
        if (!element.isJsonObject()) {
            return;
        }
    
        BetterJsonObject object = new BetterJsonObject(element.getAsJsonObject());
        
        String identifier = object.optString("identifier", UUID.randomUUID().toString());
        String toggleName = object.optString("displayName", "Custom Toggle");
        
        List<String> baseDescription = new LinkedList<>();
        List<ToggleCondition> conditionList = new LinkedList<>();
        
        // Time to scan for all the description lines.
        // NOTE: for the time being this also supports objects
        // however I'm unsure of if I'll continue doing this? Who knows.
        if (object.has("description")) {
            JsonElement descriptionElem = object.get("description");
            
            // If it's just an object use the one line because why not?
            if (descriptionElem.isJsonObject()) {
                baseDescription.add(descriptionElem.toString());
            } else if (descriptionElem.isJsonArray()) {
                for (JsonElement child : descriptionElem.getAsJsonArray()) {
                    baseDescription.add(child.getAsString());
                }
            }
        }
        
        // Time to scan for all the possible conditions!
        if (object.has("conditions")) {
            JsonElement conditions = object.get("conditions");
            
            if (conditions.isJsonArray()) {
                for (JsonElement condition : conditions.getAsJsonArray()) {
                    ToggleCondition interpreted = interpretCondition(condition);
                    
                    // A bad element :)
                    if (interpreted == null) {
                        continue;
                    }
                    
                    // Add it woo!
                    conditionList.add(interpreted);
                }
            }
        }
        
        String saveFileName = "";
        
        if (object.has("saveFile")) {
            saveFileName = object.optString("saveFile");
            
            // Remove all path separator's in the file
            if (saveFileName.contains(File.pathSeparator)) {
                String[] temp = saveFileName.split(File.pathSeparator);
                
                // Use the final split
                saveFileName = temp[temp.length - 1];
            }
            
            // Remove all the relative paths.
            if (saveFileName.contains("..")) {
                saveFileName = saveFileName.replace("..", ".");
            }
            
            saveFileName = sanitizeFileName(saveFileName);
        } else {
            saveFileName = identifier.replace("-", "") + ".json";
        }
        
        // Construct the custom toggle instance
        CustomToggle custom = new CustomToggle(toggleName, identifier, conditionList, baseDescription);
        
        custom._setSaveFile(new File(this.customToggleDir, saveFileName));
        
        // Register it
        this.mod.getToggleHandler().addToggle(custom);
    }
    
    private ToggleCondition interpretCondition(JsonElement incoming) {
        // We don't allow these things here!
        if (incoming == null || !incoming.isJsonObject()) {
            return null;
        }
        
        BetterJsonObject sub = new BetterJsonObject(incoming.getAsJsonObject());
        
        // Did not actually have the valid stuffs
        if (!sub.has("type") && !sub.has("condition")) {
            return null;
        }
        
        ToggleCondition cond = null;
    
        ConditionType type = parseCondition(sub.get("type").getAsString());
        String condition = sub.get("condition").getAsString();
        
        switch (type) {
            case CONTAINS:
                int matchCount = sub.optInt("matchCount", -1);
                
                cond = new ConditionContains(condition, matchCount);
                
                break;
            case CHARACTERAT:
                int index = sub.optInt("charIndex", -1);
                
                if (condition.trim().length() > 0 && index >= 0) {
                    cond = new ConditionCharacterAt(condition, index);
                }
                break;
            case ISLETTER:
                cond = new ConditionIsLetter(condition);
                
                break;
            case ISNUMBER:
                cond = new ConditionIsNumber(condition);
                
                break;
            case STARTSWITH:
                cond = new ConditionStartsWith(condition);
                
                break;
            case ENDSWITH:
                cond = new ConditionEndsWith(condition);
                
                break;
            case EQUALS:
                cond = new ConditionEquals(condition);
                
                break;
            case REGEX:
                cond = new ConditionRegex(condition);
                
                break;
        }
        
        return cond;
    }
    
    public void saveCustomToggles() {
        Map<String, ToggleBase> customToggles = this.mod.getToggleHandler().getCustomToggles();
        
        // Store the list of saved file names.
        List<String> savedFileNames = new ArrayList<>();
        
        for (ToggleBase base : customToggles.values()) {
            // Somehow this got through? Weird but anyway
            if (!(base instanceof CustomToggle)) {
                continue;
            }
            
            CustomToggle custom = (CustomToggle) base;
            
            // Set the default save name
            if (custom._getSaveFile() == null) {
                custom._setSaveFile(new File(this.customToggleDir, sanitizeFileName(custom.getIdString().replace("-", "") + ".json")));
            }
            
            // Don't bother saving toggles which haven't been edited.
            if (!custom.isDirty()) {
                // Unmodified toggles should still be added
                savedFileNames.add(custom._getSaveFile().getName());
                
                continue;
            }
    
            // Get this ones save file name
            String saveFileName = custom._getSaveFile().getName();
    
            // If the save file name has already been written to this pass then
            // we'll incrementally make a new file to write to instead of overwriting
            // one of our other files. Useful for the one person that hits this.
            if (savedFileNames.contains(saveFileName)) {
                // Store the new file location
                File getNewFile = getIncrementalFile(custom._getSaveFile());
                
                // If we are past the threshold then just fail
                if (getNewFile == null) {
                    this.logger.warn("Failed to save {} since a toggle already exists at that location!", saveFileName);
                    
                    continue;
                } else {
                    this.logger.warn("Moved the save file for {} to {} from {} to prevent overwriting.", saveFileName, getNewFile.getName(), saveFileName);
                    
                    // Update the variable.
                    saveFileName = getNewFile.getName();
                }
            }
            
            // Add it to the list.
            savedFileNames.add(saveFileName);
    
            // Save the custom toggle to the location
            saveCustomToggle(custom);
        }
    }
    
    /**
     * Saves a single custom toggle to its stored file name
     *
     * @param custom the custom toggle we're saving.
     */
    private void saveCustomToggle(CustomToggle custom) {
        BetterJsonObject object = new BetterJsonObject();
    
        // Set the display name and identifier of the file
        object.addProperty("displayName", custom.getName());
        object.addProperty("identifier", custom.getIdString());
    
        JsonArray array = new JsonArray();
        
        // Copy all the descriptions into the save file.
        for (String description : custom.getDescription()) {
            // We need to create a primitive because gson 2.2.4 (used in 1.8.9) did not support adding anything else to Json Arrays
            array.add(new JsonPrimitive(description));
        }
        
        // Store it in the array
        object.getData().add("description", array);
    
        JsonArray customToggles = new JsonArray();
    
        // Grab each serialized condition
        for (ToggleCondition condition : custom._getConditions()) {
            customToggles.add(condition.serialize());
        }
        
        // Set the conditions based on the serialized values
        object.getData().add("conditions", customToggles);
        
        // Set the save file to what it wants... can it be trusted?
        object.addProperty("saveFile", custom._getSaveFile().getName());
        
        // Write to the file.
        object.writeToFile(custom._getSaveFile());
    }
    
    /**
     * The idea of this function is to create a new file if one already exists with its name.
     * We incrementally create a new file up to a certain threshold ( 69 ) of new files
     *
     * @param incoming the incoming file
     * @return the new file or null if past the threshold.
     */
    private File getIncrementalFile(File incoming) {
        // Get the current file name
        String name = incoming.getName();
        
        // Split the file format off of the name if possible
        if (name.contains(".")) {
            name = name.split("\\.")[0];
        }
    
        // Check if the file exists
        for (int num = 0; incoming.exists(); num++) {
            // Get the next file. Name is nameN.json
            // Where name is the original file's name, and N is the current index.
            incoming = new File(this.customToggleDir, name + num + ".json");
            
            // Have we passed the threshold?
            if (num > 69) {
                this.logger.error("Why do you have {} files with the same. What the hell dude? Please rename some!", 69);
                
                // Return null since we are past the threshold
                return null;
            }
        }
    
        // Return the file
        return incoming;
    }
    
    /**
     * Removes invalid characters from a filename
     *
     * @param filename the file name to sanitize
     * @return a sanitized filename.
     */
    private String sanitizeFileName(String filename) {
        if (filename == null) {
            return null;
        }
        
        // iterate through the characters
        char[] chars = filename.toCharArray();
        boolean changed = false;
        
        for (int i = 0; i < chars.length; i++) {
            // get current char
            char charAt = chars[i];
            
            switch(charAt) {
                case '/':
                case ':':
                case '*':
                case '?':
                case '\"':
                case '<':
                case '>':
                case '|':
                case '\\':
                case '&':
                    // flag it
                    changed = true;
                    
                    // set char to underscore
                    chars[i] = '_';
            }
        }
        
        // construct new string or use old one if nothing changed
        return changed ? String.valueOf(chars) : filename;
    }
    
    /**
     * Attempts to find a toggle matching the string input.
     * This method will never have a null response or throw an error.
     *
     * @param in the text to search
     * @return the {@link ConditionType} for the text, or {@link ConditionType#EMPTY} if nothing is found
     */
    private ConditionType parseCondition(String in) {
        if (in == null || in.isEmpty()) {
            return ConditionType.EMPTY;
        }
        
        for (ConditionType type : ConditionType.values()) {
            if (type == ConditionType.EMPTY) {
                continue;
            }
            
            if (type.getDisplayText().equalsIgnoreCase(in) ||
                    type.name().equalsIgnoreCase(in)) {
                
                return type;
            }
        }
        
        return ConditionType.EMPTY;
    }
}
