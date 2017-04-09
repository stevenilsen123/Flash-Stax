package edu.mercy.flashstax.database.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "DBHelper";

    public static final String DATABASE_NAME = "FlashStax.db";
    public static final int DATABASE_VER = 1;

    // cards table
    public static final String TABLE_CARDS = "cards";
    public static final String COL_CARD_STACK_NAME = "stackName";
    public static final String COL_CARD_INDEX = "index";
    public static final String COL_CARD_FRONT_TEXT = "frontText";
    public static final String COL_CARD_BACK_TEXT = "backText";
    public static final String COL_CARD_ACTIVE_FLAG = "activeFlag";
    public static final String COL_CARD_DATE_TIME_CR = "dateTimeCR";
    public static final String COL_CARD_DATE_TIME_LM = "dateTimeLM";

    public static final String SQL_CREATE_TABLE_CARDS = "CREATE TABLE " + TABLE_CARDS + "("
            + COL_CARD_STACK_NAME + " VARCHAR(50),"
            + COL_CARD_INDEX + " INTEGER,"
            + COL_CARD_FRONT_TEXT + " VARCHAR(500),"
            + COL_CARD_BACK_TEXT + " VARCHAR(500),"
            + COL_CARD_ACTIVE_FLAG + " BOOLEAN,"
            + COL_CARD_DATE_TIME_CR + " TIMESTAMP,"
            + COL_CARD_DATE_TIME_LM + " TIMESTAMP"
            + ");";

    // stacks table
    public static final String TABLE_STACKS = "stacks";
    public static final String COL_STACK_NAME = "name";
    public static final String COL_STACK_CATEGORY = "category";
    public static final String COL_STACK_INDEX = "index";
    public static final String COL_STACK_ACTIVE_FLAG = "activeFlag";
    public static final String COL_STACK_DATE_TIME_CR = "dateTimeCR";
    public static final String COL_STACK_DATE_TIME_LM = "dateTimeLM";

    public static final String SQL_CREATE_TABLE_STACKS = "CREATE TABLE " + TABLE_STACKS + "("
            + COL_STACK_NAME + " VARCHAR(50),"
            + COL_STACK_CATEGORY + " VARCHAR(50),"
            + COL_STACK_INDEX + " INTEGER,"
            + COL_STACK_ACTIVE_FLAG + " BOOLEAN,"
            + COL_STACK_DATE_TIME_CR + " TIMESTAMP,"
            + COL_STACK_DATE_TIME_LM + " TIMESTAMP"
            + ");";

    // categories table
    public static final String TABLE_CATEGORIES = "categories";
    public static final String COL_CAT_NAME = "name";
    public static final String COL_CAT_COLOR = "color";
    public static final String COL_CAT_INDEX = "index";
    public static final String COL_CAT_ACTIVE_FLAG = "activeFlag";
    public static final String COL_CAT_DATE_TIME_CR = "dateTimeCR";
    public static final String COL_CAT_DATE_TIME_LM = "dateTimeLM";

    public static final String SQL_CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + "("
            + COL_CAT_NAME + " VARCHAR(50),"
            + COL_CAT_COLOR + " INTEGER,"
            + COL_CAT_INDEX + " INTEGER,"
            + COL_CAT_ACTIVE_FLAG + " BOOLEAN,"
            + COL_CAT_DATE_TIME_CR + " TIMESTAMP,"
            + COL_CAT_DATE_TIME_LM + " TIMESTAMP"
            + ");";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_CARDS);
        db.execSQL(SQL_CREATE_TABLE_CATEGORIES);
        db.execSQL(SQL_CREATE_TABLE_STACKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to "+ newVersion);
        // clear all data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STACKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);

        // recreate the tables
        onCreate(db);
    }
}