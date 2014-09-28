package in.theindiangeek.eventtracker.objects;

public class EventObject {

    private int mId;
    private String mImagePath;
    private String mEventName;
    private String mEventLocation;
    private String mEventCost;

    public EventObject(int mId, String mEventName, String mEventLocation, String mEventCost, String mImagePath) {
        this.mId = mId;
        this.mImagePath = mImagePath;
        this.mEventName = mEventName;
        this.mEventLocation = mEventLocation;
        this.mEventCost = mEventCost;
    }

    public int getId() {
        return mId;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public String getEventName() {
        return mEventName;
    }

    public String getEventLocation() {
        return mEventLocation;
    }

    public String getEventCost() {
        return mEventCost;
    }

    @Override
    public String toString() {
        return mId + ", " + mEventName + ", " + mEventLocation + ", " + mEventCost + ", " + mImagePath;
    }
}
