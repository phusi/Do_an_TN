package com.example.phusi.do_an_tn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.phusi.do_an_tn.fragment.Connect_Fragment;
import com.example.phusi.do_an_tn.smart_config.demo_activity.EsptouchDemoActivity;

public class Main_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    Fragment fragment = null;
    NavigationView navigationView;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.setCheckedItem(R.id.nav_connect);
        fragment = new Connect_Fragment();
        title=getString(R.string.title_control);
        getSupportActionBar().setTitle(title);
        replaceFragment(fragment);

    }

    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().show();

            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);

                }
            };

//            toggle.syncState();
//            drawer.setDrawerListener(toggle);

            //Show icon back and hamburger when switch fragment
            getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_main);
                        if (!(fragment instanceof Connect_Fragment ||
                                fragment instanceof EsptouchDemoActivity )) {
                            showBackNavigation();
                        } else {
                            showHomeNavigation();
                        }
                    } else {
                        showHomeNavigation();
                    }
                }
            });
        }
    }
    private void showBackNavigation() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }

        }
    }

    private void showHomeNavigation() {
        //show hamburger

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
        int id = item.getItemId();

        switch (id) {
//            case R.id.nav_smart_config:
//                fragment = new EsptouchDemoActivity();
//                title  = getString(R.string.title_esp);
//                break;
            case R.id.nav_connect:
                fragment = new Connect_Fragment();
                title = getString(R.string.title_control);
                break;
            case R.id.nav_map:
                Intent i = new Intent(Main_activity.this,MapsActivity.class);
                startActivity(i);

        }

        replaceFragment(fragment);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);





//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            item.setEnabled(false);
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_connect) {
//            replaceFragmentContent(new Connect_Fragment());
////            item.setEnabled(false);
////            getSupportFragmentManager().executePendingTransactions();
//
//
//        } else if (id == R.id.nav_smart_config) {
//            EsptouchDemoActivity esptouchDemoActivity = new EsptouchDemoActivity();
////            item.setEnabled(false);
//            replaceFragmentContent(esptouchDemoActivity);
//        }
//
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
////        clearFragmentBackStack();
        return true;

    }
   protected void replaceFragment(Fragment fragment){
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_main, fragment);
            ft.commit();
        }
    }

    protected void replaceFragmentContent(Fragment fragment) {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction
                .replace(R.id.fragment_main, fragment)
                .addToBackStack(null)
                .commit();
    }
    protected void clearFragmentBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            for (int i = 1; i < fragmentManager.getBackStackEntryCount(); i++) {
                //remove other fragments from stack
                getSupportFragmentManager().popBackStack();
            }
        }
    }
}
