package mb.project;

import android.provider.BaseColumns;

/**
 * Created by Mickaël BERNARD on 06-Aug-16.
 */

//A contract class is a container for constants that define names for URIs, tables, and columns.
// We define here a Contract and Schema

public class DBContract {
  // To prevent someone from accidentally instantiating the contract class,
  // Empty constructor (for now)
  public DBContract() {}







  /*Inner class that defines the table's contents*/
  public static abstract class DBEntry implements BaseColumns{
    // Labels table name

    public static final String TABLE_NAME = "USER_LIST";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
   }
}

