package com.nanachi.adapter;

import com.nanachi.adapter.impl.NewBehaviorsImpl;

public class App {
    public static void main(String[] args) {
        INewBehaviors newBehaviors = new NewBehaviorsImpl();
        OriginalBehaviorAdapter originalBehaviorAdapter = new OriginalBehaviorAdapter(newBehaviors);

        BehaviorCaller behaviorCaller = new BehaviorCaller(originalBehaviorAdapter);
        String ret1 = behaviorCaller.eat("Eat something msg");
        System.out.println(ret1);

        String ret2 = behaviorCaller.say("Something to Say");
        System.out.println(ret2);
    }
}
