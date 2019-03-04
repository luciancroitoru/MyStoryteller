package com.example.lucian.mystoryteller.storytheisland;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lucian.mystoryteller.R;
import com.example.lucian.mystoryteller.StoryWidgetService;
import com.example.lucian.mystoryteller.storythefuture.StoryADayInTheFutureNameActivity;
import com.example.lucian.mystoryteller.utils.DataMng;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryTheIslandNameActivity extends AppCompatActivity {

    @BindView(R.id.user_name_edit_text)
    EditText userNameEditText;
    @BindView(R.id.button_user_name_screen)
    Button button;
    String getUserName;
    String getStoryTitle;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_the_island_name);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        setTitle(getString(R.string.the_island_story_title));
        getStoryTitle = getString(R.string.the_island_story_title);
        //stores user name and story title information in global variables, together with information that will be displayed
        //in a widget, then goes to next activity
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInputName();
                if (((getUserName == null) || (getUserName.equals("")))) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_message_user_name_not_completed, Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    intent = new Intent(StoryTheIslandNameActivity.this, StoryTheIslandBeachActivity.class);
                    DataMng.storyTitle = getStoryTitle;
                    DataMng.userName = getUserName;
                    DataMng.storyAttemptforWidget = getUserName + " showed interest in a story called " + getStoryTitle + " on ";
                    startActivity(intent);
                }
            }
        });
    }

    public void getUserInputName() {
        getUserName = userNameEditText.getText().toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        StoryWidgetService.startActionUpdateWidget(this);
        Toast.makeText(StoryTheIslandNameActivity.this, R.string.widget_was_updated_message, Toast.LENGTH_SHORT).show();
    }
}


