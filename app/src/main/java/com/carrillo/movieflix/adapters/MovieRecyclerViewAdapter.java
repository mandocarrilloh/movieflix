package com.carrillo.movieflix.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.carrillo.movieflix.R;
import com.carrillo.movieflix.activities.MovieDetailActivity;
import com.carrillo.movieflix.models.Movie;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Movie> movies;

    public MovieRecyclerViewAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.cardview_item_movie, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieRecyclerViewAdapter.MyViewHolder holder, final int position) {
        Picasso.get().load(movies.get(position).getPosterSmall()).into(holder.ivMoviePoster);
        holder.tvTitle.setText(movies.get(position).getTitle());
        holder.cvMovieItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("MovieId", movies.get(position).getMovieId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cvMovieItem;
        ImageView ivMoviePoster;
        TextView tvTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            cvMovieItem = itemView.findViewById(R.id.cv_movie_item);
            ivMoviePoster = itemView.findViewById(R.id.iv_movie_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);

            Animation fadeInAnimation = AnimationUtils.loadAnimation(cvMovieItem.getContext(), R.anim.fade_in);
            ivMoviePoster.startAnimation(fadeInAnimation);
        }
    }
}