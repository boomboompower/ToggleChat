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

package me.boomboompower.all.togglechat.versions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;

import java.net.URL;

public class Hooker {

    private static Hooker instance;

    public String version;
    public Message message;

    public static void update() {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        try {
            String json = IOUtils.toString(new URL("https://gist.githubusercontent.com/boomboompower/662c5c98f6640ce9c72144b659d556ff/raw/togglechat.json"));
            instance = gson.fromJson(json, Hooker.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Hooker getInstance() {
        return instance;
    }
}
