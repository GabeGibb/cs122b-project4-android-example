package edu.uci.ics.fabflixmobile.ui.search;
import android.content.ContextParams;
import android.net.Uri;
import android.widget.ListView;
import edu.uci.ics.fabflixmobile.R;
import edu.uci.ics.fabflixmobile.data.model.Url;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import edu.uci.ics.fabflixmobile.data.NetworkManager;
import edu.uci.ics.fabflixmobile.databinding.ActivityLoginBinding;
import edu.uci.ics.fabflixmobile.ui.login.LoginActivity;
import edu.uci.ics.fabflixmobile.ui.movielist.MovieListActivity;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class SearchActivity extends AppCompatActivity {

    private EditText search;
    private Button btn;


    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        search = (EditText) findViewById(R.id.searchField);
        btn = (Button) findViewById(R.id.searchButton);

        //assign a listener to call a function to handle the user request when clicking a button
        btn.setOnClickListener(view -> search());
    }

    @SuppressLint("SetTextI18n")
    public void search() {
        // use the same network queue across our application
//        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        // request type is POST

        Intent SearchPage = new Intent(SearchActivity.this, MovieListActivity.class);
        SearchPage.setData(Uri.parse(Url.baseUrl + "/api/movies?n=10&full-text=true&title=" + search.getText().toString()));
        startActivity(SearchPage);
        finish();
    }
}