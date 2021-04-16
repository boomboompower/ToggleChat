package wtf.boomy.togglechat.toggles.defaults.gamemode;

import wtf.boomy.togglechat.toggles.Categories;
import wtf.boomy.togglechat.toggles.ToggleBase;

import java.util.regex.Pattern;

public class TypeSkyBlockAbility extends ToggleBase {
    
    private final Pattern abilitiesRegex = Pattern.compile("(You do not have enough mana to do this!)|(There are blocks in the way!)|(Whow! Slow down there!)");
    
    @Override
    public String getName() {
        return "Skyblock Abilities";
    }
    
    @Override
    public String getDisplayName() {
        return "Skyblock Abilities: %s";
    }
    
    @Override
    public boolean shouldToggle(String message) {
        return this.abilitiesRegex.matcher(message).matches();
    }
    
    @Override
    public String[] getDescription() {
        return new String[] {
                "Toggles most SkyBlock ability",
                "chat messages.",
                "",
                "Such as:",
                "&cYou do not ... to do this!",
                "&cThere are blocks in the way!",
                "&cWhow! Slow down there!"
        };
    }
    
    @Override
    public Categories getCategory() {
        return Categories.GAMES;
    }
}
