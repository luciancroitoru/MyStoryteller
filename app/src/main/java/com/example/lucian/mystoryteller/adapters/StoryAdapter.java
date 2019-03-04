package com.example.lucian.mystoryteller.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lucian.mystoryteller.R;
import com.example.lucian.mystoryteller.database.StoryEntry;
import com.example.lucian.mystoryteller.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

//StoryAdapter that creates and binds ViewHolders and displays data in a recyclerview.

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.RecyclerViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = Constants.DATE_TIME;

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<StoryEntry> storyEntries;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    /* Constructor for the StoryAdapter that initializes Context.
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public StoryAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }


     // This is called when ViewHolders are created.
     // @return a new StoryViewHolder that holds view for each story

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the story_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.story_layout, parent, false);

        return new RecyclerViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        // Determine the values of the wanted data
        StoryEntry storyEntry = storyEntries.get(position);
        String title = storyEntry.getTitle();
        String submittedAt = dateFormat.format(storyEntry.getSubmittedAt());
        String userName = storyEntry.getUserName();

        //Set values
        holder.storyContentTitleView.setText(title);
        holder.submittedAtView.setText(submittedAt);
        holder.userNameView.setText(userName);
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (storyEntries == null) {
            return 0;
        }
        return storyEntries.size();
    }

    public List<StoryEntry> getStories() {
        return storyEntries;
    }

     //updates the list of storyEntries
    public void setStories(List<StoryEntry> entries) {
        storyEntries = entries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the story title, story date and story author(userName) TextViews
        TextView storyContentTitleView;
        TextView submittedAtView;
        TextView userNameView;

        /**
         * Constructor for the StoryViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public RecyclerViewHolder(View itemView) {
            super(itemView);

            storyContentTitleView = itemView.findViewById(R.id.recycler_view_content_title);
            submittedAtView = itemView.findViewById(R.id.recyclerview_content_date);
            userNameView = itemView.findViewById(R.id.recyclerview_content_username);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = storyEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
