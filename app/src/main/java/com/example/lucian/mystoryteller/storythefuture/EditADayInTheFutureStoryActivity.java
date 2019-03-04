package com.example.lucian.mystoryteller.storythefuture;

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
import com.example.lucian.mystoryteller.StoryWidgetService;
import com.example.lucian.mystoryteller.utils.DataMng;
import com.example.lucian.mystoryteller.utils.UtilsAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditADayInTheFutureStoryActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_story_the_future)
    EditText storyADayInTheFuture;
    @BindView(R.id.button_edit_story_the_future)
    Button button;
    @BindView(R.id.story_edit_the_future_introduction)
    TextView textViewStoryIntroduction;

    String getStoryIntroduction;
    String getUserInput;
    String story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_a_day_in_the_future_story);
        ButterKnife.bind(this);

        //action bar title initialization
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);}
        setTitle(getString(R.string.a_day_in_the_future_story_title));

        getStoryIntroduction = textViewStoryIntroduction.getText().toString();

        //save user input in a global variable and go to next screen
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInput();
                if(((getUserInput==null) || (getUserInput.equals("")))){
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_message_story_field_cannot_be_empty, Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    story = getStoryIntroduction + " " + getUserInput;
                    // final story version is saved in the global variable manager class
                    DataMng.finalStory = story;
                    Log.i("final story:", story);
                    Intent intent = new Intent(EditADayInTheFutureStoryActivity.this, FinalStoryActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    // the widget is updated when user enters this screen
    @Override
    protected void onStart() {
        super.onStart();
        StoryWidgetService.startActionUpdateWidget(this);
    }
    //data confirmation dialog pops up when user presses back button
    @Override
    public void onBackPressed() {
        getUserInput();
        if ((getUserInput==null) || (getUserInput.equals(""))) {
            finish();
        } else{
            UtilsAlertDialog.showDiscardDataConfirmationDialog(this, EditADayInTheFutureStoryActivity.this);
        }
    }
    //get user input
    public void getUserInput(){
        getUserInput = storyADayInTheFuture.getText().toString();
    }
}
