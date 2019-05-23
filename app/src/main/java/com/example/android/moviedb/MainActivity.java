package com.example.android.moviedb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    MovieAdapter movieAdapter;
    Button reset;
    private String url_string= "https://api.themoviedb.org/3/movie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.recycle_view2);
        progressBar = findViewById(R.id.progressbar);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        reset = findViewById(R.id.reset);

        if (isConnected()) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(0, null, this).forceLoad(); // this means belong to LoaderCallback
            progressBar.setVisibility(View.GONE);
            reset.setVisibility(View.GONE);

        }
        else {
            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isConnected()) {
                        LoaderManager loaderManager = getSupportLoaderManager();
                        loaderManager.initLoader(0, null, MainActivity.this).forceLoad(); // this means belong to LoaderCallback
                        progressBar.setVisibility(View.GONE);
                        reset.setVisibility(View.GONE);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "No Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }

//        Bundle bundle = new Bundle();
//        bundle.putInt("page",1);
    }

    private boolean isConnected()
    {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
           return connected = true;
        }
        else
            return connected = false;
    }
    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        // int page = bundle.getInt()
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String section = sharedPreferences.getString(getString(R.string.settings_section_by_key),"popular");

        Uri baseUri = Uri.parse(url_string);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath(section);
        uriBuilder.appendQueryParameter("api_key" , "c1e72fc9716bf1438d70d5a9478e0398");
        Log.i("myUrl", "onCreateLoader: "+uriBuilder.toString());
        return new MovieAsyncTask(this , uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> movies) {
        if (movies.isEmpty())
        {
            Log.i("content", "onLoadFinished: no content");
            Toast.makeText(this, "No contents", Toast.LENGTH_SHORT).show();
        }else {
            movieAdapter = new MovieAdapter(MainActivity.this, (ArrayList<Movie>) movies);
            recyclerView.setAdapter(movieAdapter);
            progressBar.setVisibility(View.GONE);
            reset.setVisibility(View.GONE);

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {
        movieAdapter.clearApplications();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
