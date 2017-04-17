package edu.mercy.flashstax;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.mercy.flashstax.database.dao.CardDAO;
import edu.mercy.flashstax.database.dao.StackDAO;
import edu.mercy.flashstax.database.dao.dbHelper;
import edu.mercy.flashstax.database.model.Card;
import edu.mercy.flashstax.database.model.Stack;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //------------------------------------
    //  Declarations
    //------------------------------------
    public static final String TAG = "Main2Activity";
    private StackDAO mStackDao;
    SQLiteDatabase newDB;
    ArrayList<String> results = new ArrayList<>();

    final Context context = this;
    static final int SET_STACK_NAME_REQUEST = 1;
    String input;

    //  List view stuff
    //  **************** Note, add to list method at bottom - 'addListItem'
    ArrayList<String> listStacks = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private ListView stackList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStackDao = new StackDAO(this);

        //  List view setup
        stackList = (ListView) findViewById(R.id.listStacks);
        openAndQueryDatabase();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listStacks);
        stackList.setAdapter(adapter);

        for (String r: results) {
            addListItem(r);
        }
        stackList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Bundle extras = new Bundle();
                extras.putString("stackName", (String) ((TextView) view).getText());
                extras.putString("category", "category1");

                Intent intent = new Intent(getApplicationContext(), EditStackActivity.class);
                intent.putExtras(extras);
                startActivityForResult(intent,SET_STACK_NAME_REQUEST);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addStack);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //---------------------
                // Input Dialog popup
                //---------------------
                //Upon button press, create user dialogue box
                //Get input, set to variable, then add variable to list.

                //  get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                //  sets prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                //  set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        //  Enter button logic
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Get user input, convert to string, and store
                                        input = userInput.getText().toString();
                                        //  Add to list
                                        addListItem(input);
                                        // Add to database
                                        Stack createdStack = mStackDao.createStack(input, "category1");
                                        Log.d(TAG, "added stack : "+ createdStack.getName());
                                    }
                                })
                        //  Cancel button logic
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                //  create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                //  display
                alertDialog.show();




            }//end of onClick
        }); //*******End of fab listener


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cardStacks) {
            Intent newAct = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(newAct);
        } else if (id == R.id.nav_categories) {
            Intent newAct = new Intent(getApplicationContext(), CategoriesActivity.class);
            startActivity(newAct);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //  Method to go to edit stack screen?
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), EditCardActivity.class);
        intent.putExtra("cardName", ((TextView) view).getText());
        startActivityForResult(intent,SET_STACK_NAME_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String newCardName;

        // Check which request we're responding to
        if (requestCode == SET_STACK_NAME_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                newCardName = data.getStringExtra("newCardName");
                Toast.makeText(this, newCardName, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //--------------------------------
    //  Method to add items to list
    //--------------------------------
    private void addListItem(String item) {
        listStacks.add(item);
        adapter.notifyDataSetChanged();

    }//end of addListItem

    private void openAndQueryDatabase() {
        try {
            dbHelper dataHelper = new dbHelper(this.getApplicationContext());
            newDB = dataHelper.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT name FROM stacks", null);

            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                        String stackName = c.getString(c.getColumnIndex("name"));
                        results.add(stackName);
                    }while (c.moveToNext());
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }

}//end of class
