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
package me.boomboompower.all.togglechat;

import me.boomboompower.all.togglechat.loading.ToggleTypes;
import me.boomboompower.all.togglechat.utils.GlobalUtils;
import me.boomboompower.all.togglechat.utils.Writer;

import java.util.HashMap;
import java.util.List;

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

//    public static boolean ignoreUHC = false;
//    public static boolean ignoreSpec = false;
//    public static boolean ignoreTeam = false;
//    public static boolean ignoreJoin = false;
//    public static boolean ignoreLeave = false;
//    public static boolean ignoreGuild = false;
//    public static boolean ignoreParty = false;
//    public static boolean ignoreShout = false;
//    public static boolean ignoreHousing = false;
//    public static boolean ignoreColored = false;
//    public static boolean ignoreMessage = false;
//    public static boolean ignorePartyInv = false;
//    public static boolean ignoreFriendReqs = false;
//    public static boolean ignoreSeparators = false;

    private static Options instance;
    private static HashMap<Integer, ToggleTypes.ToggleBase> baseTypes = new HashMap<Integer, ToggleTypes.ToggleBase>();

    static {
        baseTypes.put(0, new ToggleTypes.TypeUHC());
        baseTypes.put(1, new ToggleTypes.TypeTeam());
        baseTypes.put(2, new ToggleTypes.TypeJoin());
        baseTypes.put(3, new ToggleTypes.TypeLeave());
        baseTypes.put(4, new ToggleTypes.TypeGuild());
        baseTypes.put(5, new ToggleTypes.TypeParty());
        baseTypes.put(6, new ToggleTypes.TypeShout());
        baseTypes.put(11, new ToggleTypes.TypeHousing());
        baseTypes.put(12, new ToggleTypes.TypeColored());
        baseTypes.put(13, new ToggleTypes.TypeMessage());
        baseTypes.put(14, new ToggleTypes.TypePartyInvite());
        baseTypes.put(15, new ToggleTypes.TypeSpectator());
        baseTypes.put(16, new ToggleTypes.TypeFriendRequest());
        baseTypes.put(17, new ToggleTypes.TypeSeparators());
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
    public void addType(ToggleTypes.ToggleBase... bases) {
        if (bases.length > 0) {
            for (ToggleTypes.ToggleBase base : bases) {
                if (!baseTypes.containsKey(base.getId()) && base.getId() > 17) {
                    baseTypes.put(base.getId(), base);
                } else {
                    throw new RuntimeException(String.format("baseTypes entry is already loaded. (ID: %s)", base.getId()));
                }
            }
        }
    }

    /**
     * Allow other developers to remove buttons (as long as they aren't)
     * one of our private ids
     *
     * @param base Button to be removed
     */
    public void removeType(ToggleTypes.ToggleBase base) {
        if (baseTypes.containsKey(base.getId()) && base.getId() > 17) {
            baseTypes.remove(base.getId());
        }
    }

    /*
     * Prevent people wiping the original
     * list by providing a new temporary one
     */
    public HashMap<Integer, ToggleTypes.ToggleBase> getBaseTypes() {
        HashMap<Integer, ToggleTypes.ToggleBase> tempHashMap = new HashMap<>();
        baseTypes.forEach(tempHashMap::put);

        return tempHashMap;
    }

    public void setup(List<String> lines) {
        try {
            Options.showTeam = Boolean.parseBoolean(lines.get(0));
            Options.showJoin = Boolean.parseBoolean(lines.get(1));
            Options.showLeave = Boolean.parseBoolean(lines.get(2));
            Options.showGuild = Boolean.parseBoolean(lines.get(3));
            Options.showParty = Boolean.parseBoolean(lines.get(4));
            Options.showShout = Boolean.parseBoolean(lines.get(5));
            Options.showMessage = Boolean.parseBoolean(lines.get(6));

            Options.showUHC = Boolean.parseBoolean(lines.get(7));
            Options.showPartyInv = Boolean.parseBoolean(lines.get(8));
            Options.showFriendReqs = Boolean.parseBoolean(lines.get(9));

            Options.showSpectator = Boolean.parseBoolean(lines.get(10));
            Options.showColored = Boolean.parseBoolean(lines.get(11));

            Options.showHousing = Boolean.parseBoolean(lines.get(12));
            Options.showSeparators = Boolean.parseBoolean(lines.get(13));
        } catch (Exception ex) {
            GlobalUtils.log("Failed to setup all main option values. Rewriting!");
            Writer.execute();
            GlobalUtils.log("------------------- ERROR -------------------");
            ex.printStackTrace();
            GlobalUtils.log("------------------- ERROR -------------------");
            GlobalUtils.log("The error and its details are shown above.");
        }
    }

    public static Options getInstance() {
        return instance;
    }

    private void doTutorialCheck() {
        try {
            Class.forName("me.boomboompower.all.togglechat.tutorial.TutorialGui");
        } catch (Exception ex) {
            ToggleChat.tutorialEnabled = false;
        }
    }
}
