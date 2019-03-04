package com.example.lucian.mystoryteller.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.lucian.mystoryteller.database.AppDatabase;
import com.example.lucian.mystoryteller.database.StoryEntry;

import java.util.List;
//ViewModel implementation
public class StoryViewModel extends AndroidViewModel {
    // Constant for logging
    private static final String TAG = StoryViewModel.class.getSimpleName();

    private LiveData<List<StoryEntry>> stories;

    public StoryViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the stories from Database");
        stories = database.storyDao().loadAllStories();
    }

    public LiveData<List<StoryEntry>> getStories() {
        return stories;
    }
}
