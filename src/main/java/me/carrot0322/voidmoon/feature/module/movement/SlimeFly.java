package me.carrot0322.voidmoon.feature.module.movement;

import me.carrot0322.voidmoon.feature.module.Module;
import me.carrot0322.voidmoon.feature.setting.Setting;
import net.minecraft.init.Blocks;

import static me.carrot0322.voidmoon.util.client.Util.mc;

public class SlimeFly extends Module {
    public Setting<Float> power = register(new Setting("Power", 4.5f, 0.1f, 10.0f));

    public SlimeFly() {
        super("SlimeFly", "", Module.Category.MOVEMENT, true, false, false);
    }

    @Override
    public void onUpdate() {
        if(nullCheck())
            return;

        if(!mc.theWorld.getBlockState(mc.thePlayer.getPosition().down()).getBlock().equals(Blocks.slime_block))
            return;

        if(!mc.gameSettings.keyBindJump.isPressed() && !mc.thePlayer.onGround && !mc.thePlayer.movementInput.jump && mc.thePlayer.motionY <= 0.0 && mc.thePlayer.fallDistance <= 1.0f)
            mc.thePlayer.setVelocity(mc.thePlayer.motionX, -power.getValue(), mc.thePlayer.motionZ);
    }
}
