package com.example.lucian.mystoryteller;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.lucian.mystoryteller.utils.Constants;
import com.example.lucian.mystoryteller.utils.DataMng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StoryWidgetService extends IntentService {

    private static final String ACTION_UPDATE_WIDGET = "com.example.lucian.mystoryteller.action.update_widget";

    /**
     * Creates an IntentService
     */
    public StoryWidgetService() {
        super("StoryWidgetService");
    }

    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, StoryWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME);
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            String dateString = formatter.format(date);
            String dataForWidget = DataMng.storyAttemptforWidget;
            dataForWidget = dataForWidget + " . The date and time were " + dateString;

            if (ACTION_UPDATE_WIDGET.equals(action)) {
                handleActionUpdateWidget(dataForWidget);
            }
        }
    }

    private void handleActionUpdateWidget(String date) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, StoryWidgetProvider.class));

        // Update widgets
        StoryWidgetProvider.updateStoryWidgets(this, appWidgetManager, appWidgetIds, date);
    }
}
