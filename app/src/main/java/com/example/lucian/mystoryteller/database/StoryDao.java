package com.example.lucian.mystoryteller.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface StoryDao {

    @Query("SELECT * FROM story ORDER BY submitted_at")
    LiveData<List<StoryEntry>> loadAllStories();

    @Insert
    void insertStory(StoryEntry storyEntry);

    @Delete
    void deleteTask(StoryEntry storyEntry);

    @Query("SELECT * FROM story WHERE id = :id")
    StoryEntry loadStoryById(int id);
}
