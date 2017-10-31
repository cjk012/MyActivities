package com.j_kemp.chris.myactivities;

import android.support.v4.app.Fragment;

/**
 * Host Activity for TaskListFragment.
 * Created by Christopher Kemp on 19/10/17.
 */

public class TaskListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TaskListFragment();
    }
}
