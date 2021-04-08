/*
 *     Copyright (C) 2021 boomboompower
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

package wtf.boomy.togglechat.toggles.dummy;

import wtf.boomy.togglechat.toggles.ToggleBase;

/**
 * A dummy ToggleBase instance used to display messages on the ModernGui screens.
 * <p>
 * DO NOT REGISTER THIS IN THE {@link ToggleBase} CLASS!
 */
public class ToggleDummyMessage extends ToggleBase {

    private String[] message;

    public ToggleDummyMessage(String... message) {
        this.message = message;
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
    public String[] getDescription() {
        return this.message;
    }

    public void appendLine(String line) {
        // Treat null chars as an empty line.
        if (line == null) {
            line = "";
        }
        
        String[] addedArray = new String[this.message.length + 1];
    
        System.arraycopy(this.message, 0, addedArray, 0, this.message.length);
        
        addedArray[this.message.length] = line;
    
        this.message = addedArray;
    }

    public void clearLines() {
        this.message = new String[0];
    }
}
