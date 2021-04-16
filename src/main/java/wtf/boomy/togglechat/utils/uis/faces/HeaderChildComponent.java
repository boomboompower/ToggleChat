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

import wtf.boomy.togglechat.utils.uis.components.HeaderComponent;

public interface HeaderChildComponent extends ModernUIElement {
    
    /**
     * Renders the element from a header position. By default this will just call the {@link
     * #render(int, int, float, float)} method, however some {@link ModernUIElement}'s will react differently
     * to this change.
     *
     * @param xPos               the x position of the element
     * @param yPos               the y position of the element
     * @param yTranslation       the translation in the y axis
     * @param mouseX             the raw x location of the mouse
     * @param mouseY             the raw y location of the mouse
     * @param recommendedYOffset the recommended offset this {@link ModernUIElement} should follow (how
     *                           far down it should be shifted).
     */
    public default void renderFromHeader(int xPos, int yPos, float yTranslation, float partialTicks, int mouseX, int mouseY, int recommendedYOffset) {
        render(mouseX, mouseY, yTranslation, partialTicks);
    }
    
    /**
     * Tells this element to register itself as part of this header. Some elements will react
     * differently to this change
     *
     * @param parent the header which the element should be set under.
     */
    public void setAsPartOfHeader(HeaderComponent parent);
    
    /**
     * Should this element be rendered relative to its header (if its part of one)?
     *
     * @return true if the element should be moved based on header position.
     */
    public default boolean renderRelativeToHeader() {
        return true;
    }
}
