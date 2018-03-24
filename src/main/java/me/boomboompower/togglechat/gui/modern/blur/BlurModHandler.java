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

package me.boomboompower.togglechat.gui.modern.blur;

import me.boomboompower.togglechat.ToggleChatMod;
import me.boomboompower.togglechat.gui.modern.ModernGui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Handles all blur-things for the mod. Licensing info and permission availible in resources
 *
 * @author tterrag1098
 */
@SuppressWarnings("unchecked")
public class BlurModHandler {
    
    private final Minecraft mc = Minecraft.getMinecraft();
    private final ToggleChatMod theMod;
    
    private Field _listShaders;
    private long start;
    
    private boolean enabled;
    
    public BlurModHandler(ToggleChatMod mod) {
        this.theMod = mod;
    }
    
    public BlurModHandler preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        
        return this;
    }
    
    @SubscribeEvent
    public void onGuiChange(final GuiOpenEvent event) {
        if (this._listShaders == null) {
            this._listShaders = ReflectionHelper
                .findField(ShaderGroup.class, "field_148031_d", "listShaders");
        }
        if (this.mc.theWorld != null) {
            final EntityRenderer er = this.mc.entityRenderer;
            if (this.theMod.getConfigLoader().isModernBlur() && !er.isShaderActive()
                && event.gui != null && event.gui instanceof ModernGui) {
                loadShader(er, new ResourceLocation("shaders/post/fade_in_blur.json"));
                this.start = System.currentTimeMillis();
            } else if (er.isShaderActive() && event.gui == null) {
                er.stopUseShader();
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderTick(final TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.END && this.mc.currentScreen != null
            && this.mc.entityRenderer.isShaderActive()) {
            final ShaderGroup sg = this.mc.entityRenderer.getShaderGroup();
            try {
                if (this._listShaders == null) {
                    this._listShaders = ReflectionHelper
                        .findField(ShaderGroup.class, "field_148031_d", "listShaders");
                }
                
                final List<Shader> shaders = (List<Shader>) this._listShaders.get(sg);
                for (final Shader s : shaders) {
                    final ShaderUniform su = s.getShaderManager().getShaderUniform("Progress");
                    if (su != null) {
                        su.set(this.getProgress());
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
            }
        }
    }
    
    /**
     * Reloads all shaders
     */
    public void reload() {
        if (this.mc.theWorld != null) {
            GuiScreen gui = this.mc.currentScreen;
            
            final EntityRenderer er = this.mc.entityRenderer;
            if (!er.isShaderActive() && gui != null && gui instanceof ModernGui) {
                loadShader(er, new ResourceLocation("shaders/post/fade_in_blur.json"));
                this.start = System.currentTimeMillis();
            } else if (er.isShaderActive() && (gui == null || !this.theMod.getConfigLoader()
                .isModernBlur())) {
                er.stopUseShader();
            }
        }
    }
    
    /**
     * Gets the progress of the blur
     *
     * @return
     */
    private float getProgress() {
        return Math.min((System.currentTimeMillis() - this.start), 1000.0f);
    }
    
    /**
     * Loads a shader into the game
     *
     * @param er the entity renderer
     * @param shader the shader
     */
    private void loadShader(EntityRenderer er, ResourceLocation shader) {
        try {
            ReflectionHelper
                .findMethod(EntityRenderer.class, er, new String[] {"func_175069_a", "loadShader"},
                    ResourceLocation.class).invoke(er, shader);
        } catch (ReflectionHelper.UnableToFindMethodException | IllegalAccessException | InvocationTargetException ex) {
        }
    }
}
