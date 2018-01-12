package com.frizkykramer.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherModel {

    @SerializedName("name")
    private String cityName;

    @SerializedName("main")
    private Main main;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("weather")
    private List<WeatherInfo> weather;

    public WeatherModel() {
    }

    public String getCityName() {
        return cityName;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public String getWeatherType() {
        return weather.get(0).getMain();
    }

    public int getHumidity() {
        return main.getHumidity();
    }

    public double getTemp() {
        return main.getTemp();
    }

    public double getWindspeed() {
        return wind.getSpeed();
    }

    public double getWindDeg() {
        return wind.getDeg();
    }

    private class Main {
        private double temp;
        @SerializedName("humidity")
        private int humidity;

        public double getTemp() {
            return temp;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    private class Wind {
        private double speed;
        private int deg;

        public double getSpeed() {
            return speed;
        }

        public int getDeg() {
            return deg;
        }
    }

    private class WeatherInfo {
        private int id;
        private String main;
        private String description;
        private String icon;

        public String getMain() {
            return main;
        }
    }
}