package me.carrot0322.voidmoon.feature.module.movement;

import com.google.common.eventbus.Subscribe;
import me.carrot0322.voidmoon.event.impl.PacketEvent;
import me.carrot0322.voidmoon.feature.module.Module;
import me.carrot0322.voidmoon.feature.setting.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;

import static me.carrot0322.voidmoon.util.client.Util.mc;

public class Flight extends Module {
    enum modes {
        VulcanGlide
    }

    public Setting<modes> mode = register(new Setting("Mode", modes.VulcanGlide));

    public Flight() {
        super("Flight", "airline", Category.MOVEMENT, true, false, false);
        setInstance();
    }

    private int ticks = 0;

    @Subscribe
    public void onPacketSendPRE(PacketEvent.SendPRE e) {
        if(nullCheck()) return;

        if (e.getPacket() instanceof C03PacketPlayer) {
            if(mode.getValue().equals(modes.VulcanGlide)){
                if (!mc.thePlayer.onGround) {
                    mc.thePlayer.setVelocity(mc.thePlayer.motionX, ticks % 2 == 0 ? -0.17 : -0.10, mc.thePlayer.motionZ);

                    if (ticks == 0)
                        mc.thePlayer.setVelocity(mc.thePlayer.motionX, -0.16, mc.thePlayer.motionZ);

                    ticks++;
                }
            }
        }
    }

    private static Flight INSTANCE = new Flight();

    public static Flight getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Flight();
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}
