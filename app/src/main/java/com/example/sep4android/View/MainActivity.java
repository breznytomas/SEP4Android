package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sep4android.R;
import com.example.sep4android.Repository.Repository;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Repository repository;
    private TextView textView;
    private String printIt;

    private ImageView loginButton;
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
}

