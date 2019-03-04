package com.example.lucian.mystoryteller;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.lucian.mystoryteller.adapters.StoryAdapter;
import com.example.lucian.mystoryteller.database.AppDatabase;
import com.example.lucian.mystoryteller.database.StoryEntry;
import com.example.lucian.mystoryteller.utils.AppExecutors;
import com.example.lucian.mystoryteller.viewmodels.StoryViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;
import static com.example.lucian.mystoryteller.utils.Constants.FIREBASE_EVENT_ANALYTICS_CONTENT;

public class MainActivity extends AppCompatActivity implements StoryAdapter.ItemClickListener {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_stories)
    RecyclerView mRecyclerView;
    private Intent intent;
    private StoryAdapter mAdapter;

    private AppDatabase mDb;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //toolbar implementation
        setSupportActionBar(toolbar);
        //Firebase data collection for analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, FIREBASE_EVENT_ANALYTICS_CONTENT);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        // fab opens activity for a new story
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, NewStoryActivity.class);
                startActivity(intent);
            }
        });

        //database call
        mDb = AppDatabase.getInstance(getApplicationContext());

        //viewmodel setup call
        setupViewModel();

        // linear layout setup for the RecyclerView, sets items in a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // this intializes the adapter and attaches it to the RecyclerView
        mAdapter = new StoryAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        // this is an item decoration added to recyclerview for better UI appearance
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        /*
         touch helper added to the RecyclerView to recognize when a user swipes left to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         * swipe behaviour idea implementation with the help of Android Architecture Components.
         * -> if user has a list of 200 stories and wants to easily delete some of them, this seems an easy
         * and natural way to do it.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where swipe to delete is implemented
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<StoryEntry> stories = mAdapter.getStories();
                        mDb.storyDao().deleteTask(stories.get(position));

                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    private void setupViewModel() {
        StoryViewModel storyViewModel = ViewModelProviders.of(this).get(StoryViewModel.class);
        storyViewModel.getStories().observe(this, new Observer<List<StoryEntry>>() {
            @Override
            public void onChanged(@Nullable List<StoryEntry> storyEntries) {
                //updates the list of stories from livedata to viewmodel
                mAdapter.setStories(storyEntries);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds the items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        //displays a dialogue screen
        if (id == R.id.menu_main) {
            showAboutMyStoryteller();
        }
        return true;
    }

    //Method that will pop up a dialog box with details about My Storyteller app
    private void showAboutMyStoryteller() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.about_my_storyteller_text);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicks the "Cancel" button and dialog is dismissed
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //when recyclerview item is clicked, it sends item ID to story details activity via an intent
    @Override
    public void onItemClickListener(int itemId) {
        Intent intent = new Intent(MainActivity.this, StoryDetailsActivity.class);
        intent.putExtra(StoryDetailsActivity.EXTRA_STORY_ID, itemId);
        startActivity(intent);
    }
}
