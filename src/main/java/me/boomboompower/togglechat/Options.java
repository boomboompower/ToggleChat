/*
 *     Copyright (C) 2016 boomboompower
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
package me.boomboompower.togglechat;

import me.boomboompower.togglechat.toggles.*;
import me.boomboompower.togglechat.utils.UnmodifiableHashMap;

import java.util.HashMap;

public class Options {

    public static boolean showUHC = true;
    public static boolean showTeam = true;
    public static boolean showJoin = true;
    public static boolean showLeave = true;
    public static boolean showGuild = true;
    public static boolean showParty = true;
    public static boolean showShout = true;
    public static boolean showHousing = true;
    public static boolean showColored = true;
    public static boolean showMessage = true;
    public static boolean showPartyInv = true;
    public static boolean showSpectator = true;
    public static boolean showFriendReqs = true;
    public static boolean showSeparators = true;

    private static Options instance;
    private static UnmodifiableHashMap<Integer, ToggleBase> baseTypes = new UnmodifiableHashMap<Integer, ToggleBase>();

    static {
        baseTypes.put(0, new TypeUHC());
        baseTypes.put(1, new TypeTeam());
        baseTypes.put(2, new TypeJoin());
        baseTypes.put(3, new TypeLeave());
        baseTypes.put(4, new TypeGuild());
        baseTypes.put(5, new TypeParty());
        baseTypes.put(6, new TypeShout());
        baseTypes.put(11, new TypeHousing());
        baseTypes.put(12, new TypeColored());
        baseTypes.put(13, new TypeMessages());
        baseTypes.put(14, new TypePartyInvite());
        baseTypes.put(15, new TypeSpectator());
        baseTypes.put(16, new TypeFriendRequest());
        baseTypes.put(17, new TypeSeparator());
    }

    public Options() {
        instance = this;

        doTutorialCheck();
    }

    /**
     * Allow other developers to add their own custom toggle buttons
     * (As long as they aren't registered as one of our private ids)
     *
     * @param bases Buttons to be added
     */
    public void addType(ToggleBase... bases) {
        if (bases.length > 0) {
            for (ToggleBase base : bases) {
                if (!baseTypes.containsKey(base.getId()) && base.getId() > 17) {
                    baseTypes.put(base.getId(), base);
                } else {
                    throw new RuntimeException(String.format("baseTypes entry is already loaded. (ID: %s)", base.getId()));
                }
            }
        }
    }

    /*
     * Prevent people wiping the original
     * list by providing a new temporary one
     */
    public HashMap<Integer, ToggleBase> getBaseTypes() {
        HashMap<Integer, ToggleBase> tempHashMap = new HashMap<>();
        baseTypes.forEach(tempHashMap::put);

        return tempHashMap;
    }

    public static Options getInstance() {
        return instance;
    }

    private void doTutorialCheck() {
        try {
            Class.forName("me.boomboompower.togglechat.tutorial.MainTutorialGui");
            Class.forName("me.boomboompower.togglechat.tutorial.WhitelistTutorialGui");
        } catch (Exception ex) {
            ToggleChat.getInstance().disableTutorial();
        }
    }
}
