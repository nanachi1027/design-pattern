package com.nanachi.headfirst.designpattern.observer.javaapi;

import com.nanachi.headfirst.designpattern.observer.javaapi.observers.CurrentConditionDisplayObserver;
import com.nanachi.headfirst.designpattern.observer.javaapi.observers.DisplayObserver;
import com.nanachi.headfirst.designpattern.observer.weatherstation.WeatherType;

import java.util.Random;

public class App {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionDisplayObserver observer = new CurrentConditionDisplayObserver();
        weatherData.addObserver(observer);

        DisplayObserver displayObserver = new DisplayObserver(weatherData);

        WeatherType [] weathers = {WeatherType.SUNNY, WeatherType.RAINY, WeatherType.SNOW};
        Random random = new Random();

        while (true) {
            System.out.println();
            WeatherType currWeather = weathers[Math.abs(random.nextInt())  % (weathers.length - 1)];
            weatherData.updateWeather(currWeather);
            weatherData.weatherChanged();
            System.out.println();
        }
    }
}
