package edu.uci.ics.fabflixmobile.ui.movielist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import edu.uci.ics.fabflixmobile.R;
import edu.uci.ics.fabflixmobile.data.NetworkManager;
import edu.uci.ics.fabflixmobile.data.model.Movie;
import edu.uci.ics.fabflixmobile.data.model.Url;
import edu.uci.ics.fabflixmobile.ui.login.LoginActivity;
import edu.uci.ics.fabflixmobile.ui.search.SearchActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity {
    public static String[] toStringArray(JSONArray array) {
        if(array==null)
            return new String[0];

        String[] arr=new String[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optString(i);
        }
        return arr;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);
        // TODO: this should be retrieved from the backend server


        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        // request type is POST
        final StringRequest movieRequest = new StringRequest(
                Request.Method.GET,
                this.getIntent().getData().toString(),
                response -> {
                    Log.d("response", response);

                    try {
                        final ArrayList<Movie> movies = new ArrayList<>();
//                        movies.add(new Movie("The Terminal", (short) 2004));
//                        movies.add(new Movie("The Final Season", (short) 2007));

                        JSONArray json = new JSONArray(response);
                        JSONObject obj;
                        String title;
                        short year;
                        String[] actors;
                        String[] genres;
                        for(int i = 1; i < json.length(); i++){
                            obj = json.getJSONObject(i);
                            title = obj.getString("movie_title");
                            year = (short) obj.getInt("movie_year");
                            actors = toStringArray(obj.getJSONArray("movie_stars"));
                            genres = toStringArray(obj.getJSONArray("movie_genres"));
                            movies.add(new Movie(title, year, actors, genres));

                        }

                        MovieListViewAdapter adapter = new MovieListViewAdapter(this, movies);

                        ListView listView = findViewById(R.id.list);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener((parent, view, position, id) -> {
                            Movie movie = movies.get(position);
                            @SuppressLint("DefaultLocale") String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getName(), movie.getYear());
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        });


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                },
                error -> {
                    // error
                    Log.d("movie.error", error.toString());
                }) {

        };
        // important: queue.add is where the login request is actually sent
        queue.add(movieRequest);

    }
}