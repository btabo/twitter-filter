package com.bta.twitter.test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twitter4j.TwitterObjectFactory;

import com.bta.twitter.component.bo.TweetBO;
import com.bta.twitter.fmk.PMF;
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
    			null,
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
