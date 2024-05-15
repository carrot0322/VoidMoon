package me.carrot0322.voidmoon.feature.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.carrot0322.voidmoon.VoidMoon;
import me.carrot0322.voidmoon.feature.command.Command;
import me.carrot0322.voidmoon.feature.module.Module;
import me.carrot0322.voidmoon.feature.setting.Bind;
import me.carrot0322.voidmoon.util.client.ChatUtil;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {
    public BindCommand() {
        super("bind", new String[]{"<module>", "<bind>"});
    }

    @Override
    public void execute(String[] commands) {
        if (commands.length == 1) {
            ChatUtil.sendInfo("Please specify a module.");
            return;
        }
        String rkey = commands[1];
        String moduleName = commands[0];
        Module module = VoidMoon.moduleManager.getModuleByName(moduleName);
        if (module == null) {
            ChatUtil.sendInfo("Unknown module '" + module + "'!");
            return;
        }
        if (rkey == null) {
            ChatUtil.sendInfo(module.getName() + " is bound to " + ChatFormatting.GRAY + module.getBind().toString());
            return;
        }
        int key = Keyboard.getKeyIndex(rkey.toUpperCase());
        if (rkey.equalsIgnoreCase("none")) {
            key = -1;
        }
        if (key == 0) {
            ChatUtil.sendInfo("Unknown key '" + rkey + "'!");
            return;
        }
        module.bind.setValue(new Bind(key));
        ChatUtil.sendInfo("Bind for " + ChatFormatting.GREEN + module.getName() + ChatFormatting.WHITE + " set to " + ChatFormatting.GRAY + rkey.toUpperCase());
    }
}