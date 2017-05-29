package com.example.olamide.moviesa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Olamide on 5/22/17.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    private Context context;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.poster_image_details) ImageView image;
    @BindView(R.id.release_date) TextView releaseDate;
    @BindView(R.id.rating) TextView ratings;
    @BindView(R.id.overview) TextView overView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("title")){

            String thumbnail = getIntent().getExtras().getString("poster_path");
            String movieName = getIntent().getExtras().getString("title");
            String synopsis = getIntent().getExtras().getString("overview");
            String rating = getIntent().getExtras().getString("vote_average");
            String dateOfRelease = getIntent().getExtras().getString("release_date");

            Glide.with(this)
                    .load(thumbnail)
                    .placeholder(R.drawable.movi)
                    .into(image);

            title.setText(movieName);
            ratings.setText(rating);
            overView.setText(synopsis);
            releaseDate.setText(dateOfRelease);

        }else{
            Toast.makeText(getApplicationContext(), "Error fetching data\nCheck your Internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
