package com.nanachi.headfirst.designpattern.observer.simpleversion;

import java.util.ArrayList;
import java.util.List;

public class ConcreteSubject implements Subject {

    private State state;
    private List<Observer> observerList;

    public ConcreteSubject() {
        state = State.DELETE;
        this.observerList = new ArrayList<Observer>();
    }

    public boolean registerObserver(Observer observer) {
        observerList.add(observer);
        return true;
    }

    public boolean removeObserver(Observer observer) {
        return false;
    }

    public void notifyAllObservers() {
        for (Observer observer : this.observerList) {
            observer.update(state);
        }
    }

    public State getState() {
        return this.state;
    }


    public void setState(State state) {
        this.state = state;
    }
}
