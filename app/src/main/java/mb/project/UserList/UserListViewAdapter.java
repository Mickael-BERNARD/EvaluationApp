package mb.project.UserList;

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
import mb.project.Database.TableContent;
import mb.project.R;

public class UserListViewAdapter extends CursorAdapter{

  DBHelper database;

  // Check the third parameter
  public UserListViewAdapter(Context context, Cursor cursor, DBHelper dbHelper) {
    super(context, cursor,0);
    database = dbHelper;

  }

// The newView method is used to inflate a new view and return it.
  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
   return LayoutInflater.from(context).inflate(R.layout.user_list_row, parent, false);
  }

  // The bindView method is used to bind all data to a given view such
  // as setting the text on a TextView.
  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    // Find fields to populate in inflated template

    TextView country = (TextView) view.findViewById(R.id.tv_country);
    TextView firstName = (TextView) view.findViewById(R.id.tv_first_name);
    TextView lastName = (TextView) view.findViewById(R.id.tv_last_name);

    // Extract properties from the cursor
    int position = cursor.getInt(cursor.getColumnIndexOrThrow(TableContent.COLUMN_ID));
    Log.d("ULV_Apdapter","position="+position);
    String s_country = cursor.getString(cursor.getColumnIndexOrThrow(TableContent.COLUMN_COUNTRY));
    int userId = cursor.getInt(cursor.getColumnIndexOrThrow(TableContent.COLUMN_USER_ID));

    // Retrieve user information
    ContractAccount account = database.getUser(userId);

    String s_firstName = account.getFirstName();
    String s_lastName = account.getLastName();
    Log.d("ULV_Apdapter","firstName="+s_firstName);
    //Populate fields with extracted properties
    country.setText(s_country);
    firstName.setText(s_firstName);
    lastName.setText(s_lastName);
    // We use a tag to store additional data within a view without
    // having to resort to an additional data structure.
    view.setTag(position);


    /*
    final UserInputContract contract = UserInputContract.fromCursor(cursor);
    ((TextView)view.findViewById(R.id.p_first_name)).setText(contract.getFirstName());
    ((TextView)view.findViewById(R.id.p_last_name)).setText(contract.getLastName());
*/

  }
}
