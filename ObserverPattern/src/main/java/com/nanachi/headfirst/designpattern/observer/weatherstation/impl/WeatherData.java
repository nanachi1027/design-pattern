package com.nanachi.headfirst.designpattern.observer.weatherstation.impl;

import com.nanachi.headfirst.designpattern.observer.weatherstation.IObserver;
import com.nanachi.headfirst.designpattern.observer.weatherstation.ISubject;
import com.nanachi.headfirst.designpattern.observer.weatherstation.WeatherType;

import java.util.ArrayList;
import java.util.List;

public class WeatherData implements ISubject<WeatherType> {

    private List<IObserver<WeatherType>> observers;

    private WeatherType weatherType;

    public WeatherData() {
        this.observers = new ArrayList<>();
        weatherType = WeatherType.RAINY;
    }

    public void weatherChanged() {
        notifyAllObservers();
    }

    @Override
    public void register(IObserver observer) {
        if (this.observers.contains(observer)) {
            this.observers.remove(observer);
        }
        this.observers.add(observer);
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    @Override
    public void unregister(IObserver observer) {
        if (this.observers.contains(observer)) {
            this.observers.remove(observer);
        }
    }

    @Override
    public void notifyAllObservers() {
        for (IObserver obj : this.observers) {
            obj.update(weatherType);
        }
    }
}
