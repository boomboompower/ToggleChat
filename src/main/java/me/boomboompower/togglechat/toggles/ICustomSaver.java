package me.boomboompower.togglechat.toggles;

import me.boomboompower.togglechat.utils.BetterJsonObject;

/**
 * Extend this if you do not wish to use default saving and loading
 */
public interface ICustomSaver {

    public default boolean useDefaultSave() {
        return true;
    }

    public default boolean useDefaultLoad() {
        return true;
    }

    public default void onSave(BetterJsonObject config) {
    }

    public default void onLoad(BetterJsonObject config) {
    }
}
