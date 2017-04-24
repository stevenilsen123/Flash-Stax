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

//----------------------------------------------------------------------
//  This class handles the play screen, which displays cards and their
//  front/back text.  The cards can be cycled through.
//----------------------------------------------------------------------

public class PlayActivity extends AppCompatActivity {

    //------------------------------------
    //  Declarations
    //------------------------------------
    final Context context = this;
    //  Create arrays to later pull in data from strings.xml
    String[] card1;
    String[] card2;
    String[] card3;

    //  Create button holder variables
    private Button buttonFlip;
    private Button buttonNext;

    //  flags
    int cardIndex = 0;
    int sideIndex = 0;
    int numCards = 2;

    //  create variables to hold textviews
    private TextView textCard;
    //private TextView textTip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //  Enable Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //From content_play.xml
        textCard = (TextView) findViewById(R.id.textViewCard);

        //  Flip Button, flip card value.
        buttonFlip=(Button)findViewById(R.id.buttonFlip);
        buttonFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Call flip card method
                flipCard();
            }
        });
        //  Next Button, cycle to next card.
        buttonNext=(Button)findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Call next card method
                nextCard();
            }
        });
        //  Previous Button, cycle to previous card.
        buttonNext=(Button)findViewById(R.id.buttonPrevious);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Call next card method
                previousCard();
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
            if (cardIndex == 1) {
                textCard.setText(card2[sideIndex]);
            }
            else {
                textCard.setText(card3[sideIndex]);
            }
        }
    }//end of nextCard

    //--------------------------------
    //  Method to flip card between front/back
    //--------------------------------
    private void flipCard () {
        //  Check card index, flip then display

        //  placeholder Card 1
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
        else if (cardIndex == 1) {
            if (sideIndex == 0) {
                sideIndex = 1;
                textCard.setText(card2[sideIndex]);
            }
            else {
                sideIndex = 0;
                textCard.setText(card2[sideIndex]);
            }
        }

        //  placeholder card 3
        else {
            if (sideIndex == 0) {
                sideIndex = 1;
                textCard.setText(card3[sideIndex]);
            }
            else {
                sideIndex = 0;
                textCard.setText(card3[sideIndex]);
            }
        }

    }//end of flipCard

    //--------------------------------
    //  Method to cycle backwards through cards
    //--------------------------------
    private void previousCard () {
        //  Check if at end of stack/list
        if (cardIndex == 0) {
            //  if at start, cycle to end
            cardIndex = numCards;
            sideIndex = 0;
            textCard.setText(card3[sideIndex]);
        }
        else {
            //  else decrement through stack/list
            cardIndex--;
            sideIndex = 0;

            //  placeholder card 2
            if (cardIndex == 1) {
                textCard.setText(card2[sideIndex]);
            }
            //  placeholder card 1
            else {
                textCard.setText(card1[sideIndex]);
            }
        }
    }//end of previousCard

}//end of class
