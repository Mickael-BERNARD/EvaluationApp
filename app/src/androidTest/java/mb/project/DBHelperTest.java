package mb.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.ArrayRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;

import static android.database.DatabaseUtils.queryNumEntries;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Mickaël BERNARD on 15-Aug-16.
 */
@RunWith(AndroidJUnit4.class)

@SmallTest
public class DBHelperTest {

  private DBHelper dbHelper;

  @Before
  public void setUp() throws Exception {

    Context context = InstrumentationRegistry.getTargetContext();
    context.deleteDatabase(DBHelper.DATABASE_NAME);
    dbHelper = new DBHelper(context);
    dbHelper.eraseAllUsers();

  }

  /**
   * This test allows us to verify that we can add two users in the database, that
   * their index will be incremented correctly and that their data will be properly
   * stored in a UserInputContract class.
   * @throws Exception
     */
  @Test
  public void testInsertUser() throws Exception{
    long value1 =  dbHelper.insertUser(new UserInputContract("Barrack","Obama"));
    assert(value1 == 1);
    long value2 = dbHelper.insertUser(new UserInputContract("Hillary", "Clinton"));
    assert(value2 == 2);


    // Verifying that the database data is properly stored in the container class:
    UserInputContract userInputContract1 = dbHelper.getUser((int) value1);
    assertTrue(userInputContract1.getFirstName().compareTo("Barrack")==0);
    assertTrue(userInputContract1.getLastName().compareTo("Obama")==0);

  }

  /**
   * This test verifies that the erallAllUser() method applied to a table will empty it.
   * @throws Exception
     */
  @Test
  public void testEraseAllUsers() throws Exception{
      dbHelper.insertUser(new UserInputContract("Hillary", "Clinton"));
     dbHelper.insertUser(new UserInputContract("Donald","Trump"));
     dbHelper.insertUser(new UserInputContract("Barrack","Obama"));

    // Removing all the users
    dbHelper.eraseAllUsers();
    // Retrieving the size of the Database:
    int size = dbHelper.getAllUsers().size();
    //==
    assertTrue(size==0);
  }

  /**
   * This test checks that the application of the method eraseUser
   * will remove the row with the id indicated in the content of the contract parameter.
   * Note: In the same way as Updating the database, the delete method only needs to know the id number
   * of the row to delete so the content of the contract is not important (as it can be seen in the test).
   * @throws Exception
     */
  @Test
  public void testEraseUser() throws Exception{
    dbHelper.insertUser(new UserInputContract("Hillary", "Clinton"));
    UserInputContract contract = new UserInputContract("Barrack","Obama");
    contract.setID(1);
    dbHelper.deleteRecord(contract);
    assertTrue(dbHelper.getAllUsers().size()==0);
  }

  /**
   * This test checks that all the rows are selected when calling
   * the getAllUsers method and that the data is correctly stored in the ArrayList.
   * @throws Exception
     */
  @Test
  public void testGetAllUsers() throws Exception{
    dbHelper.insertUser(new UserInputContract("Hillary", "Clinton"));
    dbHelper.insertUser(new UserInputContract("Donald","Trump"));
    dbHelper.insertUser(new UserInputContract("Barrack","Obama"));
   ArrayList<UserInputContract> userInputContracts=  dbHelper.getAllUsers();
    int size = userInputContracts.size();
    //===
    assertTrue(size == 3);
    UserInputContract userInputContract = userInputContracts.get(0);
    assertTrue(userInputContract.getFirstName().compareTo( "Hillary")==0 & userInputContract.getLastName().compareTo("Clinton")==0 );
  }

  @Test
  public void testGetUser() throws  Exception{
    dbHelper.insertUser(new UserInputContract("Hillary", "Clinton"));
    UserInputContract contract =  dbHelper.getUser(1);
    //===
    assertTrue(contract.getFirstName().compareTo("Hillary")==0);
    assertTrue(contract.getLastName().compareTo("Clinton")==0);
  }

  @Test
  public void testUpdateUser() throws Exception{
    // We insert a user
    dbHelper.insertUser(new UserInputContract("Hillary", "Clinton"));
    // We create a new user and set it's ID to 1.
    UserInputContract contract = new UserInputContract("Donald","Trump");
    contract.setID(1);
    // Update user
    dbHelper.updateUser(contract);
    //====
    assertTrue(contract.getFirstName().compareTo("Donald")==0);
    assertTrue(contract.getLastName().compareTo("Trump")==0);
    // We make sure there is only one row left in the database.
    assertTrue(dbHelper.getAllUsers().size()==1);
  }
}
