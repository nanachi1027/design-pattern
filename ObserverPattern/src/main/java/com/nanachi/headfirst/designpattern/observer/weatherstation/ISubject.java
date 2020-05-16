package com.nanachi.headfirst.designpattern.observer.weatherstation;

import com.nanachi.headfirst.designpattern.observer.weatherstation.IObserver;

public interface ISubject<WeatherType> {
    void register(IObserver<com.nanachi.headfirst.designpattern.observer.weatherstation.WeatherType> observer);
    void unregister(IObserver<com.nanachi.headfirst.designpattern.observer.weatherstation.WeatherType> observer);
    void notifyAllObservers();
}
