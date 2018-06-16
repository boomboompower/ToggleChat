package me.boomboompower.togglechat.gui.modern.scrollable;

import me.boomboompower.togglechat.gui.custom.ICustomToggleGui;
import me.boomboompower.togglechat.gui.modern.ModernGui;
import me.boomboompower.togglechat.toggles.custom.ToggleCondition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModernGuiToggleList extends GuiListExtended {
    
    private final ICustomToggleGui scrollableData;
    private final List<IGuiListEntry> listEntries;
    private int maxLabelWidth;
    
    public ModernGuiToggleList(ICustomToggleGui scrollableData) {
        super(Minecraft.getMinecraft(), scrollableData.width, scrollableData.height, 63,
            scrollableData.height - 32, 20);
        
        List<ToggleCondition> customs = scrollableData.getCustomToggle()._getConditions();
        
        this.scrollableData = scrollableData;
        this.listEntries = new ArrayList<>();
        
        int i = 0;
        String s = null;
        int maxListLabelWidth = 0;
        
        for (ToggleCondition custom : customs) {
            String text = custom.getText();
            
            int width = this.mc.fontRendererObj.getStringWidth(text);
            
            if (width > maxListLabelWidth) {
                maxListLabelWidth = width;
            }
            
            this.listEntries.add(new CustomToggleEntry(text));
        }
        this.maxLabelWidth = maxListLabelWidth;
    }
    
    @Override
    public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
        this.mouseX = mouseXIn;
        this.mouseY = mouseYIn;
        int i = this.getScrollBarX();
        int j = i + 6;
        this.bindAmountScrolled();
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        int k = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
        int l = this.top + 4 - (int) this.amountScrolled;
        
        if (this.hasListHeader) {
            this.drawListHeader(k, l, tessellator);
        }
        
        this.drawSelectionBox(k, l, mouseXIn, mouseYIn);
        GlStateManager.disableDepth();
        
        int j1 = this.func_148135_f();
        
        if (j1 > 0) {
            int k1 = MathHelper.clamp_int(
                (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight(), 32,
                this.bottom - this.top - 8);
            int scrollTop =
                (int) this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;
            
            if (scrollTop < this.top) {
                scrollTop = this.top;
            }
            
            ModernGui.drawRect(i, this.top, j, this.bottom, new Color(0, 0, 0, 170).getRGB());
            ModernGui
                .drawRect(i, scrollTop + k1 - 1, j, scrollTop, new Color(192, 192, 192).getRGB());
        }
        
        int i1 = 4;
        this.top = 32;
        this.bottom = this.height - 27;
        this.overlayBackground(0, this.top, 255, 255);
        this.overlayBackground(this.bottom, this.height, 255, 255);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(this.left, this.top + i1, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0)
            .endVertex();
        worldrenderer.pos(this.right, this.top + i1, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0)
            .endVertex();
        worldrenderer.pos(this.right, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255)
            .endVertex();
        worldrenderer.pos(this.left, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255)
            .endVertex();
        tessellator.draw();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(this.left, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255)
            .endVertex();
        worldrenderer.pos(this.right, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255)
            .endVertex();
        worldrenderer.pos(this.right, this.bottom - i1, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0)
            .endVertex();
        worldrenderer.pos(this.left, this.bottom - i1, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0)
            .endVertex();
        tessellator.draw();
        
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
    }
    
    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent) {
        if (this.isMouseYWithinSlotBounds(mouseY)) {
            int i = this.getSlotIndexFromScreenCoords(mouseX, mouseY);
            
            if (i >= 0) {
                int j = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
                int k = this.top + 4 - this.getAmountScrolled() + i * this.slotHeight
                    + this.headerPadding;
                int l = mouseX - j;
                int i1 = mouseY - k;
                
                if (this.getListEntry(i).mousePressed(i, mouseX, mouseY, mouseEvent, l, i1)) {
                    this.setEnabled(false);
                    return true;
                }
            }
        }
        
        return false;
    }
    
    protected int getMaxLabelWidth() {
        return this.maxLabelWidth;
    }
    
    @Override
    protected int getSize() {
        return this.listEntries.size();
    }
    
    @Override
    public IGuiListEntry getListEntry(int index) {
        return this.listEntries.get(index);
    }
    
    @Override
    protected int getScrollBarX() {
        return this.width - 6;
    }
    
    @Override
    public int getListWidth() {
        return super.getListWidth() + 32;
    }
}