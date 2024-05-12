package me.carrot0322.voidmoon.feature.module.client;

import com.google.common.eventbus.Subscribe;
import me.carrot0322.voidmoon.VoidMoon;
import me.carrot0322.voidmoon.event.impl.ClientEvent;
import me.carrot0322.voidmoon.feature.module.Module;
import me.carrot0322.voidmoon.feature.setting.Setting;
import me.carrot0322.voidmoon.util.client.ChatUtil;

import java.awt.*;

public class FontMod extends Module {
    public Setting<String> fontName = this.register(new Setting<String>("FontName", "Arial", "Name of the font."));
    public Setting<Boolean> antiAlias = this.register(new Setting<Boolean>("AntiAlias", Boolean.valueOf(true), "Smoother font."));
    public Setting<Boolean> fractionalMetrics = this.register(new Setting<Boolean>("Metrics", Boolean.valueOf(true), "Thinner font."));
    public Setting<Integer> fontSize = this.register(new Setting<Integer>("Size", Integer.valueOf(18), Integer.valueOf(12), Integer.valueOf(30), "Size of the font."));
    public Setting<Integer> fontStyle = this.register(new Setting<Integer>("Style", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(3), "Style of the font."));

    public FontMod() {
        super("CustomFont", "smooth font", Category.CLIENT, true, false, false);
        this.setInstance();
    }

    private static FontMod INSTANCE = new FontMod();
    private boolean reloadFont = false;

    public static FontMod getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FontMod();
        }
        return INSTANCE;
    }

    public static boolean checkFont(String font, boolean message) {
        String[] fonts;
        for (String s : fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
            if (!message && s.equals(font)) {
                return true;
            }
            if (!message) continue;
            ChatUtil.sendError(s);
        }
        return false;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Subscribe
    public void onSettingChange(ClientEvent event) {
        Setting setting;
        if (event.getStage() == 2 && (setting = event.getSetting()) != null && setting.getFeature().equals(this)) {
            if (setting.getName().equals("FontName") && !FontMod.checkFont(setting.getPlannedValue().toString(), false)) {
                ChatUtil.sendError("That font doesnt exist.");
                event.cancel();
                return;
            }
            this.reloadFont = true;
        }
    }

    @Override
    public void onTick() {
        if (this.reloadFont) {
            VoidMoon.textManager.init(false);
            this.reloadFont = false;
        }
    }
}
