package me.carrot0322.voidmoon.feature;

import me.carrot0322.voidmoon.feature.setting.Setting;

import java.util.ArrayList;
import java.util.List;

import static me.carrot0322.voidmoon.util.client.Util.mc;

public class Feature {
        public List<Setting<?>> settings = new ArrayList<>();
        private String name;

        public Feature() {
        }

        public Feature(String name) {
            this.name = name;
        }

        public static boolean nullCheck() {
            return mc.thePlayer == null;
        }

        public static boolean fullNullCheck() {
            return mc.thePlayer == null || mc.theWorld == null;
        }

        public String getName() {
            return this.name;
        }

        public List<Setting<?>> getSettings() {
            return this.settings;
        }

        public boolean hasSettings() {
            return !this.settings.isEmpty();
        }

        public boolean isEnabled() {
            return false;
        }

        public boolean isDisabled() {
            return !this.isEnabled();
        }

        public <T> Setting<T> register(Setting<T> setting) {
            setting.setFeature(this);
            this.settings.add(setting);
            return setting;
        }

        public void unregister(Setting<?> settingIn) {
            ArrayList<Setting<?>> removeList = new ArrayList<>();
            for (Setting<?> setting : this.settings) {
                if (!setting.equals(settingIn)) continue;
                removeList.add(setting);
            }
            if (!removeList.isEmpty()) {
                this.settings.removeAll(removeList);
            }
        }

        public Setting<?> getSettingByName(String name) {
            for (Setting<?> setting : this.settings) {
                if (!setting.getName().equalsIgnoreCase(name)) continue;
                return setting;
            }
            return null;
        }

        public void reset() {
            for (Setting<?> setting : this.settings) {
                setting.reset();
            }
        }

        public void clearSettings() {
            this.settings = new ArrayList<>();
        }
    }