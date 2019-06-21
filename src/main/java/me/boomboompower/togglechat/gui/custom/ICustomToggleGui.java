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

package me.boomboompower.togglechat.gui.custom;

import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.custom.TypeCustom;

/**
 * If a gui extends this, it'll have all modifications from {@link ModernGui} and will require
 * a {@link #getCustomToggle()} method which returns the custom toggle assigned to this gui.
 *
 * This class is used in a few places throughout the code, its basically just an indicator
 * for some of the other internal gui code which tells them that this class has data they can use.
 */
public abstract class ICustomToggleGui extends ModernGui {
    
    /**
     * Returns the data assigned to this gui, which can be used in other places
     * in the code (without needing reflection etc)
     *
     * @return the custom toggle for this gui
     */
    public abstract TypeCustom getCustomToggle();
}
