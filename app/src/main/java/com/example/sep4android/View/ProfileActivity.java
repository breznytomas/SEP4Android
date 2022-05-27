package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sep4android.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView firstNameTextView, lastNameTextView, streetTextView, countryTextView, postcodeTextView, emailTextView;
    private ImageView backButton;
    private Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backButton = findViewById(R.id.back_button_profile);
        backButton.setOnClickListener(this);

        logOutButton = findViewById(R.id.logoutButtonProfile);
        logOutButton.setOnClickListener(this);

        firstNameTextView = findViewById(R.id.firstNameTextViewProfile);
        lastNameTextView = findViewById(R.id.lastNameTextViewProfile);
        streetTextView = findViewById(R.id.streetTextViewProfile);
        countryTextView = findViewById(R.id.countryTextViewProfile);
        postcodeTextView = findViewById(R.id.postcodeTextViewProfile);
        emailTextView = findViewById(R.id.emailTextViewProfile);

        // Set text for textViews
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_profile) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (view.getId() == R.id.logoutButtonProfile) {
            Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}