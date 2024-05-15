package me.carrot0322.voidmoon.feature.module.client;

import me.carrot0322.voidmoon.feature.module.Module;

public class Hud extends Module {


    public Hud() {
        super("Hud", "Hud Elements rendered on your screen", Module.Category.CLIENT, true, false, false);
        setInstance();
    }

    private static Hud INSTANCE = new Hud();

    public static Hud getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Hud();
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}