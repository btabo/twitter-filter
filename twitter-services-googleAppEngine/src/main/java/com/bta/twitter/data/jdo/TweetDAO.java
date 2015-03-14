package com.bta.twitter.data.jdo;

public class TweetDAO {

	public TweetBO createBO(TweetBO tweetBo){		
		return PMF.get().getPersistenceManager().makePersistent(tweetBo);
	}
	
}
