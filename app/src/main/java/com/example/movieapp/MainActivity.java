package com.example.movieapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private RecyclerView movieRV = null;
    private static RecyclerView historyRV = null;
    private static EditText searchInput;
    private static Context c;
    public static List<Genre> genresIds;
    private static List<String> history = null;
    public static String PREF_NAME = "history_pref";
    private Integer MAX_HISTORY = 6;



    private final static String API_KEY = "0b64c64f63a4164a3162df706eaa709f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        history = new ArrayList<>();
        c = this;
        LoadHistory();

        setContentView(R.layout.activity_main);
        movieRV = findViewById(R.id.recycler_view);
        movieRV.setHasFixedSize(true);
        movieRV.setLayoutManager(new LinearLayoutManager(this));
        searchInput = findViewById(R.id.search_field);
        historyRV = findViewById(R.id.hostoryView);
        CreateListGenre();


        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String search = searchInput.getText().toString();
                historyRV.setVisibility(View.INVISIBLE);
                if(search.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Empty search field", Toast.LENGTH_SHORT).show();
                }
                else{
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    AddHistory(search);
                    SearchMovie(search);
                }
            }
        });

        searchInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEST", "Setting visible");
                historyRV.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput.setText("");
                historyRV.setVisibility(View.INVISIBLE);
            }
        });

        historyRV.setVisibility(View.VISIBLE);
        historyRV.setHasFixedSize(true);
        historyRV.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        historyRV.setAdapter(new HistoryAdapteur(history, getApplicationContext()));
    }

    public void SearchMovie(String search){

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<MovieResponse> call = movieApiService.getSearchedMovies(API_KEY, search);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                movieRV.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
                movieRV.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }

        });
    }

    public void CreateListGenre(){
        genresIds = new ArrayList<>();
        genresIds.add(new Genre(28, "Action", R.drawable.actionicon));
        genresIds.add(new Genre(12, "Adventure", R.drawable.adventureicon));
        genresIds.add(new Genre(16, "Animation", R.drawable.animationcon));
        genresIds.add(new Genre(35, "Comedy", R.drawable.comedyicon));
        genresIds.add(new Genre(80, "Crime", R.drawable.crimeicon));
        genresIds.add(new Genre(99, "Documentary", R.drawable.docuicon));
        genresIds.add(new Genre(18, "Drama", R.drawable.dramaicon));
        genresIds.add(new Genre(10751, "Family", R.drawable.familyicon));
        genresIds.add(new Genre(14, "Fantasy", R.drawable.fantasyicon));
        genresIds.add(new Genre(36, "History", R.drawable.historyeicon));
        genresIds.add(new Genre(27, "Horror", R.drawable.horroricon));
        genresIds.add(new Genre(10402, "Music", R.drawable.musicicon));
        genresIds.add(new Genre(9648, "Mystery", R.drawable.mysteryicon));
        genresIds.add(new Genre(10749, "Romance", R.drawable.romanceicon));
        genresIds.add(new Genre(878, "Science Fiction", R.drawable.sifiicon));
        genresIds.add(new Genre(10770, "TV Movie", R.drawable.tvicon));
        genresIds.add(new Genre(53, "Thriller", R.drawable.thrillericon));
        genresIds.add(new Genre(10752, "War", R.drawable.waricon));
        genresIds.add(new Genre(37, "Western", R.drawable.westernicon));
    }

    public void AddHistory(String search){
        history.add(0,search);
        if(history.size() >= (int)MAX_HISTORY){
            Log.d("Test", "Removing");
            history.remove(history.size() - 1);
        }
        historyRV.setHasFixedSize(true);
        historyRV.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        historyRV.setAdapter(new HistoryAdapteur(history, getApplicationContext()));
    }

    public static void DeleteHistory(int index){
        history.remove(index);
        historyRV.setAdapter(new HistoryAdapteur(history, c));
    }

    public static void SetText(String text){
        searchInput.setText(text);
    }

    @Override
    public void onStop() {

        SaveHistory();
        super.onStop();
    }

    private void SaveHistory(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        for(int i = 0; i < history.size(); i++){
            editor.putString("h" + i, history.get(i));
        }
        editor.apply();
    }

    private void LoadHistory(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREF_NAME, 0);
        for(int i = 0; i < MAX_HISTORY; i++){
            String h = settings.getString("h" + i, "");
            if(!h.isEmpty()){
                history.add(i, h);
            }

        }
        int homeScore = settings.getInt("homeScore", 0);
}
}