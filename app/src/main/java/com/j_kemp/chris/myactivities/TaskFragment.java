package com.j_kemp.chris.myactivities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.widget.CompoundButton.OnClickListener;

/**
 * A Fragment for viewing and editing the details of a Task object.
 * Created by Christopher Kemp on 19/10/17.
 */

public class TaskFragment extends Fragment {
    private static final String TAG = "ck.TaskFragment";
    private static final String ARG_TASK_ID = "task_id";
    private static final String ARG_TASK_OBJECT = "task_object";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;
    private static final int REQUEST_LOCATION_PERMISSIONS = 77;
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    private Task mTask;
    private File mPhotoFile;
    private EditText mTitleField;
    private Spinner mType;
    private ImageView mPhotoView;
    private ImageButton mPhotoButton;
    private Button mDateButton;
    private Button mPlaceButton;
    private EditText mDuration;
    private EditText mCommentField;
    private Button mSaveButton;
    private Button mDeleteButton;


    public static TaskFragment newInstance(UUID taskId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);

        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID taskId = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        mTask = TaskLog.get(getActivity()).getTask(taskId);
        mPhotoFile = TaskLog.get(getActivity()).getPhotoFile(mTask);
        checkPermissionLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        TaskLog.get(getActivity()).updateTask(mTask);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);

        mTitleField = (EditText) v.findViewById(R.id.task_title);
        mTitleField.setText(mTask.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No Action
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setTitle(s.toString());
                updateTask();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Also No Action
            }
        });

        mType = (Spinner) v.findViewById(R.id.task_type);
        mType.setSelection(mTask.getType());
        mType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTask.setType(mType.getSelectedItemPosition());
                updateTask();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action required
            }
        });

        mDateButton = (Button) v.findViewById(R.id.task_date);
        updateDate();
        mDateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mTask.getDate());
                dialog.setTargetFragment(TaskFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mPlaceButton = (Button) v.findViewById(R.id.task_place);
        updatePlace();
        mPlaceButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionLocation();
                TaskGMapFragment gMapFrag = new TaskGMapFragment();
                Bundle pushBundle = new Bundle();
                pushBundle.putSerializable(ARG_TASK_OBJECT, mTask);
                gMapFrag.setArguments(pushBundle);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragment_container, gMapFrag)
                        .addToBackStack(this.toString())
                        .commit();
            }
        });

        PackageManager packageManager = getActivity().getPackageManager();

        mPhotoButton = (ImageButton) v.findViewById(R.id.task_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = ((mPhotoFile != null) &&
                (captureImage.resolveActivity(packageManager) != null));
        mPhotoButton.setEnabled(canTakePhoto);
        mPhotoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.j_kemp.chris.myactivities.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    // Grant camera permission to write into app's private data folder.
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mDuration = (EditText) v.findViewById(R.id.task_duration);
        mDuration.setText(mTask.getDuration());
        mDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setDuration(s.toString());
                updateTask();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Do nothing
            }
        });

        mCommentField = (EditText) v.findViewById(R.id.task_comment);
        mCommentField.setText(mTask.getComment());
        mCommentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setComment(s.toString());
                updateTask();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action
            }
        });

        mSaveButton = (Button) v.findViewById(R.id.save_task);
        mSaveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskLog.get(getActivity()).updateTask(mTask);
                getActivity().finish();
            }
        });

        mDeleteButton = (Button) v.findViewById(R.id.delete_task);
        mDeleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v1) {
                TaskLog.get(getActivity()).deleteTask(mTask);
                getActivity().finish();
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.task_photo);
        updatePhotoView();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTask.setDate(date);
            updateTask();
            updateDate();
        } else if (requestCode == REQUEST_PHOTO) {
            // Stop the camera app accessing internal files while we are not trying to capture.
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.j_kemp.chris.myactivities.fileprovider", mPhotoFile);
            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }
    }

    private void updateTask() {
        TaskLog.get(getActivity()).updateTask(mTask); // Save the changes to the model.
    }

    private void updateDate() {
        mDateButton.setText(getString(R.string.date_button_text, mTask.getFormattedDate()));
    }

    private void updatePlace() {
        mPlaceButton.setText(getString(R.string.task_place_button_text, mTask.getPlaceFriendly()));
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    private void checkPermissionLocation() {
        Log.d(TAG, "Check Permissions: ");
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "Not granted");
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                Log.d(TAG, "Creating Request");
                AlertDialog.Builder requestPermissions = new AlertDialog.Builder(getActivity());
                requestPermissions.setTitle(R.string.permission_location_title)
                        .setMessage(R.string.permission_location_message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(getActivity(),
                                        LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
            }
        }
    }
}
