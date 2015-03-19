package com.bta.twitter.component;

import java.util.List;

import com.bta.twitter.component.bo.TweetBO;

public interface TwitterComponent {

	public List<TweetBO> getTopTweet();
}
