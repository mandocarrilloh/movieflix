package com.carrillo.movieflix.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import com.carrillo.movieflix.R;
import com.carrillo.movieflix.models.Genres;
import com.carrillo.movieflix.models.Movie;

public class GenresRecyclerViewAdapter extends RecyclerView.Adapter<GenresRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Genres> genres;

    public GenresRecyclerViewAdapter(Context context, List<Genres> genres) {
        this.context = context;
        this.genres = genres;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.cardview_item_genres, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenresRecyclerViewAdapter.MyViewHolder holder, final int position) {
        holder.tvGenres.setText(genres.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cvGenreItem;
        TextView tvGenres;

        public MyViewHolder(View itemView) {
            super(itemView);
            cvGenreItem = itemView.findViewById(R.id.cv_genre_item);
            tvGenres = itemView.findViewById(R.id.tv_genres);
            Animation fadeInAnimation = AnimationUtils.loadAnimation(cvGenreItem.getContext(), R.anim.fade_in);
        }
    }
}
