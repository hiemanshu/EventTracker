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

import in.theindiangeek.eventtracker.R;
import in.theindiangeek.eventtracker.adapters.EventListViewAdapter;


public class EventListingActivity extends Activity {

    ListView mEventListView;
    EventListViewAdapter mEventListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_listing);

        mEventListViewAdapter = new EventListViewAdapter(this, false);

        mEventListView = (ListView) findViewById(R.id.event_list_view);
        mEventListView.setAdapter(mEventListViewAdapter);
        mEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getBaseContext(), EventViewActivity.class);
                intent.putExtra("event_id", mEventListViewAdapter.getEventId(position));
                startActivity(intent);
            }
        });
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
