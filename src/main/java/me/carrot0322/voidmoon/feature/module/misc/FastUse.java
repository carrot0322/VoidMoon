package me.carrot0322.voidmoon.feature.module.misc;

import me.carrot0322.voidmoon.feature.module.Module;
import me.carrot0322.voidmoon.feature.setting.Setting;

public class FastUse extends Module {
    public Setting<Integer> speed = register(new Setting("Speed", 0, 0, 4));

    public FastUse() {
        super("FastUse", "", Category.MISC, true, false, false);
        setInstance();
    }

    private static FastUse INSTANCE = new FastUse();

    public static FastUse getInstance() {
        if (INSTANCE == null)
            INSTANCE = new FastUse();
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}
