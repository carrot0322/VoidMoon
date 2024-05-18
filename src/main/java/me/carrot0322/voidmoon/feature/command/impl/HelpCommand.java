package me.carrot0322.voidmoon.feature.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.carrot0322.voidmoon.VoidMoon;
import me.carrot0322.voidmoon.feature.command.Command;
import me.carrot0322.voidmoon.util.client.ChatUtil;

import java.util.ArrayList;

public class HelpCommand extends Command {
    public HelpCommand(){
        super("help");
    }

    @Override
    public void execute(String[] var1) {
        ArrayList<Command> array = VoidMoon.commandManager.getCommands();
        ChatUtil.sendInfo("Commands: (" + ChatFormatting.GREEN + array.size() + ChatFormatting.GRAY + "): " + ChatFormatting.GREEN + array.toString().replaceAll("Command", ""));
    }
}
