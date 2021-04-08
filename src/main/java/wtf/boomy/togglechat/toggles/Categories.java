package wtf.boomy.togglechat.toggles;

/**
 * Represents the different categories supported by this mod. Another mode for filtering
 * the results in the toggle list.
 *
 * @since 3.1.44
 */
public enum Categories {
    
    ALL("All", "Displays all toggles"),
    CHAT("General", "Displays general toggles"),
    CUSTOM("Custom", "Displays custom toggles"),
    FRIENDS("Friends", "Displays friend toggles"),
    GAMES("Games", "Displays game toggles"),
    GUILDS("Guilds", "Displays guild toggles"),
    PARTIES("Parties", "Displays party toggles"),
    QOL("QOL", "Displays quality-of-life toggles"),
    OTHER("Other", "Displays everything else");
    
    private final String displayName;
    private final String description;
    
    Categories(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public Categories getNextMode() {
        int next = ordinal() + 1;
        
        if (next >= values().length) {
            next = 0;
        }
        
        return values()[next];
    }
}
