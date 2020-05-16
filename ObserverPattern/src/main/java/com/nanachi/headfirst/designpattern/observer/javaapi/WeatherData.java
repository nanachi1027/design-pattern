package com.nanachi.headfirst.designpattern.observer.javaapi;

import com.nanachi.headfirst.designpattern.observer.weatherstation.WeatherType;

import java.util.Observable;
import java.util.Observer;

public class WeatherData extends Observable {

    private WeatherType weatherType;

    public WeatherData() {
        this.weatherType = WeatherType.RAINY;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }

    public void weatherChanged() {
        notifyObservers(weatherType);
        // update the lastest value is set, we call the set change to update the
        // status in parent class in order to invoke the observable to begin traverse all observers
        // and execute each update(Object obj) method.
        setChanged();
    }

    public void updateWeather(WeatherType currWeather) {
        this.weatherType = currWeather;
    }
}
