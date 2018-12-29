package com.juyng.movieapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context context;
    private List<Movie> moviesList;

    public MoviesAdapter(Context context, List<Movie> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_movie_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MyViewHolder myViewHolder, int i) {
        Movie movie = moviesList.get(i);
        if(movie.getImage().equals("")) {
            Glide.with(context).load(R.drawable.preparing).into(myViewHolder.image);
        } else {
            Glide.with(context).load(movie.getImage()).into(myViewHolder.image);
        }
        myViewHolder.title.setText(movie.getTitle());
        myViewHolder.userRating.setRating(Float.parseFloat(movie.getUserRating()));
        myViewHolder.pubDate.setText(movie.getPubDate());
        myViewHolder.director.setText(movie.getDirector());
        myViewHolder.actor.setText(movie.getActor());
    }

    @Override
    public int getItemCount() {
        if(moviesList != null) {
            return moviesList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        RatingBar userRating;
        TextView pubDate;
        TextView director;
        TextView actor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            userRating = (RatingBar) itemView.findViewById(R.id.userRating);
            pubDate = (TextView) itemView.findViewById(R.id.pubDate);
            director = (TextView) itemView.findViewById(R.id.director);
            actor = (TextView) itemView.findViewById(R.id.actor);
        }
    }
}
