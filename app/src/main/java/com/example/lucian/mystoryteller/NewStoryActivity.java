package com.example.lucian.mystoryteller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucian.mystoryteller.database.WeatherDatabase;
import com.example.lucian.mystoryteller.database.WeatherEntry;
import com.example.lucian.mystoryteller.storythefuture.StoryADayInTheFutureNameActivity;
import com.example.lucian.mystoryteller.storytheisland.StoryTheIslandNameActivity;
import com.example.lucian.mystoryteller.utils.Constants;
import com.example.lucian.mystoryteller.utils.DataMng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

//activity screen that opens when the fab button from main screen is pressed
public class NewStoryActivity extends AppCompatActivity {

    // Constant for default weather id to be used
    private static final int DEFAULT_WEATHER_ID = -1;
    @BindView(R.id.new_story_first_story)
    TextView TheIslandStory;
    @BindView(R.id.new_story_second_story)
    TextView TheFutureStory;
    @BindView(R.id.button_check_weather)
    Button buttonCheckWeather;
    @BindView(R.id.your_city)
    EditText userInputCity;
    private Intent intent;
    // Member variable for the Weather Database
    private WeatherDatabase weatherDb;
    private int weatherId = DEFAULT_WEATHER_ID;
    String getUserName = "You";
    String getStoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_story);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.select_story));

        //Weather Database is assigned for weather data collection
        weatherDb = WeatherDatabase.getInstance(getApplicationContext());

        //textviews that display story titles for story selection. The developer has the power over the number of views
        // that are displayed, therefore it can easily check for overpopulation of the screen and memory usage.
        // Since there is a limited number of stories, this was the preferred solution.
        TheIslandStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(NewStoryActivity.this, StoryTheIslandNameActivity.class);
                getStoryTitle = "The island";
                DataMng.storyAttemptforWidget = getUserName + " showed interest in a story called " + getStoryTitle + " on ";
                startActivity(intent);
            }
        });

        TheFutureStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(NewStoryActivity.this, StoryADayInTheFutureNameActivity.class);
                getStoryTitle = "A day in the future";
                DataMng.storyAttemptforWidget = getUserName + " showed interest in a story called " + getStoryTitle + " on ";
                startActivity(intent);
            }
        });

        //
        buttonCheckWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = userInputCity.getText().toString();
                new WeatherData().execute(city);
            }
        });

    }
    // short-term on-demand search requests using an AsyncTask and performing the operation in the background. the information
    // that is retrieved is stored in a local database, together with the date and time when it was made.
    class WeatherData extends AsyncTask<String, Integer, String> {
        //String inserted by user
        private String mCity = "Default city";

        //Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // code to dismiss keyboard, reference: https://stackoverflow.com/questions/3553779/android-dismiss-keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(userInputCity.getWindowToken(), 0);
            }
        }

        //background thread
        @Override
        protected String doInBackground(String... params) {
            //get string from params array

            String weatherIs = "City name misspelled";
            mCity = params[0];

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Constants.URI.replace("_myCity_", mCity))
                    .build();

            Response response = null;

            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (response != null) {

                String responseBody = null;
                try {
                    responseBody = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();

                }

                Gson gson = new Gson();
                JsonObject responseJson = gson.fromJson(responseBody, JsonObject.class);

                try {
                    /*the app trows a null pointer exception here. The reason is that when user misspells a city name, sends the
                     request and the value does not exist, it does not have an adaptive mechanism, like google maps,
                     and without checking for similar string values and offering alternative suggestions, it will return a null value.
                     The app does not crash and logs the error in a stackTrace.
                    */
                    weatherIs = responseJson.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();

                } catch (Exception e) {
                    e.printStackTrace();
                    weatherIs = "City misspelled";
                }
            }

            //current date, formatted to string for logging purposes, but saved as a Date variable
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME);
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            String dateString = formatter.format(date);
            // search request implemented in background thread
            // the UI only appears for short-term, in a toast message. This weather service is an integrated part of the app,
            // but it does not represent its main purpose
            WeatherEntry weather = new WeatherEntry(mCity, weatherIs, date);
            if (weatherId == DEFAULT_WEATHER_ID)
                //insert weather request entity
                weatherDb.weatherDao().insertWeatherEntity(weather);
            Log.i("city", mCity);
            Log.i("weather is", weatherIs);
            Log.i("current date and time:", dateString);
            return weatherIs;
        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(NewStoryActivity.this, "Weather in " + mCity + " is: " + result, Toast.LENGTH_LONG).show();

        }
    }
}