package com.nanachi.headfirst.designpattern.observer.simpleversion;

public interface Subject {
    boolean registerObserver(Observer observer);
    boolean removeObserver(Observer observer);
    void notifyAllObservers();
}
