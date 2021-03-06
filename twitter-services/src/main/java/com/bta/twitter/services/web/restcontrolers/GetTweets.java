package com.bta.twitter.services.web.restcontrolers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.jdo.Query;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.HashtagEntity;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.bta.twitter.component.TwitterComponent;
import com.bta.twitter.component.bo.TweetBO;
import com.bta.twitter.fmk.PMF;
import com.bta.twitter.fmk.TwitterUtils;

@RestController
public class GetTweets {
	
	private static final Logger log = Logger.getLogger(GetTweets.class.getName());
	
	@Resource
	private TwitterComponent twitterComponent;
	
	
    @RequestMapping("/hello")
    public String hello(@RequestParam(value="name", required=false, defaultValue="World") String name) {
      return "helloworld";    
    }
    
    @RequestMapping("/getLatestTweet")
    public Status getLatestTweet() {
    	Twitter twitter = TwitterFactory.getSingleton();
		try {
			return twitter.getHomeTimeline().get(0);
		} catch (TwitterException e) {
			e.printStackTrace();
			return null;
		}    
    }
    
    @RequestMapping("/getHomeTimeline")
    public List<TweetBO> getHomeTimeline() {
		int p = 1;
		Paging page = new Paging(p,50);
		Boolean continuePaging = Boolean.TRUE;
		
		Twitter twitter = TwitterFactory.getSingleton();
		List<TweetBO> result = new ArrayList<TweetBO>();
		
		try {
			
			do {
				page.setPage(p);
				List<Status> myTimeLine = twitter.getHomeTimeline(page);
				for (Status status : myTimeLine){					
					if (TwitterUtils.isInTimeFrame(status) ){						
						result.add(TwitterUtils.mapToBo(status));
						log.info(status.getId() +
								"/" + status.getCreatedAt().getTime() +
								"/" + status.getUser().getName() + 
								"/" + status.getText());
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
		//log.info("############ RANK HASHTAGS : " + rankHashtag(result).toString());
		//PMF.get().getPersistenceManager().makePersistentAll(result);
		
		return result;
    }
    
    
    public Map<String,Integer> rankHashtag(List<Status> tweets){
    	Map<String,Integer> result = new HashMap<String, Integer>();
    	
    	for (Status status : tweets){
    		List<HashtagEntity> hashtags = new ArrayList<HashtagEntity>(Arrays.asList(status.getHashtagEntities()));
    		for (HashtagEntity hash : hashtags){
    			Integer nb = result.get(hash.getText());
    			if (null != nb){
    				result.put(hash.getText(), nb + 1);
    			} else {
    				result.put(hash.getText(), 1);
    			}
    			result.put(hash.getText(), nb);
    		}
    	}
    	
    	return result;
    }
    
    
    @RequestMapping("/getTopTweets")
    public List<TweetBO> getTopTweets() {
    	return twitterComponent.getTopTweet();
    }

    @RequestMapping("/getAllCache")
    public List<TweetBO> getAllCache() {    	
    	Query q = PMF.get().getPersistenceManager().newQuery(TweetBO.class);
    	return (List<TweetBO>)q.execute();
    }
    
}
