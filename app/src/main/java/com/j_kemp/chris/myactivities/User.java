package com.j_kemp.chris.myactivities;

import java.util.UUID;

/**
 * User object for holding values related to the current user.
 * Created by Christopher Kemp on 15/10/17.
 */

public class User {
    private UUID mUUID;
    private String mName;
    private String mEmail;
    private String mGender;
    private String mComment;

    public User() {
        this(UUID.randomUUID());
    }

    public User(UUID id) {
        mUUID = id;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
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
