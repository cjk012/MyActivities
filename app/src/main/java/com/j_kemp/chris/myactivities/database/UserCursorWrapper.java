package com.j_kemp.chris.myactivities.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.j_kemp.chris.myactivities.User;

/**
 * A database store cursor for the user values.
 * Probably overkill for the single record that will be overwritten, but...
 * Created by Christopher Kemp on 31/10/17.
 */

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) { super(cursor);}

    public User getUser(){
        String name = getString(getColumnIndex(TaskDbSchema.UsersTable.Cols.NAME));
        String email = getString(getColumnIndex(TaskDbSchema.UsersTable.Cols.EMAIL));
        String user_id = getString(getColumnIndex(TaskDbSchema.UsersTable.Cols.USER_ID));
        String gender = getString(getColumnIndex(TaskDbSchema.UsersTable.Cols.GENDER));
        String comment = getString(getColumnIndex(TaskDbSchema.UsersTable.Cols.COMMENT));

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setUserID(user_id);
        user.setGender(gender);
        user.setComment(comment);

        return user;
    }
}
