package com.example.android.moviedb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

public class MovieAsyncTask extends AsyncTaskLoader<List<Movie>> {
   // private String url_string= "https://api.themoviedb.org/3/movie/popular?api_key=c1e72fc9716bf1438d70d5a9478e0398&language=en-US&page=1";
   private String url_string;
    public  final String LOG_TAG = MainActivity.class.getSimpleName();

    public MovieAsyncTask(@NonNull Context context , String url) {
        super(context);
        url_string = url;
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {
        StringBuilder jsonResponse = new StringBuilder();
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        List<Movie> movieArrayList = null;
        try {
            URL url = new URL(url_string);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                jsonResponse.append(line);
                line = reader.readLine();
            }

            JSONObject root = new JSONObject(jsonResponse.toString());
            JSONArray movies = root.getJSONArray("results");

            movieArrayList = new ArrayList<>();
            String title;
            String imageUrl;
            float rate;
            String date;
            String description;
            for (int i = 0; i < movies.length(); i++) {
                JSONObject element = movies.getJSONObject(i);
                title = element.getString("title");
                imageUrl = element.getString("poster_path");
                rate = (float) element.getDouble("vote_average");
                description = element.getString("overview");
                date = element.getString("release_date");
                movieArrayList.add(new Movie(title, imageUrl, rate, description, date));

            }


        } catch (MalformedInputException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        int numberOfColumns = 2;
//        recyclerView.setLayoutManager(new GridLayoutManager(Main2Activity.this,numberOfColumns));
//        final MovieAdapter movieAdapter = new MovieAdapter(Main2Activity.this,movieArrayList );
//        recyclerView.setAdapter(movieAdapter);
//        recyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(Main2Activity.this, "hello", Toast.LENGTH_SHORT).show();
//            }
//        });

        Log.i(LOG_TAG, "size: "+movieArrayList.size());
        return movieArrayList;
    }
}
