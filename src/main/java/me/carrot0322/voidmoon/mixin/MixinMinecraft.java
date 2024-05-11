package me.carrot0322.voidmoon.mixin;

import me.carrot0322.voidmoon.event.impl.TickEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.carrot0322.voidmoon.util.client.Util.EVENT_BUS;

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
}
