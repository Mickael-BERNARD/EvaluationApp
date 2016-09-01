package mb.project.Database;

import android.content.ContentValues;
import android.content.Context;
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


  private static final String TEXT_TYPE = " VARCHAR";
  private static final String COMMA_SEP = " , ";
  private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TableAccounts.TABLE_NAME;

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
    // SQL statement to create the tables
    String SQL_CREATE_ENTRIES_ACCOUNT = "CREATE TABLE " + TableAccounts.TABLE_NAME + " (" + TableAccounts.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TableAccounts.COLUMN_FIRST_NAME + TEXT_TYPE  + COMMA_SEP + TableAccounts.COLUMN_LAST_NAME  +TEXT_TYPE + COMMA_SEP + TableAccounts.COLUMN_EMAIL + TEXT_TYPE + COMMA_SEP + TableAccounts.COLUMN_TEL + TEXT_TYPE + " )";
    String SQL_CREATE_ENTRIES_CONTENT = "CREATE TABLE " + TableContent.TABLE_NAME + " (" + TableContent.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TableContent.COLUMN_USER_ID + " INTEGER ,"+ TableContent.COLUMN_COUNTRY + TEXT_TYPE  + COMMA_SEP  + TableContent.COLUMN_CITIES + TEXT_TYPE  + COMMA_SEP + TableContent.COLUMN_DESCR +TEXT_TYPE + COMMA_SEP + TableContent.COLUMN_POF + TEXT_TYPE + COMMA_SEP + TableContent.COLUMN_ACCOMMODATIONS + TEXT_TYPE + COMMA_SEP + TableContent.COLUMN_TRANSPORT+ TEXT_TYPE + COMMA_SEP + TableContent.COLUMN_BUSINESS + TEXT_TYPE + COMMA_SEP + TableContent.COLUMN_EDUCATION + TEXT_TYPE +" )";


    // Create the tables
    db.execSQL(SQL_CREATE_ENTRIES_ACCOUNT);
    db.execSQL(SQL_CREATE_ENTRIES_CONTENT);
  }

  //2. INSERTION
  public long insertUser(ContractAccount contract){
    Log.d("insertUser",contract.toString());
    // Get reference to a writable DB
    SQLiteDatabase database = this.getWritableDatabase();
    // Create ContentValues to associate each column to their value.
    ContentValues contentValues = new ContentValues();
    contentValues.put(TableAccounts.COLUMN_FIRST_NAME, contract.getFirstName());
    contentValues.put(TableAccounts.COLUMN_LAST_NAME, contract.getLastName());
    contentValues.put(TableAccounts.COLUMN_EMAIL, contract.getEmail());
    contentValues.put(TableAccounts.COLUMN_TEL, contract.getTel());
    // Insert Row (-> this method returns the row of the newly inserted row or -1 if an error occurred)
    long index = database.insert(TableAccounts.TABLE_NAME, null, contentValues);
    //For safe coding, it is better to close the database once we are done with our operation. -
    database.close();
    return index;
  }

  public long insertContent(ContractContent contract){
    Log.d("insertContent",contract.toString());
    // Get reference to a writable DB
    SQLiteDatabase database = this.getWritableDatabase();
    // Create ContentValues to associate each column to their value.
    ContentValues contentValues = new ContentValues();
    contentValues.put(TableContent.COLUMN_USER_ID, contract.getUserId());
    contentValues.put(TableContent.COLUMN_COUNTRY, contract.getCountry());
    contentValues.put(TableContent.COLUMN_CITIES, contract.getCities());
    contentValues.put(TableContent.COLUMN_DESCR, contract.getDescription());
    contentValues.put(TableContent.COLUMN_POF, contract.getPlacesOfInterest());
    contentValues.put(TableContent.COLUMN_ACCOMMODATIONS, contract.getAccommodations());
    contentValues.put(TableContent.COLUMN_TRANSPORT, contract.getTransport());
    contentValues.put(TableContent.COLUMN_BUSINESS, contract.getBusiness());
    contentValues.put(TableContent.COLUMN_EDUCATION, contract.getEducation());


    // Insert Row (-> this method returns the row of the newly inserted row or -1 if an error occurred)
    long index = database.insert(TableContent.TABLE_NAME, null, contentValues);
    //For safe coding, it is better to close the database once we are done with our operation. -
    database.close();
    return index;
  }



  public void insertRecordAlternative(ContractAccount contract){
    SQLiteDatabase database = this.getReadableDatabase();
    // This method will execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data.
    database.execSQL("INSERT INTO "+ TableAccounts.TABLE_NAME + "(" + TableAccounts.COLUMN_FIRST_NAME + "," + TableAccounts.COLUMN_LAST_NAME  + ") VALUES ('"+ contract.getFirstName() + "','" + contract.getLastName() + "')");
    database.close();
  }


  // UPDATE RECORDS

  // V.1:
  // This update method uses the id in the table of the stored row to update it!
  public void updateUser(ContractAccount contract){
    // Get a reference to the writable DB
    SQLiteDatabase database = this.getReadableDatabase();
    // Create contentValues
    ContentValues contentValues = new ContentValues();
    contentValues.put(TableAccounts.COLUMN_FIRST_NAME, contract.getFirstName());
    contentValues.put(TableAccounts.COLUMN_LAST_NAME, contract.getLastName());
    contentValues.put(TableAccounts.COLUMN_EMAIL,contract.getEmail());
    contentValues.put(TableAccounts.COLUMN_TEL, contract.getTel());

    // returns the number of rows affected -> those affected are the rows with COLUMN_ID = contract.getID
    database.update(TableAccounts.TABLE_NAME, // Table
      contentValues ,
      TableAccounts.COLUMN_ID + " = ? ",
      new String[]{String.valueOf(contract.getID())});
    database.close();
  }

  public void updateContent(ContractContent contract){
    // Get a reference to the writable DB
    SQLiteDatabase database = this.getReadableDatabase();
    // Create contentValues
    ContentValues contentValues = new ContentValues();
    contentValues.put(TableContent.COLUMN_USER_ID, contract.getUserId());
    contentValues.put(TableContent.COLUMN_COUNTRY, contract.getCountry());
    contentValues.put(TableContent.COLUMN_CITIES, contract.getCities());
    contentValues.put(TableContent.COLUMN_DESCR, contract.getDescription());
    contentValues.put(TableContent.COLUMN_POF, contract.getPlacesOfInterest());
    contentValues.put(TableContent.COLUMN_ACCOMMODATIONS, contract.getAccommodations());
    contentValues.put(TableContent.COLUMN_TRANSPORT, contract.getTransport());
    contentValues.put(TableContent.COLUMN_BUSINESS, contract.getBusiness());
    contentValues.put(TableContent.COLUMN_EDUCATION, contract.getEducation());

    // returns the number of rows affected -> those affected are the rows with COLUMN_ID = contract.getID
    database.update(TableContent.TABLE_NAME, // Table
      contentValues ,
      TableContent.COLUMN_ID + " = ? ",
      new String[]{String.valueOf(contract.getID())});
    database.close();
  }

  public void updateUserAlt(ContractAccount contract){
    SQLiteDatabase database = this.getReadableDatabase();
    database.execSQL("update "+ TableAccounts.TABLE_NAME + " set " + TableAccounts.COLUMN_FIRST_NAME + " = '" + contract.getFirstName() + "', "+ TableAccounts.COLUMN_LAST_NAME + " = '" + contract.getLastName() + "' where " + TableAccounts.COLUMN_ID +  " = '" + contract.getID() + "'");
  }


  // DELETE RECORDS
