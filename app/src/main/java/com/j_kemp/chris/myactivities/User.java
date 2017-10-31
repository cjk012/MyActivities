package com.j_kemp.chris.myactivities;

import java.io.Serializable;

/**
 * User object for holding values related to the user.
 * Created by Christopher Kemp on 15/10/17.
 */

public class User implements Serializable {
    private String mID;
    private String mName;
    private String mEmail;
    private String mUserID;
    private String mGender;
    private String mComment;

    public User() {
        this("777");
    }

    public User(String id) {
        mID = id;
    }

    public String getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String userID) {
        mUserID = userID;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }
}
