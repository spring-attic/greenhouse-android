/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springsource.greenhouse.twitter;

import java.util.List;

import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.EventSession;
import org.springframework.social.greenhouse.api.Tweet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.springsource.greenhouse.AbstractGreenhouseListActivity;
import com.springsource.greenhouse.R;

/**
 * @author Roy Clarkson
 */
public abstract class TweetsListActivity extends AbstractGreenhouseListActivity {
	
	protected static final String TAG = TweetsListActivity.class.getSimpleName();
	
	private List<Tweet> tweets;
	
	
	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		downloadTweets();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.tweets_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    	case R.id.tweets_menu_refresh:
	    		downloadTweets();
	    		return true;
	    	default:
	    		return super.onOptionsItemSelected(item);
	    }
	}
	
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Tweet tweet = getTweet(position);
		getApplicationContext().setSelectedTweet(tweet);
		startActivity(new Intent(v.getContext(), TweetDetailsActivity.class));
	}
	
	
	//***************************************
	// Abstract methods
	//***************************************
	protected abstract void downloadTweets();
	
	
	//***************************************
    // Protected methods
    //***************************************
	protected Event getSelectedEvent() {
		return getApplicationContext().getSelectedEvent();
	}
	
	protected EventSession getSelectedSession() {
		return getApplicationContext().getSelectedSession();
	}
	
	protected void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
		getApplicationContext().setSelectedTweet(null);
		setListAdapter(new TweetsListAdapter(this, tweets));
	}
	
	protected List<Tweet> getTweets() {
		return tweets;
	}
	
	protected Tweet getTweet(int position) {
		return tweets.get(position);
	}

}
