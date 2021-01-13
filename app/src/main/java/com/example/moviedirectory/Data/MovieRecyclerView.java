package com.example.moviedirectory.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedirectory.Model.Movie;
import com.example.moviedirectory.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<MovieRecyclerView.ViewHolder> {
    private Context context;
    private List<Movie> movieList;

    public MovieRecyclerView(Context context, List<Movie> movies) {
        this.context = context;
        this.movieList = movies;
    }

    @NonNull
    @Override
    public MovieRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerView.ViewHolder holder, int position) {
            Movie movie = movieList.get(position);
            String posterLink = movie.getPoster();

            holder.title.setText(movie.getTitle());
            holder.year.setText(movie.getYear());

        Picasso.with(context)
                .load(posterLink)
                .placeholder(android.R.drawable.ic_btn_speak_now)
                .into(holder.poster);

            holder.type.setText(movie.getMovieType());

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title,year,type;
        ImageView poster;
        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context = ctx;

            title = (TextView) itemView.findViewById(R.id.movieTitleID);
            year = (TextView) itemView.findViewById(R.id.movieReleaseId);
            poster = (ImageView) itemView.findViewById(R.id.movieImageID);
            type = (TextView) itemView.findViewById(R.id.movieCatID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Row Tapped",Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
