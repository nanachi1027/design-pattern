package com.nanachi.adapter;

/**
 * Suppose we have a system caller: BehaviorCaller.
 * Only instance which implements the IOriginBehaviors can adopt to it.
 */
public class BehaviorCaller {
    private IOriginBehaviors behaviors;

    public BehaviorCaller(IOriginBehaviors behaviors) {
        this.behaviors = behaviors;
    }

    public String eat(String msg) {
        return behaviors.eatSomething(msg);
    }

    public String say(String msg) {
        return behaviors.saySoemthing(msg);
    }
}
