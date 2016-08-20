package mb.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import mb.project.Database.DBHelper;
import mb.project.Database.ContractAccount;

public class UserWall extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_wall);
    Intent intent = getIntent();
    int position = intent.getIntExtra("position",0);
    Log.d("UserWall","got position:"+position);


    // If the id of the profile, here dubbed as a position has been correctly retrieved from the intent,
    //we load it directly from the Database.
    // TODO: Handle  the case where the profile has been deleted during the loading of the new activity.
    DBHelper dbHelper = new DBHelper(this);
    ContractAccount contract = dbHelper.getUser(position);
    Log.d("UserWall","FirstName="+contract.getFirstName());

    // Assign data to their containers
    TextView firstName = (TextView) findViewById(R.id.w_firstName);
    TextView lastName = (TextView) findViewById(R.id.w_lastName);
    firstName.setText(contract.getFirstName());
    lastName.setText(contract.getLastName());
  }
}
