package com.nanachi.adapter.impl;

import com.nanachi.adapter.INewBehaviors;

public class NewBehaviorsImpl implements INewBehaviors {
    public String haveSomethingToSay(String msg) {
        return "NewBehaviors have something to say: " + msg;
    }

    public String haveSomethingToEat(String msg) {
        return "NewBehaviors have something to eat: " + msg;
    }
}
