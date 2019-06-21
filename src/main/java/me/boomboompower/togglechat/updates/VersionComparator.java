/*
 *     Copyright (C) 2019 boomboompower
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

package me.boomboompower.togglechat.updates;

import java.util.Comparator;

/**
 * A class to compare two versions by making them reformat to each other.
 * Supports more varieties of versions than the previous model.
 *
 * @author boomboompower
 * @version 1.0
 */
public class VersionComparator implements Comparator<String> {

    /**
     * A simple method of doing an update check.
     * This method will resizes version if the sizes (structure)
     * of the versions do not match.
     *
     * @param first The latest version retrieved by the client
     * @param second The current version the client is running
     *
     * @return 1 if there is an update
     * -1 if the client is newer
     * 0 if there is no update founde
     */
    @Override
    public int compare(String first, String second) {
        String[] newestVer = new String[] {first};
        String[] currentVer = new String[] {second};

        // Supports versioning like 1.3 or numerical 1.2
        if (first.contains(".")) {
            newestVer = first.split("\\.");
        }

        // Supports versioning like 1.3 or numerical 1.2
        if (second.contains(".")) {
            currentVer = second.split("\\.");
        }

        // Naturally choose the expected array size to work with in the end
        int expectedSize = newestVer.length;

        // If the version string in the newer version has more components
        // Than the version the client is running, attempt to modify the
        // Clients displayed version to match the same format.
        // E.g if newest is "2.1.1.1" and the current version is "2.1.0"
        // This will change the current version to "2.1.0.0" to match it
        if (newestVer.length > currentVer.length) {

            // Creates a temporary variable which will store
            // the modified array with the appropriate size
            String[] temp = new String[newestVer.length];

            for (int i = 0; i < temp.length; i++) {
                // Checks to see if the current array supports
                // or contains a value at this index
                if (currentVer.length > i) {
                    // Store the version number at the index
                    // if it exists. This is to use it later
                    temp[i] = currentVer[i];
                } else {
                    // If the array size does not contain it,
                    // default the number to just be nothing
                    temp[i] = "0";
                }
            }

            // Set the array to our temporary array
            currentVer = temp;

        } else if (currentVer.length > newestVer.length) {
            // Changes the expected array size to match the largest candidate
            expectedSize = currentVer.length;

            // Creates a temporary variable which will store
            // the modified array with the appropriate size
            String[] temp = new String[currentVer.length];

            for (int i = 0; i < temp.length; i++) {
                // Checks to see if the current array supports
                // or contains a value at this index
                if (newestVer.length > i) {
                    // Store the version number at the index
                    // if it exists. This is to use it later
                    temp[i] = newestVer[i];
                } else {
                    // If the array size does not contain it,
                    // default the number to just be nothing
                    temp[i] = "0";
                }
            }

            // Set the array to our temporary array
            newestVer = temp;
        }

        // This is the loop which compares each segment
        // of the version. It loops using the expected
        // size and continues if the version at each index
        // is equal.
        //
        // If the newer version has a greater version number
        // than the current version then 1 will be returned
        //
        // If the current version has a greater version number
        // than the newer version then -1 will be returned
        //
        // If each version segment is identical the loop will expire
        // and 0 will be returned (since they are the same version).
        for (int i = 0; i < expectedSize; i++) {
            int nInt = safeParseInt(newestVer[i]); // Latest released
            int cInt = safeParseInt(currentVer[i]); // Current version (You're using)

            if (nInt > cInt) {
                return 1;
            } else if (nInt < cInt) {
                return -1;
            }
        }

        // If there is an error or the numbers are identical then 0 will
        // be returned (Usually means they are running the latest version)
        return 0;
    }

    /**
     * Safely coverts a String to an Integer without throwing
     * an exception if the string cannot be converted.
     *
     * @param in the string to convert
     * @return the numerical representation of the string or
     * 0 if the string cannot be transferred to an integer
     */
    private int safeParseInt(String in) {
        try {
            return Integer.parseInt(in);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}
