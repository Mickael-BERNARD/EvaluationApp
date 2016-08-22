package mb.project;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import mb.project.Database.ContractAccount;
import mb.project.Database.DBHelper;

public class UserProfileEdit extends AppCompatActivity {

  SessionManager session;
  DBHelper database;

  EditText firstName ;
  EditText lastName ;
  EditText email;
  EditText tel;

  int userId;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile_edit);

  //Load the session
    session = new SessionManager(this);
    // Get userId -> if we can't we end the activity
     userId = session.getUserId();
    if (userId == -1) finish();

    // Get all the EditText components:
    firstName = (EditText) findViewById(R.id.pe_first_name);
    lastName = (EditText) findViewById(R.id.pe_last_name);
    email = (EditText) findViewById(R.id.pe_email);
    tel = (EditText) findViewById(R.id.pe_tel);

    // Load current profile information in the Editext component 'hints'.

    database = new DBHelper(this);
    ContractAccount account = database.getUser(userId);

    firstName.setText(account.getFirstName());
    lastName.setText(account.getLastName());
    email.setText(account.getEmail());
    tel.setText(account.getTel());

  }

  public void saveUserAccountChanges(View view){
    // Init Alertdialog builder
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    String s_firstName = firstName.getText().toString();
    String s_lastName = lastName.getText().toString();
    String s_email = email.getText().toString();
    String s_tel = tel.getText().toString();

    // TODO check user input
    // If incorrect show a pop up with a message
    if (s_firstName.isEmpty() | s_lastName.isEmpty()){
      builder.setMessage("Erreur: veuillez remplir au moins les deux premiers champs");
      builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          dialogInterface.cancel();
        }
      });
      builder.show();
    } else {
      ContractAccount user = new ContractAccount(s_firstName,s_lastName,s_email,s_tel);
      user.setID(userId);
      database.updateUser(user);
      //TODO: include a pop up
      // Add an alert dialog

      // set dialog message
      builder.setMessage("Modification enregitrées");
      builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int i) {
          // if this button is pressed close pop-up message
          dialog.cancel();
        }
      });

      // create alert dialog
      AlertDialog dialog = builder.create();
      // show it
      builder.show();

    }



  }


}
