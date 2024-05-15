package me.carrot0322.voidmoon.manager;

import com.google.common.eventbus.Subscribe;
import me.carrot0322.voidmoon.VoidMoon;
import me.carrot0322.voidmoon.event.impl.*;
import me.carrot0322.voidmoon.feature.Feature;
import me.carrot0322.voidmoon.feature.command.Command;
import me.carrot0322.voidmoon.util.client.ChatUtil;
import me.carrot0322.voidmoon.util.client.Timer;
import org.lwjgl.input.Keyboard;

import static me.carrot0322.voidmoon.util.client.Util.EVENT_BUS;
import static me.carrot0322.voidmoon.util.client.Util.mc;

public class EventManager extends Feature {
    private final Timer logoutTimer = new Timer();

    public void init() {
        EVENT_BUS.register(this);
    }

    public void onUnload() {
        EVENT_BUS.unregister(this);
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (!fullNullCheck()) {
            VoidMoon.moduleManager.onUpdate();
            VoidMoon.moduleManager.sortModules(true);
            onTick();
        }
    }

    public void onTick() {
        if (fullNullCheck())
            return;
        VoidMoon.moduleManager.onTick();
    }

    @Subscribe
    public void onKeyInput(KeyEvent event) {
        if (Keyboard.getEventKeyState())
            VoidMoon.moduleManager.onKeyPressed(event.getKey());
    }

    @Subscribe
    public void onChatSent(ChatEvent event) {
        if (event.getMsg().startsWith(Command.getCommandPrefix())) {
            event.setCancel(true);
            try {
                mc.ingameGUI.getChatGUI().addToSentMessages(event.getMsg());
                if (event.getMsg().length() > 1) {
                    VoidMoon.commandManager.executeCommand(event.getMsg().substring(Command.getCommandPrefix().length() - 1));
                } else {
                    ChatUtil.sendInfo("Please enter a command.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ChatUtil.sendError("An error occurred while running this command. Check the log!");
            }
        }
    }

    /*
    @Subscribe
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
        if (fullNullCheck())
            return;
        if (event.getStage() == Stage.PRE) {
            VoidMoon.speedManager.updateValues();
            VoidMoon.rotationManager.updateRotations();
            VoidMoon.positionManager.updatePosition();
        }
        if (event.getStage() == Stage.POST) {
            VoidMoon.rotationManager.restoreRotations();
            VoidMoon.positionManager.restorePosition();
        }
    }

    @Subscribe
    public void onPacketReceive(PacketEvent.ReceivePRE event) {
        VoidMoon.serverManager.onPacketReceived();
        if (event.getPacket() instanceof WorldTimeUpdateS2CPacket)
            VoidMoon.serverManager.update();
    }
 */
    @Subscribe
    public void onWorldRender(Render3DEvent event) {
        VoidMoon.moduleManager.onRender3D(event);
    }

    @Subscribe public void onRenderGameOverlayEvent(Render2DEvent event) {
        VoidMoon.moduleManager.onRender2D(event);
    }
}