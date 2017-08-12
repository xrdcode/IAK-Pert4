package com.xrdcode.pertemuan3;

import android.app.DownloadManager;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xrdcode.pertemuan3.adapter.MovieAdapter;
import com.xrdcode.pertemuan3.helper.MovieHelper;
import com.xrdcode.pertemuan3.model.Movies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public Movies movie;
    public LinearLayoutManager linearLayoutManager;
    public MovieAdapter movieAdapter;
    public String apiUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        apiUrl = MovieHelper.MOVIE_URL + "popular" + "?api_key=" + MovieHelper.API_KEY;
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
                movie = gson.fromJson(response, Movies.class);
                movieAdapter = new MovieAdapter(MainActivity.this, movie.results);
                recyclerView.setAdapter(movieAdapter);
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
