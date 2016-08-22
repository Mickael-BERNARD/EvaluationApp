package mb.project;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Mickaël BERNARD on 21-Aug-16.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SessionManagerTest {
  SessionManager session;

  @Before
  public void setUp(){
    Context context = InstrumentationRegistry.getTargetContext();
    session = new SessionManager(context);
    // Delete all information stored:
    session.deleteUserAccount();
  }

  @Test
  public void testInitialization(){
    // user registered = false
    assertFalse(session.getRegistrationStatus());
    // userID = null
    assertTrue(session.getUserId()==-1);

  }

  @Test
  public void testRegistration(){
    // A new user registers under user id = 5
    session.createRegistrationSession(5);
    // We check that the system now recognises him as registered:
    assertTrue(session.getRegistrationStatus());
    assertTrue(session.getUserId()==5);
  }

}

