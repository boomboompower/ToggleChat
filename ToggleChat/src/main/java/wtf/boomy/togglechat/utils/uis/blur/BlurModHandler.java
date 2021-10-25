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

package wtf.boomy.togglechat.utils.uis.blur;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wtf.boomy.mods.modernui.uis.ModernGui;
import wtf.boomy.togglechat.ToggleChatMod;
import wtf.boomy.togglechat.mixin.EntityRendererAccessor;
import wtf.boomy.togglechat.mixin.ShaderGroupAccessor;

import java.util.List;

/**
 * An implementation of the BlurMC mod by tterrag1098.
 *
 * For the original source see https://github.com/tterrag1098/Blur/blob/1.8.9/src/main/java/com/tterrag/blur/Blur.java
 * For the public license, see https://github.com/tterrag1098/Blur/blob/1.8.9/LICENSE
 *
 * License available under https://github.com/boomboompower/ToggleChat/blob/master/src/main/resources/licenses/BlurMC-License.txt
 *
 * @author tterrag1098, boomboompower
 */
public class BlurModHandler {
    
    private final ResourceLocation blurShader = new ResourceLocation("shaders/post/fade_in_blur.json");
    private final Logger logger = LogManager.getLogger("ToggleChat - Blur");
    private final Minecraft mc = Minecraft.getMinecraft();
    private final ToggleChatMod theMod;
    
    private long start;
    private float lastProgress = 0;
    
    public BlurModHandler(ToggleChatMod mod) {
        this.theMod = mod;
    }
    
    /**
     * Simply initializes the blur mod so events are properly handled by forge.
     */
    public BlurModHandler load() {
        MinecraftForge.EVENT_BUS.register(this);
        return this;
    }
    
    @SubscribeEvent
    public void onGuiChange(GuiOpenEvent event) {
        reloadBlur(event.gui);
    }
    
    @SubscribeEvent
    public void onRenderTick(final TickEvent.RenderTickEvent event) {
        this.mc.mcProfiler.startSection("blur");
        
        if (event.phase != TickEvent.Phase.END) {
            this.mc.mcProfiler.endSection();
            return;
        }
        
        // Only blur if we have blur enabled.
        if (!this.theMod.getConfigLoader().isModernBlur()) {
            this.mc.mcProfiler.endSection();
            return;
        }
        
        // Only blur on our own menus
        if (this.mc.currentScreen == null) {
            this.mc.mcProfiler.endSection();
            return;
        }
        
        // Only update the shader if one is active
        if (!this.mc.entityRenderer.isShaderActive()) {
            this.mc.mcProfiler.endSection();
            return;
        }
        
        float progress = getBlurStrengthProgress();
        
        // If the new progress value matches the old one this
        // will skip the frame update, which (hopefully) resolves the issue
        // with the heavy computations after the "animation" is complete.
        if (progress == this.lastProgress) {
            this.mc.mcProfiler.endSection();
            return;
        }
        
        // Store it for the next iteration!
        this.lastProgress = progress;

        // This is hilariously bad, and could cause frame issues on low-end computers.
        // Why is this being computed every tick? Surely there is a better way?
        // This needs to be optimized.
        try {
            final List<Shader> listShaders = ((ShaderGroupAccessor) this.mc.entityRenderer.getShaderGroup()).getListShaders();

            // Should not happen. Something bad happened.
            if (listShaders == null) {
                this.mc.mcProfiler.endSection();
                return;
            }

            // Iterate through the list of shaders.
            for (Shader shader : listShaders) {
                ShaderUniform su = shader.getShaderManager().getShaderUniform("Progress");
                
                if (su == null) {
                    continue;
                }
    
                // All this for this.
                su.set(progress);
            }
        } catch (IllegalArgumentException  ex) {
            this.logger.error("An error occurred while updating ToggleChat's blur. Please report this!", ex);
        }

        this.mc.mcProfiler.endSection();
    }
    
    /**
     * Activates/deactivates the blur in the current world if
     * one of many conditions are met, such as no current other shader
     * is being used, we actually have the blur setting enabled
     */
    public void reloadBlur(GuiScreen gui) {
        // Don't do anything if no world is loaded
        if (this.mc.theWorld == null) {
            return;
        }
    
        EntityRenderer er = this.mc.entityRenderer;
    
        // If a shader is not already active and the UI is
        // a one of ours, we should load our own blur!
        if (!er.isShaderActive() && gui instanceof ModernGui) {
            ((EntityRendererAccessor) this.mc.entityRenderer).invokeLoadShader(this.blurShader);
        
            this.start = System.currentTimeMillis();
            
        // If a shader is active and the incoming UI is null or we have blur disabled, stop using the shader.
        } else if (er.isShaderActive() && (gui == null || !this.theMod.getConfigLoader().isModernBlur())) {
            String name = er.getShaderGroup().getShaderGroupName();
        
            // Only stop our specific blur ;)
            if (!name.endsWith("fade_in_blur.json")) {
                return;
            }
        
            er.stopUseShader();
        }
    }
    
    /**
     * Returns the strength of the blur as determined by the duration the effect of the blur.
     *
     * The strength of the blur does not go below 5.0F.
     */
    private float getBlurStrengthProgress() {
        return Math.min((System.currentTimeMillis() - this.start) / 50F, 5.0F);
    }
}
