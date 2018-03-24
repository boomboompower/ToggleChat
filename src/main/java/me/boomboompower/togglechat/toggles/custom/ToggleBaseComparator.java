package me.boomboompower.togglechat.toggles.custom;

import java.util.Comparator;

import java.util.Map.Entry;

import me.boomboompower.togglechat.toggles.ToggleBase;

import net.minecraft.client.Minecraft;

/**
 * Compare by fontrender length, rather than relying on a linked list
 */
public class ToggleBaseComparator implements Comparator<Entry<String, ToggleBase>> {
    
    private final Minecraft mc = Minecraft.getMinecraft();
    
    @Override
    public int compare(Entry<String, ToggleBase> firstIn, Entry<String, ToggleBase> secondIn) {
        Integer first = this.mc.fontRendererObj.getStringWidth(firstIn.getValue().getDisplayName());
        Integer second = this.mc.fontRendererObj.getStringWidth(secondIn.getValue().getDisplayName());
        
        return first.compareTo(second);
    }
}
