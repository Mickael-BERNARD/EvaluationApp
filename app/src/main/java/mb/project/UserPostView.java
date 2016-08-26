package mb.project;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import mb.project.Database.ContractAccount;
import mb.project.Database.ContractContent;
import mb.project.Database.DBHelper;
import mb.project.ProfileView.UserProfileView;

public class UserPostView extends AppCompatActivity {

  DBHelper database = new DBHelper(this);

  int userId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_post_view);

    // Recover intent
    Intent intent = getIntent();
    int position = intent.getIntExtra("position",0);
    Log.d("UserPostView","got position:"+position); // The row id of of the element in the COntent table

    // Recover the row
    //TODO: Try to recover row. If it doesn't exist anymore put up a pop up message that will lead to resuming the previous activity.
    ContractContent contract = database.getContentByRow(position);

    // Make a link to the necessary components
    TextView country = (TextView) findViewById(R.id.upv_country);
    TextView cities = (TextView) findViewById(R.id.upv_cities);
    TextView description = (TextView) findViewById(R.id.upv_description);
    TextView userName = (TextView) findViewById(R.id.upv_username);

    userId = contract.getUserId();
    ContractAccount account = database.getUser(userId);
    String firstName = account.getFirstName();
    String lastName = account.getLastName();


    country.setText(contract.getCountry());
    cities.setText(contract.getCities());
    description.setText(contract.getDescription());
    userName.setPaintFlags(userName.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
    userName.setText(firstName+" "+lastName);


  }

  public void handleOnClickName(View view){
    // Start the activity UserProfile View
    Intent intent = new Intent(this, UserProfileView.class);
    intent.putExtra("userId",userId);
    startActivity(intent);
  }
}