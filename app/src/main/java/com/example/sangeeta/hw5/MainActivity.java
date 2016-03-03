package com.example.sangeeta.hw5;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    DrawerLayout draw;
    NavigationView nav;
    //ActionBarDrawerToggle actbdrtog;
    Fragment mContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mContent = getSupportFragmentManager().getFragment(savedInstanceState,"mContent");
        }
        else{
            mContent = BlankFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, mContent)
                .commit();

//Initailising Toolbar and setting it as actionbar
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.toolbar1);

        //Initailising NavigationView
        nav  = (NavigationView)findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);

        draw = (DrawerLayout)findViewById(R.id.fmain);

        ActionBarDrawerToggle actbdrtog = new ActionBarDrawerToggle(this, draw, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };


        draw.setDrawerListener(actbdrtog);
        actbdrtog.syncState();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if(mContent.isAdded())
            getSupportFragmentManager().putFragment(outState,"mContent", mContent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch(id){

            case R.id.about_me:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, BlankFragment.newInstance())
                        .commit();
                break;
            case R.id.item1:
                intent = new Intent(this, recycleview1.class);
                startActivity(intent);
                break;

            case R.id.item2:

                intent = new Intent(this, recycleview2.class);
                startActivity(intent);
                break;

            default:
                //if()---check if fragment doesnt exist
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, BlankFragment.newInstance())
                        .commit();
                break;
        }
        draw.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar1, menu);
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();

        if(id== R.id.mypic){
            Toast.makeText(getApplicationContext(), "PIKA PIKA !!!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
