package com.example.movieapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    public  List<Movie> movies;
    private int rowLayout;
    public Context context;

    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;

    }
    
    

//A view holder inner class where we get reference to the views in the layout using their ID

    public  class MovieViewHolder extends RecyclerView.ViewHolder{

        TextView movieTitle;
        ImageView movieImage;


        public MovieViewHolder(View v) {

            super(v);
            movieImage = (ImageView) v.findViewById(R.id.movie_image);
            movieTitle = (TextView) v.findViewById(R.id.title);

            movieImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movie m = movies.get(getAdapterPosition());
                    Intent intent = new Intent (v.getContext(), MovieDetail.class);
                    intent.putExtra("movie", m);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        String image_url = IMAGE_URL_BASE_PATH + movies.get(position).getPosterPath();
        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.movieImage);

        holder.movieTitle.setText(movies.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}