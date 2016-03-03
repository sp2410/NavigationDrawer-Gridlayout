package com.example.sangeeta.hw5;

import android.app.ActionBar;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;


/**
 * A simple {@link Fragment} subclass.
 */
public class recycleviewfrag extends Fragment {
    private RecyclerView rec;
    private RecyclerViewAdapter recyclerViewAdapter;
    private MovieData movieData;

    private OnItemSelected mListnerFragment;
    int restoreIndex;

    private HashMap<Integer,Boolean> saveCheck = new HashMap<Integer,Boolean>();
    private HashMap<Integer,Integer> saveDupli = new HashMap<Integer,Integer>();


    public recycleviewfrag() {
        // Required empty public constructor
    }

    public static recycleviewfrag newInstance(){
        recycleviewfrag myfragment = new recycleviewfrag();
        return myfragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        movieData = new MovieData();
        if(savedInstanceState != null){
            addDup((HashMap<Integer, Integer>) savedInstanceState.getSerializable("DuplicateMovie"));
            //fillCheck((HashMap<Integer, Boolean>) savedInstanceState.getSerializable("CheckBox"));
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        if(menu.findItem(R.id.search)==null){

            inflater.inflate(R.menu.menufrag1, menu);

            SearchView sea= (SearchView) menu.findItem(R.id.search).getActionView();
            if(sea!=null){

                sea.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        int position = movieData.findFirst(query);
                        if (position >= 0)
                            rec.scrollToPosition(position);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String query) {
                        return true;
                    }


                });
            }

            super.onCreateOptionsMenu(menu, inflater);
        }


    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();

        switch(id){
            case R.id.search:
            //mysearch():
                break;
            case R.id.setting:
                Toast.makeText(getActivity(),"Clicked settings", Toast.LENGTH_LONG).show();
                break;
            case R.id.help:
                Toast.makeText(getActivity(),"Clicked help", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getActivity(),"Pika Pika !!!", Toast.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);

    }


    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }
/*
    private void fillCheck(HashMap<Integer,Boolean> s){

        for(Integer i : s.keySet()){
            HashMap<String,Boolean> currentItem = movieData.getItem(i);
            currentItem.put("selection", true);
            saveCheck.put(i,true);
        }
    }
*/
    private void addDup(HashMap<Integer,Integer> saveDups) {

        HashMap<Integer,Integer> temp = new HashMap<Integer,Integer>();
        List<Integer> toRemove = new ArrayList<Integer>();

        for (int k : saveDups.keySet()) {

            int offset = k+saveDups.get(k);
            int totalCount = saveDups.get(k);
            for (int checkKey : saveDups.keySet()) {

                if (checkKey == offset) {
                    totalCount = totalCount + saveDups.get(checkKey);

                }
            }
            toRemove.add(offset);
            temp.put(k,totalCount);
        }

        for(int e : toRemove){

            if(temp.get(e) != null)
                temp.remove(e);
        }



        for (int k1 : temp.keySet()){
            int movieOccurance = temp.get(k1);

            restoreIndex= k1;

            for(int lessKey : temp.keySet()){
                if(lessKey < k1){
                    restoreIndex = restoreIndex - temp.get(lessKey);
                }
            }


            while(movieOccurance > 0) {
                movieData.addMovie(movieData.getItem(restoreIndex), restoreIndex);
                movieOccurance--;
            }
        }
        saveDupli = temp;
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        //outState.putSerializable("CheckBox", saveCheck);
        outState.putSerializable("DuplicateMovie", saveDupli);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;

        try{
            mListnerFragment = (OnItemSelected) getContext();
        }catch (ClassCastException e){
            throw new ClassCastException("Fragment not getting context");
        }

        rootView = inflater.inflate(R.layout.activity_recyclerview, container, false);
        rec= (RecyclerView) rootView.findViewById(R.id.rellayout);


        LinearLayoutManager mLayout = new LinearLayoutManager(getActivity());
        rec.setLayoutManager(mLayout);

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), movieData);
        rec.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.registerListener(new RecyclerViewAdapter.OnItemViewSelected() {

            @Override
            public void onItemClick(View x, int position) {
                if (mListnerFragment != null) {
                    mListnerFragment.onClickItemSelected(movieData.getItem(position));
                }
            }

            @Override
            public void onItemLongClick(View x, int position) {
                getActivity().startActionMode(new ActionBarCallBack(position));
            }




            @Override
            public void onOverflowMenuClick(View v, final int position){
                PopupMenu popup= new PopupMenu(getActivity(),v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                    @Override
                    public boolean onMenuItemClick(MenuItem item){
                        switch(item.getItemId()){

                            case R.id.del:
                                movieData.removeItem(position);
                                recyclerViewAdapter.notifyItemRemoved(position);
                                return true;
                                //break;
                            case R.id.copy:
                                movieData.addMovie((HashMap) ((HashMap) movieData.getItem(position)).clone(), position + 1);
                                recyclerViewAdapter.notifyItemRemoved(position + 1);
                                return true;
                                //break;
                            default:
                                return false;
                        }
                    }

                });
                MenuInflater inflator1=popup.getMenuInflater();
                inflator1.inflate(R.menu.menufrag2, popup.getMenu());
                popup.show();

            }

        });


        itemAni();
        adapterAni();
        return rootView;
    }
    private void itemAni(){
        SlideInRightAnimator animator= new SlideInRightAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        animator.setAddDuration(500);
        animator.setRemoveDuration(500);
        rec.setItemAnimator(animator);

    }


    private void adapterAni(){

        AlphaInAnimationAdapter alphaAdap = new AlphaInAnimationAdapter(recyclerViewAdapter);
        ScaleInAnimationAdapter scaleAdap = new ScaleInAnimationAdapter(alphaAdap);
        rec.setAdapter(scaleAdap);
        rec.setAdapter(scaleAdap);

    }

    public interface OnItemSelected {
        public void onClickItemSelected(HashMap<String, ?> movie);
    }



    class ActionBarCallBack implements ActionMode.Callback{
    int position;
        public ActionBarCallBack(int position){this.position=position;}

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item){
        int id= item.getItemId();
            switch(id){

                case R.id.del:
                    movieData.removeItem(position);
                    recyclerViewAdapter.notifyItemRemoved(position);
                    mode.finish();
                    break;
                case R.id.copy:
                    movieData.addMovie((HashMap)((HashMap) movieData.getItem(position)).clone(),position + 1);
                    recyclerViewAdapter.notifyItemRemoved(position+1);
                    mode.finish();
                    break;
                default:
                    break;
            }
            return false;

        }


        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menufrag2, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }


    }


}
