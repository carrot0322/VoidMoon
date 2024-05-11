package me.carrot0322.voidmoon.event;

public class Event {
    private boolean cancelled;

    public boolean isCancelled(){
        return cancelled;
    }

    public void cancel(){
        cancelled = true;
    }

    public void setCancel(Boolean cancel){
        cancelled = cancel;
    }
}
