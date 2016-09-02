package mb.project.PostView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import mb.project.Database.ContractAccount;
import mb.project.Database.ContractComment;
import mb.project.Database.ContractContent;
import mb.project.Database.DBHelper;
import mb.project.ProfileEdit.UserProfileEdit;
import mb.project.ProfileView.UserProfileView;
import mb.project.R;
import mb.project.SessionManager;
import mb.project.UserList.UserListView;

public class UserPostView extends AppCompatActivity {

  DBHelper database = new DBHelper(this);

  SessionManager session;

  int userId;
  int postId;

  ListView commentList;

  CommentListAdapter dataAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_post_view_scrollview);

    session = new SessionManager(this);

    // Recover intent
    Intent intent = getIntent();
    int position = intent.getIntExtra("position",0);
    postId = position;
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

    commentList = (ListView) findViewById(R.id.upv_commentList);

    TextView userName = (TextView) findViewById(R.id.upv_username);


    userId = contract.getUserId();
    ContractAccount account = database.getUser(userId);
    String firstName = account.getFirstName();
    String lastName = account.getLastName();


    country.setText(contract.getCountry());
    cities.setText(contract.getCities());
    description.setText(contract.getDescription());

    manageTextViewContent(placesOfInterest, contract.getPlacesOfInterest(), (TextView) findViewById(R.id.upv_tv_poi));
    manageTextViewContent(accomodations,contract.getAccommodations(), (TextView) findViewById(R.id.upv_tv_accommodations));
    manageTextViewContent(transport,contract.getTransport(), (TextView) findViewById(R.id.upv_tv_transport));
    manageTextViewContent(business,contract.getBusiness(), (TextView) findViewById(R.id.upv_tv_business));
    manageTextViewContent(education,contract.getEducation(), (TextView) findViewById(R.id.upv_tv_education));

    /*
    placesOfInterest.setText(contract.getPlacesOfInterest());
    accomodations.setText(contract.getAccommodations());
    transport.setText(contract.getTransport());
    business.setText(contract.getBusiness());
    education.setText(contract.getEducation());*/

    userName.setPaintFlags(userName.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
    userName.setText(firstName+" "+lastName);
    displayCommentList();

  }

  public void displayCommentList(){
    database = new DBHelper(this);
    Cursor cursor = database.getCommentByPostId(postId);

    Log.d("displayCommentList"," "+cursor.getCount());
    dataAdapter = new CommentListAdapter(this, cursor,0);
    commentList = (ListView)findViewById(R.id.upv_commentList);
    commentList.setAdapter(dataAdapter);
    database.close();
  }

  /**
   * This method will remove a textview if it doesn't have any content to display.
   * @param
   * @param container
   * @param content
     */
  public void manageTextViewContent(TextView container, String content, TextView segmentTitle){
    if( content.isEmpty()){
      container.setVisibility(View.GONE);
      segmentTitle.setVisibility(View.GONE);
    }
    else container.setText(content);
  }

  public void handleOnClickName(View view){
    // Start the activity UserProfile View
    Intent intent = new Intent(this, UserProfileView.class);
    intent.putExtra("userId",userId);
    startActivity(intent);
  }

  /**
   * This method will create a new ContractComment object and add it
   * to the Table Comments before updating the layout's ListRow
   * @param view
   */
  public void addPost(View view){
    Log.d("addPost","The method was called");
    EditText content = (EditText) findViewById(R.id.upv_new_comment);

    // We create a new ContractComment Object
    ContractComment contract = new ContractComment();
    // We retrieve the userId
     int ownerId = session.getUserId();
    contract.setPostId(postId);
    contract.setUserId(ownerId);
    contract.setContent(content.getText().toString());
    database.insertComment(contract);

    // Empty the comment input section:
    content.setText("");
    refreshCommentList();
  }

  public void refreshCommentList(){

      commentList.setAdapter(null);
      // Reset cursor
     Cursor cursor = database.getCommentByPostId(postId);
    cursor.moveToFirst();

    Log.d("refreshCommentList","Number of elements to load:"+cursor.getCount());
      // Reset adapter
      dataAdapter = new CommentListAdapter(this, cursor,0);

      // Reset listview

    commentList = (ListView) findViewById(R.id.upv_commentList);
      commentList.invalidateViews();
      commentList.setAdapter(dataAdapter);

      database.close();

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
