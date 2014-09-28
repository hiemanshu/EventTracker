package in.theindiangeek.eventtracker.ui.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import in.theindiangeek.eventtracker.R;
import in.theindiangeek.eventtracker.database.Datastore;
import in.theindiangeek.eventtracker.objects.EventObject;

public class EventViewActivity extends Activity {

    private int mEventId;
    private String mUserName;
    private EventObject mEvent;

    private ImageView mEventThumbnail;
    private TextView mEventName;
    private TextView mEventLocation;
    private TextView mEventCost;
    private Button mFollowButton;
    private ActionBar mActionBar;

    private Datastore mDatastore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        mEventId = getIntent().getExtras().getInt("event_id", 0);
        mEventName = (TextView) findViewById(R.id.event_view_name);

        if (mEventId != 0) {
            mDatastore = new Datastore(this);
            try {
                mDatastore.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            mEvent = mDatastore.getEvent(mEventId);

            mActionBar = getActionBar();
            mActionBar.setTitle(mEvent.getEventName());

            mUserName = PreferenceManager.getDefaultSharedPreferences(this).getString("user_name", null);

            mEventLocation = (TextView) findViewById(R.id.event_view_location);
            mEventCost = (TextView) findViewById(R.id.event_view_cost);
            mEventThumbnail = (ImageView) findViewById(R.id.event_view_thumbnail);
            mFollowButton = (Button) findViewById(R.id.event_view_follow_button);

            mEventName.setText(mEvent.getEventName());
            mEventCost.setText(mEvent.getEventCost());
            mEventLocation.setText(mEvent.getEventLocation());
            try {
                InputStream inputStream = getAssets().open(mEvent.getImagePath());
                Drawable image = Drawable.createFromStream(inputStream, null);
                mEventThumbnail.setImageDrawable(image);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (mDatastore.isEventTracked(mUserName, mEventId)) {
                mFollowButton.setText(R.string.unfollow);
            }

            mFollowButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (mDatastore.isEventTracked(mUserName, mEventId)) {
                        mDatastore.stopTrackingEvent(mUserName, mEventId);
                        mFollowButton.setText(R.string.follow);
                        Toast.makeText(getBaseContext(), "Unfollowed", Toast.LENGTH_LONG).show();
                    } else {
                        mDatastore.trackEvent(mUserName, mEventId);
                        mFollowButton.setText(R.string.unfollow);
                        Toast.makeText(getBaseContext(), "Followed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Log.e("EventTracker", "This shouldn't have happened.");
        }
    }

    @Override
    protected void onDestroy() {
        mDatastore.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tracked_events_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_tracked_events) {
            Intent intent = new Intent(this, TrackedEventsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
