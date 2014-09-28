package in.theindiangeek.eventtracker.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import in.theindiangeek.eventtracker.R;
import in.theindiangeek.eventtracker.database.Datastore;
import in.theindiangeek.eventtracker.objects.EventObject;

public class EventListViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Boolean mShowTrackedEvents;

    private List<EventObject> mEvents;

    private Datastore mDatastore;

    public EventListViewAdapter(Context context, Boolean showTrackedEvents) {
        mContext = context;
        mShowTrackedEvents = showTrackedEvents;
        getEvents();
    }

    private void getEvents() {
        mDatastore = new Datastore(mContext);
        try {
            mDatastore.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (mShowTrackedEvents) {
            mEvents = mDatastore.getTrackedEventsFor(PreferenceManager
                    .getDefaultSharedPreferences(mContext).getString("user_name", null));
        } else {
            mEvents = mDatastore.getAllEvents();
        }
        mDatastore.close();
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            mLayoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = mLayoutInflater.inflate(R.layout.listviewcell_event, parent, false);
        }

        ImageView imageThumbnail = (ImageView) row.findViewById(R.id.event_cell_image);
        TextView eventName = (TextView) row.findViewById(R.id.event_cell_name);
        TextView eventLocation = (TextView) row.findViewById(R.id.event_cell_place);
        TextView eventCost = (TextView) row.findViewById(R.id.event_cell_cost_type);

        try {
            InputStream inputStream = mContext.getAssets().open(mEvents.get(position).getImagePath());
            Drawable image = Drawable.createFromStream(inputStream, null);
            imageThumbnail.setImageDrawable(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        eventName.setText(mEvents.get(position).getEventName());
        eventLocation.setText(mEvents.get(position).getEventLocation());
        eventCost.setText(mEvents.get(position).getEventCost());

        return row;
    }

    public int getEventId(int position) {
        return mEvents.get(position).getId();
    }

    public void stopTrackingEvent(int position) {
        String userName = PreferenceManager
                .getDefaultSharedPreferences(mContext).getString("user_name", null);
        int eventId = mEvents.get(position).getId();
        try {
            mDatastore.open();
            mDatastore.stopTrackingEvent(userName, eventId);
            mDatastore.close();
            mEvents.remove(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
