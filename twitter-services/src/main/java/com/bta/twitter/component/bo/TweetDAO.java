package com.bta.twitter.component.bo;

import com.bta.twitter.fmk.PMF;

public class TweetDAO {

	public TweetBO createBO(TweetBO tweetBo){		
		return PMF.get().getPersistenceManager().makePersistent(tweetBo);
	}
	
}
