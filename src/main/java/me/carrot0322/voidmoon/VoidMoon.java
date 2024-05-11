package me.carrot0322.voidmoon;

import me.carrot0322.voidmoon.manager.*;
import me.carrot0322.voidmoon.util.auth.AuthUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(modid = VoidMoon.MOD_ID, name = VoidMoon.MOD_NAME, version = VoidMoon.MOD_VERSION, clientSideOnly = true)
public class VoidMoon {
    public static Logger logger = LogManager.getLogger(VoidMoon.MOD_NAME);
    public static final String MOD_ID = "voidmoon", MOD_NAME = "VoidMoon", MOD_VERSION = "1.0.0";

    // Manager
    public static EventManager eventManager;
    public static ModuleManager moduleManager;
    public static ConfigManager configManager;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        try {
            AuthUtil.sendWebhook();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!AuthUtil.auth()) {
            logger.warn("Invalid HWID");
            logger.warn("Your HWID is : " + AuthUtil.getHwid());
            System.exit(512);
        }

        eventManager = new EventManager();
        moduleManager = new ModuleManager();

        eventManager.init();
        moduleManager.init();

        configManager = new ConfigManager();
        configManager.load();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            configManager.save();
        }));
    }
}
