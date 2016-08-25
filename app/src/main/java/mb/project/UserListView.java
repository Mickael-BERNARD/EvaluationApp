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

// TODO: Refresh UserListView content upon onResume call.
/**
 * Created by Mickaël BERNARD on 17-Aug-16.
 */
public class UserListView extends Activity {

  private DBHelper dbHelper;
  private UserListViewAdapter dataAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_list_view);
    dbHelper = new DBHelper(this);
    //<-OK->
    displayList();
  }

  public void displayList() {
    Cursor cursor = dbHelper.getAllContentAlt();
    dataAdapter = new UserListViewAdapter(this, cursor, dbHelper);
    ListView listView = (ListView)findViewById(R.id.listUsers);
    listView.setAdapter(dataAdapter);
    dbHelper.close();

  }

  public void handleOnCLick(View view){
    int position = (int)  view.getTag();
    Log.d("handleOnCLick","got position:"+position);
    Intent intent = new Intent(this, UserPostView.class);
    intent.putExtra("position",position);
    startActivity(intent);
  }




}




