package mb.project;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import mb.project.Database.DBHelper;
import mb.project.Database.TableAccounts;
import mb.project.ProfileEdit.UserProfileEdit;

// TODO: Refresh UserListView content upon onResume call.
/**
 * Created by Mickaël BERNARD on 17-Aug-16.
 */
public class UserListView extends AppCompatActivity {

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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_user_list_view, menu);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    switch (id){
      case R.id.action_user_profile_edit:
        Log.d("Appbar","Selected User list");
        // Go to the user list.
        Intent intent = new Intent(this, UserProfileEdit.class);
        startActivity(intent);
        break;

      case R.id.action_settings:
        Log.d("Appbar","Selected Settings");
        break;

      default:
        Log.d("Appbar","Another option was selected");
        break;
    }

    return super.onOptionsItemSelected(item);
  }




}




