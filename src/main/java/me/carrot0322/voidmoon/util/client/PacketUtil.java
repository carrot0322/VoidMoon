package me.carrot0322.voidmoon.util.client;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;

import java.util.ArrayList;
import java.util.List;

import static me.carrot0322.voidmoon.util.client.Util.mc;

public class PacketUtil {
    public static PacketType getPacketType(Packet<?> packet) {
        String className = packet.getClass().getSimpleName();
        if (className.toUpperCase().startsWith("C")) {
            return PacketType.CLIENTSIDE;
        } else if (className.toUpperCase().startsWith("S")) {
            return PacketType.SERVERSIDE;
        }
        // idk
        return PacketType.UNKNOWN;
    }

    public enum PacketType {
        SERVERSIDE,
        CLIENTSIDE,
        UNKNOWN
    }

    private static List<Packet<?>> packetList = new ArrayList<>();

    public static void sendPacketNoEvent(Packet<?> packet) {
        packetList.add(packet);
        NetworkManager netManager = mc.getNetHandler().getNetworkManager();

        if (netManager == null || !netManager.isChannelOpen()) {
            return;
        }

        netManager.flushOutboundQueue();
        netManager.dispatchPacket(packet, null);
    }
}
