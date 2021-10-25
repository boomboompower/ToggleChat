package wtf.boomy.togglechat.mixin;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Tuple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wtf.boomy.togglechat.GuiNewChatToggleHook;

import java.util.Collections;
import java.util.List;

@Mixin(GuiNewChat.class)
public class GuiNewChatMixin {
    @Redirect(method = "setChatLine", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiUtilRenderComponents;splitText(Lnet/minecraft/util/IChatComponent;ILnet/minecraft/client/gui/FontRenderer;ZZ)Ljava/util/List;"))
    private List<IChatComponent> invokeMessageEvent(IChatComponent p_178908_0_, int p_178908_1_, FontRenderer p_178908_2_, boolean p_178908_3_, boolean p_178908_4_) {
        Tuple<Boolean, IChatComponent> component = GuiNewChatToggleHook.handleChat(p_178908_0_);
        return component.getFirst() ? Collections.emptyList() : GuiUtilRenderComponents.splitText(component.getSecond(), p_178908_1_, p_178908_2_, p_178908_3_, p_178908_4_);
    }
}