package com.example.lucian.mystoryteller.storytheisland;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lucian.mystoryteller.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryTheIslandEndOfStoryActivity extends AppCompatActivity {

    @BindView(R.id.story_so_far_end_of_story)
    TextView endOfStory;
    @BindView(R.id.button_end_of_story_screen)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_the_island_end_of_story);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        setTitle(getString(R.string.the_island_story_title));
        // outro text displayed
        endOfStory.setText(R.string.story_the_island_end_of_story);
        // gets user to the editing screen
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(StoryTheIslandEndOfStoryActivity.this, EditTheIslandStoryActivity.class);
            startActivity(intent);}
        });
    }
}
