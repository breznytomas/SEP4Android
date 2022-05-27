package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sep4android.R;
import com.example.sep4android.ViewModel.ForgotPasswordViewModel;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton, resetButton;
    private EditText emailEditText;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_forgot_password);

        ForgotPasswordViewModel viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        /* -------------------------------------------------- */

        backButton = findViewById(R.id.back_button_forgot);
        backButton.setOnClickListener(this);

        resetButton = findViewById(R.id.resetPassButtonItemView);
        resetButton.setOnClickListener(this);

        emailEditText = findViewById(R.id.emailAddressEditTextForgotPassword);
        progressBar = findViewById(R.id.progressBarForgotPassword);

        /* -------------------------------------------------- */

        String email = emailEditText.getText().toString().trim();

        /* -------------------------------------------------- */
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_forgot) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (view.getId() == R.id.resetPassButtonItemView) {
            resetPassword();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private void resetPassword() {
        Toast.makeText(ForgotPasswordActivity.this,
                "Instructions link sent",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}