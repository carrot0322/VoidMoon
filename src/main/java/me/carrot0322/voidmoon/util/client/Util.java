package me.carrot0322.voidmoon.util.client;

import com.google.common.eventbus.EventBus;
import net.minecraft.client.Minecraft;

public class Util {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static EventBus EVENT_BUS = new EventBus();
}
