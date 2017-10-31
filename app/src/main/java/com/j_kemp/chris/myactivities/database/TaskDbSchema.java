package com.j_kemp.chris.myactivities.database;

/**
 * Database Schema for MyActivities, represented in Java Object.
 * Created by Christopher Kemp on 12/10/17.
 */

public class TaskDbSchema {
    public static final class TaskTable {
        public static final String NAME = "tasks";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String TASK_TYPE = "type";
            public static final String PLACE_LAT = "place_lat";
            public static final String PLACE_LON = "place_lon";
            public static final String PLACE_FRIENDLY = "place_friendly";
            public static final String DURATION = "duration";
            public static final String COMMENT = "comment";
        }
    }

    public static final class UsersTable {
        public static final String NAME = "users";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String EMAIL = "email";
            public static final String USER_ID = "user_id";
            public static final String GENDER = "gender";
            public static final String COMMENT = "comment";
        }
    }
}
