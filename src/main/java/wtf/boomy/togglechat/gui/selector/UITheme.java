package wtf.boomy.togglechat.gui.selector;

import wtf.boomy.togglechat.gui.core.MainGui;
import wtf.boomy.togglechat.gui.redesign.NewMainUI;
import wtf.boomy.togglechat.utils.uis.ModernGui;

/**
 * A simple enum containing all the possible theme types for the mod
 *
 * @author boomboompower
 * @since 3.1.27
 */
public enum UITheme {
    
    UNKNOWN(null),
    LEGACY(MainGui.class),
    NEW(NewMainUI.class);
    
    // Store the class
    private final Class<? extends ModernGui> theme;
    
    /**
     * Constructor for the enum value
     *
     * @param theme the class representing this value
     */
    UITheme(Class<? extends ModernGui> theme) {
        this.theme = theme;
    }
    
    /**
     * Returns the nullable value for the class for this value.
     *
     * It's worth noting this will be null if unknown is chosen so make sure to have appropriate validation.
     *
     * @return the class representing this value.
     */
    public Class<? extends ModernGui> getThemeClass() {
        return this.theme;
    }
}
