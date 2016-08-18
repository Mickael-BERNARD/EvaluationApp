package mb.project;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Mickaël BERNARD on 17-Aug-16.
 */
public class UserListView extends Activity {
  private DBHelper dbHelper;
  private ListViewAdapter dataAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_list_view);
    dbHelper = new DBHelper(this);
    //<-OK->
    displayList();
  }

  public void displayList() {
    Cursor cursor = dbHelper.getAllUsersAlt();
    // Select the desired columns to be bound
    String[] fromColumns = new String[]{ DBHelper.COLUMN_FIRST_NAME,DBHelper.COLUMN_LAST_NAME};
    // The XML defined views which the data will be bound to
    int[] to = new int[]{R.id.p_first_name,R.id.p_last_name};
    dataAdapter = new ListViewAdapter(this, cursor,0);

    ListView listView = (ListView)findViewById(R.id.listUsers);
    listView.setAdapter(dataAdapter);
    dbHelper.close();

  }


}




