package com.bta.twitter.fmk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.TransformerUtils;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.TwitterObjectFactory;

import com.bta.twitter.component.bo.TweetBO;

public class TwitterUtils {
	
	private static final Logger log = Logger.getLogger(TwitterUtils.class.getName());
	
	static final private long TIMEFRAME_FROM = 10000000L; // age in ms of the oldest tweets to analyse
	
	
	/** 
	 * Returns true if the status date is in the timeframe to analyze 
	 * @param status
	 * @return
	 */
	public static Boolean isInTimeFrame(Status status){
		Long now = new Date().getTime();
		
		long from = now - TIMEFRAME_FROM;
		
		long t = status.getCreatedAt().getTime();
		log.info("time = " + t + "/ from = " + from);
		log.info("from = " + from);
		
		return t > from;
	}
	
	/**
	 * Maps Twitter Status to TwitterBO 
	 * @param status
	 * @return
	 */
	public static TweetBO mapToBo(Status status){
    	return new TweetBO(
    			status.getId(), 
    			status.getUser().getName(),
    			status.getText(),
    			status.getCreatedAt(),
    			getHashtags(status),
    			TwitterObjectFactory.getRawJSON(status)
    			);
    }
	
	private static List<String> getHashtags(Status status){
		List<HashtagEntity> list = new ArrayList<HashtagEntity>(Arrays.asList(status.getHashtagEntities()));
		List<String> hashtags = new ArrayList<String>();
		
		for (HashtagEntity hashEntity : list){
			hashtags.add(hashEntity.getText());
		}
		//List<String> hashtags = (List<String>) CollectionUtils.collect(list, TransformerUtils.invokerTransformer("getText"));
		return hashtags;
	}
}
