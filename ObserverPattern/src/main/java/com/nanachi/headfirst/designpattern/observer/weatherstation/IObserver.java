package com.nanachi.headfirst.designpattern.observer.weatherstation;

public interface IObserver<T extends WeatherType> {
    void update(T weather);
}
