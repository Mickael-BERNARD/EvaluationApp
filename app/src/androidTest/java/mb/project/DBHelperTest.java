package mb.project;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import mb.project.Database.ContractContent;
import mb.project.Database.DBHelper;
import mb.project.Database.TableAccounts;
import mb.project.Database.ContractAccount;
import mb.project.Database.TableContent;

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
    dbHelper.eraseAllUserContent();
  }

  // INSERT RECORDS
  /**
   * This test allows us to verify that we can add two users in the database, that
   * their index will be incremented correctly and that their data will be properly
   * stored in a UserInputContract class.
   * @throws Exception
     */
  @Test
  public void testInsertUser() throws Exception{
    long value1 =  dbHelper.insertUser(new ContractAccount("Barrack","Obama"));
    assert(value1 == 1);
    long value2 = dbHelper.insertUser(new ContractAccount("Hillary", "Clinton"));
    assert(value2 == 2);
    // Verifying that the database data is properly stored in the container class:
    ContractAccount contractAccount1 = dbHelper.getUser((int) value1);
    assertTrue(contractAccount1.getFirstName().compareTo("Barrack")==0);
    assertTrue(contractAccount1.getLastName().compareTo("Obama")==0);
  }
  @Test
  public void testInsertContent() throws Exception{
    long value1 =  dbHelper.insertContent(new ContractContent(5,"Australia","Sydney","What a lovely day"));
   assertTrue(value1 == 1);
    long value2 =  dbHelper.insertContent(new ContractContent(5,"Russia","Moscow","The red phone is silent"));
    //assertTrue(value2 == 2);
    // Verifying that the database data is properly stored in the container class:
    ContractContent  contractContent = dbHelper.getContent((int) value1);
    Log.d("Test", contractContent.toString());
    assertTrue(contractContent.getUserId()==5);
    assertTrue(contractContent.getCountry().compareTo("Australia")==0);
    assertTrue(contractContent.getCities().compareTo("Sydney")==0);
    assertTrue(contractContent.getDescription().compareTo("What a lovely day")==0);

  }

  // ERASE
  /**
   * This test verifies that the erallAllUser() method applied to a table will empty it.
   * @throws Exception
     */
  @Test
  public void testEraseAllUsers() throws Exception{
      dbHelper.insertUser(new ContractAccount("Hillary", "Clinton"));
     dbHelper.insertUser(new ContractAccount("Donald","Trump"));
     dbHelper.insertUser(new ContractAccount("Barrack","Obama"));

    // Removing all the users
    dbHelper.eraseAllUsers();
    // Retrieving the size of the Database:
    int size = dbHelper.getAllUsers().size();
    //==
    assertTrue(size==0);
  }

  @Test
  public void testEraseAllUserContent() throws Exception{
    dbHelper.insertContent(new ContractContent(5,"Australia","Sydney","What a lovely day"));
    dbHelper.insertContent(new ContractContent(5,"Russia","Moscow","The red phone is silent"));
    dbHelper.insertContent(new ContractContent(1,"North Korea","Pyongyang","Great leader is best leader"));

    // Removing all the content
    dbHelper.eraseAllUserContent();
    // Retrieving the resulting size of the database
    int size = dbHelper.getAllUserContent().size();
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
    dbHelper.insertUser(new ContractAccount("Hillary", "Clinton"));
    ContractAccount contract = new ContractAccount("Barrack","Obama");
    contract.setID(1);
    dbHelper.deleteUserAccount(contract);
    assertTrue(dbHelper.getAllUsers().size()==0);
  }

  @Test
  public void testEraseUserContent() throws Exception{
    Log.d("EraseUserCOntent",""+dbHelper.getAllUserContent().size());
    dbHelper.insertContent(new ContractContent(5,"Australia","Sydney","What a lovely day"));
    ContractContent contract = new ContractContent(5,"Russia","Moscow","The red phone is silent");
    Log.d("EraseUserCOntent",""+dbHelper.getAllUserContent().size());
    contract.setID(1);
    dbHelper.deleteUserContent(contract);

    assertTrue(dbHelper.getAllUserContent().size()==0);
  }


 // SELECT
  /**
   * This test checks that all the rows are selected when calling
   * the getAllUsers method and that the data is correctly stored in the ArrayList.
   * @throws Exception
     */
  @Test
  public void testGetAllUsers() throws Exception{
    dbHelper.insertUser(new ContractAccount("Hillary", "Clinton"));
    dbHelper.insertUser(new ContractAccount("Donald","Trump"));
    dbHelper.insertUser(new ContractAccount("Barrack","Obama"));
   ArrayList<ContractAccount> contractAccounts =  dbHelper.getAllUsers();
    int size = contractAccounts.size();
    //===
    assertTrue(size == 3);
    ContractAccount contractAccount = contractAccounts.get(0);
    assertTrue(contractAccount.getFirstName().compareTo( "Hillary")==0 & contractAccount.getLastName().compareTo("Clinton")==0 );
  }

  @Test
  public void testGetAllUserContent(){
    dbHelper.insertContent(new ContractContent(5,"Australia","Sydney","What a lovely day"));
    dbHelper.insertContent(new ContractContent(2,"Russia","Moscow","The red phone is silent"));
    dbHelper.insertContent(new ContractContent(1,"North Korea","Pyongyang","Great leader is best leader"));
    ArrayList<ContractContent> contractContents = dbHelper.getAllUserContent();
    int size = contractContents.size();
    //==
    assertTrue(size ==3);
    ContractContent contract = contractContents.get(1);
    assertTrue(contract.getUserId()==2);
    assertTrue(contract.getCountry().compareTo("Russia")==0);
    assertTrue(contract.getCities().compareTo("Moscow")==0);
    assertTrue(contract.getDescription().compareTo("The red phone is silent")==0);

  }


  @Test
  public void testGetUser() throws  Exception{
    dbHelper.insertUser(new ContractAccount("Barrack", "Obama"));
    dbHelper.insertUser(new ContractAccount("Hillary", "Clinton"));

    ContractAccount contract =  dbHelper.getUser(2);
    //===
    assertTrue(contract.getFirstName().compareTo("Hillary")==0);
    assertTrue(contract.getLastName().compareTo("Clinton")==0);
  }

  @Test
  public void testGetUserContent(){
    dbHelper.insertContent(new ContractContent(5,"Australia","Sydney","What a lovely day"));
    dbHelper.insertContent(new ContractContent(2,"Russia","Moscow","The red phone is silent"));
    dbHelper.insertContent(new ContractContent(1,"North Korea","Pyongyang","Great leader is best leader"));

    ContractContent contract = dbHelper.getContent(2);
    //==
    assertTrue(contract.getUserId()==2);
    assertTrue(contract.getCountry().compareTo("Russia")==0);
    assertTrue(contract.getCities().compareTo("Moscow")==0);
    assertTrue(contract.getDescription().compareTo("The red phone is silent")==0);
  }

  //UPDATE
  @Test
  public void testUpdateUser() throws Exception{
    // We insert a user
    dbHelper.insertUser(new ContractAccount("Hillary", "Clinton"));
    // We create a new user and set it's ID to 1.
    ContractAccount contract = new ContractAccount("Donald","Trump");
    contract.setID(1);
    // Update user
    dbHelper.updateUser(contract);
    //====
    contract = dbHelper.getUser(1); // We recover the row!
    assertTrue(contract.getFirstName().compareTo("Donald")==0);
    assertTrue(contract.getLastName().compareTo("Trump")==0);
    // We make sure there is only one row left in the database.
    assertTrue(dbHelper.getAllUsers().size()==1);
  }

  @Test
  public void testUpdateUserContent() throws Exception{
    // We insert content
    dbHelper.insertContent(new ContractContent(5,"Australia","Sydney","What a lovely day"));
    // We create a new user content class object and set it's id to 1.
    ContractContent contract = new ContractContent(2,"Russia","Moscow","The red phone is silent");
    contract.setID(1);
    // Update content row in database.
    dbHelper.updateContent(contract);

    //===
    contract = dbHelper.getContent(1);
    assertTrue(contract.getCountry().compareTo("Russia")==0);
    assertTrue(contract.getCities().compareTo("Moscow")==0);
    assertTrue(contract.getDescription().compareTo("The red phone is silent")==0);

  }

  @Test
  public void testGetAllUsersAlt() throws Exception{
    // We add 3 users
    dbHelper.insertUser(new ContractAccount("Hillary", "Clinton"));
    dbHelper.insertUser(new ContractAccount("Donald","Trump"));
    dbHelper.insertUser(new ContractAccount("Barrack","Obama"));
    // We recover the cursor:
    Cursor cursor = dbHelper.getAllUsersAlt();
    // We move to the first
    cursor.moveToFirst();
    String s_firstName = cursor.getString(cursor.getColumnIndexOrThrow(TableAccounts.COLUMN_FIRST_NAME));
    String s_lastName = cursor.getString(cursor.getColumnIndexOrThrow(TableAccounts.COLUMN_LAST_NAME));
    assertTrue(s_firstName.compareTo("Hillary")==0);
    assertTrue(s_lastName.compareTo("Clinton")==0);

  }

  @Test
  public void testGetAllUSerContentAlt() throws Exception{
    // We add 3 content class objects
    dbHelper.insertContent(new ContractContent(5,"Australia","Sydney","What a lovely day"));
    dbHelper.insertContent(new ContractContent(2,"Russia","Moscow","The red phone is silent"));
    dbHelper.insertContent(new ContractContent(1,"North Korea","Pyongyang","Great leader is best leader"));
    // We recover the cursor
    Cursor cursor = dbHelper.getAllContentAlt();
    // move to the first element
    cursor.moveToFirst();
    int i_userId = cursor.getInt(cursor.getColumnIndexOrThrow(TableContent.COLUMN_USER_ID));
    String s_country = cursor.getString(cursor.getColumnIndexOrThrow(TableContent.COLUMN_COUNTRY));
    String s_cities = cursor.getString(cursor.getColumnIndexOrThrow(TableContent.COLUMN_CITIES));
    String s_desc = cursor.getString(cursor.getColumnIndexOrThrow(TableContent.COLUMN_DESCR));

    assertTrue(i_userId == 5);
    assertTrue(s_country.compareTo("Australia")==0);
    assertTrue(s_cities.compareTo("Sydney")==0);
    assertTrue(s_desc.compareTo("What a lovely day")==0);


  }
}
