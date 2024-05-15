package me.carrot0322.voidmoon.mixin;

import me.carrot0322.voidmoon.event.impl.KeyEvent;
import me.carrot0322.voidmoon.event.impl.TickEvent;
import me.carrot0322.voidmoon.feature.gui.VoidMoonGui;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.carrot0322.voidmoon.util.client.Util.EVENT_BUS;
import static me.carrot0322.voidmoon.util.client.Util.mc;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(at = @At("HEAD"), method = "runTick")
    private void onPreTick(CallbackInfo info) {
        EVENT_BUS.post(TickEvent.Pre.get());
    }

    @Inject(at = @At("TAIL"), method = "runTick")
    private void onTick(CallbackInfo info) {
        EVENT_BUS.post(TickEvent.Post.get());
    }

    @Inject(method = {"dispatchKeypresses"}, at = {@At(value = "INVOKE", remap = false, target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 0, shift = At.Shift.BEFORE)})
    private void onKeyboard(CallbackInfo callbackInfo) {
        boolean whitelist = mc.currentScreen == null
                || mc.currentScreen instanceof VoidMoonGui;

        if (!whitelist) return;

        int i;
        int n = i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();

        if (Keyboard.getEventKeyState()) {
            KeyEvent event = new KeyEvent(i);
            EVENT_BUS.post(event);
        }
    }
}
