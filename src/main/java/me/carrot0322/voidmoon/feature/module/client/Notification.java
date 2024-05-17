package me.carrot0322.voidmoon.feature.module.client;

import me.carrot0322.voidmoon.feature.module.Module;
import me.carrot0322.voidmoon.feature.setting.Setting;

public class Notification extends Module {
    public Setting<Boolean> toggleNotify = register(new Setting("Module Toggle", true));
    public Setting<Boolean> toggleSound = register(new Setting("Module Toggle Sound", true));
    public Setting<Integer> toggleVolume = register(new Setting("Module Toggle Volume", 100, 0, 100, v -> toggleSound.getValue()));

    public Notification() {
        super("Notification", "notification", Category.CLIENT, true, false, false);
        setInstance();
    }

    private static Notification INSTANCE = new Notification();

    public static Notification getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Notification();
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}
