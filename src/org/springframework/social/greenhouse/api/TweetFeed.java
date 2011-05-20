package org.springframework.social.greenhouse.api;

import java.util.List;

public class TweetFeed {
	
	private boolean lastPage;
	
	private long maxId;
	
	private long sinceId;
	
	private List<Tweet> tweets;
	
	public TweetFeed(boolean lastPage, long maxId, long sinceId, List<Tweet> tweets) {
		this.lastPage = lastPage;
		this.maxId = maxId;
		this.sinceId = sinceId;
		this.tweets = tweets;
	}

	public boolean getLastPage() {
		return lastPage;
	}
	
	public long getMaxId() {
		return maxId;
	}
	
	public long getSinceId() {
		return sinceId;
	}
	
	public List<Tweet> getTweets() {
		return tweets;
	}

}
