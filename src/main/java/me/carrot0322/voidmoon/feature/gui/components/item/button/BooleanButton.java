package me.carrot0322.voidmoon.feature.gui.components.item.button;

import me.carrot0322.voidmoon.VoidMoon;
import me.carrot0322.voidmoon.feature.gui.VoidMoonGui;
import me.carrot0322.voidmoon.feature.module.client.ClickGui;
import me.carrot0322.voidmoon.feature.setting.Setting;
import me.carrot0322.voidmoon.util.render.RenderUtil;

public class BooleanButton extends Button {
    private final Setting setting;

    public BooleanButton(Setting setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 15;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + (float) this.width + 7.4f, this.y + (float) this.height - 0.5f, this.getState() ? (!this.isHovering(mouseX, mouseY) ? VoidMoon.colorManager.getColorWithAlpha(VoidMoon.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue()) : VoidMoon.colorManager.getColorWithAlpha(VoidMoon.moduleManager.getModuleByClass(ClickGui.class).alpha.getValue())) : (!this.isHovering(mouseX, mouseY) ? 0x11555555 : -2007673515));
        VoidMoon.textManager.drawStringWithShadow(this.getName(), this.x + 2.3f, this.y - 1.7f - (float) VoidMoonGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
    }

    @Override
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public int getHeight() {
        return 14;
    }

    @Override
    public void toggle() {
        this.setting.setValue((Boolean) this.setting.getValue() == false);
    }

    @Override
    public boolean getState() {
        return (Boolean) this.setting.getValue();
    }
}