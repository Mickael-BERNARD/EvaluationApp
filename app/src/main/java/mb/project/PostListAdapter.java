package mb.project;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mb.project.Database.TableContent;

/**
 * Created by Mickaël BERNARD on 23-Aug-16.
 */
public class PostListAdapter extends CursorAdapter {


  public PostListAdapter(Context context, Cursor c, int flags) {
    super(context, c, 0);
  }

  // The newView method is used to inflate a new view and return it.
  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return LayoutInflater.from(context).inflate(R.layout.post_edit_list_row, parent, false);
  }

  // The bindView method is used to bind all data to a given view such
  // as setting the text on a TextView.
  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    TextView country = (TextView) view.findViewById(R.id.pelr_country);
    TextView cities = (TextView) view.findViewById(R.id.pelr_cities);
    // Extract properties from the cursor
    int position = cursor.getInt(cursor.getColumnIndexOrThrow(TableContent.COLUMN_ID));
    Log.d("PostListAdapter","got position"+ position);
    String s_country = cursor.getString(cursor.getColumnIndexOrThrow(TableContent.COLUMN_COUNTRY));
    String s_cities = cursor.getString(cursor.getColumnIndexOrThrow(TableContent.COLUMN_CITIES));

    // Assign values to their  respective containers
    country.setText(s_country);
    cities.setText(s_cities);

    view.setTag(position);
    ImageView delete = (ImageView) view.findViewById(R.id.pelr_delete);
    delete.setTag(position);
    ImageView edit = (ImageView) view.findViewById(R.id.pelr_edit);
    edit.setTag(position);


  }
}
