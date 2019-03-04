package com.example.lucian.mystoryteller.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
//Dao for weather city and data insertion
//user input regarding location might facilitate collection of data for targeting users with commercials relevant to their
// preferred location.
@Dao
public interface WeatherDao {

    @Insert
    void insertWeatherEntity(WeatherEntry weatherEntry);
}
