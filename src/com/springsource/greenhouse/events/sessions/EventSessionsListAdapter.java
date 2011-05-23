package com.springsource.greenhouse.events.sessions;

import java.util.List;

import org.springframework.social.greenhouse.api.EventSession;
import org.springframework.social.greenhouse.api.Room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.springsource.greenhouse.R;

public class EventSessionsListAdapter extends BaseAdapter {
	
	private List<EventSession> sessions;
	private final LayoutInflater layoutInflater; 
	
	public EventSessionsListAdapter(Context context, List<EventSession> sessions) {
		this.sessions = sessions;
		this.layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		if (sessions == null) {
			return 0;
		}
		return sessions.size();
	}

	public EventSession getItem(int position) {
		return sessions.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		EventSession session = getItem(position);
		View view = convertView;
		
		if (view == null) {
			view = layoutInflater.inflate(R.layout.event_sessions_list_item, parent, false);
		}
		
		TextView t = (TextView) view.findViewById(R.id.event_sessions_list_item_title);
		t.setText(session.getTitle());
		
		t = (TextView) view.findViewById(R.id.event_sessions_list_item_leaders);
		t.setText(session.getJoinedLeaders(", "));
		
		t = (TextView) view.findViewById(R.id.event_sessions_list_item_time_and_room);
		String s = session.getFormattedTimeSpan();
		Room room = session.getRoom();
		if (room != null) {
			s += " in " + room.getLabel();
		}
		t.setText(s);
		
		return view;
	}

}
