package edu.mercy.flashstax.database.model;

import java.io.Serializable;
import java.util.Date;

public class Stack implements Serializable {

    public static final String TAG = "Stack";
    private static final long serialVersionUID = -7406082437623008161L;

    private int mId;
    private String mName;
    private String mCategory;
    private boolean mActiveFlag;
    private Date mDateTimeCR;
    private Date mDateTimeLM;

    public Stack() {}

    public Stack(String name, String category, int index, boolean activeFlag, Date dateTimeCR, Date dateTimeLM) {
        this.mName = name;
        this.mCategory = category;
        this.mActiveFlag = activeFlag;
        this.mDateTimeCR = dateTimeCR;
        this.mDateTimeLM = dateTimeLM;
    }

    public int getId() {
        return mId;
    }
    public void setId(int mId) {
        this.mId = mId;
    }
    public String getName() { return mName; }
    public void setName(String mName) {
        this.mName = mName;
    }
    public String getCategory() {
        return mCategory;
    }
    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
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
