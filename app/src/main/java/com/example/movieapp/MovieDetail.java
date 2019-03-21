package com.example.movieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetail extends AppCompatActivity {

    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        Movie m = (Movie) intent.getSerializableExtra("movie");

        ((TextView)findViewById(R.id.title)).setText(m.getOriginalTitle());
        ((TextView)findViewById(R.id.description)).setText(m.getOverview());
        ((TextView)findViewById(R.id.rating)).setText("Note : "+ m.getVoteAverage() + "/10");
        ((TextView)findViewById(R.id.date)).setText("Sortie le : " + m.getReleaseDate());

        ImageView imageV = findViewById(R.id.movie_image);

        Picasso.with(this)
                .load(IMAGE_URL_BASE_PATH + m.getPosterPath())
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(imageV);


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvGenres);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        GenreAdapteur adapter = new GenreAdapteur(m.getGenreIds(), this);
        recyclerView.setAdapter(adapter);
    }

    void SetIconsToGenres(){


    }
}
