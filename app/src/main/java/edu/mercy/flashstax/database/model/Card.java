package edu.mercy.flashstax.database.model;

import java.io.Serializable;
import java.util.Date;

public class Card implements Serializable {

    public static final String TAG = "Card";
    private static final long serialVersionUID = -7406082437623008161L;

    private int mId;
    private String mStackName;
    private String mCardName;
    private String mFrontText;
    private String mBackText;
    private boolean mActiveFlag;
    private Date mDateTimeCR;
    private Date mDateTimeLM;

    public Card() {}

    public Card(String stackName, int index, String cardName, String frontText, String backText, boolean activeFlag, Date dateTimeCR, Date dateTimeLM) {
        this.mStackName = stackName;
        this.mCardName = cardName;
        this.mFrontText = frontText;
        this.mBackText = backText;
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
    public String getStackName() {
        return mStackName;
    }
    public void setStackName(String mStackName) {
        this.mStackName = mStackName;
    }
    public String getCardName() {
        return mCardName;
    }
    public void setCardName(String mCardName) {
        this.mCardName = mCardName;
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
    public String getTextByIndex(int index) {
        if (index == 0) {
            return getFrontText();
        } else {
            return getBackText();
        }
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
