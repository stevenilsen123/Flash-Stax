package edu.mercy.flashstax;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.mercy.flashstax.database.dao.dbHelper;

public class EditStackActivity extends AppCompatActivity implements OnItemClickListener{
    SQLiteDatabase newDB;
    ArrayList<String> results = new ArrayList<>();
    ArrayList<String> listCards = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    
    EditText stackName;
    EditText category;
    ListView cardsList;
    static final int SET_CARD_NAME_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stack);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addCard);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Bundle extras = new Bundle();
            extras.putString("stackName", String.valueOf(stackName.getText()));

            Intent intent = new Intent(getApplicationContext(), EditCardActivity.class);
            intent.putExtras(extras);
            startActivityForResult(intent,SET_CARD_NAME_REQUEST);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        stackName = (EditText) findViewById(R.id.textStackName);
        stackName.setText(getIntent().getStringExtra("stackName"));
        category = (EditText) findViewById(R.id.textCategory);
        category.setText(getIntent().getStringExtra("category"));

        cardsList = (ListView) findViewById(R.id.listCards);
        openAndQueryDatabase();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listCards);
        cardsList.setAdapter(adapter);

        for (String r: results) {
            addListItem(r);
        }

        cardsList.setOnItemClickListener(this);
        }
    /*
     * Parameters:
        adapter - The AdapterView where the click happened.
        view - The view within the AdapterView that was clicked
        position - The position of the view in the adapter.
        id - The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        String locStackName = String.valueOf(stackName.getText());
        String locCardName = (String) ((TextView) view).getText();
        String frontText;
        String backText;

        // get the front and back text from the database
        removeListItem((int) id);
        dbHelper dataHelper = new dbHelper(this.getApplicationContext());
        newDB = dataHelper.getWritableDatabase();
        String queryText = "SELECT frontText, backText FROM cards " +
                "WHERE stackName = '" + locStackName + "' AND " +
                "name = '" + locCardName + "'";
        Cursor c = newDB.rawQuery(queryText, null);
        c.moveToFirst();
        frontText = c.getString(c.getColumnIndex("frontText"));
        backText = c.getString(c.getColumnIndex("backText"));

        Bundle extras = new Bundle();
        extras.putString("stackName", locStackName);
        extras.putString("cardName", locCardName);
        extras.putString("frontText", frontText);
        extras.putString("backText", backText);

        //remove the card that was click on from the list and the database

        queryText = "DELETE FROM cards " +
                "WHERE stackName = '" + locStackName + "' AND " +
                "name = '" + locCardName + "'";
        newDB.execSQL(queryText);

        Intent intent = new Intent(this.getApplicationContext(), EditCardActivity.class);
        intent.putExtras(extras);
        startActivityForResult(intent, SET_CARD_NAME_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String newCardName;

        // Check which request we're responding to
        if (requestCode == SET_CARD_NAME_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                newCardName = data.getStringExtra("newCardName");
                addListItem(newCardName);
                Toast.makeText(this, newCardName + " added!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openAndQueryDatabase() {
        try {
            dbHelper dataHelper = new dbHelper(this.getApplicationContext());
            newDB = dataHelper.getWritableDatabase();

            String locStackName = String.valueOf(stackName.getText());
            String queryText = "SELECT name FROM cards WHERE stackName = '"+ locStackName +"'";
            Cursor c = newDB.rawQuery(queryText, null);

            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                        String cardName = c.getString(c.getColumnIndex("name"));
                        results.add(cardName);
                    }while (c.moveToNext());
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }
    //--------------------------------
    //  Method to add items to list
    //--------------------------------
    private void addListItem(String item) {
        listCards.add(item);
        adapter.notifyDataSetChanged();
    }//end of addListItem
    //--------------------------------
    //  Method to remove items from list
    //--------------------------------
    private void removeListItem(int index) {
        listCards.remove(index);
        adapter.notifyDataSetChanged();
    }//end of addListItem
}