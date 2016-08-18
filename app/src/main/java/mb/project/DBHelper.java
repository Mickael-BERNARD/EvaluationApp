package mb.project;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Mickaël BERNARD on 07-Aug-16.
 */

/**
 * This class will help us to create the database "Feed.db" with one table "USER_LIST"
 */

// Here we implement methods that create and maintain the database and tables using CRUD operations..

public class DBHelper extends SQLiteOpenHelper {

  // If the database schema is changed (by adding, editing ... ) we must
  // increment the database version.
  // Database Version
  public static final int DATABASE_VERSION =  1;
  // Database Name
  public static final String DATABASE_NAME = "Feed.sqlite";

  public DBHelper(Context context)
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);

  }


  // TABLE MANIPULATION
    // TABLE CONTRACT:
      // User Table name
      public static final String TABLE_NAME = "USER_LIST";
      // User Table Columns names
      public static final String COLUMN_ID = "_id";
      public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
      public static final String COLUMN_LAST_NAME = "LAST_NAME";

    private static final String[] COLUMNS = {COLUMN_ID,COLUMN_FIRST_NAME,COLUMN_LAST_NAME};


  private static final String TEXT_TYPE = " VARCHAR";
  private static final String COMMA_SEP = " , ";
  private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

  /**
   * This method must be called when the database needs to be upgraded (when the Database version changes)
   * @param db
   * @param oldVersion
   * @param newVersion
   */
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    // This database is only a cache for online data, so its upgrade policy is
    // to simply to discard the data and start over
    db.execSQL(SQL_DELETE_ENTRIES);
    onCreate(db);
  }
  // Not required
  public void onDowngrade(SQLiteDatabase db, int oldVersion , int newVersion){
    onUpgrade(db, oldVersion,newVersion);
  }


  /**
   * This method must be called when the database is created for the first time.
   * This is where the creation of the table happens.
   * @param db
   */

  //1: CREATION
  public void onCreate(SQLiteDatabase db){
    // SQL statement to create the table
    String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FIRST_NAME + TEXT_TYPE  + COMMA_SEP + COLUMN_LAST_NAME  +TEXT_TYPE + " )";
    // Create the table
    db.execSQL(SQL_CREATE_ENTRIES);
  }

  //2. INSERTION
  public long insertUser(UserInputContract contract){
    Log.d("addUser",contract.toString());

    // Get reference to a writable DB
    SQLiteDatabase database = this.getWritableDatabase();
    // Create ContentValues to associate each column to their value.
    ContentValues contentValues = new ContentValues();
    contentValues.put(COLUMN_FIRST_NAME, contract.getFirstName());
    contentValues.put(COLUMN_LAST_NAME, contract.getLastName());
    // Insert Row (-> this method returns the row of the newly inserted row or -1 if an error occurred)
    long index = database.insert(TABLE_NAME, null, contentValues);
    //For safe coding, it is better to close the database once we are done with our operation. -
    database.close();
    return index;
  }

  public void insertRecordAlternative(UserInputContract contract){
    SQLiteDatabase database = this.getReadableDatabase();
    // This method will execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data.
    database.execSQL("INSERT INTO "+ TABLE_NAME + "(" + COLUMN_FIRST_NAME + "," + COLUMN_LAST_NAME  + ") VALUES ('"+ contract.getFirstName() + "','" + contract.getLastName() + "')");
    database.close();
  }


  // UPDATE RECORDS (or rows)

  // V.1:
  // This update method uses the id of the person!
  public void updateUser(UserInputContract contract){
    // Get a reference to the writable DB
    SQLiteDatabase database = this.getReadableDatabase();
    // Create contentValues
    ContentValues contentValues = new ContentValues();
    contentValues.put(COLUMN_FIRST_NAME, contract.getFirstName());
    contentValues.put(COLUMN_LAST_NAME, contract.getLastName());

    // returns the number of rows affected -> those affected are the rows with COLUMN_ID = contract.getID
    database.update(TABLE_NAME, // Table
      contentValues ,
      COLUMN_ID + " = ? ",
      new String[]{String.valueOf(contract.getID())});
    database.close();
  }

  public void updateRecordAlt(UserInputContract contract){
    SQLiteDatabase database = this.getReadableDatabase();
    database.execSQL("update "+ TABLE_NAME + " set " + COLUMN_FIRST_NAME + " = '" + contract.getFirstName() + "', "+ COLUMN_LAST_NAME + " = '" + contract.getLastName() + "' where " + COLUMN_ID +  " = '" + contract.getID() + "'");
  }

  // DELETE records
