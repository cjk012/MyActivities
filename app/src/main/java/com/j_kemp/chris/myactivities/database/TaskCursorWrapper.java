package com.j_kemp.chris.myactivities.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.j_kemp.chris.myactivities.Task;

import java.util.Date;
import java.util.UUID;

/**
 * Database interaction class for accessing and storing in SQLite db.
 * Created by Christopher Kemp on 14/10/17.
 */

public class TaskCursorWrapper extends CursorWrapper {
    public TaskCursorWrapper(Cursor cursor) { super(cursor);}

    public Task getTask() {
        String uuidString = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.UUID));
        String title = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.TITLE));
        long date = getLong(getColumnIndex(TaskDbSchema.TaskTable.Cols.DATE));
        int type = getInt(getColumnIndex(TaskDbSchema.TaskTable.Cols.TASK_TYPE));
        double place_lat = getDouble(getColumnIndex(TaskDbSchema.TaskTable.Cols.PLACE_LAT));
        double place_lon = getDouble(getColumnIndex(TaskDbSchema.TaskTable.Cols.PLACE_LON));
        String place_friendly = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.PLACE_FRIENDLY));
        String duration = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.DURATION));
        String comment = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.COMMENT));

        Task task = new Task(UUID.fromString(uuidString));
        task.setTitle(title);
        task.setDate(new Date(date));
        task.setType(type);
        task.setPlaceLat(place_lat);
        task.setPlaceLon(place_lon);
        task.setPlaceFriendly(place_friendly);
        task.setDuration(duration);
        task.setComment(comment);

        return task;
    }
}
