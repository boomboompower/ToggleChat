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

package wtf.boomy.togglechat.utils.uis.faces;

import wtf.boomy.togglechat.utils.uis.ModernGui;

/**
 * Interface for a basic ModernUIElement item. Contains universal methods which **ALL** elements
 * should implement. This is used internally such as in the {@link ModernGui class}
 *
 * @author boomboompower
 * @version 1.0
 * @since 3.0.0
 */
public interface ModernUIElement {
    
    /**
     * Returns the x position of this element
     *
     * @return the x position of the element.
     */
    public int getX();
    
    /**
     * Returns the y location of this element (not translated)
     *
     * @return the y position of the element.
     */
    public int getY();
    
    /**
     * Returns the width of this element.
     *
     * @return the width of the element
     */
    public int getWidth();
    
    /**
     * Calls the render function for the element.
     *
     * @param mouseX the current x position of the mouse.
     * @param mouseY the current y position of the mouse.
     */
    public void render(int mouseX, int mouseY, float yTranslation, float partialTicks);
    
    /**
     * Should this element be drawn? If this is false the header will not call the {@link
     * #render(int, int, float, float)} method.
     *
     * @return true if {@link #render(int, int, float, float)} should be called for the element.
     */
    public boolean isEnabled();
    
    /**
     * Stops this element being translatable
     *
     * @return this translatable
     */
    public default ModernUIElement disableTranslatable() {
        return this;
    }
    
    /**
     * Should this element be translated (up/down)
     *
     * @return true if it should be translated.
     */
    public default boolean isTranslatable() {
        return true;
    }
}