// Uses the ID to delete!
  public long deleteUserAccount( ContractAccount contract){
    // Get reference to writable DB
    SQLiteDatabase database = this.getReadableDatabase();
    // Delete: Returns the number of rows affected.
    long value = database.delete(TableAccounts.TABLE_NAME, TableAccounts.COLUMN_ID + " = ?", new String[]{String.valueOf(contract.getID())});
    database.close();
    Log.d("deleteUserAccount", contract.toString());
    return value;
  }

  public long deleteUserContent( ContractContent contract){
    // Get reference to writable DB
    SQLiteDatabase database = this.getReadableDatabase();
    // Delete: Returns the number of rows affected.
    long value = database.delete(TableContent.TABLE_NAME, TableContent.COLUMN_ID + " = ?", new String[]{String.valueOf(contract.getID())});
    database.close();
    Log.d("deleteUserContent", contract.toString());
    return value;
  }


  public void eraseAllUsers(){
    // Get reference to writable DB
    SQLiteDatabase database = this.getReadableDatabase();
    database.execSQL("delete from "+ TableAccounts.TABLE_NAME);
    database.close();
  }

  public void eraseAllUserContent(){
    // Get reference to writable DB
    SQLiteDatabase database = this.getReadableDatabase();
    database.execSQL("delete from "+ TableContent.TABLE_NAME);
    database.close();
  }


  // SELECTING RECORDS

  /**
   * Selecting ONE row of the database (using it's id)
   */

  public ContractAccount getUser(int id){
    // Get a reference to the readable DB
    SQLiteDatabase db = this.getReadableDatabase();
    // Build query
    Cursor cursor = db.query(TableAccounts.TABLE_NAME, // table
      TableAccounts.COLUMNS, // column names
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
      ContractAccount user = new ContractAccount();
      user.setID(Integer.parseInt(cursor.getString(0)));
      user.setFirstName(cursor.getString(1));
      user.setLastName(cursor.getString(2));
      user.setEmail(cursor.getString(3));
    user.setTel(cursor.getString(4));
      // Log
      Log.d("getUser(" + id + ")", user.toString());
      return user;
  }



public ContractContent getContentByRow(int id){
  // Get a reference to the readable DB
  SQLiteDatabase db = this.getReadableDatabase();
  // Build query
  Cursor cursor = db.query(TableContent.TABLE_NAME, // table
    TableContent.COLUMNS, // column names
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
  ContractContent content = new ContractContent();
  content.setID(Integer.parseInt(cursor.getString(0)));
  content.setUserId(Integer.parseInt(cursor.getString(1)));
  content.setCountry(cursor.getString(2));
  content.setCity(cursor.getString(3));
  content.setDescription(cursor.getString(4));
  content.setPlacesOfInterest(cursor.getString(5));
  content.setAccommodations(cursor.getString(6));
  content.setTransport(cursor.getString(7));
  content.setBusiness(cursor.getString(8));
  content.setEducation(cursor.getString(9));
  // Log
  Log.d("getContentByRow(" + id + ")", content.toString());
  return content;
}

  public Cursor getContentByRowAlt(int id){
    // Get a reference to the readable DB
    SQLiteDatabase db = this.getReadableDatabase();
    // Build query
    Cursor cursor = db.query(TableContent.TABLE_NAME, // table
      TableContent.COLUMNS, // column names
      " _id=?", // selections
      new String[]{String.valueOf(id)}, // selection args
      null, // group by
      null, // having
      null, // order by
      null);  // limit

    // If results are found:
    if (cursor != null) {
      cursor.moveToFirst();}
    return cursor;
  }



  public Cursor getContentByUserAlt(int id){
    // Get a reference to the readable DB
    SQLiteDatabase db = this.getReadableDatabase();
    // Build query
    Cursor cursor = db.query(TableContent.TABLE_NAME, // table
      TableContent.COLUMNS, // column names
      ""+TableContent.COLUMN_USER_ID+"=?", // selections
      new String[]{String.valueOf(id)}, // selection args
      null, // group by
      null, // having
      null, // order by
      null);  // limit

    // If results are found:
    if (cursor != null) {
      cursor.moveToFirst();}
    return cursor;
  }
  /**
   * Selecting ALL the data of the database
   * @return an arrayList containing all the data in the DB in the form of
   * UserInputContract class objects
     */

  public ArrayList<ContractAccount> getAllUsers(){
    ArrayList<ContractAccount> contracts = new ArrayList<ContractAccount>();

    SQLiteDatabase database = this.getReadableDatabase();
    // We are quering the database and fetching the data in the cursor.
    Cursor cursor = database.query(TableAccounts.TABLE_NAME, null , null ,null , null , null, null ,null);
      ContractAccount model;
    // We check if we have any values in the Cursor.
    if (cursor.getCount() > 0){
      for (int i = 0 ; i < cursor.getCount(); i++){
        // The initial position of the cursor is at -1.
        // For a loop we use the moveToNext method instead of the moveToFirst method.
        cursor.moveToNext();
        model = new ContractAccount();
        // Retrieve values in each column.
        model.setID(Integer.parseInt(cursor.getString(0)));
        model.setFirstName(cursor.getString(1));
        model.setLastName(cursor.getString(2));
        model.setEmail(cursor.getString(3));
        model.setTel(cursor.getString(4));
        contracts.add(model);
      }
    }
    cursor.close();
    database.close();
    Log.d("getAllUsers()",contracts.toString());
    return contracts; // The arrayList
  }

  public ArrayList<ContractContent> getAllUserContent(){
    ArrayList<ContractContent> contracts = new ArrayList<>();

    SQLiteDatabase database = this.getReadableDatabase();
    // We are quering the database and fetching the data in the cursor.
    Cursor cursor = database.query(TableContent.TABLE_NAME, null,null,null,null,null,null,null);
    ContractContent model;
    // We check if we have any values in the Cursor.
    if (cursor.getCount() >0){
      for (int i = 0; i < cursor.getCount(); i++){
        // The initial position of the cursor is at -1.
        // For a loop we use the moveToNext method instead of the moveToFirst method.
        cursor.moveToNext();
        model = new ContractContent();
        // retrieve values in each column
        model.setID(Integer.parseInt(cursor.getString(0)));
        model.setUserId(Integer.parseInt(cursor.getString(1)));
        model.setCountry(cursor.getString(2));
        model.setCity(cursor.getString(3));
        model.setDescription(cursor.getString(4));
        model.setPlacesOfInterest(cursor.getString(5));
        model.setAccommodations(cursor.getString(6));
        model.setTransport(cursor.getString(7));
        model.setBusiness(cursor.getString(8));
        model.setEducation(cursor.getString(9));
        contracts.add(model);
      }
    }
    cursor.close();
    database.close();
    Log.d("getAllUserContent",contracts.toString());
    return contracts; // the arrayList


  }

  public Cursor getAllUsersAlt(){
    SQLiteDatabase database = this.getReadableDatabase();
    String[] projections = {"_id", TableAccounts.COLUMN_FIRST_NAME, TableAccounts.COLUMN_LAST_NAME , TableAccounts.COLUMN_EMAIL , TableAccounts.COLUMN_TEL};
    Cursor cursor = database.query(TableAccounts.TABLE_NAME,projections,null,null,null,null,null);

    Log.d("getAllUsersAlt()","");
    return cursor;
}


  public Cursor getAllContentAlt(){
    SQLiteDatabase database = this.getReadableDatabase();
    String[] projections = {"_id",TableContent.COLUMN_USER_ID,TableContent.COLUMN_COUNTRY,TableContent.COLUMN_CITIES,TableContent.COLUMN_DESCR};
    Cursor cursor = database.query(TableContent.TABLE_NAME, projections, null,null,null,null,null);
    Log.d("getAllContentAlt()","");
    return cursor;
  }

  /**
   * Same as before except with a Cursor declaration in SQL.
   * @return
     */
  public ArrayList<ContractAccount> getAllRecordsAlt(){
    ArrayList<ContractAccount> contractsArray = new ArrayList<ContractAccount>();
    SQLiteDatabase database = this.getReadableDatabase();
    // public Cursor rawQuery (String sql, String[] selectionArgs)  -> returns a Cursor.
    // Get reference to writable DB
    Cursor cursor = database.rawQuery("SELECT * FROM "+ TableAccounts.TABLE_NAME, null);

      ContractAccount contract;
      if (cursor.getCount()>0){
        for (int i =0 ; i< cursor.getCount(); i++){
          cursor.moveToNext();
          contract = new ContractAccount();
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
