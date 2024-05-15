package me.carrot0322.voidmoon.event.impl;

import me.carrot0322.voidmoon.event.Event;

public class ChatEvent extends Event {
    private final String msg;

    public ChatEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}
