package com.j_kemp.chris.myactivities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j_kemp.chris.myactivities.database.TaskBaseHelper;
import com.j_kemp.chris.myactivities.database.TaskDbSchema.UsersTable;
import com.j_kemp.chris.myactivities.database.UserCursorWrapper;

/**
 * Java class to manage the array of one user.
 * Created by Christopher Kemp on 29/10/17.
 */

public class UserManager {
    private static final String TAG = "UserManager";

    private static UserManager sUserManager;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static UserManager get(Context context){
        if (sUserManager == null){
            sUserManager = new UserManager(context);
        }
        return sUserManager;
    }

    private UserManager(Context context){
        mContext = context;
        mDatabase = new TaskBaseHelper(mContext).getWritableDatabase();
    }

    public void addUser(User u){
        Log.d(TAG, "Added User: ");
        ContentValues values = getContentValues(u);
        mDatabase.insert(UsersTable.NAME, null, values);
    }

    public User getUser(String id) {
        UserCursorWrapper cursor = queryUsers(UsersTable.Cols.ID + " = ?", new String[]{id});

        try {
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }

    public void updateUser(User user) {
        String id = user.getID();
        ContentValues values = getContentValues(user);

        mDatabase.update(UsersTable.NAME, values, UsersTable.Cols.ID + " = ?", new String[]{id});
    }

    private UserCursorWrapper queryUsers(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                UsersTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy - As we are not performing any grouping or ordering, unused.
        );

        return new UserCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(User user){
        ContentValues values = new ContentValues();
        values.put(UsersTable.Cols.ID, user.getID());
        values.put(UsersTable.Cols.NAME, user.getName());
        values.put(UsersTable.Cols.EMAIL, user.getEmail());
        values.put(UsersTable.Cols.USER_ID, user.getUserID());
        values.put(UsersTable.Cols.GENDER, user.getGender());
        values.put(UsersTable.Cols.COMMENT, user.getComment());

        return values;
    }
}
