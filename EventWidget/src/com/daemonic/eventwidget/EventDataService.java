package com.daemonic.eventwidget;

import android.text.format.DateFormat;
import java.text.Format;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.RemoteViewsService.RemoteViewsFactory;
import android.util.Log;

public class EventDataService extends RemoteViewsService {
	@Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
		Log.w("eventwidget","testing 3");
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
    private static final String[] COLS = new String[] 
    		{ CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART};

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
    	
    	Log.w("eventwidget","testing 2");
    	
        // Get the data for this position from the content provider
        String title = "N/A";
        Long start = 0L;
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.event_item);
        
        if(!mCursor.isLast()) {
        	if (mCursor.moveToNext()) {
        		Format df = DateFormat.getDateFormat(mContext);
        		Format tf = DateFormat.getTimeFormat(mContext);
        	
        		try {
        			title = mCursor.getString(0);
        			start = mCursor.getLong(1);
        		} catch (Exception e) {
        			//ignore
        		}
        		rv.setTextViewText(R.id.event_item_text,title+" on "+df.format(start)+" at "+tf.format(start));
        		Log.w("eventwidget",title+" on "+df.format(start)+" at "+tf.format(start));
        	}
        }
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
        mCursor = mContext.getContentResolver().query(Events.CONTENT_URI, COLS, null,
                null, null);
        Log.w("eventwidget","testing");
    }
}
