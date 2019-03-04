package com.example.lucian.mystoryteller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lucian.mystoryteller.database.AppDatabase;
import com.example.lucian.mystoryteller.database.StoryEntry;
import com.example.lucian.mystoryteller.utils.AppExecutors;
import com.example.lucian.mystoryteller.utils.DataMng;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinalStoryActivity extends AppCompatActivity {

    // Constant for default story id to be used
    private static final int DEFAULT_STORY_ID = -1;
    @BindView(R.id.story_final_story_text_view)
    TextView finalStory;
    @BindView(R.id.button_submit_story)
    Button buttonSubmitStory;
    String theFinalStory;
    // Member variable for the Database
    private AppDatabase mDb;

    private int storyId = DEFAULT_STORY_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_story);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        setTitle(getString(R.string.action_bar_title_for_submission));

        theFinalStory = DataMng.finalStory;
        finalStory.setText(theFinalStory);
        //prepares database for data collection
        mDb = AppDatabase.getInstance(getApplicationContext());
        //stores object data in the database on a back thread using an executor
        buttonSubmitStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    String title = DataMng.storyTitle;
                    String author = DataMng.userName;
                    Date submittedAt = new Date();
                    String storyContent = DataMng.finalStory;

                    final StoryEntry story = new StoryEntry(title, author, submittedAt, storyContent);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (storyId == DEFAULT_STORY_ID)
                                //insert story
                                mDb.storyDao().insertStory(story);
                            finish();
                        }
                    });
                }
                Intent intent = new Intent(FinalStoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
