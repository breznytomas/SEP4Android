package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sep4android.R;
import com.example.sep4android.ViewModel.HomeViewModel;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView  emailTextView;
    private ImageView backButton, logOutButton;
    private HomeViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        /* -------------------------------------------------- */

        backButton = findViewById(R.id.back_button_profile);
        backButton.setOnClickListener(this);

        logOutButton = findViewById(R.id.profileLogoutButton);
        logOutButton.setOnClickListener(this);

        /* -------------------------------------------------- */

        emailTextView = findViewById(R.id.emailTextViewProfile);
        emailTextView.setText(viewModel.getCurrentUser().getEmail());
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