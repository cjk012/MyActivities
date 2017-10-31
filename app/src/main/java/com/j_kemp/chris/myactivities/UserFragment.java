package com.j_kemp.chris.myactivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Fragment for managing user details.
 * Created by Christopher Kemp on 31/10/17.
 */

public class UserFragment extends Fragment {
//    private static final String TAG = "UserSettings";
//    private static final String EXTRA_USER_UUID = "uuid";

    private User mUser;
    private EditText mName;
    private EditText mEmail;
    private EditText mUserID;
    private EditText mGender;
    private EditText mComment;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUser = UserManager.get(getActivity()).getUser(getString(R.string.user_fixed_id));

        if (mUser == null) {
            mUser = new User(getString(R.string.user_fixed_id));
            mUser.setName(getString(R.string.dummy_user_name));
            mUser.setEmail(getString(R.string.dummy_user_email));
            mUser.setUserID(getString(R.string.dummy_user_id));
            mUser.setGender(getString(R.string.dummy_user_gender));
            mUser.setComment(getString(R.string.dummy_user_comment));
            UserManager.get(getActivity()).addUser(mUser);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        mName = (EditText) v.findViewById(R.id.user_name);
        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setName(s.toString());
                updateUser();
            }

            @Override
            public void afterTextChanged(Editable s) { /* No Action */ }
        });

        mEmail = (EditText) v.findViewById(R.id.user_email);
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setEmail(s.toString());
                updateUser();
            }

            @Override
            public void afterTextChanged(Editable s) { /* No Action */ }
        });

        mUserID = (EditText) v.findViewById(R.id.user_id);
        mUserID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setUserID(s.toString());
                updateUser();
            }

            @Override
            public void afterTextChanged(Editable s) { /* No Action */ }
        });

        mGender = (EditText) v.findViewById(R.id.user_gender);
        mGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* No action */ }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setGender(s.toString());
                updateUser();
            }

            @Override
            public void afterTextChanged(Editable s) { /* No Action */ }
        });

        mComment = (EditText) v.findViewById(R.id.user_comment);
        mComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* No action */ }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setComment(s.toString());
                updateUser();
            }

            @Override
            public void afterTextChanged(Editable s) { /* No Action */ }
        });

        updateView();
        return v;
    }

    @Override
    public void onPause(){
        super.onPause();
        UserManager.get(getActivity()).updateUser(mUser);
    }

    private void updateView(){
        mName.setText(mUser.getName());
        mEmail.setText(mUser.getEmail());
        mUserID.setText(mUser.getUserID());
        mGender.setText(mUser.getGender());
        mComment.setText(mUser.getComment());
    }

    private void updateUser(){
        UserManager.get(getActivity()).updateUser(mUser);
    }
}
