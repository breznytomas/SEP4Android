package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sep4android.R;
import com.example.sep4android.Repository.Repository;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView loginButton;
    private EditText emailEditText, passwordEditText;
    private TextView registerButton, forgotPassButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make to run your application only in portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

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
            loginUser();
            startActivity(new Intent(this, GreenhouseHomeActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.registerButtonTextView) {
            startActivity(new Intent(this, RegistrationActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (view.getId() == R.id.forgotPasswordButtonTextView) {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (email.isEmpty()) {
                emailEditText.setError("Email field cannot be empty!");
                emailEditText.requestFocus();
                return;
            }
            emailEditText.setError("Please provide a valid email");
            emailEditText.requestFocus();
            return;
        } else if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
        } else if (password.length() < 6) {
            passwordEditText.setError("Password field should contain " +
                    "at least 6 characters");
            passwordEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Toast.makeText(MainActivity.this,
                "Logging in",
                Toast.LENGTH_SHORT).show();
    }
}

