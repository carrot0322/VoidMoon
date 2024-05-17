package me.carrot0322.voidmoon.manager;

import com.google.gson.*;
import me.carrot0322.voidmoon.VoidMoon;
import me.carrot0322.voidmoon.feature.Feature;
import me.carrot0322.voidmoon.feature.module.client.ClickGui;
import me.carrot0322.voidmoon.feature.setting.Bind;
import me.carrot0322.voidmoon.feature.setting.EnumConverter;
import me.carrot0322.voidmoon.feature.setting.Setting;
import me.carrot0322.voidmoon.util.client.Jsonable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static me.carrot0322.voidmoon.util.client.Util.mc;

public class ConfigManager {
    public static final Path PATH = mc.mcDataDir.toPath().resolve("VoidMoon");
    public static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    public final List<Jsonable> jsonables = Collections.unmodifiableList(
            Arrays.asList(VoidMoon.friendManager, VoidMoon.moduleManager)
    );

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void setValueFromJson(Feature feature, Setting setting, JsonElement element) {
        String str;
        switch (setting.getType()) {
            case "Boolean":
                setting.setValue(Boolean.valueOf(element.getAsBoolean()));
                return;
            case "Double":
                setting.setValue(Double.valueOf(element.getAsDouble()));
                return;
            case "Float":
                setting.setValue(Float.valueOf(element.getAsFloat()));
                return;
            case "Integer":
                setting.setValue(Integer.valueOf(element.getAsInt()));
                return;
            case "String":
                str = element.getAsString();
                setting.setValue(str.replace("_", " "));
                return;
            case "Bind":
                setting.setValue((new Bind.BindConverter()).doBackward(element));
                return;
            case "Enum":
                try {
                    EnumConverter converter = new EnumConverter(((Enum) setting.getValue()).getClass());
                    Enum value = converter.doBackward(element);
                    setting.setValue((value == null) ? setting.getDefaultValue() : value);
                } catch (Exception exception) {
                }
                return;
        }
        VoidMoon.logger.error("Unknown Setting type for: " + feature.getName() + " : " + setting.getName());
    }

    public void load() {
        if (!PATH.toFile().exists()) PATH.toFile().mkdirs();

        for (Jsonable jsonable : jsonables) {
            try {
                String read = new String(Files.readAllBytes(PATH.resolve(jsonable.getFileName())));
                jsonable.fromJson(new JsonParser().parse(read).getAsJsonObject());
            } catch (Throwable ignored) {
            }
        }
    }

    public void save() {
        if (!PATH.toFile().exists()) PATH.toFile().mkdirs();

        // disable some modules
        if(ClickGui.getInstance().isEnabled())
            ClickGui.getInstance().disable();

        for (Jsonable jsonable : jsonables) {
            try {
                JsonElement json = jsonable.toJson();
                Files.write(PATH.resolve(jsonable.getFileName()), gson.toJson(json).getBytes());
            } catch (Throwable e) {
                VoidMoon.logger.error(e);
            }
        }
    }
}