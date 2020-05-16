package com.nanachi.headfirst.designpattern.observer.javaapi.observers;

import com.nanachi.headfirst.designpattern.observer.weatherstation.WeatherType;

import java.util.Observable;
import java.util.Observer;

public class CurrentConditionDisplayObserver implements Observer {

    private String weatherInfo;

    @Override
    public void update(Observable o, Object arg) {
        WeatherType weatherType = (WeatherType) arg;
        this.weatherInfo = weatherType.name();
        display();
    }

    public void display() {
        System.out.println(this.getClass().getSimpleName() + " display weather info " + weatherInfo);
    }
}
