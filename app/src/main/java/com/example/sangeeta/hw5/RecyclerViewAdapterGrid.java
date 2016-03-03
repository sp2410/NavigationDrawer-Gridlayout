package com.example.sangeeta.hw5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by sangeeta on 3/1/2016.
 */
public class RecyclerViewAdapterGrid extends RecyclerView.Adapter<RecyclerViewAdapterGrid.ViewHolder> {


    // int count;
    private MovieData movieData = null;
    private Context mContext;

    private OnItemViewSelected listadap = null;


    public RecyclerViewAdapterGrid(Context context, MovieData movieData){
        this.mContext = context;
        this.movieData = movieData;
    }

    @Override
    public int getItemCount() {
        return movieData.getSize();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        //if(viewType==0) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.insiderecviewgrid, parent, false);
        //}

       /* else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.insiderecview, parent, false);
        }
    */

        ViewHolder vhold = new ViewHolder(v);
        return vhold;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        HashMap<String,?> currentMovie = movieData.getItem(position);
        holder.bindMovieData(currentMovie);
    }

    public void registerListener(OnItemViewSelected mListenerAdapter){
        this.listadap = mListenerAdapter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImage;
        //private TextView mTitle,mDescription,myear;
        //private CheckBox mCheckBox;
        //private RatingBar mMovieRating;

        public ViewHolder(View itemView) {
            super(itemView);

            mImage = (ImageView) itemView.findViewById(R.id.imagegrid);
           // mTitle = (TextView) itemView.findViewById(R.id.title);
            //mDescription = (TextView) itemView.findViewById(R.id.des);
            //mCheckBox = (CheckBox)itemView.findViewById(R.id.checkbox_meat);
           // mMovieRating = (RatingBar) itemView.findViewById(R.id.rate);
            //myear = (TextView) itemView.findViewById(R.id.year);
           // mMenu = (ImageView) itemView.findViewById(R.id.popupbutton);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (listadap != null) {
                        listadap.onItemClick(v, getLayoutPosition());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    if (listadap != null) {
                        listadap.onItemLongClick(v, getLayoutPosition());
                    }
                    return true;
                }
            });

        }


        public void bindMovieData(HashMap<String,?> movie){
            mImage.setImageResource((Integer) movie.get("image"));
           // mTitle.setText((String) movie.get("name"));
            //myear.setText((String) movie.get("year"));

            //String Lengthofdes = (String) movie.get("description");
            //mDescription.setText(Lengthofdes);


            //double e= (Double) movie.get("rating");
            //mMovieRating.setRating(((Double) (e / 2D)).floatValue());

        }


    }

    public interface OnItemViewSelected {
        public void onItemClick(View x, int position);
        public void onItemLongClick(View x, int position);
        public void onOverflowMenuClick(View x, int position);

    }


}
