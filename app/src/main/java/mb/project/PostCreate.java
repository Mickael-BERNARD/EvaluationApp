package mb.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import mb.project.Database.ContractContent;
import mb.project.Database.DBHelper;
import mb.project.ProfileEdit.UserProfileEdit;

public class PostCreate extends AppCompatActivity {

  SessionManager session;
  DBHelper database;

  int userId;

  EditText country;
  EditText cities;
  EditText description;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_post_create);

    // Initialize the database
    database = new DBHelper(this);
    // Open session
    session = new SessionManager(this);
    // Get userId -> if we can't we end the activity
    userId = session.getUserId();
    if (userId == -1) finish();

    // Initialize editText components:
    country = (EditText) findViewById(R.id.pe_country);
    cities = (EditText) findViewById(R.id.pe_cities);
    description = (EditText) findViewById(R.id.pe_description);

  }

  /**
   * This method is called when the user presses the CREATE button on the associated layout.
   * It saves the content of the fields in a new entry in hte Content table.
   */
  public void submitPost(View view){

    String s_country = country.getText().toString();
    String s_cities = cities.getText().toString();
    String s_desc = description.getText().toString();

    Log.d("submitPost","button clicked"+(s_country.isEmpty() | s_cities.isEmpty() | s_desc.isEmpty()));
    // Check if the form is valid:
    if (s_country.isEmpty() | s_cities.isEmpty() | s_desc.isEmpty()){
      // Create a pop up
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage("Erreur: veuillez remplir tous les champs indiqués");
      builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          dialogInterface.cancel();
        }
      });
      builder.show();
    }
    // The form has been filled out correctly.
    else {
      // Insert row in the database:
      Log.d("PostCreate","Value for user:"+userId);

      ContractContent contract = new ContractContent(userId,s_country,s_cities,s_desc);
      Log.d("PostCreate","Content:"+contract.toString());
      database.insertContent(contract);
      // End the session -> restart the previous activity

      Intent intent = new Intent(this, UserProfileEdit.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // To resume Main Activity
      startActivity(intent);
    }
  }


}
