package me.carrot0322.voidmoon.util.client;

import net.minecraft.network.Packet;

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
}
