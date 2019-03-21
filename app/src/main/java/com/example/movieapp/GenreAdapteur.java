package com.example.movieapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.util.zip.Inflater;

import static com.example.movieapp.MainActivity.genresIds;

public class GenreAdapteur extends RecyclerView.Adapter<GenreAdapteur.GenreViewHolder> {

    public static List<Integer> genres;
    public Context context;
    private LayoutInflater  mInflater;


    public GenreAdapteur(List<Integer> genres,Context context) {
        this.genres = genres;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }



//A view holder inner class where we get reference to the views in the layout using their ID

    public static class GenreViewHolder extends RecyclerView.ViewHolder{

        TextView genreText;
        ImageView genreIcon;


        public GenreViewHolder(View v) {
            super(v);
            genreIcon = (ImageView) v.findViewById(R.id.genreIcon);
            genreText = (TextView) v.findViewById(R.id.genreText);
        }
    }

    @Override
    public GenreAdapteur.GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_genre, parent, false);
        return new GenreViewHolder(view);
    }


    @Override
    public void onBindViewHolder(GenreViewHolder holder, final int position) {
        holder.genreText.setText(getGenreValue(genres.get(position)));
        holder.genreIcon.setImageResource(getResourceId(genres.get(position)));
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    String getGenreValue(Integer id){
        for(int i = 0; i < genresIds.size(); i++){
            if(genresIds.get(i).getId() == (int)id){
                return genresIds.get(i).getName();
            }
        }
        return genresIds.get(0).getName();
    }

    Integer getResourceId(Integer id){
        for(int i = 0; i < genresIds.size(); i++){
            if(genresIds.get(i).getId() == (int)id){
                return genresIds.get(i).getResId();
            }
        }
        return genresIds.get(0).getResId();
    }
}