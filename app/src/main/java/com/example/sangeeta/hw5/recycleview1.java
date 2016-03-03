package com.example.sangeeta.hw5;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;

/**
 * Created by sangeeta on 2/29/2016.
 */
public class recycleview1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, recycleviewfrag.OnItemSelected{


    Fragment mContent;
    Toolbar  mToolBar;
    NavigationView nav;
    DrawerLayout draw;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionbar=getSupportActionBar();

        mToolBar=(Toolbar) findViewById(R.id.toolbar);
        mToolBar.inflateMenu(R.menu.toolbar1);
        setSupportActionBar(mToolBar);


        actionbar=getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
       // actionbar.setLogo(R.drawable.settings);

        //Initailising NavigationView
        nav  = (NavigationView)findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);

        draw = (DrawerLayout)findViewById(R.id.fmain);

        ActionBarDrawerToggle actbdrtog = new ActionBarDrawerToggle(this, draw,  mToolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){

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
        //Loading fragment

        if(savedInstanceState != null){
            mContent = getSupportFragmentManager().getFragment(savedInstanceState,"mContent");
        }
        else{
            mContent = recycleviewfrag.newInstance();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, mContent)
                .commit();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar1, menu);
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item){
    int id= item.getItemId();

    if(id== R.id.mypic){
        Toast.makeText(getApplicationContext(),"PIKA PIKA !!!", Toast.LENGTH_LONG).show();
    }
        return super.onOptionsItemSelected(item);
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
    public void onClickItemSelected(HashMap<String, ?> movie) {
        mContent = movieinfo.newInstance(movie);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mContent)
                .addToBackStack(null)
                .commit();
    }
}