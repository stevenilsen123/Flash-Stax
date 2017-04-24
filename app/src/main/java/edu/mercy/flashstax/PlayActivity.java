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

import java.util.List;

import edu.mercy.flashstax.database.dao.StackDAO;
import edu.mercy.flashstax.database.model.Card;
import edu.mercy.flashstax.database.model.Stack;

//----------------------------------------------------------------------
//  This class handles the play screen, which displays cards and their
//  front/back text.  The cards can be cycled through.
//----------------------------------------------------------------------

public class PlayActivity extends AppCompatActivity {

    //------------------------------------
    //  Declarations
    //------------------------------------
    String passedStackName;
    StackDAO stackDAO;
    Stack stack;
    List<Card> listCards;

    //  Create button holder variables
    private Button buttonFlip;
    private Button buttonNext;
    private Button buttonFinished;

    //  flags
    int cardIndex = 0;
    int sideIndex = 0;
    int numCards;

    //  create variables to hold textviews
    private TextView textCard;
    //private TextView textTip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //  disable Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        passedStackName = getIntent().getStringExtra("stackName");
        stackDAO = new StackDAO(getApplicationContext());
        stack = stackDAO.getStackByName(passedStackName);
        listCards = stack.getCards();
        numCards = listCards.size();

        //From content_play.xml
        textCard = (TextView) findViewById(R.id.textViewCard);
        textCard.setText(listCards.get(0).getFrontText());

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
        //  Finished Button, go back to main activity.
        buttonFinished=(Button)findViewById(R.id.buttonFinished);
        buttonFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Call finished method
                finished();
            }
        });

    }//End of onCreate


    //--------------------------------
    //  Method to cycle through cards
    //--------------------------------
    private void nextCard () {
        //  Check if at end of stack/list
        if (cardIndex >= numCards-1) {
            //  if at end, cycle back to start
            cardIndex = 0;
            sideIndex = 0;
            textCard.setText(listCards.get(cardIndex).getTextByIndex(sideIndex));
        }
        else {
            //  else increment through stack/list
            cardIndex++;
            sideIndex = 0;
            textCard.setText(listCards.get(cardIndex).getTextByIndex(sideIndex));
        }
    }//end of nextCard

    //--------------------------------
    //  Method to flip card between front/back
    //--------------------------------
    private void flipCard () {
        //  Check card index, flip then display
        if (sideIndex == 0) {
            sideIndex = 1;
        } else {
            sideIndex = 0;
        }
        textCard.setText(listCards.get(cardIndex).getTextByIndex(sideIndex));

    }//end of flipCard

    //--------------------------------
    //  Method to cycle backwards through cards
    //--------------------------------
    private void previousCard () {
        //  Check if at end of stack/list
        if (cardIndex == 0) {
            //  if at start, cycle to end
            cardIndex = numCards-1;
            sideIndex = 0;
            textCard.setText(listCards.get(cardIndex).getTextByIndex(sideIndex));
        }
        else {
            //  else decrement through stack/list
            cardIndex--;
            sideIndex = 0;
            textCard.setText(listCards.get(cardIndex).getTextByIndex(sideIndex));
        }
    }//end of previousCard

    //--------------------------------
    //  Method to exit back to the main screen
    //--------------------------------
    private void finished () {
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(intent);
    }//end of finished

    @Override
    public void onBackPressed() {
        //do nothing
    }

}//end of class
