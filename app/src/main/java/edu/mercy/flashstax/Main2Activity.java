package edu.mercy.flashstax;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.mercy.flashstax.database.dao.StackDAO;
import edu.mercy.flashstax.database.dao.dbHelper;
import edu.mercy.flashstax.database.model.Stack;

//----------------------------------------------------------------------
//  This class handles the main screen of the app, which allows you to add stacks
//  and select them to be edited.
//----------------------------------------------------------------------

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
    static final int EDIT_STACK_REQUEST = 1;
    String input;

    private Button buttonPlay;

    //  List view stuff
    //  **************** Note, add to list method at bottom - 'addListItem'
    ArrayList<String> listStacks = new ArrayList<>();
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

        adapter = new ArrayAdapter<>(this,
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
                extras.putLong("position", position);

                Intent intent = new Intent(getApplicationContext(), EditStackActivity.class);
                intent.putExtras(extras);
                startActivityForResult(intent,EDIT_STACK_REQUEST);
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
                        //probably .set something to nab text from clicked item
                        .setMessage("Enter stack name: ")
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

        //  Play Button, go to play screen.
        buttonPlay=(Button)findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),PlayActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // do nothing;
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String newStackName;
        long position;
        String returnedFrom;

        // Check which request we're responding to
        if (requestCode == EDIT_STACK_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                returnedFrom = data.getStringExtra("returnedFrom");

                if (returnedFrom.equals("save")) {
                    newStackName = data.getStringExtra("newStackName");
                    position = data.getLongExtra("position", -1);
                    listStacks.set((int) position, newStackName);
                    adapter.notifyDataSetChanged();
                } else if (returnedFrom.equals("delete")) {
                    position = data.getLongExtra("position", -1);
                    listStacks.remove(position);
                    adapter.remove(adapter.getItem((int) position));
                    adapter.notifyDataSetChanged();
                }
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
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}//end of class
