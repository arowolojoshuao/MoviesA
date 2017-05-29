package com.example.olamide.moviesa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.olamide.moviesa.MovieDetailsActivity;
import com.example.olamide.moviesa.R;
import com.example.olamide.moviesa.models.Movies;

import java.util.List;

import static com.example.olamide.moviesa.R.id.poster_image;

/**
 * Created by Olamide on 5/22/17.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movies> movieList;
    private int rowLayout;
    LinearLayout moviesLayout;
    ImageView posterImage;
    Context context;

    public MoviesAdapter(List<Movies> movies, int rowLayout, Context context){
        this.movieList = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( rowLayout,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int position){
        viewHolder.title.setText(movieList.get(position).getTitle());
        String vote = Double.toString(movieList.get(position).getRating());
        viewHolder.userrating.setText(vote);
//        Movies image = movieList.get(position);
//        String posterPathUrl = "http://image.tmdb.org/t/p/w185" + image.getPosterPath();
//        Glide.with(context)
//                .load(posterPathUrl)
//                .placeholder(R.drawable.movi)
//                .into(viewHolder.thumbnail);

        Glide.with(context)
                .load(movieList.get(position).getPosterPath())
                .placeholder(R.drawable.movi)
                .into(viewHolder.thumbnail);


    }

    @Override
    public int getItemCount(){
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, userrating;
        public ImageView thumbnail;

        public MyViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            moviesLayout = (LinearLayout) itemView.findViewById(R.id.movies_layout);
            posterImage = (ImageView) itemView.findViewById(poster_image);
            userrating = (TextView) view.findViewById(R.id.userrating);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Movies clickedDataItem = movieList.get(pos);
                        Intent intent = new Intent(context, MovieDetailsActivity.class);
                        intent.putExtra("title", movieList.get(pos).getTitle());
                        intent.putExtra("poster_path", movieList.get(pos).getPosterPath());
                        intent.putExtra("overview", movieList.get(pos).getOverview());
                        intent.putExtra("vote_average", Double.toString(movieList.get(pos).getRating()));
                        intent.putExtra("release_date", movieList.get(pos).getDate());
                        intent.putExtra("id", movieList.get(pos).getId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
