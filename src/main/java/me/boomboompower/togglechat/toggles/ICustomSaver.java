package me.boomboompower.togglechat.toggles;

import com.google.gson.JsonObject;

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

    public default void onSave(JsonObject config) {
    }

    public default void onLoad(JsonObject config) {
    }
}
