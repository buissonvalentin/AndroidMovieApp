package com.example.movieapp;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.util.zip.Inflater;

import static com.example.movieapp.MainActivity.genresIds;

public class HistoryAdapteur extends RecyclerView.Adapter<HistoryAdapteur.HistoryViewHolder> {

    public static List<String> history;
    public Context context;
    private LayoutInflater  mInflater;


    public HistoryAdapteur(List<String> history,Context context) {
        this.history = history;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }



//A view holder inner class where we get reference to the views in the layout using their ID

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView searchHistory;
        Button del;

        public HistoryViewHolder(View v) {
            super(v);
            searchHistory = (TextView) v.findViewById(R.id.search);
            del = v.findViewById(R.id.del);

            searchHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.SetText(searchHistory.getText().toString());
                }
            });

            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("T", "click");
                    MainActivity.DeleteHistory(getLayoutPosition());
                }
            });
        }

    }

    @Override
    public HistoryAdapteur.HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_history, parent, false);
        return new HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(HistoryViewHolder holder, final int position) {
        holder.searchHistory.setText(history.get(position));

    }

    @Override
    public int getItemCount() {
        return history.size();
    }



}