package com.example.neven.fragmentsv2;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements Contacts_Add.sendData {

    DatabaseHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new DatabaseHandler(this, null, null, 1);

        //____________________________PRINT DATABASE_____________________

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Contacts_All(), "allFrag");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        getSupportFragmentManager().executePendingTransactions();

        Contacts_All fragAll = (Contacts_All) getSupportFragmentManager().findFragmentByTag("allFrag");

        fragAll.printDatabase();


        //______________________________________________________________________

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_call) {
                    // Handle the camera action
                    Toast.makeText(MainActivity.this, "call", Toast.LENGTH_SHORT).show();


                } else if (id == R.id.nav_adddcontact) {

                    displayFragmentContactsAdd();


                } else if (id == R.id.nav_allcontacts) {

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new Contacts_All(), "allFrag");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    getSupportFragmentManager().executePendingTransactions();

                    Contacts_All fragAll = (Contacts_All) getSupportFragmentManager().findFragmentByTag("allFrag");
                    fragAll.printDatabase();


                } else if (id == R.id.nav_favorites) {

                    Toast.makeText(MainActivity.this, "favorites", Toast.LENGTH_SHORT).show();

                } else if (id == R.id.nav_about) {

                    Toast.makeText(MainActivity.this, "about", Toast.LENGTH_SHORT).show();

                } else if (id == R.id.nav_exit) {

                    finishAffinity();

                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
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

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.my_back_arrowID) {

            onBackPressed();


        }


        return super.onOptionsItemSelected(item);
    }


    public void displayFragmentContactsAdd() {


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Contacts_Add(), "add");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }


    @Override
    public void etValues(String fname, String lname, String phone, String email) {


        ContactsObjects contact = new ContactsObjects(fname, lname, phone, email);

        handler.insertContact(contact);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Contacts_All(), "allFrag");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        getSupportFragmentManager().executePendingTransactions();

        Contacts_All fragAll = (Contacts_All) getSupportFragmentManager().findFragmentByTag("allFrag");
        fragAll.printDatabase();


    }


}
