package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sep4android.Model.User;
import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.AuthentificationDataSource;
import com.example.sep4android.Repository.Repository;
import com.example.sep4android.ViewModel.AuthVMFactory;
import com.example.sep4android.ViewModel.AuthentificationViewModel;
import com.example.sep4android.ViewModel.LoggedUserView;

public class GreeneticsMainActivity extends AppCompatActivity implements View.OnClickListener {

    private Repository repository;
    private AuthentificationViewModel authViewModel;
    private TextView textView;
    private String printIt;


    private ImageView loginButton;
    private EditText emailEditText, passwordEditText;
    private TextView registerButton, forgotPassButton;
    private ProgressBar progressBar;
    private User loggedInUser;
    private AuthentificationDataSource auth;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedPreferences;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make to run your application only in portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        authViewModel = new ViewModelProvider(this,new AuthVMFactory())
                .get(AuthentificationViewModel.class);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(EMAIL_KEY,null);
        password = sharedPreferences.getString(PASSWORD_KEY,null);
        /* -------------------------------------------------- */

        progressBar = findViewById(R.id.progressBarLogin);

        emailEditText = findViewById(R.id.emailAddressEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        loginButton = findViewById(R.id.loginButtonItemView);
        loginButton.setOnClickListener(this);

        registerButton = findViewById(R.id.registerButtonTextView);
        registerButton.setOnClickListener(this);

        forgotPassButton = findViewById(R.id.forgotPasswordButtonTextView);
        forgotPassButton.setOnClickListener(this);

        /* -------------------------------------------------- */



    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButtonItemView) {
            if(loginUser()){
                Intent i = new Intent(GreeneticsMainActivity.this, GreeneticsHomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else{
                Toast.makeText(this,"Login failed", Toast.LENGTH_SHORT).show();
            }

        } else if (view.getId() == R.id.registerButtonTextView) {
            startActivity(new Intent(this, RegistrationActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.forgotPasswordButtonTextView) {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    private boolean loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        final boolean[] successful = {false};

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (email.isEmpty()) {
                emailEditText.setError("Email field cannot be empty!");
                emailEditText.requestFocus();

            }
            emailEditText.setError("Please provide a valid email");
            emailEditText.requestFocus();
        } else if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
        } else if (password.length() < 6) {
            passwordEditText.setError("Password field should contain " +
                    "at least 6 characters");
            passwordEditText.requestFocus();

        }

        progressBar.setVisibility(View.VISIBLE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(EMAIL_KEY, email);
//        editor.putString(PASSWORD_KEY,password);
//        editor.apply();
        authViewModel.login(email,password).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                successful[0] = user.getEmail().equals(email);
            }
        });

        return successful[0];

    }
    public void makenewUser(LoggedUserView model){
        String welcome = "Welcome " + model.getEmail();
        Toast.makeText(getApplicationContext(),welcome,Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, GreeneticsHomeActivity.class));

    }

    @Override
    protected void onStart(){
        super.onStart();
//        if(email!=null && password !=null){
//            Intent i = new Intent(GreeneticsMainActivity.this, GreeneticsHomeActivity.class);
//            authViewModel.login(email,password);
//            startActivity(i);
//        }
    }
}

