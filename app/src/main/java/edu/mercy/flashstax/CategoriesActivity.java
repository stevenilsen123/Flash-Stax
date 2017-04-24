package edu.mercy.flashstax;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final Context context = this;
    static final int SET_CATEGORIES_NAME_REQUEST = 1;
    String input;
    String dialogCatName;
    int dialogPosition;

    //  List view stuff
    //  **************** Note, add to list method at bottom - 'addListItem'
    ArrayList<String> listCategories = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private ListView categoriesListView;
/////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialogCatName = null;
        dialogPosition = -1;

        //  List view and adapter setup
        categoriesListView = (ListView) findViewById(R.id.listCategories);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listCategories);
        categoriesListView.setAdapter(adapter);
        categoriesListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            //  Method to go to edit category screen?
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                dialogCatName = (String) ((TextView) view).getText();
                dialogPosition = position;
                //---------------------
                // Input Dialog popup
                //---------------------
                //Upon list item press, create user dialogue box w/ clicked item
                //Get input, set to variable, then add variable to list.

                //  get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                //  sets prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);
                userInput.setText(((TextView) view).getText());

                //  set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setMessage("Enter category name: ")
                        //  Enter button logic
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Get user input, convert to string, and store
                                        input = userInput.getText().toString();
                                        //  Change display text, and add to list
                                        updateListItem(dialogPosition, dialogCatName);
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

            }

        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addCategory);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Category added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //---------------------
                // Input Dialog popup
                //---------------------////
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
                        .setMessage("Enter category name: ")
                        //  Enter button logic
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Get user input, convert to string, and store
                                        input = userInput.getText().toString();
                                        //  Change display text, and add to list
                                        addListItem(input);//

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

            }
        });



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
            // do nothing
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.categories, menu);
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
        String newCategoryName;

        // Check which request we're responding to
        if (requestCode == SET_CATEGORIES_NAME_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                newCategoryName = data.getStringExtra("newCategoryName");
                Toast.makeText(this, newCategoryName, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //--------------------------------
    //  Method to add items to list
    //--------------------------------
    private void addListItem(String item) {
        listCategories.add(item);
        adapter.notifyDataSetChanged();


    }//end of addListItem
    //update list
    private void updateListItem(int position, String newName) {
        listCategories.set(position, newName);
        adapter.notifyDataSetChanged();
    }
}
