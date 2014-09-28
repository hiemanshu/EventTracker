package in.theindiangeek.eventtracker.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import in.theindiangeek.eventtracker.R;
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
    }
}