// Uses the ID to delete!
  public long deleteRecord( UserInputContract contract){
    // Get reference to writable DB
    SQLiteDatabase database = this.getReadableDatabase();
    // Delete: Returns the number of rows affected.
    long value = database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(contract.getID())});
    database.close();
    Log.d("deleteBook", contract.toString());
    return value;

  }



  public void eraseAllUsers(){
    // Get reference to writable DB
    SQLiteDatabase database = this.getReadableDatabase();
    database.execSQL("delete from "+ TABLE_NAME);
    database.close();
  }


  // SELECTING RECORDS


  /**
   * Selecting ONE row of the database (using it's id)
   */

  public UserInputContract getUser(int id){
    // Get a reference to the readable DB
    SQLiteDatabase db = this.getReadableDatabase();
    // Build query
    Cursor cursor = db.query(TABLE_NAME, // table
      COLUMNS, // column names
      " _id=?", // selections
      new String[]{String.valueOf(id)}, // selection args
      null, // group by
      null, // having
      null, // order by
      null);  // limit

    // If results are found:
    if (cursor != null) {
      cursor.moveToFirst();}
      // Build a Contract object
      UserInputContract user = new UserInputContract();
      user.setID(Integer.parseInt(cursor.getString(0)));
      user.setFirstName(cursor.getString(1));
      user.setLastName(cursor.getString(2));
      // Log
      Log.d("getUser(" + id + ")", user.toString());
      return user;
  }





  /**
   * Selecting ALL the data of the database
   * @return an arrayList containing all the data in the DB in the form of
   * UserInputContract class objects
     */

  public ArrayList<UserInputContract> getAllUsers(){
    ArrayList<UserInputContract> contracts = new ArrayList<UserInputContract>();

    SQLiteDatabase database = this.getReadableDatabase();
    // We are quering the database and fetching the data in the cursor.
    Cursor cursor = database.query(TABLE_NAME, null , null ,null , null , null, null ,null);
      UserInputContract model;
    // We check if we have any values in the Cursor.
    if (cursor.getCount() > 0){
      for (int i = 0 ; i < cursor.getCount(); i++){
        // The initial position of the cursor is at -1.
        // For a loop we use the moveToNext method instead of the moveToFirst method.
        cursor.moveToNext();
        model = new UserInputContract();
        // Retrieve values in each column.
        model.setID(Integer.parseInt(cursor.getString(0)));
        model.setFirstName(cursor.getString(1));
        model.setLastName(cursor.getString(2));
        contracts.add(model);
      }
    }
    cursor.close();
    database.close();
    Log.d("getAllUsers()",contracts.toString());
    return contracts; // The arrayList
  }

  public Cursor getAllUsersAlt(){
    SQLiteDatabase database = this.getReadableDatabase();
    String[] projections = {"_id", COLUMN_FIRST_NAME, COLUMN_LAST_NAME};
    Cursor cursor = database.query(TABLE_NAME,projections,null,null,null,null,null);

    Log.d("getAllUsersAlt()","");
    return cursor;

  }
  /**
   * Same as before except with a Cursor declaration in SQL.
   * @return
     */
  public ArrayList<UserInputContract> getAllRecordsAlt(){
    ArrayList<UserInputContract> contractsArray = new ArrayList<UserInputContract>();
    SQLiteDatabase database = this.getReadableDatabase();
    // public Cursor rawQuery (String sql, String[] selectionArgs)  -> returns a Cursor.
    // Get reference to writable DB
    Cursor cursor = database.rawQuery("SELECT * FROM "+ TABLE_NAME, null);

      UserInputContract contract;
      if (cursor.getCount()>0){
        for (int i =0 ; i< cursor.getCount(); i++){
          cursor.moveToNext();
          contract = new UserInputContract();
          contract.setID(Integer.parseInt(cursor.getString(0)));
          contract.setFirstName(cursor.getString(1));
          contract.setLastName(cursor.getString(2));
          contractsArray.add(contract);
        }
      }
    cursor.close();
    database.close();
    return contractsArray;
  }




}
