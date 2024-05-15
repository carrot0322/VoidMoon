package me.carrot0322.voidmoon.event.impl;

import me.carrot0322.voidmoon.event.Event;

public class KeyEvent extends Event {
    private final int key;

    public KeyEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
