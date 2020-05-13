package com.nanachi.adapter;

/**
 * First implements the original behavior interface
 * Then we send instance of INewBehavior implementation to it.
 * Last we call corresponding methods from INewBehavior in IOriginalBehavior methods.
 */
public class OriginalBehaviorAdapter implements IOriginBehaviors {
    private INewBehaviors newBehaviors;

    public OriginalBehaviorAdapter(INewBehaviors newBehaviorsInstance) {
        this.newBehaviors = newBehaviorsInstance;
    }

    public String saySoemthing(String msg) {
        return newBehaviors.haveSomethingToSay(msg);
    }

    public String eatSomething(String msg) {
        return newBehaviors.haveSomethingToEat(msg);
    }
}
