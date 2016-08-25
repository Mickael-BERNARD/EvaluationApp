package mb.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import mb.project.Database.DBHelper;

public class PostEdit extends AppCompatActivity {

  SessionManager session;
  DBHelper database;

  int userId;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_post_edit);

    // Recover intent
    Intent intent = getIntent();
    int position = intent.getIntExtra("position",0);
    //
    Log.d("PostEdit","got position:"+position);

    // Open session
      session = new SessionManager(this);
    // Get userId -> if we can't we end the activity
    userId = session.getUserId();
    if (userId == -1) finish();
  }




}
