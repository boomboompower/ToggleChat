package wtf.boomy.togglechat.utils.uis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import wtf.boomy.togglechat.ToggleChatMod;
import wtf.boomy.togglechat.config.ConfigLoader;
import wtf.boomy.togglechat.toggles.ToggleBase;
import wtf.boomy.togglechat.utils.ChatColor;
import wtf.boomy.togglechat.utils.uis.impl.tc.ToggleChatButton;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ToggleChatModernUI extends ModernGui {
    
    protected final ToggleChatMod mod = ToggleChatMod.getInstance();
    protected final ConfigLoader configLoader = ToggleChatMod.getInstance().getConfigLoader();
    
    protected List<ToggleChatButton> toggleChatButtons = new ArrayList<>();
    
    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        this.toggleChatButtons.clear();
        
        super.setWorldAndResolution(mc, width, height);
    }
    
    @Override
    public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    
        if (false) {
//        if (this.mod.getApagogeHandler().getBuildType() == -1) {
            GlStateManager.pushMatrix();
        
            drawString(this.fontRendererObj, "Open Beta - Subject to change", 5, this.height - 10, Color.LIGHT_GRAY.getRGB());
        
            GlStateManager.popMatrix();
        }
    }
    
    @Override
    public final void registerElement(Object element) {
        // A hack for the checkHover function.
        if (element instanceof ToggleChatButton) {
            this.toggleChatButtons.add((ToggleChatButton) element);
        }
        
        super.registerElement(element);
    }
    
    public final void checkHover(int firstPosition) {
        for (ToggleChatButton button : this.toggleChatButtons) {
            if (button == null) continue;
            
            if (button.isHovered() && button.hasButtonData()) {
                ToggleBase toggleBase = button.getButtonData();
                
                if (!toggleBase.hasDescription()) continue;
                
                final int[] position = {firstPosition};
                
                Arrays.stream(toggleBase.getDescription()).forEach(text -> {
                    drawCenteredString(this.mc.fontRendererObj, ChatColor.translateAlternateColorCodes(text), this.width / 2 + 150, position[0], Color.WHITE.getRGB());
                    position[0] += 10;
                });
            }
        }
    }
    
    /**
     * Draws multiple lines on the screen
     *
     * @param startingX the starting x position of the text
     * @param startingY the starting y position of the text
     * @param separation the Y valye  separatation between each line
     * @param lines the lines which will be drawn
     */
    public void writeInformation(int startingX, int startingY, int separation, String... lines) {
        writeInformation(startingX, startingY, separation, true, lines);
    }
    
    /**
     * Draws multiple lines on the screen
     *
     * @param startingX the starting x position of the text
     * @param startingY the starting y position of the text
     * @param separation the Y valye separatation between each line
     * @param centered true if the text being rendered should be rendered as a centered string
     * @param lines the lines which will be drawn
     */
    public void writeInformation(int startingX, int startingY, int separation, boolean centered, String... lines) {
        if (lines == null || lines.length == 0) {
            return;
        }
        
        // Loop through the lines
        for (String line : lines) {
            // Null components will be treated as an empty string
            if (line == null) {
                line = "";
            }
            
            if (centered) {
                drawCenteredString(this.fontRendererObj, ChatColor.translateAlternateColorCodes('&', line), startingX, startingY, Color.WHITE.getRGB());
            } else {
                drawString(this.fontRendererObj, ChatColor.translateAlternateColorCodes('&', line), startingX, startingY, Color.WHITE.getRGB());
            }
            
            startingY += separation;
        }
    }
    
    /**
     * Simple formatting for a toggled string.
     *
     * @param in the status of the toggle
     * @return a formatted string containing the relevant status to the boolean
     */
    public static String getStatus(boolean in) {
        return in ? ChatColor.GREEN + "Enabled" : (ToggleChatMod.getInstance().getConfigLoader().isModernButton() ? ChatColor.RED : ChatColor.GRAY) + "Disabled";
    }
}
