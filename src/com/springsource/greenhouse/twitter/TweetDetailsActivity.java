package com.springsource.greenhouse.twitter;

import java.text.SimpleDateFormat;

import org.springframework.social.greenhouse.api.Tweet;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.springsource.greenhouse.AbstractGreenhouseActivity;
import com.springsource.greenhouse.R;

public class TweetDetailsActivity extends AbstractGreenhouseActivity {
	
	protected static final String TAG = TweetDetailsActivity.class.getSimpleName();
	
	private Tweet tweet;
	
	
	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.tweet_details);
		
		final ListView listView = (ListView) findViewById(R.id.tweet_details_menu);
		
		String[] menu_items = getResources().getStringArray(R.array.tweet_details_options_array);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.menu_list_item, menu_items);
		listView.setAdapter(arrayAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		    	Intent intent = new Intent();
		    	
		    	switch(position) {
			      	case 0:
//			      		intent.setClass(view.getContext(), EventSessionsFilteredActivity.class);
			      		break;
			      	case 1:
//			      		intent.setClass(view.getContext(), EventSessionsScheduleActivity.class);
			      		break;
			      	case 2:
//			      		intent.setClass(view.getContext(), EventTweetsActivity.class);
			      		break;
			      	default:
			      		break;
		    	}
		    	
//		    	startActivity(intent);
		    }
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		tweet = getApplicationContext().getSelectedTweet();
		refreshTweetDetails();
	}
	
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshTweetDetails() {
		if (tweet == null) {
			return;
		}
		
		TextView t = (TextView) findViewById(R.id.tweet_details_fromuser);
		t.setText("@" + tweet.getFromUser());
		
		t = (TextView) findViewById(R.id.tweet_details_time);
		t.setText(new SimpleDateFormat("MMM d h:mm a").format(tweet.getCreatedAt()));
		
		t = (TextView) findViewById(R.id.tweet_details_text);
		t.setText(tweet.getText());		
	}

}
