package com.nanachi.headfirst.designpattern.observer.javaapi.observers;

import com.nanachi.headfirst.designpattern.observer.javaapi.WeatherData;
import com.nanachi.headfirst.designpattern.observer.weatherstation.WeatherType;

import java.util.Observable;
import java.util.Observer;

public class DisplayObserver implements Observer {
    private Observable observable;
    private WeatherType weatherType;

    public DisplayObserver(Observable observable) {
        this.observable = observable;
        // here is another way to append Observer to Observal's notify observer list
        this.observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (this.observable instanceof WeatherData) {
            WeatherData weatherData = (WeatherData) observable;
            this.weatherType = weatherData.getWeatherType();
            display();
        }
    }

    public void display() {
        System.out.println(this.getClass().getSimpleName() + " weather type " + this.weatherType.name());
    }
}
