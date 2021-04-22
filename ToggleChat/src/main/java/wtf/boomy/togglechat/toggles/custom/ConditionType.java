package wtf.boomy.togglechat.toggles.custom;

/**
 * Stores information about each {@link ToggleCondition}. Used in saving & loading.
 * <p>
 * Can be used to gain more information about a ToggleCondition
 */
public enum ConditionType {
    CHARACTERAT("charAt", "Triggers if the letter at the index is a certain value", true),
    CONTAINS("contains", "Triggers if the text contains this message a certain amount of times"),
    STARTSWITH("startsWith", "Triggers if the text starts with this message"),
    ISLETTER("isLetter", "Triggers it the character at the index is a letter", true),
    ISNUMBER("isNumber", "Triggers if the character at the index is a number", true),
    ENDSWITH("endsWith", "Triggers if the text ends with this message"),
    EQUALS("equals", "Triggers if the text equals this message."),
    REGEX("regex", "Triggers if the chat message matches this regex"),
    EMPTY("empty", "Never triggers");
    
    private final String displayText;
    private final String description;
    private final boolean usesIndex;
    
    /**
     * The default ctor for a condition, takes the display name and description
     *
     * @param display the display name of this value
     * @param description the description of this condition
     */
    private ConditionType(String display, String description) {
        this(display, description, false);
    }
    
    /**
     * The ctor for a type which may potentially use indexes instead of a full string.
     *
     * @param display the display name of this value
     * @param description the description of this condition
     * @param usesIndex true if this condition uses an index instead of a full string.
     */
    private ConditionType(String display, String description, boolean usesIndex) {
        this.displayText = display;
        this.description = description;
        this.usesIndex = usesIndex;
    }
    
    /**
     * Returns the text used in saving and loading (How it will appear)
     *
     * @return the text that will be used in saving & loading
     */
    public String getDisplayText() {
        return this.displayText;
    }
    
    /**
     * Returns the description of this condition for the user.
     *
     * @return the description for this condition
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Returns true if this category uses a single character index.
     *
     * @return true if this category uses an index.
     */
    public boolean isUsesIndex() {
        return this.usesIndex;
    }
    
    /**
     * Returns the next condition after this one
     *
     * @return the next condition
     */
    public ConditionType next() {
        // Get the next ordinal
        int nextOrdinal = ordinal() + 1;
        
        // If the value is out of bounds
        // loop back to the first value
        if (nextOrdinal > values().length - 1) {
            nextOrdinal = 0;
        }
        
        // Return the type at that index
        return values()[nextOrdinal];
    }
}
