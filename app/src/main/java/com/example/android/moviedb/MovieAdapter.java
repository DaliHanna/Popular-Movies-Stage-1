package com.example.android.moviedb;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<Movie> movieDatabase;
    private Context context;

    public MovieAdapter(Context context, ArrayList<Movie> movieDatabase) {
        this.layoutInflater = LayoutInflater.from(context);
        this.movieDatabase = movieDatabase;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie currentMovie = movieDatabase.get(position);

            holder.title.setText(currentMovie.getmTitle());
            holder.rate.setText(currentMovie.getmRate() + "");
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + currentMovie.getmUrlImage()).into(holder.imageView);

            //check if the description is defined by findViewById in MainActivity if not then it is defined in DetailActivity
        if (holder.description != null) {
            holder.description.setText(currentMovie.getmDescription());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movieIntent = new Intent(view.getContext(),DetailActivity.class);
                movieIntent.putExtra("Title" , currentMovie.getmTitle());
                movieIntent.putExtra("Description",currentMovie.getmDescription());
                movieIntent.putExtra("date",currentMovie.getmDate());
                movieIntent.putExtra("image",currentMovie.getmUrlImage());
                movieIntent.putExtra("rate", currentMovie.getmRate());
                view.getContext().startActivity(movieIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieDatabase == null ? 0 : movieDatabase.size();
    }

    public void clearApplications() {
        int size = this.movieDatabase.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                movieDatabase.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView rate;
        private ImageView imageView;
        private TextView description;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            imageView = itemView.findViewById(R.id.image);
            description = itemView.findViewById(R.id.description2);
            date = itemView.findViewById(R.id.release);

        }
    }
}
