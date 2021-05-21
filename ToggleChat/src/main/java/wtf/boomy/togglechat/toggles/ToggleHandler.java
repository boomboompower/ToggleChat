package wtf.boomy.togglechat.toggles;

import wtf.boomy.togglechat.ToggleChatMod;
import wtf.boomy.togglechat.toggles.custom.CustomToggle;
import wtf.boomy.togglechat.toggles.defaults.friends.TypeFriendJoin;
import wtf.boomy.togglechat.toggles.defaults.friends.TypeFriendLeave;
import wtf.boomy.togglechat.toggles.defaults.friends.TypeFriendRequests;
import wtf.boomy.togglechat.toggles.defaults.friends.TypeMessages;
import wtf.boomy.togglechat.toggles.defaults.gamemode.TypeBedwars;
import wtf.boomy.togglechat.toggles.defaults.gamemode.TypeBuildBattle;
import wtf.boomy.togglechat.toggles.defaults.gamemode.TypeHousing;
import wtf.boomy.togglechat.toggles.defaults.gamemode.TypeMysteryBox;
import wtf.boomy.togglechat.toggles.defaults.gamemode.TypePit;
import wtf.boomy.togglechat.toggles.defaults.gamemode.TypeSkyBlockAbility;
import wtf.boomy.togglechat.toggles.defaults.gamemode.TypeSkyBlockLevelUp;
import wtf.boomy.togglechat.toggles.defaults.gamemode.TypeSoulWell;
import wtf.boomy.togglechat.toggles.defaults.guilds.TypeGuild;
import wtf.boomy.togglechat.toggles.defaults.guilds.TypeGuildJoin;
import wtf.boomy.togglechat.toggles.defaults.guilds.TypeGuildLeave;
import wtf.boomy.togglechat.toggles.defaults.otherchat.TypeColored;
import wtf.boomy.togglechat.toggles.defaults.otherchat.TypeEasy;
import wtf.boomy.togglechat.toggles.defaults.otherchat.TypeGlobal;
import wtf.boomy.togglechat.toggles.defaults.otherchat.TypeShout;
import wtf.boomy.togglechat.toggles.defaults.otherchat.TypeSpecial;
import wtf.boomy.togglechat.toggles.defaults.otherchat.TypeSpectator;
import wtf.boomy.togglechat.toggles.defaults.otherchat.TypeTeam;
import wtf.boomy.togglechat.toggles.defaults.parties.TypeParty;
import wtf.boomy.togglechat.toggles.defaults.parties.TypePartyInvites;
import wtf.boomy.togglechat.toggles.defaults.qol.TypeAds;
import wtf.boomy.togglechat.toggles.defaults.qol.TypeAutoQuest;
import wtf.boomy.togglechat.toggles.defaults.qol.TypeGexp;
import wtf.boomy.togglechat.toggles.defaults.qol.TypeLimbo;
import wtf.boomy.togglechat.toggles.defaults.qol.TypeLobbyJoin;
import wtf.boomy.togglechat.toggles.defaults.qol.TypeMessageSeparator;
import wtf.boomy.togglechat.toggles.defaults.qol.TypeTip;
import wtf.boomy.togglechat.toggles.defaults.qol.TypeWatchdog;
import wtf.boomy.togglechat.toggles.sorting.impl.ToggleBaseComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ToggleHandler {
    
    private final ToggleBaseComparator comparator = new ToggleBaseComparator();
    
    /* Name | ToggleBase */
    private final LinkedHashMap<String, ToggleBase> toggles = new LinkedHashMap<>();
    
    /* Name | TypeCustom */
    private final LinkedHashMap<String, ToggleBase> custom = new LinkedHashMap<>();
    
    private boolean dirty = false;
    private LinkedHashMap<String, ToggleBase> combinedToggleList = new LinkedHashMap<>();
    
    private final ToggleChatMod mod;
    
    public ToggleHandler(ToggleChatMod mod) {
        this.mod = mod;
    }
    
    /**
     * Adds the developers own ToggleBase
     *
     * @param toggleBase the developers toggle
     */
    public void addToggle(ToggleBase toggleBase) {
        if (toggleBase == null || toggleBase.getName() == null) {
            return;
        }
        
        this.dirty = true;
        
        if (toggleBase instanceof CustomToggle) {
            this.custom.put(toggleBase.getIdString(), toggleBase);
        
            // Custom toggles reorder the map each time.
            
            sortMap(this.custom);
        } else {
            this.toggles.put(toggleBase.getIdString(), toggleBase);
        }
    }
    
    /**
     * Clears all the normal toggles and orders them by the
     * size of their unformatted display text. Used at startup
     */
    public void remake() {
        this.toggles.clear();
        
        addToggle(new TypeAds());
        addToggle(new TypePit());
        addToggle(new TypeTip());
        addToggle(new TypeEasy());
        addToggle(new TypeGexp());
        addToggle(new TypeTeam());
        addToggle(new TypeGuild());
        addToggle(new TypeLimbo());
        addToggle(new TypeParty());
        addToggle(new TypeShout());
        addToggle(new TypeGlobal());
        addToggle(new TypeBedwars());
        addToggle(new TypeColored());
        addToggle(new TypeHousing());
        addToggle(new TypeSpecial());
        addToggle(new TypeMessages());
        addToggle(new TypeSoulWell());
        addToggle(new TypeWatchdog());
        addToggle(new TypeAutoQuest());
        addToggle(new TypeGuildJoin());
        addToggle(new TypeLobbyJoin());
        addToggle(new TypeSpectator());
        addToggle(new TypeFriendJoin());
        addToggle(new TypeGuildLeave());
        addToggle(new TypeMysteryBox());
        addToggle(new TypeBuildBattle());
        addToggle(new TypeFriendLeave());
        addToggle(new TypePartyInvites());
        addToggle(new TypeFriendRequests());
        addToggle(new TypeSkyBlockAbility());
        addToggle(new TypeSkyBlockLevelUp());
        addToggle(new TypeMessageSeparator());
        
        sortMap(this.toggles);
    }
    
    /**
     * Gets a toggle by the given name, may return null
     *
     * @param name the toggle's name
     * @return a ToggleBase instance if found, or else null
     */
    public ToggleBase getToggle(String name) {
        return this.toggles.getOrDefault(name, null);
    }
    
    /**
     * Checks to see if the registered parsers contains a parser with the given name.
     *
     * @param name The toggle's name to test for
     * @return true if it is registered
     */
    public boolean hasToggle(String name) {
        return this.toggles.containsKey(name);
    }
    
    /**
     * Creates a combined list of the toggles, which is cached until a new toggle is added.
     *
     * @return The toggle list
     */
    public LinkedHashMap<String, ToggleBase> getToggles() {
        if (!this.dirty) {
            return this.combinedToggleList;
        }
    
        this.dirty = false;
        
        LinkedHashMap<String, ToggleBase> newInput = new LinkedHashMap<>();
        this.toggles.forEach(newInput::put);
        this.custom.forEach(newInput::put);
    
        this.combinedToggleList = newInput;
        
        return this.combinedToggleList;
    }
    
    /**
     * Returns an unmodifiable list of the stored custom toggles.
     *
     * @return the custom toggle list.
     */
    public Map<String, ToggleBase> getCustomToggles() {
        return Collections.unmodifiableMap(this.custom);
    }
    
    /**
     * Iterates through a string list of favourites and sets
     * their respective toggles as favourited if they appear
     * in the list of strings.
     *
     * @param favourites the arraylist containing the ids of the favourite toggles.
     */
    public void inheritFavourites(ArrayList<String> favourites) {
        for (String favourite : favourites) {
            ToggleBase toggle = getToggle(favourite);
            
            // Existence check. Some toggles could've been renamed or deleted.
            if (toggle != null) {
                toggle.setFavourite(true);
            }
        }
    }
    
    /**
     * Used to sort by the displayname of the toggle, so the gui
     * looks neat without having to manually sort the entries.
     *
     * @param map the map to sort
     */
    private void sortMap(LinkedHashMap<String, ToggleBase> map) {
        List<Map.Entry<String, ToggleBase>> list = new LinkedList<>(map.entrySet());
        
        // Use the comparator
        list.sort(this.comparator);
        
        Map<String, ToggleBase> sortedMap = new LinkedHashMap<>();
        
        for (Map.Entry<String, ToggleBase> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        // What is this shit
        map.clear();
        map.putAll(sortedMap);
    }
}
