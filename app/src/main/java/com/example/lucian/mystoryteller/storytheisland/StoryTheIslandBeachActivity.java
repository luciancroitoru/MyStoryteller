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
import com.example.lucian.mystoryteller.StoryWidgetService;
import com.example.lucian.mystoryteller.utils.DataMng;
import com.example.lucian.mystoryteller.utils.UtilsAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryTheIslandBeachActivity extends AppCompatActivity {

    @BindView(R.id.story_text_so_far_beach)
    TextView storyIntroduction;
    @BindView(R.id.button_story_beach_screen)
    Button button;
    @BindView(R.id.answer_ocean)
    EditText answerOcean;
    @BindView(R.id.answer_sky)
    EditText answerSky;
    @BindView(R.id.answer_sand)
    EditText answerSand;
    @BindView(R.id.answer_forrest)
    EditText answerForrest;

    String getAnswerSky;
    String getAnswerOcean;
    String getAnswerSand;
    String getAnswerForrest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_the_island_beach);
        ButterKnife.bind(this);
        if(getSupportActionBar()!=null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);}
        setTitle(getString(R.string.the_island_story_title));
        // displays story introduction
        storyIntroduction.setText(R.string.story_so_far_beach);
        //verifies data collection, if null or field empty show toast message;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInput();
                if(((getAnswerOcean==null) || (getAnswerOcean.equals(""))) || ((getAnswerSky==null) || (getAnswerSky.equals(""))) ||
                        ((getAnswerSand==null) || (getAnswerSand.equals(""))) || ((getAnswerForrest==null) || (getAnswerForrest.equals("")))){
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_message_fields_not_completed, Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    // user input is stored in global variables class
                    Intent intent = new Intent(StoryTheIslandBeachActivity.this, StoryTheIslandHouseActivity.class);
                    DataMng.oceanAttribute = getAnswerOcean;
                    DataMng.skyAttribute = getAnswerSky;
                    DataMng.sandAttribute = getAnswerSand;
                    DataMng.forrestAttribute = getAnswerForrest;

                    startActivity(intent);
                }
            }
        });
    }
    // widget service is called to update widget on action start
    @Override
    protected void onStart() {
        super.onStart();
        StoryWidgetService.startActionUpdateWidget(this);
    }
    // verification for null or empty fields
    @Override
    public void onBackPressed() {
            getUserInput();
        if (((getAnswerOcean==null) || (getAnswerOcean.equals("")))
                && ((getAnswerSky==null) || (getAnswerSky.equals("")))
                && ((getAnswerSand==null) || (getAnswerSand.equals("")))
                && ((getAnswerForrest==null) || (getAnswerForrest.equals("")))){
            finish();
        } else{
            UtilsAlertDialog.showDiscardDataConfirmationDialog(this, StoryTheIslandBeachActivity.this);
        }
    }
    //gets user input from this screen
    public void getUserInput(){
        getAnswerOcean = answerOcean.getText().toString();
        getAnswerSky = answerSky.getText().toString();
        getAnswerSand = answerSand.getText().toString();
        getAnswerForrest = answerForrest.getText().toString();
    }
}