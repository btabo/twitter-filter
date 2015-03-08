package com.bta.twitter.fmk;

import java.util.Date;
import java.util.logging.Logger;

import twitter4j.Status;

public class TwitterUtils {
	
	private static final Logger log = Logger.getLogger(TwitterUtils.class.getName());
	
	static final private long TIMEFRAME_FROM = 10000000L; // age in ms of the oldest tweets to analyse
	
	
	/** 
	 * returns true if the status date is in the timeframe to analyze 
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
}
