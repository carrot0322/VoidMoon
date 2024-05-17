package me.carrot0322.voidmoon.mixin;

import me.carrot0322.voidmoon.VoidMoon;
import me.carrot0322.voidmoon.event.impl.ChatEvent;
import me.carrot0322.voidmoon.event.impl.MotionEvent;
import me.carrot0322.voidmoon.event.impl.UpdateEvent;
import me.carrot0322.voidmoon.feature.command.Command;
import me.carrot0322.voidmoon.util.client.ChatUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
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

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void sendChatMessageHook(String content, CallbackInfo ci) {
        ChatEvent event = new ChatEvent(content);
        EVENT_BUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo ci) {
        if(message.equals(".")){
            ChatUtil.sendInfo("Sent . ¯\\_(ツ)_/¯");
        } else if (message.startsWith(VoidMoon.commandManager.getPrefix())) {
            try {
                if (message.length() > 1) {
                    VoidMoon.commandManager.executeCommand(message.substring(Command.getCommandPrefix().length() - 1));
                } else {
                    ChatUtil.sendInfo("Please enter a command.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ChatUtil.sendError("An error occurred while running this command. Check the log!");
            }

            ci.cancel();
        }
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
