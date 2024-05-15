package me.carrot0322.voidmoon.feature.module.movement;

import me.carrot0322.voidmoon.feature.module.Module;
import me.carrot0322.voidmoon.feature.module.client.ClickGui;
import me.carrot0322.voidmoon.feature.setting.Setting;
import me.carrot0322.voidmoon.util.player.PlayerUtil;

import static me.carrot0322.voidmoon.util.client.Util.mc;

public class Sprint extends Module {
    enum modes {
        Legit,
        Rage
    }

    public Setting<modes> mode = register(new Setting("Mode", modes.Legit));

    public Sprint() {
        super("Sprint", "", Category.MOVEMENT, true, false, false);
        setInstance();
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        mc.thePlayer.setSprinting(false);
    }

    @Override
    public void onUpdate() {
        if (nullCheck()) return;

        if(mode.getValue() == modes.Rage)
            mc.thePlayer.setSprinting(true);
        else
            mc.thePlayer.setSprinting(mc.thePlayer.getFoodStats().getFoodLevel() > 6 && !mc.thePlayer.isCollidedHorizontally & !(mc.thePlayer.movementInput.moveForward < 0.1) && PlayerUtil.isMoving() && !mc.thePlayer.isBlocking());
    }

    private static Sprint INSTANCE = new Sprint();

    public static Sprint getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Sprint();
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}
