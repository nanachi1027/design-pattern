package com.nanachi.headfirst.designpattern.observer.weatherstation;

import com.nanachi.headfirst.designpattern.observer.weatherstation.impl.*;

import java.util.Random;

public class App {
    public static void main(String[] args) {
        // create a subject instance
        WeatherData weatherDataSubscriber = new WeatherData();

        // create 4 observers and register them to the subject instance
        CurrentConditionDisplayObserver conditionDisplayObserver = new CurrentConditionDisplayObserver();
        ForcastDisplayObserver forcastDisplayObserver = new ForcastDisplayObserver();
        ThirdPartyDisplayObserver thirdPartyDisplayObserver = new ThirdPartyDisplayObserver();
        StatisticsDisplayObserver statisticsDisplayObserver = new StatisticsDisplayObserver();

        weatherDataSubscriber.register(conditionDisplayObserver);
        weatherDataSubscriber.register(forcastDisplayObserver);
        weatherDataSubscriber.register(thirdPartyDisplayObserver);
        weatherDataSubscriber.register(statisticsDisplayObserver);


        WeatherType [] weatherInfos = {WeatherType.RAINY, WeatherType.SUNNY, WeatherType.SNOW};
        Random random = new Random();

        int counter = 0;
        while (counter < 100) {
            System.out.println();
            WeatherType currentWeather = weatherInfos[ Math.abs(random.nextInt()) % (weatherInfos.length - 1)];
            weatherDataSubscriber.setWeatherType(currentWeather);
            weatherDataSubscriber.weatherChanged();
            counter++;
            System.out.println();
        }

        // here we unregister the third-party observer's subscription from WeatherData(the data publisher)
        weatherDataSubscriber.unregister(conditionDisplayObserver);

        counter = 0;
        while (counter < 100) {
            System.out.println();
            WeatherType currentWeather =  weatherInfos[ Math.abs(random.nextInt()) % (weatherInfos.length - 1)];
            weatherDataSubscriber.setWeatherType(currentWeather);
            weatherDataSubscriber.weatherChanged();
            counter++;
            System.out.println();
        }
    }
}
