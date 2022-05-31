package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.AuthentificationDataSource;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView  emailTextView;
    private ImageView backButton, logOutButton;
    private AuthentificationDataSource authentificationDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /* -------------------------------------------------- */

        backButton = findViewById(R.id.back_button_profile);
        backButton.setOnClickListener(this);

        logOutButton = findViewById(R.id.profileLogoutButton);
        logOutButton.setOnClickListener(this);

        /* -------------------------------------------------- */

        emailTextView = findViewById(R.id.emailTextViewProfile);
        emailTextView.setText(AuthentificationDataSource.loggedUser.getEmail());
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        /* -------------------------------------------------- */
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_profile) {
            onBackPressed();
        } else if (view.getId() == R.id.profileLogoutButton) {
            /* TODO implement LOGOUT*/
            Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show();
            // TODO implement this (authentificationDataSource.logout();)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}