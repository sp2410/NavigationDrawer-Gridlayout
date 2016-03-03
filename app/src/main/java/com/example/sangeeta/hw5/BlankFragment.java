package com.example.sangeeta.hw5;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {


    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(){
        BlankFragment myfragment = new BlankFragment();
        return myfragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        //int option = getArguments().getInt("FrameNumber");
        rootView = inflater.inflate(R.layout.aboutmefrag, container, false);
        return rootView;

    }

}
