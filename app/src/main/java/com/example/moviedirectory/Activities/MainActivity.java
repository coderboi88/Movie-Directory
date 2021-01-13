package com.example.moviedirectory.Activities;


import android.app.DownloadManager;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviedirectory.Data.MovieRecyclerView;
import com.example.moviedirectory.Model.Movie;
import com.example.moviedirectory.R;
import com.example.moviedirectory.Util.Constants;
import com.example.moviedirectory.Util.Prefs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerView;
    private List<Movie> movieList;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                            }
        });



        recyclerView = (RecyclerView) findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieList = new ArrayList<>();

        Prefs prefs = new Prefs(MainActivity.this);
        String search = prefs.getSearch();

        movieList = getMovies(search);

        movieRecyclerView = new MovieRecyclerView(MainActivity.this,movieList);
        recyclerView.setAdapter(movieRecyclerView);
        movieRecyclerView.notifyDataSetChanged();
    }

    public List<Movie> getMovies(String searchterm){
        movieList.clear();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.URL_LEFT + searchterm + Constants.URL_RIGHT,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray moviesArray = response.getJSONArray("search");
                    for(int i=0;i<moviesArray.length();i++){
                      JSONObject moviesObject = moviesArray.getJSONObject(i);
                      Movie movie = new Movie();
                      movie.setTitle(moviesObject.getString("Title"));
                      movie.setYear("Released Year :"+ moviesObject.getString("Year"));
                      movie.setMovieType("Type :"+moviesObject.getString("Type"));
                      movie.setPoster(moviesObject.getString("Poster"));
                      movie.setImdbId(moviesObject.getString("imdbID"));
                        Log.d("MOvie :",movie.getTitle());
                      movieList.add(movie);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest);

        return movieList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
