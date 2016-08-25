package mb.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import mb.project.Database.ContractAccount;
import mb.project.Database.ContractContent;
import mb.project.Database.DBHelper;

public class UserPostView extends AppCompatActivity {

  DBHelper database = new DBHelper(this);


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

    int userId = contract.getUserId();
    ContractAccount account = database.getUser(userId);
    String firstName = account.getFirstName();
    String lastName = account.getLastName();


    country.setText(contract.getCountry());
    cities.setText(contract.getCities());
    description.setText(contract.getDescription());
    userName.setText(firstName+" "+lastName);



  }
}
