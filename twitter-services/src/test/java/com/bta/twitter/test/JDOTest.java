package com.bta.twitter.test;

import java.util.Date;

import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bta.twitter.data.jdo.PMF;
import com.bta.twitter.data.jdo.TweetBO;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class JDOTest {
	
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

        TweetBO e = new TweetBO("Alfred", "Smith", new Date());

        try {
            pm.makePersistent(e);
        } finally {
            pm.close();
        }
	}
}
