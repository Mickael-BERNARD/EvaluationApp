package mb.project;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import mb.project.Database.DBHelper;
import mb.project.Database.TableAccounts;

public class UserListViewAdapter extends CursorAdapter{

  // Check the third parameter
  public UserListViewAdapter(Context context, Cursor cursor, int flags) {
    super(context, cursor,0);
  }

// The newView method is used to inflate a new view and return it.
  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
   return LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
  }

  // The bindView method is used to bind all data to a given view such
  // as setting the text on a TextView.
  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    // Find fields to populate in inflated template

    TextView firstName = (TextView) view.findViewById(R.id.tv_first_name);
    TextView lastName = (TextView) view.findViewById(R.id.tv_last_name);
    // Extract properties from the cursor
    int position = cursor.getInt(cursor.getColumnIndexOrThrow(TableAccounts.COLUMN_ID));
    Log.d("ULV_Apdapter","position="+position);
    String s_firstName = cursor.getString(cursor.getColumnIndexOrThrow(TableAccounts.COLUMN_FIRST_NAME));
    String s_lastName = cursor.getString(cursor.getColumnIndexOrThrow(TableAccounts.COLUMN_LAST_NAME));
    Log.d("ULV_Apdapter","firstName="+s_firstName);
    //Populate fields with extracted properties
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
