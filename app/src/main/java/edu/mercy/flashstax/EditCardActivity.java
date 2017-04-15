package edu.mercy.flashstax;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditCardActivity extends AppCompatActivity implements OnClickListener {
    EditText cardName;
    EditText frontText;
    EditText backText;
    Button saveButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // set ui element variables
        cardName = (EditText) findViewById(R.id.textCardName);
        frontText = (EditText) findViewById(R.id.textFront);
        backText = (EditText) findViewById(R.id.textBack);
        saveButton = (Button) findViewById(R.id.buttonSave);
        cancelButton = (Button) findViewById(R.id.buttonCancel);

        cardName.setText(getIntent().getStringExtra("cardName"));

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


                // return card name to edit stack
                Intent returnIntent = new Intent();
                returnIntent.putExtra("newCardName",this.cardName.getText().toString());
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        } else if (id == R.id.buttonCancel) {
            Intent returnIntent = new Intent();
            setResult(RESULT_CANCELED, returnIntent);
            finish();
        }
    }
}
