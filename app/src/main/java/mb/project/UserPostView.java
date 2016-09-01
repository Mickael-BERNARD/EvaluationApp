package mb.project;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import mb.project.Database.ContractAccount;
import mb.project.Database.ContractContent;
import mb.project.Database.DBHelper;
import mb.project.ProfileEdit.UserProfileEdit;
import mb.project.ProfileView.UserProfileView;

public class UserPostView extends AppCompatActivity {

  DBHelper database = new DBHelper(this);

  int userId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_post_view_scrollview);

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
    TextView placesOfInterest = (TextView) findViewById(R.id.upv_poi);
    TextView accomodations = (TextView) findViewById(R.id.upv_accommodations);
    TextView transport = (TextView) findViewById(R.id.upv_transport);
    TextView business = (TextView) findViewById(R.id.upv_business);
    TextView education = (TextView) findViewById(R.id.upv_education);

    TextView userName = (TextView) findViewById(R.id.upv_username);


    userId = contract.getUserId();
    ContractAccount account = database.getUser(userId);
    String firstName = account.getFirstName();
    String lastName = account.getLastName();


    country.setText(contract.getCountry());
    cities.setText(contract.getCities());
    description.setText(contract.getDescription());
    placesOfInterest.setText(contract.getPlacesOfInterest());
    accomodations.setText(contract.getAccommodations());
    transport.setText(contract.getTransport());
    business.setText(contract.getBusiness());
    education.setText(contract.getEducation());

    userName.setPaintFlags(userName.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
    userName.setText(firstName+" "+lastName);


  }

  public void handleOnClickName(View view){
    // Start the activity UserProfile View
    Intent intent = new Intent(this, UserProfileView.class);
    intent.putExtra("userId",userId);
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
