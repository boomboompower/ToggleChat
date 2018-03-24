/*
 *     Copyright (C) 2018 boomboompower
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

package me.boomboompower.togglechat.toggles.dummy;

import me.boomboompower.togglechat.toggles.ToggleBase;

import java.util.LinkedList;

/**
 * A dummy ToggleBase instance used as a hack to display messages on the ModernGui screens.
 *
 * DO NOT REGISTER THIS IN THE {@link ToggleBase} CLASS!
 */
public class ToggleDummyMessage extends ToggleBase {
    
    private LinkedList<String> message;
    
    public ToggleDummyMessage(String... message) {
        this.message = asLinked(message);
    }
    
    @Override
    public String getName() {
        return "Dummy";
    }
    
    @Override
    public boolean shouldToggle(String message) {
        return false;
    }
    
    @Override
    public boolean isEnabled() {
        return false;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
    }
    
    @Override
    public LinkedList<String> getDescription() {
        return this.message;
    }
}
