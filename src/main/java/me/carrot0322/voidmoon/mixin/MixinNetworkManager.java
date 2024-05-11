package me.carrot0322.voidmoon.mixin;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import me.carrot0322.voidmoon.event.impl.PacketEvent;
import me.carrot0322.voidmoon.util.client.PacketUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.carrot0322.voidmoon.util.client.Util.EVENT_BUS;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Shadow
    private Channel channel;

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void channelRead0(ChannelHandlerContext chc, Packet<?> packet, CallbackInfo ci) {
        if (this.channel.isOpen() && packet != null) {
            try {
                PacketEvent.ReceivePRE event = new PacketEvent.ReceivePRE(packet);
                EVENT_BUS.post(event);
                if (event.isCancelled())
                    ci.cancel();
            } catch (Exception e) {
            }
        }
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void sendPacket(Packet<?> packet, CallbackInfo ci) {
        if(PacketUtil.getPacketType(packet) != PacketUtil.PacketType.CLIENTSIDE)
            return;

        try {
            PacketEvent.SendPRE event = new PacketEvent.SendPRE(packet);
            EVENT_BUS.post(event);
            if (event.isCancelled()) ci.cancel();
        } catch (Exception e) {
        }
    }
}
