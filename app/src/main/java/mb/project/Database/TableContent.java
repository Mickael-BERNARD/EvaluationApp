package mb.project.Database;

/**
 * Created by Mickaël BERNARD on 19-Aug-16.
 */
public class TableContent {
  public static final String TABLE_NAME = "CONTENT_LIST";
  // Content Table Columns names
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_USER_ID = "USER_ID";
  public static final String COLUMN_COUNTRY = "COUNTRY";
  public static final String COLUMN_CITIES = "CITIES";
  public static final String COLUMN_DESCR = "DESCRIPTION";
  public static final  String COLUMN_POF = "PLACES_OF_INTEREST";
  public static  final String COLUMN_ACCOMMODATIONS = "ACCOMMODATIONS";
  public static  final  String COLUMN_TRANSPORT = "TRANSPORT";
  public static final String COLUMN_BUSINESS = "BUSINESS";
  public static final String COLUMN_EDUCATION = "EDUCATION";

  public static final String[] COLUMNS = {COLUMN_ID,COLUMN_USER_ID,COLUMN_COUNTRY,COLUMN_CITIES, COLUMN_DESCR, COLUMN_POF , COLUMN_ACCOMMODATIONS, COLUMN_TRANSPORT,COLUMN_BUSINESS, COLUMN_EDUCATION};

}
