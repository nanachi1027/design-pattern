package com.nanachi.headfirst.designpattern.observer.simpleversion;

public class ConcreteObserver implements Observer {

    private String name;

    public ConcreteObserver(String name) {
        this.name = name;
    }

    public void update(State state) {
        System.out.println("Observer " + this.name + " receives state is " + state.toString());
    }
}
