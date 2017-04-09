package edu.mercy.flashstax.database.model;

import java.io.Serializable;
import java.util.Date;

public class Category implements Serializable {

    public static final String TAG = "Category";
    private static final long serialVersionUID = -7406082437623008161L;

    private String mName;
    private int mColor;
    private int mIndex;
    private boolean mActiveFlag;
    private Date mDateTimeCR;
    private Date mDateTimeLM;

    public Category() {}

    public Category(String name, int color, int index, boolean activeFlag, Date dateTimeCR, Date dateTimeLM) {
        this.mName = name;
        this.mColor = color;
        this.mIndex = index;
        this.mActiveFlag = activeFlag;
        this.mDateTimeCR = dateTimeCR;
        this.mDateTimeLM = dateTimeLM;
    }

    public String getName() {
        return mName;
    }
    public void setName(String mName) {
        this.mName = mName;
    }
    public int getColor() {
        return mColor;
    }
    public void setColor(int mColor) {
        this.mColor = mColor;
    }
    public int getIndex() {
        return mIndex;
    }
    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
    }
    public boolean getActiveFlag() {
        return mActiveFlag;
    }
    public void setActiveFlag(boolean mActiveFlag) {
        this.mActiveFlag = mActiveFlag;
    }
    public Date getDateTimeCR() {
        return mDateTimeCR;
    }
    public void setDateTimeCR(Date mDateTimeCR) {
        this.mDateTimeCR = mDateTimeCR;
    }
    public Date getDateTimeLM() {
        return mDateTimeLM;
    }
    public void setDateTimeLM(Date mDateTimeLM) {
        this.mDateTimeLM = mDateTimeLM;
    }
}