package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.DetailActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.List;

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);

        // Get the movie at the passed in position
        Movie movie = movies.get(position);

        // Bind the movie data into the ViewHolder
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RatingBar rbRating;
        TextView tvRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            rbRating = itemView.findViewById(R.id.rbRating);
            tvRating = itemView.findViewById(R.id.tvRating);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl;
            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imageUrl is a back drop image
                imageUrl = movie.getBackdropPath();

                // set rating
                Float rating = (float) (movie.getRating() / 2.0);
                rbRating.setRating((float) (movie.getRating() / 2.0));
                tvRating.setText(rating.toString());
            } else {
                // else imageUrl is a poster image
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(ivPoster);

            // Register click listener on the whole row

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Navigate to a new activity on tap
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(intent);
                }
            });
        }
    }
}
