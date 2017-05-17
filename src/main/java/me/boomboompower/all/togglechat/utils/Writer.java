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
package me.boomboompower.all.togglechat.utils;

import me.boomboompower.all.togglechat.Options;
import me.boomboompower.all.togglechat.ToggleChat;

import java.io.FileWriter;
import java.io.IOException;

public class Writer implements Runnable {

    private static String ls = System.lineSeparator();

    private static boolean useWhitelist;
    private static boolean useWhitelistSettings;

    public Writer() {
    }

    public static void execute() {
        execute(false, false);
    }

    public static void execute(boolean whitelist) {
        execute(whitelist, false);
    }

    public static void execute(boolean whitelist, boolean useWhitelistSetting) {
        useWhitelist = whitelist;
        useWhitelistSettings = useWhitelistSetting;
        (new Thread(new Writer())).start();
    }

    public void run() {
        try {
            FileWriter e = new FileWriter(ToggleChat.USER_DIR + "options.nn");

            // The following cannot be moved (since v1.0.2)
            this.write(e, Options.showTeam + ls);
            this.write(e, Options.showJoin + ls);
            this.write(e, Options.showLeave + ls);
            this.write(e, Options.showGuild + ls);
            this.write(e, Options.showParty + ls);
            this.write(e, Options.showShout + ls);
            this.write(e, Options.showMessage + ls);

            // The following cannot be moved (since v1.0.4)
            this.write(e, Options.showUHC + ls);
            this.write(e, Options.showPartyInv + ls);
            this.write(e, Options.showFriendReqs + ls);

            // The following cannot be moved (since v1.1.0)
            this.write(e, Options.showSpec + ls);
            this.write(e, Options.showColored + ls);

            // The following cannot be moved (since v1.1.7)
            this.write(e, Options.showHousing + ls);
            this.write(e, Options.showSeparators + ls);

            e.close();
        } catch (Throwable var56) {
            var56.printStackTrace();
        }
        if (useWhitelistSettings) {
            try {
                FileWriter e = new FileWriter(ToggleChat.USER_DIR + "whitelist_options.nn");

                // The following cannot be moved (since v1.0.2)
                this.write(e, Options.ignoreTeam + ls);
                this.write(e, Options.ignoreJoin + ls);
                this.write(e, Options.ignoreLeave + ls);
                this.write(e, Options.ignoreGuild + ls);
                this.write(e, Options.ignoreParty + ls);
                this.write(e, Options.ignoreShout + ls);
                this.write(e, Options.ignoreMessage + ls);

                this.write(e, Options.ignoreUHC + ls);
                this.write(e, Options.ignorePartyInv + ls);
                this.write(e, Options.ignoreFriendReqs + ls);

                this.write(e, Options.ignoreSpec + ls);
                this.write(e, Options.ignoreColored + ls);

                this.write(e, Options.ignoreSpec + ls);
                this.write(e, Options.ignoreColored + ls);
                e.close();
            } catch (Throwable var56) {
                var56.printStackTrace();
            }
        }
        if (useWhitelist) {
            try {
                FileWriter e = new FileWriter(ToggleChat.USER_DIR + "whitelist.nn");

                for (String s : ToggleChat.whitelist) {
                    this.write(e, s + ls);
                }

                e.close();
            } catch (Throwable var21) {
                var21.printStackTrace();
            }
        }
    }

    private void write(FileWriter writer, String text) {
        try {
            writer.write(text);
        } catch (IOException var4) {
            var4.printStackTrace();
        }
    }
}