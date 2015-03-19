package com.bta.twitter.component.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.jdo.Query;

import twitter4j.Paging;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.bta.twitter.component.TwitterComponent;
import com.bta.twitter.component.bo.TweetBO;
import com.bta.twitter.fmk.PMF;
import com.bta.twitter.fmk.TwitterUtils;

public class TwitterComponentImpl implements TwitterComponent {

	private static final Logger log = Logger
			.getLogger(TwitterComponentImpl.class.getName());

	public List<TweetBO> getTopTweet() {

		// 1- Get timeline
		List<TweetBO> timeline = getTimeline();

		// 2- Collect hashtags list
		Map<String, Long> hashs = getHashs(timeline);

		// 3- Append secondary level tweets
		List<TweetBO> allTweets = findTweetsPerHash(hashs.keySet());

		// 4- Sort Hashtags by occurence number
		hashs = getHashs(allTweets);

		// 5- return most re-tweeted Tweets
		List<TweetBO> topTweets = getTopTweets(hashs.keySet(), allTweets);

		return topTweets;
	}

	/**
	 * returns the recent user timeline
	 * 
	 * @return
	 */
	private List<TweetBO> getTimeline() {
		int p = 1;
		Paging page = new Paging(p, 50);
		Boolean continuePaging = Boolean.TRUE;

		Twitter twitter = TwitterFactory.getSingleton();
		List<TweetBO> result = new ArrayList<TweetBO>();

		try {

			do {
				page.setPage(p);
				List<Status> myTimeLine = twitter.getHomeTimeline(page);
				for (Status status : myTimeLine) {
					if (TwitterUtils.isInTimeFrame(status)) {
						result.add(TwitterUtils.mapToBo(status));
						log.info(status.getId() + "/"
								+ status.getCreatedAt().getTime() + "/"
								+ status.getUser().getName() + "/"
								+ status.getText());
					} else {
						continuePaging = Boolean.FALSE;
						break;
					}
				}
				p++;
			} while (continuePaging);

		} catch (TwitterException e) {
			e.printStackTrace();

		}
		return result;
	}

	private Map<String, Long> getHashs(List<TweetBO> tweets) {
		Map<String, Long> hashMap = new TreeMap<String, Long>(
				Collections.reverseOrder());
		for (TweetBO tweetBo : tweets) {
			for (String hashtag : tweetBo.getHashtags()) {
				if (null != hashMap.get(hashtag)){
					hashMap.put(hashtag, hashMap.get(hashtag) + 1);
				} else {
					hashMap.put(hashtag, 1L);
				}
			}
		}

		return hashMap;
	}

	private List<TweetBO> findTweetsPerHash(Set<String> hashs) {

		if (!hashs.isEmpty()) {
			Twitter twitter = TwitterFactory.getSingleton();
			twitter4j.Query query = new twitter4j.Query("#"+hashs.iterator().next());
			query.setCount(50);
			try {
				QueryResult result = twitter.search(query);
				List<Status> tweets = result.getTweets();

				int p = 1;
				Paging page = new Paging(p, 50);
				Boolean continuePaging = Boolean.TRUE;
				List<TweetBO> topTweets = new ArrayList<TweetBO>();

				for (Status status : tweets) {
					if (TwitterUtils.isInTimeFrame(status)) {
						topTweets.add(TwitterUtils.mapToBo(status));
						log.info(status.getId() + "/"
								+ status.getCreatedAt().getTime() + "/"
								+ status.getUser().getName() + "/"
								+ status.getText());
					} else {
						continuePaging = Boolean.FALSE;
						break;
					}
				}
				return topTweets;
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	

	private List<TweetBO> getTopTweets(Set<String> sortedHashs, List<TweetBO> tweets) {
		return tweets;
	}

}
