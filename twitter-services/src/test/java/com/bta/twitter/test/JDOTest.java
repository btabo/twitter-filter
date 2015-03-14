package com.bta.twitter.test;

import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twitter4j.TwitterObjectFactory;

import com.bta.twitter.data.jdo.PMF;
import com.bta.twitter.data.jdo.TweetBO;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class JDOTest {
	
	private static final Logger log = Logger.getLogger(JDOTest.class.getName());
	
	private final LocalServiceTestHelper helper =
	        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	@Before
	public void setUp() {
	    helper.setUp();
	}

	@After
	public void tearDown() {
	    helper.tearDown();
	}
	
	@Test
	public void testInsertTweet(){
		PersistenceManager pm = PMF.get().getPersistenceManager();

        TweetBO e = new TweetBO(
    			10000000L, 
    			"someuser",
    			"salut la compagnie",
    			new Date(),
    			"{someJSON}"
    			);

        try {
            pm.makePersistent(e);
            log.info("key = " + e.getKey().getId());
        } finally {
            pm.close();
        }
	}
}
