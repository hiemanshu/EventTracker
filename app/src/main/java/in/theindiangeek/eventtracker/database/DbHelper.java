package in.theindiangeek.eventtracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    /*
     * events {
     * _id, image_path, name, location, cost
     * }
     *
     * tracked_events {
     * _id, user_name, event_id
     * }
     */

    private static final String DATABASE_NAME = "events.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_EVENTS = "events";
    public static final String TABLE_TRACKED_EVENTS = "tracked_events";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_IMAGE_PATH = "image_path";

    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_EVENT_ID = "event_id";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table_events = "CREATE TABLE " + TABLE_EVENTS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_LOCATION + " TEXT NOT NULL, "
                + COLUMN_COST + " TEXT NOT NULL, "
                + COLUMN_IMAGE_PATH + " TEXT NOT NULL);";
        String create_table_tracked_events = "CREATE TABLE " + TABLE_TRACKED_EVENTS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + COLUMN_USER_NAME + " TEXT NOT NULL, "
                + COLUMN_EVENT_ID + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(create_table_events);
        sqLiteDatabase.execSQL(create_table_tracked_events);

        addDummyEvents(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACKED_EVENTS);
        onCreate(sqLiteDatabase);
    }

    private void addDummyEvents(SQLiteDatabase sqLiteDatabase) {
        List<ContentValues> dummyData = getDummyData();

        for(ContentValues event : dummyData) {
            sqLiteDatabase.insert(TABLE_EVENTS, null,
                    event);
        }
    }

    private List<ContentValues> getDummyData() {
        List<ContentValues> events = new ArrayList<ContentValues>();

        ContentValues event;

        event = new ContentValues();
        event.put(COLUMN_NAME, "Metallica Concert");
        event.put(COLUMN_LOCATION , "Palace Grounds");
        event.put(COLUMN_COST , "Paid");
        event.put(COLUMN_IMAGE_PATH , "images/metallica.jpg");
        events.add(event);

        event = new ContentValues();
        event.put(COLUMN_NAME, "Saree Exhibition");
        event.put(COLUMN_LOCATION , "Malleshwaram");
        event.put(COLUMN_COST , "Free");
        event.put(COLUMN_IMAGE_PATH , "images/Sari.jpg");
        events.add(event);

        event = new ContentValues();
        event.put(COLUMN_NAME, "Wine Tasting");
        event.put(COLUMN_LOCATION , "Links Brewery");
        event.put(COLUMN_COST , "Paid");
        event.put(COLUMN_IMAGE_PATH , "images/wine.jpg");
        events.add(event);

        event = new ContentValues();
        event.put(COLUMN_NAME, "Startups Meet");
        event.put(COLUMN_LOCATION , "Kanteerava Indoor Stadium");
        event.put(COLUMN_COST , "Paid");
        event.put(COLUMN_IMAGE_PATH , "images/workshop.jpg");
        events.add(event);

        event = new ContentValues();
        event.put(COLUMN_NAME, "Summer Noon Party");
        event.put(COLUMN_LOCATION , "Kumara Park");
        event.put(COLUMN_COST , "Paid");
        event.put(COLUMN_IMAGE_PATH , "images/party.jpg");
        events.add(event);

        event = new ContentValues();
        event.put(COLUMN_NAME, "Rock and Roll Nights");
        event.put(COLUMN_LOCATION , "Sarjapura Road");
        event.put(COLUMN_COST , "Paid");
        event.put(COLUMN_IMAGE_PATH , "images/rock-and-roll.jpg");
        events.add(event);

        event = new ContentValues();
        event.put(COLUMN_NAME, "Barbecue Fridays");
        event.put(COLUMN_LOCATION , "Whitefield");
        event.put(COLUMN_COST , "Paid");
        event.put(COLUMN_IMAGE_PATH , "images/barbecue.jpg");
        events.add(event);

        event = new ContentValues();
        event.put(COLUMN_NAME, "Summer Workshop");
        event.put(COLUMN_LOCATION , "Indiranagar");
        event.put(COLUMN_COST , "Free");
        event.put(COLUMN_IMAGE_PATH , "images/workshop.jpg");
        events.add(event);

        event = new ContentValues();
        event.put(COLUMN_NAME, "Impressions & Expressions");
        event.put(COLUMN_LOCATION , "MG Road");
        event.put(COLUMN_COST , "Free");
        event.put(COLUMN_IMAGE_PATH , "images/party.jpg");
        events.add(event);

        event = new ContentValues();
        event.put(COLUMN_NAME, "Italian Carnival");
        event.put(COLUMN_LOCATION , "Electronic City");
        event.put(COLUMN_COST , "Free");
        event.put(COLUMN_IMAGE_PATH , "images/Sari.jpg");
        events.add(event);

        return events;
    }
}
