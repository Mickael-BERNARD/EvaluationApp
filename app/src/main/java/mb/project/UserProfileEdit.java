package mb.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import mb.project.Database.ContractAccount;
import mb.project.Database.ContractContent;
import mb.project.Database.DBHelper;

public class UserProfileEdit extends AppCompatActivity {

  SessionManager session;
  DBHelper database;
  PostListAdapter dataAdapter;

  EditText firstName ;
  EditText lastName ;
  EditText email;
  EditText tel;

  ListView postList;

  Cursor cursor;
  int userId;
  private boolean deletingPost;


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
    firstName = (EditText) findViewById(R.id.pe_country);
    lastName = (EditText) findViewById(R.id.pe_cities);
    email = (EditText) findViewById(R.id.pe_description);
    tel = (EditText) findViewById(R.id.pe_tel);

    // Load current profile information in the Editext component 'hints'.

    database = new DBHelper(this);
    ContractAccount account = database.getUser(userId);

    firstName.setText(account.getFirstName());
    lastName.setText(account.getLastName());
    email.setText(account.getEmail());
    tel.setText(account.getTel());

    displayPostList();
    database.close();

  }

  /**
   * This method is used to get the user's post in a Cursor object and load it's content in the ListView.
   */
  public void displayPostList(){
    database = new DBHelper(this);
    cursor = database.getContentByUserAlt(userId);
    dataAdapter = new PostListAdapter(this, cursor,0);
   postList = (ListView)findViewById(R.id.pe_postList);
    postList.setAdapter(dataAdapter);
    database.close();

  }

  public void handleOnClickCreatePost(View view){
    Intent intent = new Intent(this, PostCreate.class);

    startActivity(intent);
  }

  public void handleOnClickPostList(View view){
    int position = (int) view.getTag();
    Log.d("handleOnCLickPostList","got position:"+position);

    Intent intent = new Intent(this, PostEdit.class);
    intent.putExtra("position",position);
    startActivity(intent);
  }


    /**
     * This method checks if the user has entered in correct information and updated his account accordingly if so.
     * Otherwise it closes the activity.
     * @param view
     */
  public void saveUserAccountChanges(View view){
    database = new DBHelper(this);
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
    database.close();
  }

  @Override
  public void onResume(){
    super.onResume();
    Log.d("UserProfileEdit","Activity resumed");
    refreshPostList();
  }

  public void refreshPostList(){
    database = new DBHelper(this);
    postList.setAdapter(null);
    // Reset cursor
    cursor = database.getContentByUserAlt(userId);
    // Reset adapter
    dataAdapter = new PostListAdapter(this, cursor,0);

    // Reset listview
    ListView postList = (ListView)findViewById(R.id.pe_postList);
    postList.invalidateViews();
    postList.setAdapter(dataAdapter);

    database.close();
  }

  public void onClickEditButton(View view){
    int position = (int)  view.getTag();
    Intent intent = new Intent(this, PostEdit.class);
    intent.putExtra("position",position);
    startActivity(intent);
  }

  public void onClickDeleteButton(View view){
    final int position = (int)  view.getTag();
    Log.d("setNegative"," "+position);
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    // A boolean because pop-up content needs to be as simple as possible
    deletingPost = false;
    // Show a pop-up
  builder.setMessage("Êtes vous sûr de vouloir supprimer ce contenu?");

  builder.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
      deletePost(position);
    }
  });
    builder.setPositiveButton("Non", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {

        dialogInterface.cancel();
      }
    });
  builder.show();
    Log.d("onClickDeleteButton","deletingPost= "+deletingPost);


  }

  public void deletePost( int position){
    Log.d("deletePost","The method was called");
    ContractContent contractContent = new ContractContent();
    contractContent.setID(position);
    database.deleteUserContent(contractContent);
    refreshPostList();
  }


}
