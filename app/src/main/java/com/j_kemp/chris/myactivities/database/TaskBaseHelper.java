package com.j_kemp.chris.myactivities.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database manager for My Activities logger app.
 * Created by Christopher Kemp on 12/10/17.
 */

public class TaskBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "tasksBase.db";

    public TaskBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Sets up the database file on first run.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TaskDbSchema.TaskTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TaskDbSchema.TaskTable.Cols.UUID + ", " +
                TaskDbSchema.TaskTable.Cols.TITLE + ", " +
                TaskDbSchema.TaskTable.Cols.DATE + ", " +
                TaskDbSchema.TaskTable.Cols.TASK_TYPE + ", " +
                TaskDbSchema.TaskTable.Cols.PLACE_LAT + ", " +
                TaskDbSchema.TaskTable.Cols.PLACE_LON + ", " +
                TaskDbSchema.TaskTable.Cols.PLACE_FRIENDLY + ", " +
                TaskDbSchema.TaskTable.Cols.DURATION + ", " +
                TaskDbSchema.TaskTable.Cols.COMMENT+ ')'
        );

        db.execSQL("create table " + TaskDbSchema.UsersTable.NAME + "(" +
                " _id integer primary key, " +
                TaskDbSchema.UsersTable.Cols.NAME + ", " +
                TaskDbSchema.UsersTable.Cols.USER_ID + ", " +
                TaskDbSchema.UsersTable.Cols.EMAIL + ", " +
                TaskDbSchema.UsersTable.Cols.GENDER + ", " +
                TaskDbSchema.UsersTable.Cols.COMMENT + ")"
        );
    }

    /**
     * Currently unused, but required method for SQLiteHelper.
     * This would allow for database upgrades in future.
     * @param db
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
