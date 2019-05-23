package com.example.android.moviedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
TextView titleTextView;
TextView descriptionTextView;
TextView dateTextView;
ImageView imageView;
RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        titleTextView = findViewById(R.id.title_movie);
        descriptionTextView = findViewById(R.id.description2);
        dateTextView = findViewById(R.id.release);
        imageView = findViewById(R.id.image_detail);
        ratingBar = findViewById(R.id.ratingl_Bar);

        final String title = getIntent().getExtras().getString("Title");
        final String description = getIntent().getExtras().getString("Description");
         String date = getIntent().getExtras().getString("date");
         date = date.substring(0,4);
         final String image = getIntent().getExtras().getString("image");
         final float rating = getIntent().getExtras().getFloat("rate");
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        dateTextView.setText(date);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" +image).into(imageView);
        Log.i("rating", "onCreate: "+rating);
        ratingBar.setRating(rating/2);

    }
}
