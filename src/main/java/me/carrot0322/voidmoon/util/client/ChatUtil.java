package me.carrot0322.voidmoon.util.client;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.carrot0322.voidmoon.VoidMoon;
import net.minecraft.util.IChatComponent;

import static me.carrot0322.voidmoon.feature.Feature.nullCheck;
import static me.carrot0322.voidmoon.util.client.Util.mc;

public class ChatUtil {
    public static String MsgPrefix(){
        return ChatFormatting.WHITE + "[" + ChatFormatting.DARK_PURPLE + VoidMoon.MOD_NAME + ChatFormatting.WHITE + "]" + ChatFormatting.RESET + " ";
    }

    public static void sendInfo(String message) {
        sendSilentMessage(MsgPrefix() + ChatFormatting.GRAY + message);
    }

    public static void sendWarning(String message) {
        sendSilentMessage(MsgPrefix() + ChatFormatting.YELLOW + message);
    }

    public static void sendError(String message) {
        sendSilentMessage(MsgPrefix() + ChatFormatting.RED + message);
    }

    public static void sendToggle(Boolean toggle, String moduleName) {
        if (toggle)
            sendSilentMessage("[" + ChatFormatting.GREEN  + "+" + ChatFormatting.RESET + "] " + ChatFormatting.GREEN + moduleName + ChatFormatting.RESET + " toggled on.");
        else
            sendSilentMessage("[" + ChatFormatting.RED + "-" + ChatFormatting.RESET + "] " + ChatFormatting.RED + moduleName + ChatFormatting.RESET + " toggled off.");
    }

    public static void sendSilentMessage(String message) {
        if (nullCheck())
            return;

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", message);

        mc.thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent(jsonObject.toString()));
    }
}