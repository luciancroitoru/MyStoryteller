package com.example.lucian.mystoryteller.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

//class for a WeatherEntity object to be stored in the database, with getter and setter methods
@Entity(tableName = "weather")
public class WeatherEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String cityName;
    private String weatherData;
    @ColumnInfo(name = "requested_at")
    private Date requestedAt;

    @Ignore
    public WeatherEntry(String cityName, String weatherData, Date requestedAt) {
        this.cityName = cityName;
        this.weatherData = weatherData;
        this.requestedAt = requestedAt;
    }

    public WeatherEntry(int id, String cityName, String weatherData, Date requestedAt) {
        this.id = id;
        this.cityName = cityName;
        this.weatherData = weatherData;
        this.requestedAt = requestedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(String weatherData) {
        this.weatherData = weatherData;
    }

    public Date getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Date requestedAt) {
        this.requestedAt = requestedAt;
    }

}
