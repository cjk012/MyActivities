package com.j_kemp.chris.myactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Fragment using a RecyclerView containing a list of logged Tasks.
 * Created by Christopher Kemp on 19/10/17.
 */

public class TaskListFragment extends Fragment {
    private static final String TAG = "TaskListFragment";

    private RecyclerView mTaskRecyclerView;
    private TaskAdaptor mAdaptor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        mTaskRecyclerView = (RecyclerView) view.findViewById(R.id.task_recycler_view);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_task:
                Task task = new Task();
                Log.d(TAG, task.getID().toString());
                TaskLog.get(getActivity()).addTask(task);
                updateUI();
                Intent intent = TaskActivity.newIntent(getActivity(), task.getID());
                startActivity(intent);
                return true;
            case R.id.settings_menu:
                Intent settingsIntent = new Intent(getActivity(), UserActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateUI() {
        TaskLog taskLog = TaskLog.get(getActivity());
        List<Task> tasks = taskLog.getTasks();

        if (mAdaptor == null){
            mAdaptor = new TaskAdaptor(tasks);
            mTaskRecyclerView.setAdapter(mAdaptor);
        } else {
            mAdaptor.setTasks(tasks);
            mAdaptor.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private void updateSubtitle() {
        TaskLog taskLog= TaskLog.get(getActivity());
        int taskCount = taskLog.getTasks().size();
        String subtitle = getResources()
                .getQuantityString(R.plurals.subtitle_plural, taskCount, taskCount);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Task mTask;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mPlaceTextView;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.task_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.task_date);
            mPlaceTextView = (TextView) itemView.findViewById(R.id.task_place);
        }

        public void bind(Task task){
            mTask = task;
            mTitleTextView.setText(mTask.getTitle());
            mDateTextView.setText(mTask.getFormattedDate());
            mPlaceTextView.setText(mTask.getPlaceFriendly());
        }

        @Override
        public void onClick(View view){
            Intent intent = TaskActivity.newIntent(getActivity(), mTask.getID());
            startActivity(intent);
        }
    }

    private class TaskAdaptor extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> mTasks;

        public TaskAdaptor(List<Task> tasks) {
            mTasks = tasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position){
            Task task = mTasks.get(position);
            holder.bind(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }
    }
}