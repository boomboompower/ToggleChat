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

import me.boomboompower.all.togglechat.ToggleChat;

import java.io.FileWriter;
import java.io.IOException;

public class Writer implements Runnable {

    private static String ls = System.lineSeparator();

    private static boolean useWhitelist;
    private static boolean useStartup;

    public Writer() {
    }

    public static void execute(boolean startup, boolean whitelist) {
        useStartup = startup;
        useWhitelist = whitelist;
        (new Thread(new Writer())).start();
    }

    public void run() {
        try {
            FileWriter e = new FileWriter(ToggleChat.USER_DIR + "options.nn");

            // The following cannot be moved (since v1.0.2)
            this.write(e, ToggleChat.showTeam + ls);
            this.write(e, ToggleChat.showJoin + ls);
            this.write(e, ToggleChat.showLeave + ls);
            this.write(e, ToggleChat.showGuild + ls);
            this.write(e, ToggleChat.showParty + ls);
            this.write(e, ToggleChat.showShout + ls);
            this.write(e, ToggleChat.showMessage + ls);

            // The following cannot be moved (since v1.0.4)
            this.write(e, ToggleChat.showUHC + ls);
            this.write(e, ToggleChat.showPartyInv + ls);
            this.write(e, ToggleChat.showFriendReqs + ls);

            // The following cannot be moved (since v1.1.0)
            this.write(e, ToggleChat.showSpec + ls);
            this.write(e, ToggleChat.showColored + ls);

            // The following cannot be moved (since v1.1.7)
            e.close();
        } catch (Throwable var56) {
            var56.printStackTrace();
        }
        if (useStartup) {
            try {
                FileWriter e = new FileWriter(ToggleChat.USER_DIR + "startup.nn");

                // The following cannot be moved (since v1.1.7)
                this.write(e, ToggleChat.showStatupMessage + ls);
                this.write(e, ToggleChat.statupMessageRevision + ls);

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