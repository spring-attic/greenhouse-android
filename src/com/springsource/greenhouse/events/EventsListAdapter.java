package com.springsource.greenhouse.events;

import java.util.List;

import org.springframework.social.greenhouse.api.Event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.springsource.greenhouse.R;

public class EventsListAdapter extends BaseAdapter {

	private List<Event> events;
	private final LayoutInflater layoutInflater;

	public EventsListAdapter(Context context, List<Event> events) {
		this.events = events;
		this.layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return events.size();
	}

	public Event getItem(int position) {
		return events.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Event event = getItem(position);
		View view = convertView;
		
		if (view == null) {
			view = layoutInflater.inflate(R.layout.events_list_item, parent, false);
		}

		if (event != null) {
			TextView t = (TextView) view.findViewById(R.id.event_list_item_title); 
			t.setText(event.getTitle());
			
			t = (TextView) view.findViewById(R.id.event_list_item_groupname); 
			t.setText(event.getGroupName());
			
			t = (TextView) view.findViewById(R.id.event_list_item_date); 
			t.setText(event.getFormattedTimeSpan());
		}
		
		return view;
	}

}
