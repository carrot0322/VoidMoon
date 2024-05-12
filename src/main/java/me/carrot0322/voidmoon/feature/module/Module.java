package me.carrot0322.voidmoon.feature.module;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.carrot0322.voidmoon.VoidMoon;
import me.carrot0322.voidmoon.event.impl.ClientEvent;
import me.carrot0322.voidmoon.event.impl.Render2DEvent;
import me.carrot0322.voidmoon.event.impl.Render3DEvent;
import me.carrot0322.voidmoon.feature.Feature;
import me.carrot0322.voidmoon.feature.module.client.Notification;
import me.carrot0322.voidmoon.feature.setting.Bind;
import me.carrot0322.voidmoon.feature.setting.Setting;
import me.carrot0322.voidmoon.util.client.ChatUtil;
import net.minecraft.util.IChatComponent;

import static me.carrot0322.voidmoon.util.client.Util.EVENT_BUS;
import static me.carrot0322.voidmoon.util.client.Util.mc;

public class Module extends Feature {
    private final String description;
    private final Category category;
    public Setting<Boolean> enabled = this.register(new Setting<Boolean>("Enabled", false));
    public Setting<Boolean> drawn = this.register(new Setting<Boolean>("Drawn", true));
    public Setting<Bind> bind = this.register(new Setting<Bind>("Keybind", new Bind(-1)));
    public Setting<String> displayName;
    public boolean hasListener;
    public boolean alwaysListening;
    public boolean hidden;
    public float arrayListOffset = 0.0f;
    public float arrayListVOffset = 0.0f;
    public float offset;
    public float vOffset;
    public boolean sliding;

    public Module(String name, String description, Category category, boolean hasListener, boolean hidden, boolean alwaysListening) {
        super(name);
        this.displayName = this.register(new Setting<String>("DisplayName", name));
        this.description = description;
        this.category = category;
        this.hasListener = hasListener;
        this.hidden = hidden;
        this.alwaysListening = alwaysListening;
    }

    public boolean isSliding() {
        return this.sliding;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onToggle() {
    }

    public void onLoad() {
    }

    public void onTick() {
    }

    public void onLogin() {
    }

    public void onLogout() {
    }

    public void onUpdate() {
    }

    public void onRender2D(Render2DEvent event) {
    }

    public void onRender3D(Render3DEvent event) {
    }

    public void onUnload() {
    }

    public String getDisplayInfo() {
        return null;
    }

    public boolean isOn() {
        return this.enabled.getValue();
    }

    public boolean isOff() {
        return this.enabled.getValue() == false;
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            this.enable();
        } else {
            this.disable();
        }
    }

    public void enable() {
        this.enabled.setValue(Boolean.TRUE);
        this.onToggle();
        this.onEnable();

        if (Notification.getInstance().toggleNotify.getValue())
            ChatUtil.sendToggle(true, this.getDisplayName());

        if (this.isOn() && this.hasListener && !this.alwaysListening) {
            EVENT_BUS.register(this);
        }
    }

    public void disable() {
        if (this.hasListener && !this.alwaysListening) {
            EVENT_BUS.unregister(this);
        }
        this.enabled.setValue(false);

        if (Notification.getInstance().toggleNotify.getValue())
            ChatUtil.sendToggle(false, this.getDisplayName());

        this.onToggle();
        this.onDisable();
    }

    public void toggle() {
        ClientEvent event = new ClientEvent(!this.isEnabled() ? 1 : 0, this);
        EVENT_BUS.post(event);
        if (!event.isCancelled()) {
            this.setEnabled(!this.isEnabled());
        }
    }

    public String getDisplayName() {
        return this.displayName.getValue();
    }

    public void setDisplayName(String name) {
        Module module = VoidMoon.moduleManager.getModuleByDisplayName(name);
        Module originalModule = VoidMoon.moduleManager.getModuleByName(name);
        if (module == null && originalModule == null) {
            ChatUtil.sendError(this.getDisplayName() + ", name: " + this.getName() + ", has been renamed to: " + name);
            this.displayName.setValue(name);
            return;
        }
        ChatUtil.sendError("A module of this name already exists.");
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isDrawn() {
        return this.drawn.getValue();
    }

    public void setDrawn(boolean drawn) {
        this.drawn.setValue(drawn);
    }

    public Category getCategory() {
        return this.category;
    }

    public String getInfo() {
        return null;
    }

    public Bind getBind() {
        return this.bind.getValue();
    }

    public void setBind(int key) {
        this.bind.setValue(new Bind(key));
    }

    public boolean listening() {
        return this.hasListener && this.isOn() || this.alwaysListening;
    }

    public String getFullArrayString() {
        return this.getDisplayName() + ChatFormatting.GRAY + (this.getDisplayInfo() != null ? " [" + ChatFormatting.WHITE + this.getDisplayInfo() + ChatFormatting.GRAY + "]" : "");
    }

    public enum Category {
        COMBAT("Combat"),
        MOVEMENT("Movement"),
        PLAYER("Player"),
        RENDER("Render"),
        MISC("Misc"),
        EXPLOIT("Exploit"),
        CLIENT("Client");

        private final String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}