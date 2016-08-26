package mb.project.ProfileView;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import mb.project.Database.ContractAccount;
import mb.project.Database.DBHelper;
import mb.project.R;
import mb.project.UserPostView;

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
}
