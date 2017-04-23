package edu.mercy.flashstax;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.mercy.flashstax.database.dao.CardDAO;
import edu.mercy.flashstax.database.dao.dbHelper;
import edu.mercy.flashstax.database.model.Card;

public class EditCardActivity extends AppCompatActivity implements OnClickListener {
    public static final String TAG = "EditCardActivity";
    SQLiteDatabase newDB;
    ArrayList<String> results = new ArrayList<>();
    private CardDAO mCardDao;

    TextView stackName;
    EditText cardName;
    EditText frontText;
    EditText backText;
    Button saveButton;
    Button cancelButton;

    String passedStackName;
    String passedCardName;
    String passedFrontText;
    String passedBackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mCardDao = new CardDAO(this);

        // set ui element variables
        stackName = (TextView) findViewById(R.id.textStackName);
        cardName = (EditText) findViewById(R.id.textCardName);
        frontText = (EditText) findViewById(R.id.textFront);
        backText = (EditText) findViewById(R.id.textBack);
        saveButton = (Button) findViewById(R.id.buttonSave);
        cancelButton = (Button) findViewById(R.id.buttonCancel);

        passedStackName = getIntent().getStringExtra("stackName");
        passedCardName = getIntent().getStringExtra("cardName");
        passedFrontText = getIntent().getStringExtra("frontText");
        passedBackText = getIntent().getStringExtra("backText");

        stackName.setText(passedStackName);
        cardName.setText(passedCardName);
        frontText.setText(passedFrontText);
        backText.setText(passedBackText);

        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        int id = view.getId();
        boolean allFieldsFilled;

        if (id == R.id.buttonSave) {
            // make sure all fields are filled in
            allFieldsFilled = true;

            String stackname = String.valueOf(stackName.getText());
            String cardname = String.valueOf(cardName.getText());
            String fronttext = String.valueOf(frontText.getText());
            String backtext = String.valueOf(backText.getText());

            if (cardname.equals("") || fronttext.equals("") || backtext.equals("")) {
                Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
                allFieldsFilled = false;
            }

            //if all fields filled in
            if (allFieldsFilled) {
                // add new card to database
                Card createdCard = mCardDao.createCard(stackname, cardname, fronttext, backtext);
                Log.d(TAG, "added card : "+ createdCard.getCardName());

                // return card name to edit stack
                Intent returnIntent = new Intent();
                returnIntent.putExtra("newCardName",this.cardName.getText().toString());
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        } else if (id == R.id.buttonCancel) {
            //add card back to database if this was an edit
            if (passedCardName != null) {
                mCardDao = new CardDAO(this.getApplicationContext());
                mCardDao.createCard(passedStackName,passedCardName,passedFrontText,passedBackText);
            }

            //return to previous screen
            Intent returnIntent = new Intent();
            returnIntent.putExtra("oldCardName", passedCardName);
            setResult(RESULT_CANCELED, returnIntent);
            finish();
        }
    }
    private void openAndQueryDatabase() {
        try {
            dbHelper dataHelper = new dbHelper(this.getApplicationContext());
            newDB = dataHelper.getWritableDatabase();

            String locStackName = String.valueOf(stackName.getText());
            String locCardName = String.valueOf(cardName.getText());
            String queryText = "SELECT frontText, backText FROM cards " +
                    "WHERE stackName = '"+ locStackName +"' AND " +
                    "name = '"+ locCardName +"'";
            Cursor c = newDB.rawQuery(queryText, null);

            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                        String frontText = c.getString(c.getColumnIndex("frontText"));
                        String backText = c.getString(c.getColumnIndex("backText"));
                        results.add(frontText);
                        results.add(backText);
                    }while (c.moveToNext());
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }
}
