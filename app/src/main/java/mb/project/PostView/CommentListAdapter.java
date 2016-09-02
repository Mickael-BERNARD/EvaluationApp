package mb.project.PostView;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import mb.project.Database.ContractAccount;
import mb.project.Database.DBHelper;
import mb.project.Database.TableComments;
import mb.project.R;

/**
 * Created by Mickaël BERNARD on 01-Sep-16.
 */
public class CommentListAdapter extends CursorAdapter {
  DBHelper database;

  public CommentListAdapter(Context context, Cursor cursor, int flags) {
    super(context, cursor, 0);
     database = new DBHelper(context);
  }

  // The newView method is used to inflate a new view and return it.
  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return LayoutInflater.from(context).inflate(R.layout.comment_list_row, parent, false);
  }


   // The bindView method is used to bind all data to a given view such
  // as setting the text on a TextView.
  @Override
  public void bindView(View view, Context context, Cursor cursor) {


      Log.d("CommentListAdapter", "init");
      TextView userName = (TextView) view.findViewById(R.id.clr_username);
      TextView content = (TextView) view.findViewById(R.id.clr_content);
      // Extract properties from the cursor
      int position = cursor.getInt(cursor.getColumnIndexOrThrow(TableComments.COLUMN_ID));
      Log.d("CommentListAdapter", "got id" + position);
      int i_userId = cursor.getInt(cursor.getColumnIndexOrThrow(TableComments.COLUMN_USER_ID));
      String s_content = cursor.getString(cursor.getColumnIndexOrThrow(TableComments.COLUMN_CONTENT));

      // Assign values to their  respective containers
      // Find the user >
      ContractAccount account = database.getUser(i_userId);
      String name = account.getFirstName() + " " + account.getLastName();

      userName.setText(name);
      content.setText(s_content);
      Log.d("CommentListAdapter", "loaded comment content " + s_content);
      view.setTag(position);



  }
}
