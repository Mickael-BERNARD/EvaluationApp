package mb.project.ProfileView;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import mb.project.Database.ContractAccount;
import mb.project.Database.DBHelper;
import mb.project.ProfileEdit.UserProfileEdit;
import mb.project.R;
import mb.project.UserList.UserListView;
import mb.project.PostView.UserPostView;

public class UserProfileView extends AppCompatActivity {

  int userId;
  DBHelper database = new DBHelper(this);
  Cursor cursor;
  PostListAdapter dataAdapter;

  TextView firstName;
  TextView lastName;
  TextView email;
  TextView telephone;
  ListView postList;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile_view);

    Intent intent = getIntent();
    userId = intent.getIntExtra("userId",0);

    // Initialize the database

    // Inflate components
    ContractAccount account = database.getUser(userId);
    firstName = (TextView) findViewById(R.id.uprv_first_name);
    lastName = (TextView) findViewById(R.id.uprv_last_name);
    email = (TextView) findViewById(R.id.uprv_email);
    telephone = (TextView) findViewById(R.id.uprv_tel);


    firstName.setText(account.getFirstName());
    lastName.setText(account.getLastName());
    email.setText(account.getEmail());
    telephone.setText(account.getTel());
    displayPostList();
  }


  public void displayPostList(){
    database = new DBHelper(this);
    cursor = database.getContentByUserAlt(userId);
    dataAdapter = new PostListAdapter(this, cursor,0);
    postList = (ListView)findViewById(R.id.uprv_listView);
    postList.setAdapter(dataAdapter);
    database.close();
  }


  public void handleOnPostClick(View view){
    int position = (int) view.getTag();
    Intent intent = new Intent(this, UserPostView.class);
    intent.putExtra("position",position);
    startActivity(intent);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_return_to_profile_edit_or_posts, menu);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    Intent intent;
    switch (id){
      case R.id.action_user_profile_edit:
        Log.d("Appbar","Selected User list");
        // Go to the user list.
        intent = new Intent(this, UserProfileEdit.class);
        startActivity(intent);
        break;
      case R.id.action_user_list:
        Log.d("Appbar","Selected User list");
        // Go to the user list.
        intent = new Intent(this, UserListView.class);
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
