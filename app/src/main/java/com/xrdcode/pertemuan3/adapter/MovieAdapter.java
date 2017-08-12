package com.xrdcode.pertemuan3.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.xrdcode.pertemuan3.MovieDetailActivity;
import com.xrdcode.pertemuan3.R;
import com.xrdcode.pertemuan3.helper.MovieHelper;
import com.xrdcode.pertemuan3.model.Movies;

import java.util.List;

/**
 * Created by reysd on 06/08/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.AdapterHolder> {

    private Context mContext;
    private List<Movies.Result> movieList;
    private SharedPreferences pref;

    public MovieAdapter(Context mContext, List<Movies.Result> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @Override
    public MovieAdapter.AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.list_movies_item, parent, false);
        AdapterHolder adapterHolder = new AdapterHolder(rowView);
        return adapterHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapter.AdapterHolder holder, final int position) {
        holder.movieTitle.setText(movieList.get(position).title);
        holder.movieOverview.setText(movieList.get(position).overview);
        Picasso.with(mContext).load(MovieHelper.MOVIE_POSTER_URL + movieList.get(position).poster_path).into(holder.movieImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref = mContext.getSharedPreferences("PASSING_ID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("id", movieList.get(position).id);
                editor.commit();
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                //intent.putExtra("id", movieList.get(position).id);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder {

        TextView movieTitle, movieOverview;
        ImageView movieImg;

        public AdapterHolder(View itemView) {
            super(itemView);
            movieTitle = (TextView) itemView.findViewById(R.id.title);
            movieOverview = (TextView) itemView.findViewById(R.id.overview);
            movieImg = (ImageView) itemView.findViewById(R.id.movieImg);
        }
    }
}
