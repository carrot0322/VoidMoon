package me.carrot0322.voidmoon.mixin;

import me.carrot0322.voidmoon.event.impl.ChatEvent;
import me.carrot0322.voidmoon.event.impl.MotionEvent;
import me.carrot0322.voidmoon.event.impl.UpdateEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.carrot0322.voidmoon.util.client.Util.EVENT_BUS;
import static me.carrot0322.voidmoon.util.client.Util.mc;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    @Inject(method = "onUpdate", at = @At("TAIL"))
    private void tickHook(CallbackInfo ci) {
        EVENT_BUS.post(new UpdateEvent());
    }

    @Inject(method = "sendChatMessage", at = {@At(value = "HEAD")}, cancellable = true)
    public void sendChatMessage(String message, CallbackInfo callback) {
        ChatEvent chatEvent = new ChatEvent(message);
        EVENT_BUS.post(chatEvent);
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"))
    public void onUpdateWalkingPlayer(CallbackInfo ci){
        MotionEvent.PRE event = new MotionEvent.PRE(
                mc.thePlayer.posX,
                mc.thePlayer.getEntityBoundingBox().minY,
                mc.thePlayer.posZ,
                mc.thePlayer.rotationYaw,
                mc.thePlayer.rotationPitch,
                mc.thePlayer.onGround
        );
        EVENT_BUS.post(event);
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("TAIL"))
    public void onUpdateWalkingPlayerTAIL(CallbackInfo ci){
        EVENT_BUS.post(new MotionEvent.POST());
    }
}
