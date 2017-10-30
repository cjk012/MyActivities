package com.j_kemp.chris.myactivities;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Host Activity for TaskListFragment, which displays a list of the currently logged Tasks.
 * Created by Christopher Kemp on 19/10/17.
 */

public class TaskListActivity extends SingleFragmentActivity
        implements TaskListFragment.Callbacks, TaskFragment.Callbacks {
    private static final int REQUEST_ERROR = 0;

    @Override
    protected Fragment createFragment() {
        return new TaskListFragment();
    }

    @Override
    protected void onResume(){
        super.onResume();

        /*GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability
                    .getErrorDialog(this, errorCode, REQUEST_ERROR, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            // Stop if services are unavailable.
                            finish();
                        }
                    });

            errorDialog.show();
        }*/
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onTaskSelected(Task task) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = TaskActivity.newIntent(this, task.getID());
            startActivity(intent);
        } else {
            Fragment newDetail = TaskFragment.newInstance(task.getID());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    @Override
    public void onTaskUpdated(Task task){
        TaskListFragment listFragment = (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
