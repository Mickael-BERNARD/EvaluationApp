package mb.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import mb.project.Database.DBHelper;
import mb.project.Database.ContractAccount;

public class UserProfileCreationPage extends AppCompatActivity {

  DBHelper database;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile_creation_page);

    // Initialise the database:
  database = new DBHelper(UserProfileCreationPage.this);
    // Make a call to the page widgets:

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
    EditText editTextEmail = (EditText) findViewById(R.id.p_email);
    EditText editTextTel = (EditText) findViewById(R.id.p_telNum);

    String firstName = editTextFirstName.getText().toString();
    String lastName = editTextLastName.getText().toString();
    String email = editTextEmail.getText().toString();
    String tel = editTextTel.getText().toString();

    TextView textView = (TextView) findViewById(R.id.errorMessage);

    // We check to see if the the fields we need are filled.
    if (firstName.isEmpty() || lastName.isEmpty()){

      textView.setText("Erreur: Veuillez remplir au moins les deux premiers champs indiqués");
    }else {
      ContractAccount contract = new ContractAccount(firstName,lastName,email, tel);
      contract.setFirstName(firstName);
      contract.setLastName(lastName);
      contract.setEmail(email);
      contract.setTel(tel);
      database.insertUser(contract);
      // Return to the main page
      finish();
    }
  }




}

