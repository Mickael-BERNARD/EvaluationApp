package mb.project.Database;

/**
 * Created by Mickaël BERNARD on 19-Aug-16.
 */
public class TableComments {

  public static final String TABLE_NAME = "COMMENT_LIST";
  // Content Table Columns names
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_USER_ID = "USER_ID";
  public static final String COLUMN_POST_ID = "POST_ID";
  public static final String COLUMN_CONTENT = "CONTENT";


  public static final String[] COLUMNS = {COLUMN_ID,COLUMN_USER_ID,COLUMN_POST_ID,COLUMN_CONTENT};

}
