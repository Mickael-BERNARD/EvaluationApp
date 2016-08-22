package mb.project;

import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import mb.project.Database.DBHelper;


public class MainActivity extends AppCompatActivity {

  // We need to change this value in order to change the registration status of the user.
  public boolean userNotRegistered = true;
  SessionManager session;



  @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
      // Set the user interface layout for this Activity
      // The layout file is defined in the project res/layout/activity_main.xml file
      //to inflate the activity's UI:
        setContentView(R.layout.activity_main);
      // The toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


      // Session Manager Class


    session = new SessionManager(this);
   session.deleteUserAccount();
    // Assign value to TextView
    TextView userIdText = (TextView) findViewById(R.id.m_userId);
    userIdText.setText(""+ session.getUserId());

      //TODO: Automatically go to registration screen at the start of the activity if the user is not registered

      DBHelper database = new DBHelper(this);
      database.eraseAllUsers();

      /*
      if (!userNotRegistered){
        Intent intent = new Intent(this, UserProfileCreationPage.class);
        startActivity(intent);
      }*/
      //==============================

    }

  public void goToProfileCreationPage(View view){
    Intent intent = new Intent(this, UserProfileCreationPage.class);
    startActivity(intent);
  }
  public void goToUserlistView(View view){
    Intent intent = new Intent(this, UserListView.class);
    startActivity(intent);
  }
  public void goToUserProfileEdit(View view){
    Intent intent = new Intent(this, UserProfileEdit.class);
    startActivity(intent);
  }


  /*
  private void onAddUserProfile(){
    // DML - Data manipulation language.
    // Make an intent to go from MainActivity to TableManipulationActivity.class
    Intent intent = new Intent(MainActivity.this, TableManipulationActivity.class);
    // Put extra values to intent: Key: DML_TYPE , Value: Insert
    intent.putExtra(Constants.DML_TYPE, Constants.INSERT);
    // Start activity to get the results for ADD_RECORD
    startActivityForResult(intent, Constants.ADD_RECORD);
  }*/


  public final static String EXTRA_MESSAGE = "mb.project.MESSAGE";
  /*
  public void sendMessage (View view) {
    // The view will the the View that was clicked
    // Do something in response to the button
    // We will read the content of the text field and deliver that text to another activity.
    // We associate to the intent the class of the app componeent (in this case an actvity to start) which the system should deliver to the Intent.
    Intent intent = new Intent(this, DisplayMessageActivity.class);
    // We recover the component we wanted in the layout.  // We associate the intent to it's context.
    EditText editText = (EditText) findViewById(R.id.edit_message);
    String message = editText.getText().toString();
    //The putExtra() method adds the EditText's value to the intent.
    // An Intent can carry data types as key-value pairs called extras.
    intent.putExtra(EXTRA_MESSAGE, message);
    startActivity(intent);
  }*/


  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

  public void showUserIdRefresh(){
    TextView userIdText = (TextView) findViewById(R.id.m_userId);
    userIdText.setText(""+ session.getUserId());
  }

  @Override
  public void onResume(){
    super.onResume();
    Log.d("MainActivity","onResume was called");
    showUserIdRefresh();

  }
}
