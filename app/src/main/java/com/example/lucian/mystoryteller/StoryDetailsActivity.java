package com.example.lucian.mystoryteller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.lucian.mystoryteller.database.AppDatabase;
import com.example.lucian.mystoryteller.database.StoryEntry;
import com.example.lucian.mystoryteller.utils.AppExecutors;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryDetailsActivity extends AppCompatActivity {

    // Extra for the story ID to be received in the intent
    public static final String EXTRA_STORY_ID = "extraStoryId";
    // Extra for the story ID to be received after rotation
    public static final String INSTANCE_STORY_ID = "instanceStoryId";
    // Extra for the story ID to be received after rotation
    public static final String STORY_CONTENT = "instanceStoryContent";
    // Extra for the story ID to be received after rotation
    public static final String INSTANCE_STORY_CONTENT_KEY = "instanceStoryContent";
    // Constant for default story id
    private static final int DEFAULT_STORY_ID = -1;
    @BindView(R.id.story_details_story_content)
    TextView storyContent;
    @BindView(R.id.toolbar_details)
    Toolbar toolbar;
    @BindView(R.id.adView)
    AdView mAdView;
    FloatingActionButton fab;
    String storyContentEmail;
    // Member variable for the Database
    private AppDatabase mDb;
    private int mStoryId = DEFAULT_STORY_ID;
    private FirebaseAnalytics mFirebaseAnalytics;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);

        ButterKnife.bind(this);
        //toolbar implementation
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.action_bar_title_for_submission));

        //load the adView
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_STORY_ID)
                && savedInstanceState.containsKey(INSTANCE_STORY_CONTENT_KEY)) {
            mStoryId = savedInstanceState.getInt(INSTANCE_STORY_ID, DEFAULT_STORY_ID);
            storyContentEmail = savedInstanceState.getString(INSTANCE_STORY_CONTENT_KEY, STORY_CONTENT);
            storyContent.setText(storyContentEmail);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_STORY_ID))
            if (mStoryId == DEFAULT_STORY_ID) {
                // value of EXTRA_STORY_ID in the intent is assigned to mStoryId
                // DEFAULT_STORY_ID is used as the default
                mStoryId = intent.getIntExtra(EXTRA_STORY_ID, DEFAULT_STORY_ID);
                // populate the UI
                // call of diskIO execute method with a new Runnable
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // load story by id
                        final StoryEntry story = mDb.storyDao().loadStoryById(mStoryId);
                        // call to runOnUiThread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //populate UI
                                populateUI(story);
                            }
                        });
                    }
                });
            }

        // set onClick to floating action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, storyContentEmail);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Choose an e-mail client"));

            }
        });
    }

    // populateUI would be called to populate the UI
    // @param story the storyEntry to populate the UI
    private void populateUI(StoryEntry story) {
        if (story == null) {
            return;
        }
        storyContent.setText(story.getStoryContent());
        //and get this to send to e-mail, or repopulate textview in onRestoreInstanceState
        storyContentEmail = story.getStoryContent();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes
        savedInstanceState.putInt(INSTANCE_STORY_ID, mStoryId);
        savedInstanceState.putString(INSTANCE_STORY_CONTENT_KEY, storyContentEmail);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state
        savedInstanceState.getInt(INSTANCE_STORY_ID, mStoryId);
        savedInstanceState.getString(INSTANCE_STORY_CONTENT_KEY, storyContentEmail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }
}








