package me.carrot0322.voidmoon.mixin;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {
    @Redirect(method = "handleKeyboardInput", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKeyState()Z"))
    private boolean inputPatch() {
        return Keyboard.getEventKeyState() || (Keyboard.getEventKey() == 0 && Character.isDefined(Keyboard.getEventCharacter()));
    }
}
