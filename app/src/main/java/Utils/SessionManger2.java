package Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by a on 24/01/2018.
 */

/**
 * Created by a on 08/10/2017.
 */
public class SessionManger2 {
    // Shared preferences file name
    private static final String PREF_NAME = "AndroidHiveLogin2";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_IS_FIRST = "isfirst";
    // LogCat tag
    private static String TAG = Utils.SessionManager.class.getSimpleName();
    // Shared Preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    public SessionManger2(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public boolean isloggedin() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setFirst(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_FIRST, isLoggedIn);
        // commit changes
        editor.commit();
    }

    public boolean isFirst() {
        return pref.getBoolean(KEY_IS_FIRST, true);
    }
}

