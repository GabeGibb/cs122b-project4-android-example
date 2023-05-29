package edu.uci.ics.fabflixmobile.ui.login;
import android.content.ContextParams;
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
import edu.uci.ics.fabflixmobile.ui.movielist.MovieListActivity;
import java.util.HashMap;
import java.util.Map;

import edu.uci.ics.fabflixmobile.ui.search.SearchActivity;
import org.json.JSONException;
import org.json.JSONObject;
public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView message;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        // upon creation, inflate and initialize the layout
        setContentView(binding.getRoot());

        username = binding.username;
        password = binding.password;
        message = binding.message;
        final Button loginButton = binding.login;

        //assign a listener to call a function to handle the user request when clicking a button
        loginButton.setOnClickListener(view -> login());
    }

    @SuppressLint("SetTextI18n")
    public void login() {
        message.setText("Trying to login");
        // use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        // request type is POST
        final StringRequest loginRequest = new StringRequest(
                Request.Method.POST,
                Url.baseUrl + "/api/login?mobile=true&username=" + username.getText().toString() +"&password=" + password.getText().toString(),
                response -> {
                    Log.d("response", response);

                    try {
                        JSONObject json = new JSONObject(response);

                        boolean goodLogin = json.getString("status").equals("success") && json.getString("message").equals("success");
                        if (goodLogin){
//                            Intent MovieListPage = new Intent(LoginActivity.this, MovieListActivity.class);
//                            startActivity(MovieListPage);
                            Intent SearchPage = new Intent(LoginActivity.this, SearchActivity.class);
                            startActivity(SearchPage);
                            finish();
                        }else{
                            message.setText(json.getString("message"));
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                },
                error -> {
                    // error
                    Log.d("login.error", error.toString());
                }) {

        };
        // important: queue.add is where the login request is actually sent
        queue.add(loginRequest);
    }
}