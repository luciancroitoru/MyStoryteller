package com.example.lucian.mystoryteller.storytheisland;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucian.mystoryteller.R;
import com.example.lucian.mystoryteller.utils.DataMng;
import com.example.lucian.mystoryteller.utils.UtilsAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryTheIslandHouseActivity extends AppCompatActivity{

    @BindView(R.id.story_so_far_something_scary)
    TextView storyContinuationSomethingScary;
    @BindView(R.id.story_so_far_house)
    TextView storyContinuationHouse;
    @BindView(R.id.button_story_house_screen)
    Button button;
    @BindView(R.id.answer_something_scary)
    EditText answerSomethingScary;
    @BindView(R.id.answer_house)
    EditText answerHouse;
    @BindView(R.id.answer_windows)
    EditText answerWindows;
    @BindView(R.id.answer_door)
    EditText answerDoor;

    String getAnswerSomethingScary;
    String getAnswerHouse;
    String getAnswerWindows;
    String getAnswerDoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_the_island_house);
        ButterKnife.bind(this);
        if(getSupportActionBar()!=null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);}
        setTitle(getString(R.string.the_island_story_title));
        //screen populated with Strings content for story continuation and further instructions
        storyContinuationSomethingScary.setText(R.string.story_so_far_something_scary);
        storyContinuationHouse.setText(R.string.story_so_far_house);
        //verification of user input before going to next screen
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInputHouse();
                if(((getAnswerSomethingScary==null) || (getAnswerSomethingScary.equals(""))) || ((getAnswerHouse==null) || (getAnswerHouse.equals(""))) ||
                        ((getAnswerWindows==null) || (getAnswerWindows.equals(""))) || ((getAnswerDoor==null) || (getAnswerDoor.equals("")))){
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_message_fields_not_completed, Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Intent intent = new Intent(StoryTheIslandHouseActivity.this, StoryTheIslandInteriorHouseActivity.class);
                    //user input stored in global variables
                    DataMng.somethingScary = getAnswerSomethingScary;
                    DataMng.houseAttribute = getAnswerHouse;
                    DataMng.windowsAttribute = getAnswerWindows;
                    DataMng.doorAttribute = getAnswerDoor;
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        getUserInputHouse();
        if (((getAnswerSomethingScary==null) || (getAnswerSomethingScary.equals("")))
                && ((getAnswerHouse==null) || (getAnswerHouse.equals("")))
                && ((getAnswerWindows==null) || (getAnswerWindows.equals("")))
                && ((getAnswerDoor==null) || (getAnswerDoor.equals("")))){
            finish();
        } else{
            UtilsAlertDialog.showDiscardDataConfirmationDialog(this, StoryTheIslandHouseActivity.this);
        }
    }

    public void getUserInputHouse(){
        getAnswerSomethingScary = answerSomethingScary.getText().toString();
        getAnswerHouse = answerHouse.getText().toString();
        getAnswerWindows = answerWindows.getText().toString();
        getAnswerDoor = answerDoor.getText().toString();
    }
}
