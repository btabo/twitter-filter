package com.bta.twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.HashtagEntity;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.bta.twitter.fmk.TwitterUtils;

@RestController
public class GetTweets {
	
	private static final Logger log = Logger.getLogger(GetTweets.class.getName());
	
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
    public List<Status> getHomeTimeline() {
		int p = 1;
		Paging page = new Paging(p,50);
		Boolean continuePaging = Boolean.TRUE;
		
		Twitter twitter = TwitterFactory.getSingleton();
		List<Status> result = new ArrayList<Status>();
		
		try {			
			
			do {
				page.setCount(p);
				List<Status> myTimeLine = twitter.getHomeTimeline(page);
				for (Status status : myTimeLine){
					if (TwitterUtils.isInTimeFrame(status) ){
						result.add(status);
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
		log.info("############ RANK HASHTAGS : " + rankHashtag(result).toString());
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
    
}
