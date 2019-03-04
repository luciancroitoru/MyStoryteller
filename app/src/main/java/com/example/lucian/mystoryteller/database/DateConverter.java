package com.example.lucian.mystoryteller.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

// from Android Architecture Components of Android Developer Nanodegree by Udacity
public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}