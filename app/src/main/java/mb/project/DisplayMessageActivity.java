package mb.project;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

  /**
   * On creation:
   * o Add to the database a new entry (TODO: Activate action with the use of a button)
   * o Change test variable value as a reaction to this.
   * o Display variable.
   * @param savedInstanceState
     */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_message);

    //Every Activity is invoked by an Intent,
    // regardless of how the user navigated there.
    Intent intent = getIntent();
    //The call getStringExtra() retrieves the data from the first activity.
    String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
    // Make 2 text views:
    TextView textView = new TextView(this);
    TextView testVariable = new TextView(this);

    textView.setTextSize(40);
    textView.setText(message);


    ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
    layout.addView(textView);



    // Creating database and table

    // Working with the DataBase : INSERT NEW ELEMENTS
    //To access your database, instantiate your subclass of SQLiteOpenHelper:
    DBHelper mDbHelper = new DBHelper(getApplicationContext());
    //// Gets the data repository in write mode
    SQLiteDatabase db = mDbHelper.getWritableDatabase();



    //[!!]
    // Create a new [map of values], where column names are keys
    ContentValues values = new ContentValues();
    values.put(DBHelper.COLUMN_FIRST_NAME,"Thomas");
    values.put(DBHelper.COLUMN_LAST_NAME,"Edison");

    // Insert the new row, returning the primary key value of the new row
    long newRowId;
    // Arguments are: table name,
    // name of a column in which the framework can insert NULL in the event that contentValues is empty
    newRowId = db.insert(
      DBHelper.TABLE_NAME,
      null, values);



    /// READING FROM DATABASE:

    // Define a projection that specifies which columns from the database.
    // you will actually use after this query

    String[] projection = {
      DBHelper.COLUMN_ID,
      DBHelper.COLUMN_FIRST_NAME,
      DBHelper.COLUMN_LAST_NAME
    };

    // We can control how we want to sort the results in the resulting Cursor.
    // Here we want to sort the surnames descending.
    String sortOrder = DBHelper.COLUMN_FIRST_NAME + "DESC";

    /*
    Cursor cursor = db.query(DBContract.DBEntry.TABLE_NAME, // The table to query
      projection, // The columns to return
      DBContract.DBEntry.COLUMN_FIRST_NAME, // The columns for the WHERE clause
      selectionArgs, // The values for the WHERE clause
      null, //Don't group the rows
      null, //Don't filter by row groups
      sortOrder //The sort order
      );

    // To look at a row in the cursor, we use one of the Cursor move methods,
    // which must always be called before we begin to read the values.

    // We start by calling moveToFirst() which places the "read position" on the first entry in the results.
    cursor.moveToFirst();
    long itemId = cursor.getLong(
      cursor.getColumnIndexOrThrow(DBContract.DBEntry._ID)
    );*/




  }


}
