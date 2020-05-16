package com.nanachi.headfirst.designpattern.observer.weatherstation.impl;

import com.nanachi.headfirst.designpattern.observer.weatherstation.IDisplay;
import com.nanachi.headfirst.designpattern.observer.weatherstation.IObserver;
import com.nanachi.headfirst.designpattern.observer.weatherstation.WeatherType;

public class StatisticsDisplayObserver implements IObserver<WeatherType>, IDisplay {

    private String statisticsContent;
    private String weatherInfo;

    @Override
    public void display() {
        System.out.println(this.getClass().getSimpleName() + " display weather " + weatherInfo);
        System.out.println("statistics: " + this.statisticsContent);
    }

    @Override
    public void update(WeatherType weather) {
        this.weatherInfo = weather.name();
        compute(weather);
        display();
    }

    private void compute(WeatherType weatherType) {
        this.statisticsContent = "compute " + weatherType + " 's temperature is xx";
    }
}
