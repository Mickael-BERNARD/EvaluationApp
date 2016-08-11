package mb.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Mickaël BERNARD on 07-Aug-16.
 */



// Here we implement methods that create and maintain the database and tables using CRUD operations..

public class DBHelper extends SQLiteOpenHelper {

  // If the database schema is changed (by adding, editing ... ) we must
  // increment the database version.
  private static final String TEXT_TYPE = " VARCHAR";
 // private static final String PRIMARY_KEY = " PRIMARY_KEY";
  private static final String COMMA_SEP = " , ";
  private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + DBContract.DBEntry.TABLE_NAME + " (" + DBContract.DBEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + DBContract.DBEntry.COLUMN_FIRST_NAME + TEXT_TYPE  + COMMA_SEP + DBContract.DBEntry.COLUMN_LAST_NAME  +TEXT_TYPE + " )";
  private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.DBEntry.TABLE_NAME;


  public static final int DATABASE_VERSION =  1;
  public static final String DATABASE_NAME = "FeedReader.db";


  public DBHelper(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }


  /**
   * This method must be called when the database is created for the first time.
   * This is where the creation of the table happens.
   * @param db
     */
  public void onCreate(SQLiteDatabase db){
    db.execSQL(SQL_CREATE_ENTRIES);
  }

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

  //==========================================================================

  // INSERT INTO RECORDS

  // We have set UserInputContract class as an argument such that we can get the values
  // of First Name and Last Name from that model.
  public void insertRecord(UserInputContract contract){
    // To insert the record we get a SQLiteDatabase object by calling getReadableDatabase()
    SQLiteDatabase database = this.getReadableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(DBContract.DBEntry.COLUMN_FIRST_NAME, contract.getFirstName());
    contentValues.put(DBContract.DBEntry.COLUMN_LAST_NAME, contract.getLastName());
    // Insert Row -> returns the row of the newly inserted row or -1 if an error occurred
    database.insert(DBContract.DBEntry.TABLE_NAME, null, contentValues);
    //For safe coding, it is better to close the database once we are done with our operation. -
    database.close();
  }

  public void insertRecordAlternative(UserInputContract contract){
    SQLiteDatabase database = this.getReadableDatabase();
    // This method will execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data.
    database.execSQL("INSERT INTO "+ DBContract.DBEntry.TABLE_NAME + "(" + DBContract.DBEntry.COLUMN_FIRST_NAME + "," + DBContract.DBEntry.COLUMN_LAST_NAME  + ") VALUES ('"+ contract.getFirstName() + "','" + contract.getLastName() + "')");
    database.close();
  }

  // UPDATE RECORDS (or rows)

  // V.1:
  public void updateRecord(UserInputContract contract){
    SQLiteDatabase database = this.getReadableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(DBContract.DBEntry.COLUMN_FIRST_NAME, contract.getFirstName());
    contentValues.put(DBContract.DBEntry.COLUMN_LAST_NAME, contract.getLastName());
    // returns the number of rows affected -> those affected are the rows with COLUMN_ID = contract.getID
    database.update(DBContract.DBEntry.TABLE_NAME, contentValues , DBContract.DBEntry.COLUMN_ID + " = ? ", new String[]{contract.getID()});
    database.close();
  }

  // V.2:
  public void updateRecordAlt(UserInputContract contract){
    SQLiteDatabase database = this.getReadableDatabase();
    database.execSQL("update "+ DBContract.DBEntry.TABLE_NAME + " set " + DBContract.DBEntry.COLUMN_FIRST_NAME + " = '" + contract.getFirstName() + "', "+ DBContract.DBEntry.COLUMN_LAST_NAME + " = '" + contract.getLastName() + "' where " + DBContract.DBEntry.COLUMN_ID +  " = '" + contract.getID() + "'");
  }

  // DELETE records

  public void deleteRecord( UserInputContract contract){
    SQLiteDatabase database = this.getReadableDatabase();
    // Returns the number of rows affected.
    database.delete(DBContract.DBEntry.TABLE_NAME, DBContract.DBEntry.COLUMN_ID + " = ?", new String[]{contract.getID()});
    database.close();
  }

  // TODO : Make a remove all method (dangerous)

  // SELECTING RECORDS

  /**
   *
   * @return an arrayList containing all the data in the DB in the form of
   * UserInputContract class objects
     */
  public ArrayList<UserInputContract> getAllRecords(){
    SQLiteDatabase database = this.getReadableDatabase();
    // We are quering the database and fetching the data in the cursor.
    Cursor cursor = database.query(DBContract.DBEntry.TABLE_NAME, null , null ,null , null , null, null ,null);
    ArrayList<UserInputContract> contracts = new ArrayList<UserInputContract>();
      UserInputContract model;
    // We check if we have any values in the Cursor.
    if (cursor.getCount() > 0){
      for (int i = 0 ; i < cursor.getCount(); i++){
        // The initial position of the cursor is at -1.
        // For a loop we use the moveToNext method instead of the moveToFirst method.
        cursor.moveToNext();
        model = new UserInputContract();
        // Retrieve values in each column.
        model.setID(cursor.getString(0));
        model.setFirstName(cursor.getString(1));
        model.setLastName(cursor.getString(2));
        contracts.add(model);
      }
    }
    cursor.close();
    database.close();
    return contracts; // The arrayList
  }

  /**
   * Same as before except with a Cursor declaration in SQL.
   * @return
     */
  public ArrayList<UserInputContract> getAllRecordsAlt(){
    SQLiteDatabase database = this.getReadableDatabase();
    // public Cursor rawQuery (String sql, String[] selectionArgs)  -> returns a Cursor.
    Cursor cursor = database.rawQuery("SELECT * FROM "+ DBContract.DBEntry.TABLE_NAME, null);
    ArrayList<UserInputContract> contractsArray = new ArrayList<UserInputContract>();
      UserInputContract contract;
      if (cursor.getCount()>0){
        for (int i =0 ; i< cursor.getCount(); i++){
          cursor.moveToNext();
          contract = new UserInputContract();
          contract.setID(cursor.getString(0));
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
