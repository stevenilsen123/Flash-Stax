package edu.mercy.flashstax;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {

    //------------------------------------
    //  Declarations
    //------------------------------------
    final Context context = this;
    //  Create arrays to later pull in data from strings.xml
    String[] card1;
    String[] card2;

    //  Create button holder variables
    private Button buttonFlip;
    private Button buttonNext;

    //  flags
    int cardIndex = 0;
    int sideIndex = 0;
    int numCards = 1;


    //  create variables to hold textviews
    private TextView textCard;
    private TextView textTip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //From content_play.xml
        textCard = (TextView) findViewById(R.id.textViewCard);

        //  Display helper text?
        //textTip = (TextView) findViewById(R.id.textViewTip);

        //  Get arrays from strings.xml
        Resources res = getResources();
        card1 = res.getStringArray(R.array.card1);
        card2 = res.getStringArray(R.array.card2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Text popup at bottom
                Snackbar.make(view, "Button pressed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();



            }
        });//end of fab

        //  Flip Button, go to play screen.
        buttonFlip=(Button)findViewById(R.id.buttonFlip);
        buttonFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Call flip card method
                flipCard();
            }
        });
        //  Play Button, go to play screen.
        buttonNext=(Button)findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Call next card method
                nextCard();
            }
        });

    }//End of onCreate


    //--------------------------------
    //  Method to cycle through cards
    //--------------------------------
    private void nextCard () {
        //  Check if at end of stack/list
        if (cardIndex >= numCards) {
            //  if at end, cycle back to start
            cardIndex = 0;
            sideIndex = 0;
            textCard.setText(card1[sideIndex]);
        }
        else {
            //  else increment through stack/list
            cardIndex++;
            sideIndex = 0;
            textCard.setText(card2[sideIndex]);
        }
    }//end of nextCard

    //--------------------------------
    //  Method to flip card between front/back
    //--------------------------------
    private void flipCard () {
        //  Check card index, flip then display

        //  placerholder Card 1
        if (cardIndex == 0) {
            if (sideIndex == 0) {
                sideIndex = 1;
                textCard.setText(card1[sideIndex]);
            }
            else {
                sideIndex = 0;
                textCard.setText(card1[sideIndex]);
            }
        }
        // placeholder card 2
        else {
            if (sideIndex == 0) {
                sideIndex = 1;
                textCard.setText(card2[sideIndex]);
            }
            else {
                sideIndex = 0;
                textCard.setText(card2[sideIndex]);
            }
        }
    }//end of flipCard


}//end of class
