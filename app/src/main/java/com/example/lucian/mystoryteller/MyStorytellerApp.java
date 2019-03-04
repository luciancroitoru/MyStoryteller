package com.example.lucian.mystoryteller;

import android.app.Application;
import com.google.android.gms.ads.MobileAds;
import static com.example.lucian.mystoryteller.utils.Constants.DUMMY_ADS_ID;

/**
 * Solution to initialize dummy ad by id
 * Reference:
 * https://code.tutsplus.com/tutorials/how-to-monetize-your-android-apps-with-admob--cms-29255
 * test mocks and test ID are used for the implementation of this task.
 */
public class MyStorytellerApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, DUMMY_ADS_ID);
    }
}