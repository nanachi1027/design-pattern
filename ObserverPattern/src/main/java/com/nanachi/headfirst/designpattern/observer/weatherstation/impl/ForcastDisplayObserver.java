package com.nanachi.headfirst.designpattern.observer.weatherstation.impl;

import com.nanachi.headfirst.designpattern.observer.weatherstation.IDisplay;
import com.nanachi.headfirst.designpattern.observer.weatherstation.IObserver;
import com.nanachi.headfirst.designpattern.observer.weatherstation.WeatherType;

public class ForcastDisplayObserver implements IObserver<WeatherType>, IDisplay {

    private String weatherInfo;

    @Override
    public void display() {
        System.out.println(this.getClass().getSimpleName() + " display " + weatherInfo);
    }

    @Override
    public void update(WeatherType weather) {
        this.weatherInfo = weather.name();
        display();
    }
}
