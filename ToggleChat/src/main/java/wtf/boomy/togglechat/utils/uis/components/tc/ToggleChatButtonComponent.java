package wtf.boomy.togglechat.utils.uis.components.tc;

import net.minecraft.client.gui.FontRenderer;
import wtf.boomy.mods.modernui.threads.SimpleCallback;
import wtf.boomy.mods.modernui.uis.components.ButtonComponent;
import wtf.boomy.togglechat.toggles.ToggleBase;
import wtf.boomy.togglechat.toggles.dummy.ToggleDummyMessage;

import java.awt.Color;

/**
 * A ModernButton with all the other fields ToggleChat requires,
 * such as favourites and ToggleBase data tags.
 *
 * @since 3.1
 */
public class ToggleChatButtonComponent extends ButtonComponent {
    
    private boolean isFavourite = false;
    
    // The stored button data.
    private ToggleBase buttonData;
    
    public ToggleChatButtonComponent(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }
    
    public ToggleChatButtonComponent(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, SimpleCallback<? extends ToggleChatButtonComponent> clicked) {
        super(buttonId, x, y, widthIn, heightIn, buttonText, clicked);
    }
    
    @Override
    protected void renderButtonString(FontRenderer fontrenderer, int xPosition, int yPosition, int textColor) {
        // We want to override the button to add the favourite star.
        // This will only render if the button is enabled and it's flagged as a favourite.
        if (isEnabled() && this.isFavourite) {
            fontrenderer.drawString("\u2726", getX() + getWidth() - fontrenderer.getStringWidth("\u2726") - 4, getY() + ((fontrenderer.FONT_HEIGHT / 2) + 2), Color.ORANGE.getRGB());
        }
        
        // Call the original rendering code!
        super.renderButtonString(fontrenderer, xPosition, yPosition, textColor);
    }
    
    @Override
    public void onRightClick(int mouseX, int mouseY, float yTranslation) {
        if (this.buttonData != null && !(this.buttonData instanceof ToggleDummyMessage)) {
            this.buttonData.setEnabled(!this.buttonData.isFavourite());
            
            this.setFavourite(this.buttonData.isFavourite());
        }
    }
    
    /**
     * Sets this button as a favourite or not. If this is true a star will be displayed next to
     * the displayname of the button. If false the button will exhibit normal behaviour.
     *
     * @param favourite true for the favourite flag!
     * @return the instance of this button.
     */
    public ToggleChatButtonComponent setFavourite(boolean favourite) {
        this.isFavourite = favourite;
        
        return this;
    }
    
    /**
     * Returns true if this button has the favourite flag currently applied and should
     * render the according favourite-specific options.
     *
     * @return true if this button is favourited.
     */
    public boolean isFavourite() {
        return this.isFavourite;
    }
    
    /**
     * Returns the data which has been allocated to this button. This will be null in most cases, and is generally
     * used to link a toggle to the button, for reasons such as toggling, favouriting, and for rendering descriptions.
     *
     * @return a possibly null value for the data for this button. See {@link #hasButtonData()}
     */
    public ToggleBase getButtonData() {
        return this.buttonData;
    }
    
    /**
     * Sets the button data to the supplied string. On the back-end this just uses a dummy
     * button data. This is mostly used for rendering strings on buttons which aren't actually
     * for toggles, such as for the settings menu or for the sorting buttons.
     *
     * @param buttonData the data for the button.
     * @return the instance of this button.
     */
    public ToggleChatButtonComponent setButtonData(String... buttonData) {
        this.buttonData = new ToggleDummyMessage(buttonData);
        
        return this;
    }
    
    /**
     * Sets the button data to the supplied toggle. Descriptions and other settings will
     * be derived from this button in some of the UIs.
     *
     * @param buttonData the button data for this button.
     * @return the instance of this button.
     */
    public ToggleChatButtonComponent setButtonData(ToggleBase buttonData) {
        this.buttonData = buttonData;
        
        return this;
    }
    
    /**
     * Returns true if the button actually has data allocated to it.
     * If this is true then {@link #getButtonData()} is safe to use.
     *
     * @return true if this button has a ToggleBase with it.
     */
    public boolean hasButtonData() {
        return this.buttonData != null;
    }
}
