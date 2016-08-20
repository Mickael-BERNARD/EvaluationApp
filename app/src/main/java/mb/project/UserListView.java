package mb.project;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import mb.project.Database.DBHelper;
import mb.project.Database.TableAccounts;

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
    String[] fromColumns = new String[]{ TableAccounts.COLUMN_FIRST_NAME,TableAccounts.COLUMN_LAST_NAME};
    // The XML defined views which the data will be bound to
    int[] to = new int[]{R.id.p_first_name,R.id.p_last_name};
    dataAdapter = new ListViewAdapter(this, cursor,0);

    ListView listView = (ListView)findViewById(R.id.listUsers);
    listView.setAdapter(dataAdapter);
    dbHelper.close();

  }

  public void handleOnCLick(View view){
    int position = (int)  view.getTag();
    Log.d("handleOnCLick","got position:"+position);
    Intent intent = new Intent(this, UserWall.class);
    intent.putExtra("position",position);
    startActivity(intent);
  }




}




