package mb.project;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mickaël BERNARD on 21-Aug-16.
 */
public class SessionManager {
  SharedPreferences pref;

  // Editor for shared pref:
  SharedPreferences.Editor editor;

  // Context
  Context context;

  // Shared pref mode:
  int PRIVATE_MODE = 0;

  // Sharedpref file name
  private static final String PREF_NAME = "UserAccountPref";

  // All shared Preference keys
  private static final String IS_REGISTERED = "IsRegistered";

  // User account ID (public so as to be accessed by the app)
  public static final String KEY_ID = "id";

  // Contructor
  public SessionManager(Context _context){
    this.context = _context;
    pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    editor = pref.edit();
  }

  /**
   * Creating a registeration session
   */
  public void createRegistrationSession(int id){
    // Storing id in pref
    editor.putInt(KEY_ID, id);

    // Storing login value as true
    editor.putBoolean(IS_REGISTERED, true);

    // commit changes / save changes
    editor.commit();
  }
  /**
   * Quick check for registration status
   */
  // Get registration state
  public boolean getRegistrationStatus(){
    return pref.getBoolean(IS_REGISTERED,false);
  }

  public int getUserId(){
    Object object = pref.getInt(KEY_ID,0);
    if (object != null && pref.getBoolean(IS_REGISTERED,false)){
      return (int) object;
    }
    else return -1;
  }

  public void deleteUserAccount(){
    editor.remove(KEY_ID);
    editor.putBoolean(IS_REGISTERED,false);
    editor.commit(); // Commit changes

  }


}
