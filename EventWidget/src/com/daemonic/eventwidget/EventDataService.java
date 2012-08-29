package com.daemonic.eventwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class EventDataService extends RemoteViewsService {
	@Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new EventRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

/**
 * This is the factory that will provide data to the collection widget.
 */
class EventRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Cursor mCursor;
    private int mAppWidgetId;

    public EventRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        // Since we reload the cursor in onDataSetChanged() which gets called immediately after
        // onCreate(), we do nothing here.
    }

    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    public int getCount() {
        return mCursor.getCount();
    }

    public RemoteViews getViewAt(int position) {
        // Get the data for this position from the content provider
        int temp = 0;
        String eventTitle = "";
        if (mCursor.moveToPosition(position)) {
            final int cityColIndex = mCursor.getColumnIndex(Events.TITLE);
            eventTitle = mCursor.getString(cityColIndex);
        }

        // Return a proper item with the proper city and temperature.  Just for fun, we alternate
        // the items to make the list easier to read.
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.event_item);
        rv.setTextViewText(R.id.event_item_text, eventTitle);

        // Set the click intent so that we can handle it and show a toast message
        return rv;
    }
    public RemoteViews getLoadingView() {
        // We aren't going to return a default loading view in this sample
        return null;
    }

    public int getViewTypeCount() {
        // Technically, we have two types of views (the dark and light background views)
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
        // Refresh the cursor
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = mContext.getContentResolver().query(Events.CONTENT_URI, null, null,
                null, null);
    }
}
