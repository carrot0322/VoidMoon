package me.carrot0322.voidmoon.feature.command;

import me.carrot0322.voidmoon.VoidMoon;
import me.carrot0322.voidmoon.feature.Feature;

public abstract class Command extends Feature {
    protected String name;
    protected String[] commands;

    public Command(String name) {
        super(name);
        this.name = name;
        this.commands = new String[]{""};
    }

    public Command(String name, String[] commands) {
        super(name);
        this.name = name;
        this.commands = commands;
    }

    public static String getCommandPrefix() {
        return VoidMoon.commandManager.getPrefix();
    }

    public abstract void execute(String[] var1);

    @Override
    public String getName() {
        return this.name;
    }

    public String[] getCommands() {
        return this.commands;
    }
}