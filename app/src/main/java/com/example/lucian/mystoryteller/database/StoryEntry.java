package com.example.lucian.mystoryteller.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
//class for a StoryEntity object to be stored in the database, with getter and setter methods
@Entity(tableName = "story")
public class StoryEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userName;
    private String title;
    @ColumnInfo(name = "submitted_at")
    private Date submittedAt;
    private String storyContent;

    @Ignore
    public StoryEntry(String title, String userName, Date submittedAt, String storyContent) {
        this.title = title;
        this.userName = userName;
        this.submittedAt = submittedAt;
        this.storyContent = storyContent;
    }

    public StoryEntry(int id, String title, String userName, Date submittedAt, String storyContent) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.submittedAt = submittedAt;
        this.storyContent = storyContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getSubmittedAt() { return submittedAt; }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getStoryContent() {
        return storyContent;
    }

    public void setStoryContent(String storyContent) {
        this.storyContent = storyContent;
    }
}

