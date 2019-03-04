package com.example.lucian.mystoryteller.storythefuture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lucian.mystoryteller.R;
import com.example.lucian.mystoryteller.utils.DataMng;

import butterknife.BindView;
import butterknife.ButterKnife;
// first screen of the story "The future"
public class StoryADayInTheFutureNameActivity extends AppCompatActivity {

    @BindView(R.id.user_name_edit_text_the_future)
    EditText userNameEditText;
    @BindView(R.id.button_user_name_screen_the_future)
    Button button;
    private Intent intent;
    String getUserName;
    String getStoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_a_day_in_the_future_name);
        ButterKnife.bind(this);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);}
        setTitle(getString(R.string.a_day_in_the_future_story_title));
        //gets story title
        getStoryTitle = getString(R.string.a_day_in_the_future_story_title);
        // user input is stored in global variables when button is pressed then screen moves to next activity;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInputName();
                //null & empty user input verification
                if(((getUserName==null) || (getUserName.equals("")))){
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_message_user_name_not_completed, Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    intent = new Intent(StoryADayInTheFutureNameActivity.this, EditADayInTheFutureStoryActivity.class);
                    DataMng.storyTitle = getStoryTitle;
                    DataMng.userName = getUserName;
                    DataMng.storyAttemptforWidget = "The last person who showed interest in a story was " + getUserName +
                            " and accessed the story: " + getStoryTitle;
                    startActivity(intent);
                }
            }
        });
    }
    //collects user input
    public void getUserInputName(){
        getUserName = userNameEditText.getText().toString();}
}
