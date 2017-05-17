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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {

   public FileUtils() {
   }

   public static void getVars() throws Throwable {
       try {
           File e = new File(ToggleChat.USER_DIR);
           if (!e.exists()) {
               e.mkdirs();
           }

           boolean executeWriter = false;
           BufferedReader f;
           List options;
           List<String> whitelist;

           if (exists(ToggleChat.USER_DIR + "options.nn")) {
               f = new BufferedReader(new FileReader(ToggleChat.USER_DIR + "options.nn"));

               try {
                   options = f.lines().collect(Collectors.toList());
                   if (options.size() >= 13) {
                       List<String> entries = new ArrayList<String>();
                       for (int i = 0; i <= 13; i++) {
                           entries.add((String) options.get(i));
                       }
                       Options.getInstance().setup(Options.ConfigType.MAIN_OPTIONS, entries);
                   } else {
                       executeWriter = true;
                   }
               } catch (Throwable var31) {
                   throw var31;
               }
           }

           if (exists(ToggleChat.USER_DIR + "whitelist_options.nn")) {
               f = new BufferedReader(new FileReader(ToggleChat.USER_DIR + "whitelist_options.nn"));

               try {
                   options = f.lines().collect(Collectors.toList());
                   if (options.size() >= 13) {
                       List<String> entries = new ArrayList<String>();
                       for (int i = 0; i <= 13; i++) {
                           entries.add((String) options.get(i));
                       }
                       Options.getInstance().setup(Options.ConfigType.WHITELIST_OPTIONS, entries);
                   } else {
                       executeWriter = true;
                   }
               } catch (Throwable var31) {
                   throw var31;
               }
           }

           if (exists(ToggleChat.USER_DIR + "whitelist.nn")) {
               f = new BufferedReader(new FileReader(ToggleChat.USER_DIR + "whitelist.nn"));

               try {
                   whitelist = f.lines().collect(Collectors.toList());
                   for (String s : whitelist) {
                       attemptLoad(s);
                   }
               } catch (Throwable var30) {
                   var30.printStackTrace();
               }
           }

           if (executeWriter) {
               Writer.execute(false);
           }
       } catch (IOException var32) {
           throw var32;
       }
   }

   private static void attemptLoad(String word) {
       if (word != null && word.toCharArray().length <= 16 && !word.contains(" ")) { // We don't want to load something that is over 16 characters, or has spaces in it!
           ToggleChat.whitelist.add(word);
       }
   }

   private static boolean exists(String path) {
        return Files.exists(Paths.get(path));
    }
}
