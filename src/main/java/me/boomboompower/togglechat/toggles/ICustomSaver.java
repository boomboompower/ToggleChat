/*
 *     Copyright (C) 2020 Isophene
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
