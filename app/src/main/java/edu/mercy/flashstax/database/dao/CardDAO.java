package edu.mercy.flashstax.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import edu.mercy.flashstax.database.model.Stack;
import edu.mercy.flashstax.database.model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class CardDAO {

	public static final String TAG = "CardDAO";

	private Context mContext;

	// Database fields
	private SQLiteDatabase mDatabase;
	private dbHelper mDbHelper;
	private String[] mAllColumns = { 
			dbHelper.COL_CARD_ID,
			dbHelper.COL_CARD_STACK_NAME,
			dbHelper.COL_CARD_NAME,
			dbHelper.COL_CARD_FRONT_TEXT,
			dbHelper.COL_CARD_BACK_TEXT,
			dbHelper.COL_CARD_ACTIVE_FLAG,
			dbHelper.COL_CARD_DATE_TIME_CR,
			dbHelper.COL_CARD_DATE_TIME_LM,
			dbHelper.COL_CARD_STACK_ID };

	public CardDAO(Context context) {
		mDbHelper = new dbHelper(context);
		this.mContext = context;
		// open the database
		try {
			open();
		} catch (SQLException e) {
			Log.e(TAG, "SQLException on openning database " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void open() throws SQLException {
		mDatabase = mDbHelper.getWritableDatabase();
	}

	public void close() {
		mDbHelper.close();
	}

	public Card createCard(String stackName, String cardName, String frontText, String backText) {
		ContentValues values = new ContentValues();

		values.put(dbHelper.COL_CARD_STACK_NAME, stackName);
		values.put(dbHelper.COL_CARD_NAME, cardName);
		values.put(dbHelper.COL_CARD_FRONT_TEXT, frontText);
		values.put(dbHelper.COL_CARD_BACK_TEXT, backText);
		values.put(dbHelper.COL_CARD_ACTIVE_FLAG, true);
		values.put(dbHelper.COL_CARD_DATE_TIME_CR, (int) new Date().getTime()/1000);
		values.put(dbHelper.COL_CARD_DATE_TIME_LM, (int) new Date().getTime()/1000);

		long insertId = mDatabase.insert(
				dbHelper.TABLE_CARDS, null, values);
		Cursor cursor = mDatabase.query(
				dbHelper.TABLE_CARDS, mAllColumns,
				dbHelper.COL_CARD_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		Card newCard = cursorToCard(cursor);
		cursor.close();
		return newCard;
	}

	public void deleteCard(Card card) {
		long id = card.getId();
		System.out.println("the deleted card has the id: " + id);
		mDatabase.delete(dbHelper.TABLE_CARDS, dbHelper.COL_CARD_ID
				+ " = " + id, null);
	}

	public List<Card> getAllCards() {
		List<Card> listCards = new ArrayList<Card>();

		Cursor cursor = mDatabase.query(dbHelper.TABLE_CARDS, mAllColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Card card = cursorToCard(cursor);
			listCards.add(card);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return listCards;
	}

	public List<Card> getCardsOfStack(int id) {
		List<Card> listCards = new ArrayList<Card>();

		Cursor cursor = mDatabase.query(dbHelper.TABLE_CARDS, mAllColumns,
				dbHelper.COL_STACK_ID + " = ?",
				new String[] { String.valueOf(id) }, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Card card = cursorToCard(cursor);
			listCards.add(card);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return listCards;
	}

	private Card cursorToCard(Cursor cursor) {
		Card card = new Card();
		card.setId(cursor.getInt(0));
		card.setStackName(cursor.getString(1));
		card.setCardName(cursor.getString(2));
		card.setFrontText(cursor.getString(3));
		card.setBackText(cursor.getString(4));
		card.setActiveFlag(cursor.getInt(5) > 0);
		card.setDateTimeCR(new Date(cursor.getLong(6)));
		card.setDateTimeLM(new Date(cursor.getLong(7)));

		// get The stack by id
		int stackId = cursor.getInt(8);
		StackDAO dao = new StackDAO(mContext);
		Stack stack = dao.getStackById(stackId);
		if (stack != null)
			card.setStack(stack);

		return card;
	}

}
