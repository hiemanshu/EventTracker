package in.theindiangeek.eventtracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.theindiangeek.eventtracker.objects.EventObject;

public class Datastore {

    private SQLiteDatabase mDatabase;
    private DbHelper mDbHelper;

    private String[] mEventTableColumns = {
            DbHelper.COLUMN_ID,
            DbHelper.COLUMN_NAME,
            DbHelper.COLUMN_LOCATION,
            DbHelper.COLUMN_COST,
            DbHelper.COLUMN_IMAGE_PATH
    };

    private String[] mTrackedEventsTableColumns = {
            DbHelper.COLUMN_ID,
            DbHelper.COLUMN_USER_NAME,
            DbHelper.COLUMN_EVENT_ID
    };

    public Datastore(Context context) {
        mDbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public EventObject getEvent(int id) {
        String selection = DbHelper.COLUMN_ID + " = ?";

        Cursor cursor = mDatabase.query(DbHelper.TABLE_EVENTS,
                mEventTableColumns, selection, new String[] {Integer.toString(id)},
                null, null, null);

        cursor.moveToFirst();
        return cursorToEvent(cursor);
    }

    public List<EventObject> getAllEvents() {
        List<EventObject> events = new ArrayList<EventObject>();

        Cursor cursor = mDatabase.query(DbHelper.TABLE_EVENTS,
                mEventTableColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            EventObject event = cursorToEvent(cursor);
            events.add(event);
            cursor.moveToNext();
        }
        cursor.close();
        return events;
    }

    public List<EventObject> getTrackedEventsFor(String userName) {
        List<EventObject> events = new ArrayList<EventObject>();

        String selection = DbHelper.COLUMN_USER_NAME + " = ?";
        Cursor cursor = mDatabase.query(DbHelper.TABLE_TRACKED_EVENTS,
                mTrackedEventsTableColumns, selection, new String[] { userName }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Cursor c = mDatabase.query(DbHelper.TABLE_EVENTS,
                    mEventTableColumns, DbHelper.COLUMN_ID + " = ?",
                    new String[] {Integer.toString(cursor.getInt(2))}, null, null, null);
            c.moveToFirst();
            EventObject event = cursorToEvent(c);
            events.add(event);
            cursor.moveToNext();
        }
        cursor.close();

        return events;
    }

    public void trackEvent(String userName, int eventId) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_USER_NAME, userName);
        values.put(DbHelper.COLUMN_EVENT_ID, eventId);

        mDatabase.insert(DbHelper.TABLE_TRACKED_EVENTS, null, values);
    }

    public void stopTrackingEvent(String userName, int eventId) {
        String selection = DbHelper.COLUMN_USER_NAME + " = ? AND " + DbHelper.COLUMN_EVENT_ID
                + " = ?";
        String[] selectionArgs = new String[] {userName, Integer.toString(eventId)};

        mDatabase.delete(DbHelper.TABLE_TRACKED_EVENTS, selection, selectionArgs);
    }

    public boolean isEventTracked(String userName, int eventId) {
        String selection = DbHelper.COLUMN_USER_NAME + " = '" + userName + "' AND " + DbHelper.COLUMN_EVENT_ID
                + " = " + eventId;

        Cursor cursor = mDatabase.query(DbHelper.TABLE_TRACKED_EVENTS, mTrackedEventsTableColumns,
                selection, null, null, null, null);

        if(cursor.getCount() < 1) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    private EventObject cursorToEvent(Cursor cursor) {
        return new EventObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4));
    }
}
