package com.j_kemp.chris.myactivities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Hosting Activity for TaskFragment.
 * Created by Christopher Kemp on 19/10/17.
 */

public class TaskActivity extends SingleFragmentActivity
        implements TaskFragment.Callbacks{

    public static final String EXTRA_TASK_ID = "com.j-kemp.chris.myactivities.task_id";

    public static Intent newIntent(Context packageContext, UUID taskId){
        Intent intent = new Intent(packageContext, TaskActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        UUID taskId = (UUID) getIntent().getSerializableExtra(EXTRA_TASK_ID);
        return TaskFragment.newInstance(taskId);
    }

    @Override
    public void onTaskUpdated(Task task){ }


}
