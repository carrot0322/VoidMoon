package me.carrot0322.voidmoon.mixin;

import me.carrot0322.voidmoon.event.impl.Render3DEvent;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.carrot0322.voidmoon.util.client.Util.EVENT_BUS;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {
    @Inject(method = "renderWorldPass", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = At.Shift.BEFORE))
    private void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo callbackInfo) {
        EVENT_BUS.post(new Render3DEvent(partialTicks));
    }
}
