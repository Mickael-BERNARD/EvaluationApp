package mb.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class UserProfileCreationPage extends AppCompatActivity {

  DBHelper database;
  private RadioButton rb_optionY;
  private RadioButton rb_optionN;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile_creation_page);

    // Initialise the database:
  database = new DBHelper(UserProfileCreationPage.this);
    addListenerToButton();
  }

  public void addListenerToButton(){
    rb_optionY = (RadioButton) findViewById(R.id.rb_oui);
    rb_optionN = (RadioButton) findViewById(R.id.rb_non);

    rb_optionY.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });
  }

  /**
   * Creates an UserInputContract based on input: [firstName,lastName]
   * @param view
   */
  public void submitProfile(View view){

    // We will read the content of the text fields
    // and create a contract object from it.
    EditText editTextFirstName = (EditText) findViewById(R.id.p_first_name);
    EditText editTextLastName = (EditText) findViewById(R.id.p_last_name);
    String firstName = editTextFirstName.getText().toString();
    String lastName = editTextLastName.getText().toString();
    TextView textView = (TextView) findViewById(R.id.errorMessage);

    // We check to see if the the fields we need are filled.
    if (firstName.isEmpty() || lastName.isEmpty()){

      textView.setText("Erreur: Veuillez remplir les champs");
    }else {
      UserInputContract contract = new UserInputContract(firstName,lastName);
      contract.setFirstName(firstName);
      contract.setLastName(lastName);
      database.insertUser(contract);
      // Return to the main page
      finish();



    }
  }




}

