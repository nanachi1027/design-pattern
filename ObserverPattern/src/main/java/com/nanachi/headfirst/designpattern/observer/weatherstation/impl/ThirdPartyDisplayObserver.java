package com.nanachi.headfirst.designpattern.observer.weatherstation.impl;

import com.nanachi.headfirst.designpattern.observer.weatherstation.IDisplay;
import com.nanachi.headfirst.designpattern.observer.weatherstation.IObserver;
import com.nanachi.headfirst.designpattern.observer.weatherstation.WeatherType;

public class ThirdPartyDisplayObserver implements IDisplay, IObserver<WeatherType> {
    private String weatherInfo;

    @Override
    public void display() {
        System.out.println(this.getClass().getSimpleName() + " show weather " + weatherInfo);
    }

    @Override
    public void update(WeatherType weather) {
        this.weatherInfo = weather.name();
        display();
    }
}
