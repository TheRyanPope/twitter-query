package twitter;

import java.util.ArrayList;

import twitter4j.Location;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetController {
	private static String CONSUMER_KEY;
	private static String CONSUMER_SECRET;
	private static String ACCESS_TOKEN;
	private static String ACCESS_SECRET;

	private static ArrayList<Tweet> queryResults;
	private static Tweet queryRow;

	private TwitterFactory tf;
	private Twitter twitter;
	private ConfigurationBuilder cb;

	public static ArrayList<String> trendLocations;;
	public static ArrayList<Integer> woeIds;
	public static ArrayList<Long> statusIDs;

	// set up the controller with twitter credentials
	// and instantiate all necessary twitter4j objects
	public TweetController() {
		// credentials for my twitter account
		CONSUMER_KEY = "flBhXragVwcdNUa0thHtY8nOn";
		CONSUMER_SECRET = "L5XUrXTz6i1zUOUJNJNgZq8bu0oV4VBgpaXlS2zb0myKwSWsxr";
		ACCESS_TOKEN = "723608745746542592-YXQnmygiOVrdOzWTOB6PXsaFpSl2VNi";
		ACCESS_SECRET = "B2uN2fKYWyGqczTDE0wE1MVbFIdKYYjlrE1nyMB1OTpoz";

		cb = new ConfigurationBuilder();

		cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY)
				.setOAuthConsumerSecret(CONSUMER_SECRET)
				.setOAuthAccessToken(ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(ACCESS_SECRET);

		tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}

	public ArrayList<Tweet> searchTweets(String statement,
			String tweetsToDisplay, String filterStatus)
			throws TwitterException {
		statusIDs = new ArrayList<Long>();
		queryResults = new ArrayList<Tweet>();

		Query query;
		QueryResult result;

		// filter out retweets but does not guarantee that
		// query size will persist as intended
		//
		// query can only query the same parameter once ever 15 seconds
		// switching between filtered and non filtered retweets is considered
		// a different parameter and will yield newest results on click
		if (filterStatus.equals("filter")) {
			query = new Query(statement + "+exclude:retweets");
			query.setCount(Integer.parseInt(tweetsToDisplay));
			result = twitter.search(query);
		} 
		// do not filter out retweets
		else {
			query = new Query(statement);
			query.setCount(Integer.parseInt(tweetsToDisplay));
			result = twitter.search(query);
		}

		// check if query yielded no results
		if (result.getTweets().size() == 0) {
			ArrayList<Tweet> empty = new ArrayList<Tweet>();
			Tweet blank = new Tweet("EMPTY!!!", "", "");
			empty.add(blank);
			return empty;
		}

		for (Status status : result.getTweets()) {
			queryRow = new Tweet(" ", " ", " ");
			queryRow.setUsername("@" + status.getUser().getScreenName());
			queryRow.setTweet(status.getText());
			queryRow.setDob(status.getCreatedAt().toString());

			queryResults.add(queryRow);
			statusIDs.add(status.getId());

		}

		return queryResults;
	}

	// get list of tweet ids
	public ArrayList<Long> getStatusID() {
		return statusIDs;
	}

	// get the list of trends for the specified location
	public Object[] getTrends(int woeId) throws TwitterException {
		// trends in a specific woeid region
		Trends trends = twitter.getPlaceTrends(woeId);
		ArrayList<String> trendNames = new ArrayList<String>();
		for (int j = 0; j < trends.getTrends().length; j++) {
			trendNames.add(trends.getTrends()[j].getName());
		}

		return trendNames.toArray();
	}

	// get the trend location names or woeids
	public Object[] getTrendLocations(String selection) throws TwitterException {
		trendLocations = new ArrayList<String>();
		woeIds = new ArrayList<Integer>();

		// find woeids for different locations
		ResponseList<Location> locations;
		locations = twitter.getAvailableTrends();
		for (Location location : locations) {
			trendLocations.add(location.getName());
			woeIds.add(location.getWoeid());
		}

		if (selection.equals("locations")) {
			return trendLocations.toArray();
		}

		return woeIds.toArray();
	}

	// send a tweet
	public void sendTweet(String latestStatus) throws TwitterException {
		twitter.updateStatus(latestStatus);
	}

	// retweet a specified tweet
	// if the specified tweet is a retweet itself, result will be you
	// retweeting the specified users retweet, rather than the entire original
	// tweet with the embedded retweet
	public void sendReTweet(long statusID) throws TwitterException {
		twitter.retweetStatus(statusID);
	}
}