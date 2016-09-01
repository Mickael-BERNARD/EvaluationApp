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

public class PostEdit extends AppCompatActivity {

  SessionManager session;
  DBHelper database;

  int position;
  int userId;

  EditText country;
  EditText cities;
  EditText description;
  EditText places_of_interest;
  EditText accommodations;
  EditText transport;
  EditText business;
  EditText education;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_post_edit_scrollview);

    // Recover intent
    Intent intent = getIntent();
     position = intent.getIntExtra("position",0);


    // Reload the post's content in the fields:
    DBHelper dbHelper = new DBHelper(this);
    ContractContent contract = dbHelper.getContentByRow(position);


    country = (EditText) findViewById(R.id.pe_country);
    cities = (EditText) findViewById(R.id.pe_cities);
    description = (EditText) findViewById(R.id.pe_description);
    places_of_interest = (EditText) findViewById(R.id.pe_poi);
    accommodations = (EditText) findViewById(R.id.pe_accomodations);
    transport = (EditText) findViewById(R.id.pe_transport);
    business = (EditText) findViewById(R.id.pe_business);
    education = (EditText) findViewById(R.id.pe_education);

    userId = contract.getUserId();
    country.setText(contract.getCountry());
    cities.setText(contract.getCities());
    description.setText(contract.getDescription());
    places_of_interest.setText(contract.getPlacesOfInterest());
    accommodations.setText(contract.getAccommodations());
    transport.setText(contract.getTransport());
    business.setText(contract.getBusiness());
    education.setText(contract.getEducation());
    dbHelper.close();
  }

  public void handleOnClickCancel(View view){
    Intent intent = new Intent(this, UserProfileEdit.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // To resume Main Activity
    startActivity(intent);
  }

  public void handleOnCLickValider(View view){
    DBHelper dbHelper = new DBHelper(this);
    // Read data in the form fields
    String s_country = country.getText().toString();
    String s_city = cities.getText().toString();
    String s_desc = description.getText().toString();
    String s_pof = places_of_interest.getText().toString();
    String s_accomodations = accommodations.getText().toString();
    String s_transport = transport.getText().toString();
    String s_business = business.getText().toString();
    String s_education = education.getText().toString();

    if (s_country.isEmpty() | s_city.isEmpty() | s_desc.isEmpty()){
      // Create a pop up
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage("Erreur: veuillez remplir au moins les 3 premiers champs indiqués");
      builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          dialogInterface.cancel();
        }
      });
      builder.show();
    }
    else{
      ContractContent contract = new ContractContent(userId,s_country,s_city,s_desc,s_pof, s_accomodations , s_transport , s_business , s_education);
      Log.d("handleOnClickValider"," "+contract.toString());
      contract.setID(position);
      dbHelper.updateContent(contract);
      dbHelper.close();
      Intent intent = new Intent(this, UserProfileEdit.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // To resume Main Activity
      startActivity(intent);
    }

  }





}
