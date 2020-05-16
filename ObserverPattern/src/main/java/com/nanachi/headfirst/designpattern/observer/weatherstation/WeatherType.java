package com.nanachi.headfirst.designpattern.observer.weatherstation;

public enum WeatherType {
    SUNNY((byte) 0), RAINY((byte) 1), SNOW((byte) 2);

    public final byte weather;

    WeatherType(byte weather) {
        this.weather = weather;
    }

    public static String getWeather(byte weather) {
        String value = "UNKNOWN";
        switch (weather) {
            case (byte) 0:
                value = new String("SUNNY");
                break;
            case (byte) 1:
                value = new String("RAINY");
                break;
            case (byte) 2:
                value = new String("SNOW");
                break;
            default:
                break;
        }
        return value;
    }
}
