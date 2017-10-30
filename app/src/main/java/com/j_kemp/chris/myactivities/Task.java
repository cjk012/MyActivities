package com.j_kemp.chris.myactivities;

import android.text.format.DateFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Task Object for the My Activities log application.
 * Created by Christopher Kemp on 12/10/17.
 */

public class Task implements Serializable {
    private UUID mID;
    private String mTitle;
    private Date mDate;
    private int mType;
    private String mDuration;
    private double mPlaceLat;
    private double mPlaceLon;
    private String mPlaceFriendly;
    private String mComment;

    public Task() {
        this(UUID.randomUUID()); // Pass new random Unique ID (UUID) to constructor
    }

    public Task(UUID id) {
        mID = id;
        mDate = new Date();
    }

    public UUID getID() {
        return mID;
    }

    /* Generated setter method for UUID never used, as UUID is generated on creation,
            and re-used on read from db.
    public void setID(UUID UUID) {
        mID = UUID;
    }*/

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public String getFormattedDate() {
        DateFormat df = new DateFormat();
        return df.format("yyyy MMM dd", mDate).toString();
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public double getPlaceLat() {
        return mPlaceLat;
    }

    public void setPlaceLat(double placeLat) {
        mPlaceLat = placeLat;
    }

    public double getPlaceLon() {
        return mPlaceLon;
    }

    public void setPlaceLon(double placeLon) {
        mPlaceLon = placeLon;
    }

    public String getPlaceFriendly() {
        if (mPlaceFriendly == null || mPlaceFriendly == "") {
            return "No Location Set";
        } else {
            return mPlaceFriendly;
        }
    }

    public void setPlaceFriendly(String placeFriendly) {
        mPlaceFriendly = placeFriendly;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getPhotoFilename() {
        return "IMG_" + getID().toString() + ".jpg";
    }
}
