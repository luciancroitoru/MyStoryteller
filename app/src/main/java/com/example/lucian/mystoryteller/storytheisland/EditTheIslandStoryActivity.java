package com.example.lucian.mystoryteller.storytheisland;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucian.mystoryteller.FinalStoryActivity;
import com.example.lucian.mystoryteller.R;
import com.example.lucian.mystoryteller.utils.DataMng;
import com.example.lucian.mystoryteller.utils.UtilsAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
// all user input stored in the story is used in this activity to create a string that will be set to the edittext view
public class EditTheIslandStoryActivity extends AppCompatActivity {

    String getUserName;
    String getAnswerOcean;
    String getAnswerSky;
    String getAnswerSand;
    String getAnswerForrest;
    String getAnswerSomethingScary;
    String getAnswerHouse;
    String getAnswerWindows;
    String getAnswerDoor;
    String getAnswerInteriorHouse;

    @BindView(R.id.edit_text_story_the_island)
    EditText storyTheIsland;
    @BindView(R.id.button_edit_story_the_island)
    Button button;

    String story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_story_the_island);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        setTitle(getString(R.string.the_island_story_title));
        //user input collection to local variables
        getUserName = DataMng.userName;
        getAnswerOcean = DataMng.oceanAttribute;
        getAnswerSky = DataMng.skyAttribute;
        getAnswerSand = DataMng.sandAttribute;
        getAnswerForrest = DataMng.forrestAttribute;
        getAnswerSomethingScary = DataMng.somethingScary;
        getAnswerHouse = DataMng.houseAttribute;
        getAnswerWindows = DataMng.windowsAttribute;
        getAnswerDoor = DataMng.doorAttribute;
        getAnswerInteriorHouse = DataMng.interiorHouseAttribute;
        //story creation (stored in story String variable) with user input insertion
        story = getString(R.string.final_story_the_island_1) + " " + getAnswerOcean
                + getString(R.string.final_story_the_island_2) + " " + getAnswerSky
                + getString(R.string.final_story_the_island_3) + " " + getAnswerSand
                + getString(R.string.final_story_the_island_4)+ " " + getAnswerForrest + " "
                + getString(R.string.final_story_the_island_5)+ " " + getAnswerSomethingScary + " "
                + getString(R.string.final_story_the_island_6)+ " " + getAnswerHouse + " "
                + getString(R.string.final_story_the_island_7)+ " " + getAnswerWindows
                + getString(R.string.final_story_the_island_8)+ " " + getAnswerDoor+ " "
                + getString(R.string.final_story_the_island_9)+ " " + getAnswerInteriorHouse
                + getString(R.string.final_story_the_island_10)+ " " + getUserName + ", "
                + getUserName + ", "+ getUserName + "! " + getString(R.string.final_story_the_island_11);
        //verification of data collection
        Log.i("theislandstory: ", story);
        //collected data sent to edittext
        storyTheIsland.setText(story, TextView.BufferType.EDITABLE);
        //gets user to story submission screen
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getUserInput();
                if(((story==null) || (story.equals("")))){
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_message_story_field_cannot_be_empty, Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    getUserInput();
                    // final story is saved in the variables manager class
                    DataMng.finalStory = story;
                    Log.i("final story:", story);
                    Intent intent = new Intent(EditTheIslandStoryActivity.this, FinalStoryActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void getUserInput(){
        story = storyTheIsland.getText().toString();
    }
    //Confirmation dialog for onBackPressed
    @Override
    public void onBackPressed() {
        getUserInput();
        if ((story==null) || (story.equals(""))) {
            finish();
        } else{
            UtilsAlertDialog.showDiscardDataConfirmationDialog(this, EditTheIslandStoryActivity.this);
        }
    }
}
