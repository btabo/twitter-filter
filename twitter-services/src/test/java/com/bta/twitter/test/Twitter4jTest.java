package com.bta.twitter.test;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Twitter4jTest {

	@Test
	public void getTimeLineTest() {
		// The factory instance is re-useable and thread safe.
	    Twitter twitter = TwitterFactory.getSingleton();
	    List<Status> statuses;
		try {
			statuses = twitter.getHomeTimeline();
			System.out.println("Showing home timeline.");
		    for (Status status : statuses) {
		        System.out.println(status.getUser().getName() + ":" +
		                           status.getText());
		    }
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
	    
	}
	
	@Test
	public void searchTweet(){
		Twitter twitter = new TwitterFactory().getInstance();
		try {
            Query query = new Query("#rera");
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
            } while ((query = result.nextQuery()) != null);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
	}

}
