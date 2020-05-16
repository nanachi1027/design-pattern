package com.nanachi.headfirst.designpattern.observer.simpleversion;

import java.util.Random;

public class App {
    public static void main(String[] args) {
        ConcreteObserver ob1 = new ConcreteObserver("Observer1");
        ConcreteObserver ob2 = new ConcreteObserver("Observer2");

        ConcreteSubject concreteSubject = new ConcreteSubject();
        concreteSubject.registerObserver(ob1);
        concreteSubject.registerObserver(ob2);

        State [] states = {State.ALTER, State.DELETE, State.NEW, State.UPDATE};
        Random random = new Random();

        while (true) {
            // here we pick a random value from {NEW, DELETE, UPDATE, ALTER} to current state
            State currentState = states[Math.abs(random.nextInt()) % (states.length - 1)];
            concreteSubject.setState(currentState);
            concreteSubject.notifyAllObservers();
        }

    }
}
