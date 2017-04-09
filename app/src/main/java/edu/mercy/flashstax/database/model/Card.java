package edu.mercy.flashstax.database.model;

import java.io.Serializable;
import java.util.Date;

public class Card implements Serializable {

    public static final String TAG = "Card";
    private static final long serialVersionUID = -7406082437623008161L;

    private String mStackName;
    private int mIndex;
    private String mFrontText;
    private String mBackText;
    private boolean mActiveFlag;
    private Date mDateTimeCR;
    private Date mDateTimeLM;

    public Card() {}

    public Card(String stackName, int index, String frontText, String backText, boolean activeFlag, Date dateTimeCR, Date dateTimeLM) {
        this.mStackName = stackName;
        this.mIndex = index;
        this.mFrontText = frontText;
        this.mBackText = backText;
        this.mActiveFlag = activeFlag;
        this.mDateTimeCR = dateTimeCR;
        this.mDateTimeLM = dateTimeLM;
    }

    public String getStackName() {
        return mStackName;
    }
    public void setStackName(String mStackName) {
        this.mStackName = mStackName;
    }
    public int getIndex() {
        return mIndex;
    }
    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
    }
    public String getFrontText() {
        return mFrontText;
    }
    public void setFrontText(String mFrontText) {
        this.mFrontText = mFrontText;
    }
    public String getBackText() {
        return mBackText;
    }
    public void setBackText(String mBackText) {
        this.mBackText = mBackText;
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
