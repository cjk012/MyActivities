package com.j_kemp.chris.myactivities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j_kemp.chris.myactivities.database.TaskBaseHelper;
import com.j_kemp.chris.myactivities.database.TaskCursorWrapper;
import com.j_kemp.chris.myactivities.database.TaskDbSchema.TaskTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * An ArrayList holder for Task Objects.
 * Created by Christopher Kemp on 18/10/17.
 */

public class TaskLog {
    private static final String TAG = "TaskLog";

    private static TaskLog sTaskLog;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TaskLog get(Context context) {
        if (sTaskLog == null) {
            sTaskLog = new TaskLog(context);
        }
        return sTaskLog;
    }

    private TaskLog(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TaskBaseHelper(mContext).getWritableDatabase();
    }

    public void addTask(Task t) {
        Log.d(TAG, t.getID().toString() + ' ' + t.getTitle() + ' ' + t.getDate() + ' ' + t.getType());
        ContentValues values = getContentValues(t);
        mDatabase.insert(TaskTable.NAME, null, values);
    }

    /**
     * @return List<Task> - an ArrayList of all Task objects from our database.
     */
    public List<Task> getTasks(){
        List<Task> tasks = new ArrayList<>();
        TaskCursorWrapper cursor = queryTasks(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close(); // After actions have been performed with the database, close the connection to save memory.
        }

        return tasks;
    }

    /**
     * Retrieve a specific Task object from the database, search by UUID.
     * @param id
     * @return Task
     */
    public Task getTask(UUID id) {
        TaskCursorWrapper cursor = queryTasks(TaskTable.Cols.UUID + " = ?", new String[] { id.toString() });

        try {
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getTask();
        } finally {
            cursor.close(); // After actions have been performed with the database, close the connection to save memory.
        }
    }

    /**
     * @param task
     * @return File - pointer to the Photo file for a given Task.
     */
    public File getPhotoFile(Task task){
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, task.getPhotoFilename());
    }

    /**
     * Save changes to a task into the database.
     * @param task - changed Task object to be saved.
     */
    public void updateTask(Task task){
        String uuidString = task.getID().toString();
        ContentValues values = getContentValues(task);
        Log.d(TAG, "Values retrieved from java class.");

        mDatabase.update(TaskTable.NAME, values, TaskTable.Cols.UUID + " = ?", new String[]{uuidString});
        Log.d(TAG, "Values saved to SQLite db.");
    }

    public void deleteTask(Task task){
        String uuidString = task.getID().toString();
        mDatabase.delete(TaskTable.NAME, TaskTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    /**
     * Method to perform queries against the Task Table in our SQLite database.
     * @param whereClause - any filtering fields
     * @param whereArgs - filter to be applied
     * @return (Wrapped) Cursor object to iterate through results.
     */
    private TaskCursorWrapper queryTasks(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                TaskTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy - As we are not performing any grouping or ordering, this
        );

        return new TaskCursorWrapper(cursor);
    }

    /**
     * Provides a link between columns in the database file and values in the Task Object.
     * @param task - the particular Task we want details of
     * @return ContentValues values
     */
    private static ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskTable.Cols.UUID, task.getID().toString());
        values.put(TaskTable.Cols.TITLE, task.getTitle());
        values.put(TaskTable.Cols.DATE, task.getDate().getTime());
        values.put(TaskTable.Cols.TASK_TYPE, task.getType());
        values.put(TaskTable.Cols.PLACE_LAT, task.getPlaceLat());
        values.put(TaskTable.Cols.PLACE_LON, task.getPlaceLon());
        values.put(TaskTable.Cols.PLACE_FRIENDLY, task.getPlaceFriendly());
        values.put(TaskTable.Cols.DURATION, task.getDuration());
        values.put(TaskTable.Cols.COMMENT, task.getComment());

        return values;
    }
}