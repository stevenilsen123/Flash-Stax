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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.mercy.flashstax.database.dao.StackDAO;
import edu.mercy.flashstax.database.dao.dbHelper;
import edu.mercy.flashstax.database.model.Stack;

public class EditStackActivity extends AppCompatActivity implements OnItemClickListener{
    SQLiteDatabase newDB;
    StackDAO stackDAO;
    ArrayList<String> results = new ArrayList<>();
    ArrayList<String> listCards = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    
    EditText stackName;
    EditText category;
    ListView cardsList;
    Button playButton;
    Button saveButton;
    Button deleteButton;
    Button cancelButton;

    String passedStackName;
    String passedCategory;
    long passedPosition;
    
    static final int EDIT_CARD_REQUEST = 1;
    static final int ADD_CARD_REQUEST = 2;

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
                startActivityForResult(intent,ADD_CARD_REQUEST);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        passedStackName = getIntent().getStringExtra("stackName");
        passedCategory = getIntent().getStringExtra("category");
        passedPosition = getIntent().getLongExtra("position", -1);

        stackName = (EditText) findViewById(R.id.textStackName);
        stackName.setText(passedStackName);
        category = (EditText) findViewById(R.id.textCategory);
        category.setText(passedCategory);

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
        
        // set up buttons
        playButton = (Button) findViewById(R.id.buttonPlay);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("this is a test","test");
            }
        });

        saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //override stack name and category in the database
                stackDAO = new StackDAO(getApplicationContext());
                Stack myStack = stackDAO.getStackByName(passedStackName);

                String newStackName = String.valueOf(stackName.getText());
                String newCategory = String.valueOf(category.getText());

                stackDAO.updateStack(myStack, newStackName, newCategory);

                //return to previous screen
                Intent returnIntent = new Intent();
                returnIntent.putExtra("newStackName", newStackName);
                returnIntent.putExtra("position", passedPosition);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("this is a test","test");
            }
        });

        cancelButton = (Button) findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("this is a test","test");
            }
        });
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
        startActivityForResult(intent, EDIT_CARD_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String newCardName;
        String oldCardName;
        boolean deleteFlag;

        // Check which request we're responding to
        if (requestCode == EDIT_CARD_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                deleteFlag = data.getBooleanExtra("deleteFlag",false);
                if (deleteFlag == false) {
                    newCardName = data.getStringExtra("newCardName");
                    addListItem(newCardName);
                    Toast.makeText(this, newCardName + " added!", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                oldCardName = data.getStringExtra("oldCardName");
                addListItem(oldCardName);
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