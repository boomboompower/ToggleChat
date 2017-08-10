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

package me.boomboompower.togglechat.utils;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class UnmodifiableHashMap<K, V> {

    private HashMap<K, V> hashMap = new HashMap<>();

    public void put(K t, V k) {
        hashMap.put(t, k);
    }

    public boolean containsKey(Object o) {
        return hashMap.containsKey(o);
    }

    public void forEach(BiConsumer<? super K, ? super V> action) {
        hashMap.forEach(action);
    }

    protected HashMap<K, V> get() {
        return hashMap;
    }
}