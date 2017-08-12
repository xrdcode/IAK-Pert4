package com.xrdcode.pertemuan3;

import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.xrdcode.pertemuan3.adapter.MovieAdapter;
import com.xrdcode.pertemuan3.helper.MovieHelper;
import com.xrdcode.pertemuan3.model.MovieDetail;
import com.xrdcode.pertemuan3.model.Movies;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MovieDetailActivity extends AppCompatActivity {

    TextView title, rating, overview, genre;
    ImageView poster, backdrop;
    MovieDetail movieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        title = (TextView) findViewById(R.id.title);
        rating = (TextView) findViewById(R.id.rating);
        overview = (TextView) findViewById(R.id.overview);
        genre = (TextView) findViewById(R.id.genre);

        poster = (ImageView) findViewById(R.id.poster);
        backdrop = (ImageView)findViewById(R.id.backdrop);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String apiUrl = MovieHelper.MOVIE_URL + id + "?api_key=" + MovieHelper.API_KEY;
        JsonParser(apiUrl);
    }

    private void JsonParser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Main Activity", "Response " + response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                movieDetail = gson.fromJson(response, MovieDetail.class);
                title.setText(movieDetail.title);
                rating.setText(String.valueOf(movieDetail.vote_average));
                overview.setText(movieDetail.overview);

                Picasso.with(MovieDetailActivity.this).load(MovieHelper.MOVIE_IMAGE_URL + MovieHelper.MOVIE_POSTER_SIZE[0] + movieDetail.poster_path).fit().centerCrop().into(poster);
                Picasso.with(MovieDetailActivity.this).load(MovieHelper.MOVIE_IMAGE_URL + MovieHelper.MOVIE_BACKDROP_SIZE[1] + movieDetail.backdrop_path).fit().centerCrop().into(backdrop);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                makeText(getApplication(), "Error while connecting to the server.", LENGTH_SHORT).show();
                makeText(getApplication(), "Please check your connection.", LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }
}
