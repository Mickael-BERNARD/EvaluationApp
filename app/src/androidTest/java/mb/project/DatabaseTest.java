package mb.project;

import android.test.AndroidTestCase;

import org.junit.Test;

/**
 * Created by Mickaël BERNARD on 14-Aug-16.
 */

public class DatabaseTest extends AndroidTestCase {

  @Test
    public void databaseCreationTest(){
    DBHelper dbHelper = new DBHelper(getContext());

    dbHelper.insertUser(new UserInputContract("Hillary", "Clinton"));

  }

}
