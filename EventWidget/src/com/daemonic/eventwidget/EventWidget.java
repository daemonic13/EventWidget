package com.daemonic.eventwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.ComponentName;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.util.Log;


public class EventWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		// Get all ids
	    ComponentName thisWidget = new ComponentName(context,
	    		EventWidget.class);
	    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
	    for (int widgetId : allWidgetIds) {
	      // Create some random data
	      RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
	          R.layout.event_widget);
	      
	      // Set the text
	      //final LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      //TextView tv = (TextView)inflater.inflate(R.layout.event_item, null);;
	      RemoteViews tv = new RemoteViews(context.getPackageName(),R.layout.event_item);
	      tv.setTextViewText(R.id.event_item_text,"Test Case");
	      Log.w("eventwidget",tv.toString());
	      remoteViews.addView(R.id.event_widget_body,tv);

	      // Register an onClickListener
	      Intent intent = new Intent(context, EventWidget.class);

	      intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
	      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

	      appWidgetManager.updateAppWidget(widgetId, remoteViews);
	    }
	}
	
	
}
