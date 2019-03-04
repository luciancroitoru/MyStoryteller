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

public class StoryTheIslandInteriorHouseActivity extends AppCompatActivity {

    @BindView(R.id.story_so_far_interior_house)
    TextView storyContinuationInteriorHouse;
    @BindView(R.id.answer_interior_house)
    EditText answerInteriorHouse;
    @BindView(R.id.button_story_interior_house_screen)
    Button button;

    String getAnswerInteriorHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_the_island_interior_house);
        ButterKnife.bind(this);
        if(getSupportActionBar()!=null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);}
        setTitle(getString(R.string.the_island_story_title));

        storyContinuationInteriorHouse.setText(R.string.story_so_far_interior_house);
        // goes to next activity when pressed
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDescriptionInteriorHouse();
                if (((getAnswerInteriorHouse == null) || (getAnswerInteriorHouse.equals("")))) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_message_user_name_not_completed, Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Intent intent = new Intent(StoryTheIslandInteriorHouseActivity.this, StoryTheIslandEndOfStoryActivity.class);

                    DataMng.interiorHouseAttribute = getAnswerInteriorHouse;
                    startActivity(intent);
                }
            }
        });
    }
    public void getDescriptionInteriorHouse(){
        getAnswerInteriorHouse = answerInteriorHouse.getText().toString();
    }

    @Override
    public void onBackPressed() {
        getDescriptionInteriorHouse();
        if ((getAnswerInteriorHouse==null) || (getAnswerInteriorHouse.equals(""))) {
            finish();
        } else{
            UtilsAlertDialog.showDiscardDataConfirmationDialog(this, StoryTheIslandInteriorHouseActivity.this);
        }
    }
}
