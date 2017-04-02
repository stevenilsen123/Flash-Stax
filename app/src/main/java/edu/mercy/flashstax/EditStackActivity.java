package edu.mercy.flashstax;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditStackActivity extends AppCompatActivity implements OnItemClickListener{
    ListView listView;
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listCards);
        listView.setOnItemClickListener(this);
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
        Intent intent = new Intent(getApplicationContext(), EditCardActivity.class);
        intent.putExtra("cardName", ((TextView) view).getText());
        startActivityForResult(intent,SET_CARD_NAME_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String newCardName;

        // Check which request we're responding to
        if (requestCode == SET_CARD_NAME_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                newCardName = data.getStringExtra("newCardName");
                Toast.makeText(this, newCardName, Toast.LENGTH_SHORT).show();
            }
        }
    }
}