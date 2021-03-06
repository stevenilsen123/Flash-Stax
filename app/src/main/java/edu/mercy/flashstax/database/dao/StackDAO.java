package edu.mercy.flashstax.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import edu.mercy.flashstax.database.model.Stack;
import edu.mercy.flashstax.database.model.Card;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StackDAO {

	public static final String TAG = "StackDAO";

	// Database fields
	private SQLiteDatabase mDatabase;
	private dbHelper mDbHelper;
	private Context mContext;
	private String[] mAllColumns = {
			dbHelper.COL_STACK_ID,
			dbHelper.COL_STACK_NAME,
			dbHelper.COL_STACK_CATEGORY,
			dbHelper.COL_STACK_ACTIVE_FLAG,
			dbHelper.COL_STACK_DATE_TIME_CR,
			dbHelper.COL_STACK_DATE_TIME_LM
	};

	public StackDAO(Context context) {
		this.mContext = context;
		mDbHelper = new dbHelper(context);
		// open the database
		try {
			open();
		} catch (SQLException e) {
			Log.e(TAG, "SQLException on opening database " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void open() throws SQLException {
		mDatabase = mDbHelper.getWritableDatabase();
	}

	public void close() {
		mDbHelper.close();
	}

	public Stack createStack(String stackName, String stackCategory) {
		ContentValues values = new ContentValues();
		
		values.put(dbHelper.COL_STACK_NAME, stackName);
		values.put(dbHelper.COL_STACK_CATEGORY, stackCategory);
		values.put(dbHelper.COL_STACK_ACTIVE_FLAG, true);
		values.put(dbHelper.COL_STACK_DATE_TIME_CR, (int) new Date().getTime()/1000);
		values.put(dbHelper.COL_STACK_DATE_TIME_LM, (int) new Date().getTime()/1000);
		
		long insertId = mDatabase.insert(
				dbHelper.TABLE_STACKS, null, values);
		Cursor cursor = mDatabase.query(
				dbHelper.TABLE_STACKS, mAllColumns,
				dbHelper.COL_STACK_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		Stack newStack = cursorToStack(cursor);
		cursor.close();
		return newStack;
	}

	public void deleteStack(Stack stack) {
		int id = stack.getId();
		// delete all cards of this stack
		CardDAO cardDao = new CardDAO(mContext);
		List<Card> listCards = cardDao.getCardsOfStack(id);
		if (listCards != null && !listCards.isEmpty()) {
			for (Card e : listCards) {
				cardDao.deleteCard(e);
			}
		}

		System.out.println("the deleted stack has the id: " + id);
		mDatabase.delete(dbHelper.TABLE_STACKS, dbHelper.COL_STACK_ID
				+ " = " + id, null);
	}

	public void updateStack(Stack stack, String newName, String newCategory) {
		ContentValues values = new ContentValues();

		values.put(dbHelper.COL_STACK_NAME, newName);
		values.put(dbHelper.COL_STACK_CATEGORY, newCategory);
		values.put(dbHelper.COL_STACK_ACTIVE_FLAG, true);
		values.put(dbHelper.COL_STACK_DATE_TIME_CR, stack.getDateTimeCR());
		values.put(dbHelper.COL_STACK_DATE_TIME_LM, (int) new Date().getTime()/1000);

		int id = stack.getId();
		mDatabase.update(dbHelper.TABLE_STACKS, values, dbHelper.COL_STACK_ID + " = ?",
				new String[] {Integer.toString(id)});
	}

	public List<Stack> getAllStacks() {
		List<Stack> listStacks = new ArrayList<Stack>();

		Cursor cursor = mDatabase.query(dbHelper.TABLE_STACKS, mAllColumns,
				null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Stack stack = cursorToStack(cursor);
				listStacks.add(stack);
				cursor.moveToNext();
			}

			// make sure to close the cursor
			cursor.close();
		}
		return listStacks;
	}

	public Stack getStackByName(String name) {
		Cursor cursor = mDatabase.query(dbHelper.TABLE_STACKS, mAllColumns,
				dbHelper.COL_STACK_NAME + " = ?",
				new String[] { name }, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}

		Stack stack = cursorToStack(cursor);
		return stack;
	}

	public Stack getStackById(int id) {
		Cursor cursor = mDatabase.query(dbHelper.TABLE_STACKS, mAllColumns,
				dbHelper.COL_STACK_ID + " = ?",
				new String[] { String.valueOf(id) }, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}

		Stack stack = cursorToStack(cursor);
		return stack;
	}

	protected Stack cursorToStack(Cursor cursor) {
		Stack stack = new Stack();
		stack.setId(cursor.getInt(0));
		stack.setName(cursor.getString(1));
		stack.setCategory(cursor.getString(2));
		stack.setActiveFlag(cursor.getInt(3) > 0);
		stack.setDateTimeCR(cursor.getInt(4));
		stack.setDateTimeLM(cursor.getInt(5));

		// get all cards of this stack
		int id = stack.getId();
		CardDAO cardDao = new CardDAO(mContext);
		List<Card> listCards = cardDao.getCardsOfStack(id);
		stack.setCards(listCards);

		return stack;
	}

}
