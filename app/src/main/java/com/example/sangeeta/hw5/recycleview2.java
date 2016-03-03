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
public class recycleview2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, recycleviewfrag.OnItemSelected,  recycleviewfraggrid.OnItemSelected{

    NavigationView nav;
    DrawerLayout draw;
    Fragment mContent,mContent2;
    Toolbar  mToolBar,mToolBar2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview2toolbar);

        ActionBar actionbar=getSupportActionBar();

        mToolBar=(Toolbar) findViewById(R.id.toolbar);
        mToolBar.inflateMenu(R.menu.toolbar2);
        setSupportActionBar(mToolBar);

        actionbar=getSupportActionBar();
        //actionbar.setDisplayHomeAsUpEnabled(true);
        //actionbar.setLogo(R.drawable.settings);

        mToolBar2=(Toolbar) findViewById(R.id.toolbar3);
        mToolBar2.inflateMenu(R.menu.toolbar_menu3);
        setupToolbarItemSelected();

        nav  = (NavigationView)findViewById(R.id.nav_view2);
        nav.setNavigationItemSelectedListener(this);

        draw = (DrawerLayout)findViewById(R.id.recyclerview2layout);

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

        if(savedInstanceState != null){
            mContent = getSupportFragmentManager().getFragment(savedInstanceState,"mContent");
        }
        else{
            mContent = recycleviewfrag.newInstance();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container2, mContent)
                .commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar2, menu);
        return true;
    }



    private void setupToolbarItemSelected(){

        mToolBar2.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id=item.getItemId();
                switch(id){

                    case R.id.lin2:
                        mContent = recycleviewfrag.newInstance();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container2, mContent)
                                .addToBackStack(null)
                                .commit();

                        break;
                    case R.id.grid2:
                        mContent2 = recycleviewfraggrid.newInstance();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container2, mContent2)
                                .addToBackStack(null)
                                .commit();

                        break;

                    default:
                        mContent2 = recycleviewfrag.newInstance();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container2, mContent2)
                                .addToBackStack(null)
                                .commit();

                        break;
                }

                return false;
            }
/*
            @Override
            public boolean onMenuItemClickListener(MenuItem menuItem){
                int id=menuItem.getItemId();



                return false;
            }
            */
        });

        mToolBar2.setNavigationIcon(R.drawable.arrowdown);
        mToolBar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolBar2.setVisibility(View.GONE);
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();

        if(id== R.id.mypic){
            Toast.makeText(getApplicationContext(),"PIKA PIKA !!!", Toast.LENGTH_LONG).show();
        }

        if(id== R.id.downbtn){
            mToolBar2.setVisibility(View.VISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if(mContent.isAdded())
            getSupportFragmentManager().putFragment(outState,"mContext", mContent);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch(id){

            case R.id.about_me:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
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
                    .replace(R.id.container2, mContent)
                    .addToBackStack(null)
                    .commit();
        }



}
