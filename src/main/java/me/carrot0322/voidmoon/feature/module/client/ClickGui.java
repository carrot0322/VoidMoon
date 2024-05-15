package me.carrot0322.voidmoon.feature.module.client;

import com.google.common.eventbus.Subscribe;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.carrot0322.voidmoon.VoidMoon;
import me.carrot0322.voidmoon.event.impl.ClientEvent;
import me.carrot0322.voidmoon.feature.gui.VoidMoonGui;
import me.carrot0322.voidmoon.feature.module.Module;
import me.carrot0322.voidmoon.feature.setting.Setting;
import me.carrot0322.voidmoon.util.client.ChatUtil;
import org.lwjgl.input.Keyboard;

import static me.carrot0322.voidmoon.util.client.Util.mc;

public class ClickGui extends Module {
    public Setting<String> prefix = register(new Setting<String>("Prefix", "."));
    public Setting<Integer> red = register(new Setting<Integer>("Red", 120, 0, 255));
    public Setting<Integer> green = register(new Setting<Integer>("Green", 70, 0, 255));
    public Setting<Integer> blue = register(new Setting<Integer>("Blue", 255, 0, 255));
    public Setting<Integer> hoverAlpha = register(new Setting<Integer>("Alpha", 200, 0, 255));
    public Setting<Integer> topRed = register(new Setting<Integer>("SecondRed", 125, 0, 255));
    public Setting<Integer> topGreen = register(new Setting<Integer>("SecondGreen", 68, 0, 255));
    public Setting<Integer> topBlue = register(new Setting<Integer>("SecondBlue", 255, 0, 255));
    public Setting<Integer> alpha = register(new Setting<Integer>("HoverAlpha", 240, 0, 255));
    public Setting<Boolean> rainbow = register(new Setting<Boolean>("Rainbow", false));
    public Setting<Integer> rainbowHue = register(new Setting<Object>("Delay", Integer.valueOf(240), Integer.valueOf(0), Integer.valueOf(600), v -> this.rainbow.getValue()));
    public Setting<Float> rainbowBrightness = register(new Setting<Object>("Brightness ", Float.valueOf(150.0f), Float.valueOf(1.0f), Float.valueOf(255.0f), v -> this.rainbow.getValue()));
    public Setting<Float> rainbowSaturation = register(new Setting<Object>("Saturation", Float.valueOf(150.0f), Float.valueOf(1.0f), Float.valueOf(255.0f), v -> this.rainbow.getValue()));

    public ClickGui() {
        super("ClickGui", "yeah awesome", Category.CLIENT, true, false, false);
        setBind(Keyboard.KEY_Y);
        setInstance();
    }

    private VoidMoonGui click;
    private static ClickGui INSTANCE = new ClickGui();

    public static ClickGui getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ClickGui();
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Subscribe
    public void onSettingChange(ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.prefix)) {
                VoidMoon.commandManager.setPrefix(this.prefix.getPlannedValue());
                ChatUtil.sendInfo("Prefix set to " + ChatFormatting.DARK_GRAY + VoidMoon.commandManager.getPrefix());
            }
            VoidMoon.colorManager.setColor(this.red.getPlannedValue(), this.green.getPlannedValue(), this.blue.getPlannedValue(), this.hoverAlpha.getPlannedValue());
        }
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(VoidMoonGui.getClickGui());
    }

    @Override
    public void onLoad() {
        VoidMoon.colorManager.setColor(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.hoverAlpha.getValue());
        VoidMoon.commandManager.setPrefix(this.prefix.getValue());
    }

    @Override
    public void onTick() {
        if (!(mc.currentScreen instanceof VoidMoonGui)) {
            this.disable();
        }
    }
}
