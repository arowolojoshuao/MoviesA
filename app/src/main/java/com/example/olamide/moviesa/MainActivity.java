package com.example.olamide.moviesa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.olamide.moviesa.adapter.MoviesAdapter;
import com.example.olamide.moviesa.models.Movies;
import com.example.olamide.moviesa.models.MovieResponse;
import com.example.olamide.moviesa.rest.Client;
import com.example.olamide.moviesa.rest.Service;
import com.example.olamide.moviesa.utils.ConnectionTest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.olamide.moviesa.R.id.recyclerView;

/**
 * Created by Olamide on 5/22/17.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecylerView;
    boolean isConnected;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    private static SharedPreferences sharedPreferences;


    private final static String MOVIE_API_KEY = BuildConfig.MOVIE_API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initviews();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipecontainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                popular();
                Toast.makeText(MainActivity.this, "Movies refreshed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initviews() {
        pd = new ProgressDialog(this);
        pd.setMessage("Loading Movies ...");
        pd.setCancelable(false);
        pd.show();
        if (MOVIE_API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.api_key_error_message, Toast.LENGTH_LONG).show();
        }

        mRecylerView = (RecyclerView) findViewById(recyclerView);

        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecylerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            mRecylerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        popular();
    }


    public void popular() {
        Service apiService = Client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getPopularMovies(MOVIE_API_KEY);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movies> movies = response.body().getResults();
                mRecylerView.setAdapter(new MoviesAdapter(movies, R.layout.movie_card, getApplicationContext()));
//                mRecylerView.setAdapter(new MoviesAdapter(getApplicationContext(), R.layout.movie_card);
//               mRecylerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                mRecylerView.smoothScrollToPosition(0);
                swipeContainer.setRefreshing(false);
                pd.hide();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.failure_message, Toast.LENGTH_SHORT).show();
                return;
            }
        });

    }

    public void topRated() {
        Service apiService = Client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getTopRatedMovies(MOVIE_API_KEY);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movies> movies = response.body().getResults();
                mRecylerView.setAdapter(new MoviesAdapter(movies, R.layout.movie_card, getApplicationContext()));
                mRecylerView.smoothScrollToPosition(0);
                swipeContainer.setRefreshing(false);
                pd.hide();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.failure_message, Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }


    protected void onResume() {
        super.onResume();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isPopularMovie = sharedPreferences.getBoolean("popular_key", false);
        boolean isTopratedMovie = sharedPreferences.getBoolean("toprated_key", false);

        isConnected = ConnectionTest.isNetworkAvailable(this);
        if (isConnected) {
            if (isPopularMovie) {
                popular();
            } else if (isTopratedMovie) {
                topRated();
            } else
                popular();
        } else {

            Toast.makeText(MainActivity.this, R.string.failure_message, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.settings_menu) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
