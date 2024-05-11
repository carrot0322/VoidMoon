package me.carrot0322.voidmoon.manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.carrot0322.voidmoon.feature.Feature;
import me.carrot0322.voidmoon.feature.module.Module;
import me.carrot0322.voidmoon.util.client.Jsonable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static me.carrot0322.voidmoon.util.client.Util.EVENT_BUS;
import static me.carrot0322.voidmoon.util.client.Util.mc;

public class ModuleManager implements Jsonable {
    public List<Module> modules = new ArrayList<>();
    public List<Module> sortedModules = new ArrayList<>();
    public List<String> sortedModulesABC = new ArrayList<>();

    public void init() {
        // Module Init
        // COMBAT
        //modules.add(new Aura());

        // MOVEMENT

        // RENDER

        // MISC

        // CLIENT

        // EXPLOIT

        // LEGIT
    }

    public Module getModuleByName(String name) {
        for (Module module : this.modules) {
            if (!module.getName().equalsIgnoreCase(name)) continue;
            return module;
        }
        return null;
    }

    public <T extends Module> T getModuleByClass(Class<T> clazz) {
        for (Module module : this.modules) {
            if (!clazz.isInstance(module)) continue;
            return (T) module;
        }
        return null;
    }

    public void enableModule(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        if (module != null)
            module.enable();
    }

    public void disableModule(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        if (module != null)
            module.disable();
    }

    public void toggleModule(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        if(module != null)
            module.toggle();
    }

    public void enableModule(String name) {
        Module module = this.getModuleByName(name);
        if (module != null)
            module.enable();
    }

    public void disableModule(String name) {
        Module module = this.getModuleByName(name);
        if (module != null)
            module.disable();
    }

    public void toggleModule(String name) {
        Module module = this.getModuleByName(name);
        if(module != null)
            module.toggle();
    }

    public boolean isModuleEnabled(String name) {
        Module module = this.getModuleByName(name);
        return module != null && module.isOn();
    }

    public boolean isModuleEnabled(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        return module != null && module.isOn();
    }

    private Module moduleToBind;

    public void setModuleToBind(Module moduleToBind) {
        this.moduleToBind = moduleToBind;
    }


    public ArrayList<Module> getAllModules() {
        return new ArrayList<>(this.modules);
    }

    public Module getModuleByDisplayName(String displayName) {
        for (Module module : this.modules) {
            if (!module.getDisplayName().equalsIgnoreCase(displayName)) continue;
            return module;
        }
        return null;
    }

    public ArrayList<Module> getEnabledModules() {
        ArrayList<Module> enabledModules = new ArrayList<>();
        for (Module module : this.modules) {
            if (!module.isEnabled()) continue;
            enabledModules.add(module);
        }
        return enabledModules;
    }

    public ArrayList<String> getEnabledModulesName() {
        ArrayList<String> enabledModules = new ArrayList<>();
        for (Module module : this.modules) {
            if (!module.isEnabled() || !module.isDrawn()) continue;
            enabledModules.add(module.getFullArrayString());
        }
        return enabledModules;
    }

    public ArrayList<Module> getModulesByCategory(Module.Category category) {
        ArrayList<Module> modulesCategory = new ArrayList<Module>();
        this.modules.forEach(module -> {
            if (module.getCategory() == category) {
                modulesCategory.add(module);
            }
        });
        return modulesCategory;
    }

    public List<Module.Category> getCategories() {
        return Arrays.asList(Module.Category.values());
    }

    public void onLoad() {
        this.modules.stream().filter(Module::listening).forEach(EVENT_BUS::register);
        this.modules.forEach(Module::onLoad);
    }

    public void onUpdate() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onUpdate);
    }

    public void onTick() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onTick);
    }

    public void onRender2D(Render2DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender2D(event));
    }

    public void onRender3D(Render3DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender3D(event));
    }

    public void sortModules(boolean reverse) {
        this.sortedModules = this.getEnabledModules().stream().filter(Module::isDrawn)
                .sorted(Comparator.comparing(module -> mc.fontRendererObj.getStringWidth(module.getFullArrayString()) * (reverse ? -1 : 1)))
                .collect(Collectors.toList());
    }

    public void sortModulesABC() {
        this.sortedModulesABC = new ArrayList<>(this.getEnabledModulesName());
        this.sortedModulesABC.sort(String.CASE_INSENSITIVE_ORDER);
    }

    public void onUnload() {
        this.modules.forEach(EVENT_BUS::unregister);
        this.modules.forEach(Module::onUnload);
    }

    public void onUnloadPost() {
        for (Module module : this.modules) {
            module.enabled.setValue(false);
        }
    }

    public void onKeyPressed(int eventKey) {
        if (eventKey == -1 || eventKey == 0 || mc.currentScreen instanceof me.coolmint.ngm.features.gui.ClickGui) {
            return;
        }
        modules.forEach(module -> {
            if (module.getBind().getKey() == eventKey)
                module.toggle();
        });
    }

    public void onKeyReleased(int eventKey) {
        if (eventKey == -1 || eventKey == 0 || mc.currentScreen instanceof me.coolmint.ngm.features.gui.ClickGui)
            return;

        modules.forEach(module -> {
            //if (module.getBind().getKey() == eventKey && module.getBind().isHold())
            //module.disable();
        });
    }

    @Override public JsonElement toJson() {
        JsonObject object = new JsonObject();
        for (Module module : modules) {
            object.add(module.getName(), module.toJson());
        }
        return object;
    }

    @Override public void fromJson(JsonElement element) {
        for (Module module : modules) {
            module.fromJson(element.getAsJsonObject().get(module.getName()));
        }
    }

    @Override public String getFileName() {
        return "modules.json";
    }
}