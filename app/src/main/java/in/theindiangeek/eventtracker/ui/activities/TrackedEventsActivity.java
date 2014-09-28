package in.theindiangeek.eventtracker.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import in.theindiangeek.eventtracker.R;
import in.theindiangeek.eventtracker.Utils.SwipeDismissListViewTouchListener;
import in.theindiangeek.eventtracker.adapters.EventListViewAdapter;

public class TrackedEventsActivity extends Activity {

    ListView mEventListView;
    EventListViewAdapter mEventListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracked_events);

        mEventListViewAdapter = new EventListViewAdapter(this, true);

        mEventListView = (ListView) findViewById(R.id.tracked_event_list_view);
        mEventListView.setAdapter(mEventListViewAdapter);
        mEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getBaseContext(), EventViewActivity.class);
                intent.putExtra("event_id", mEventListViewAdapter.getEventId(position));
                startActivity(intent);
            }
        });

        SwipeDismissListViewTouchListener swipeDismissListViewTouchListener =
                new SwipeDismissListViewTouchListener(mEventListView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {

                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    mEventListViewAdapter.stopTrackingEvent(position);
                                    Toast.makeText(getBaseContext(), "Unfollowed", Toast.LENGTH_SHORT).show();
                                }
                                mEventListViewAdapter.notifyDataSetChanged();
                            }
                        });

        mEventListView.setOnTouchListener(swipeDismissListViewTouchListener);
        mEventListView.setOnScrollListener(swipeDismissListViewTouchListener.makeScrollListener());
    }
}
