package com.daemonic.eventwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.ComponentName;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.net.Uri;
import android.util.Log;


public class EventWidget extends AppWidgetProvider {

	@SuppressWarnings("deprecation")
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		// Get all ids
	    ComponentName thisWidget = new ComponentName(context,
	    		EventWidget.class);
	    Log.w("eventwidget","started");
	    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
	    for (int widgetId : allWidgetIds) {
	    	Log.w("eventwidget",Integer.toString(widgetId));
	    	
	    	final Intent intent = new Intent(context, EventDataService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[widgetId]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            
            final RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.event_widget);
            rv.setRemoteAdapter(appWidgetIds[widgetId], R.id.event_widget_body, intent);

            // Set the empty view to be displayed if the collection is empty.  It must be a sibling
            // view of the collection view.
            rv.setEmptyView(R.id.event_widget_body, R.id.empty_view);
	    /*
	      // Create some random data
	      RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
	          R.layout.event_widget);
	      
	      // Set the text for 3 items...?
	      RemoteViews tv = new RemoteViews(context.getPackageName(),R.layout.event_item);
	      tv.setTextViewText(R.id.event_item_text,"Test Case");
	      Log.w("eventwidget",tv.toString());
	      remoteViews.addView(R.id.event_widget_scroll,tv);
	      
	      RemoteViews tv2 = new RemoteViews(context.getPackageName(),R.layout.event_item);
	      tv2.setTextViewText(R.id.event_item_text,"Test Case 2");
	      Log.w("eventwidget",tv2.toString());
	      remoteViews.addView(R.id.event_widget_scroll,tv2);
	      
	      RemoteViews tv3 = new RemoteViews(context.getPackageName(),R.layout.event_item);
	      tv3.setTextViewText(R.id.event_item_text,"Test Case 3");
	      Log.w("eventwidget",tv3.toString());
	      remoteViews.addView(R.id.event_widget_scroll,tv3);

	      // Register an onClickListener
	      Intent intent = new Intent(context, EventWidget.class);

	      intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
	      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
	      */

	      appWidgetManager.updateAppWidget(widgetId, rv);
	    }
	}
	
	
}
