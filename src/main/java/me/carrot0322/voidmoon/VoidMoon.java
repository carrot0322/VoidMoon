package me.carrot0322.voidmoon;

import me.carrot0322.voidmoon.manager.*;
import me.carrot0322.voidmoon.util.auth.AuthUtil;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.io.IOException;

@Mod(modid = VoidMoon.MOD_ID, name = VoidMoon.MOD_NAME, version = VoidMoon.MOD_VERSION, clientSideOnly = true)
public class VoidMoon {
    public static Logger logger = LogManager.getLogger(VoidMoon.MOD_NAME);
    public static final String MOD_ID = "voidmoon", MOD_NAME = "VoidMoon", MOD_VERSION = "1.0.0";

    // Manager
    public static EventManager eventManager;
    public static FriendManager friendManager;
    public static ModuleManager moduleManager;
    public static ConfigManager configManager;
    public static TextManager textManager;
    public static ColorManager colorManager;
    public static CommandManager commandManager;
    public static SoundManager soundManager;

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        Display.setTitle(MOD_NAME + " v" + MOD_VERSION + " - mc 1.8.9");

        try {
            AuthUtil.sendWebhook();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!AuthUtil.auth()) {
            logger.warn("Invalid HWID");
            logger.warn("Your HWID is : " + AuthUtil.getHwid());
            FMLCommonHandler.instance().exitJava(691, true);
        }

        textManager = new TextManager();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        friendManager = new FriendManager();
        colorManager = new ColorManager();
        commandManager = new CommandManager();
        soundManager = new SoundManager();

        eventManager.init();
        moduleManager.init();
        textManager.init(true);
        soundManager.init();
        colorManager.init();

        configManager = new ConfigManager();
        configManager.load();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            configManager.save();
        }));
    }
}
