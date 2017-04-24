package edu.mercy.flashstax.database.model;

import java.io.Serializable;
import java.util.List;

public class Stack implements Serializable {

    public static final String TAG = "Stack";
    private static final long serialVersionUID = -7406082437623008161L;

    private int mId;
    private String mName;
    private String mCategory;
    private boolean mActiveFlag;
    private int mDateTimeCR;
    private int mDateTimeLM;
    private List<Card> mCards;

    public Stack() {}

    public Stack(String name, String category, int index, boolean activeFlag, int dateTimeCR,
                 int dateTimeLM, List<Card> cards) {
        this.mName = name;
        this.mCategory = category;
        this.mActiveFlag = activeFlag;
        this.mDateTimeCR = dateTimeCR;
        this.mDateTimeLM = dateTimeLM;
        this.mCards = cards;
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
    public int getDateTimeCR() {
        return mDateTimeCR;
    }
    public void setDateTimeCR(int mDateTimeCR) {
        this.mDateTimeCR = mDateTimeCR;
    }
    public int getDateTimeLM() {
        return mDateTimeLM;
    }
    public void setDateTimeLM(int mDateTimeLM) {
        this.mDateTimeLM = mDateTimeLM;
    }
    public List<Card> getCards() { return mCards; }
    public void setCards(List<Card> mCards) { this.mCards = mCards; }
}
