package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sep4android.Model.User;
import com.example.sep4android.R;
import com.example.sep4android.ViewModel.ForgotPasswordViewModel;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton, resetButton;
    private EditText emailEditText, newPasswordEditText, oldPasswordEditText;
    private ProgressBar progressBar;
    private ForgotPasswordViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_forgot_password);

        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        /* -------------------------------------------------- */

        backButton = findViewById(R.id.back_button_forgot);
        backButton.setOnClickListener(this);

        resetButton = findViewById(R.id.resetPassButtonItemView);
        resetButton.setOnClickListener(this);

        emailEditText = findViewById(R.id.emailAddressEditTextForgotPassword);
        newPasswordEditText = findViewById(R.id.newPasswordEditTextForgotPassword);
        oldPasswordEditText = findViewById(R.id.oldPasswordEditTextForgotPassword);
        progressBar = findViewById(R.id.progressBarForgotPassword);

        /* -------------------------------------------------- */

        String email = emailEditText.getText().toString().trim();

        /* -------------------------------------------------- */
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_forgot) {
            onBackPressed();
        } else if (view.getId() == R.id.resetPassButtonItemView) {
            resetPassword();
            onBackPressed();
        }
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();
        String oldPassword = oldPasswordEditText.getText().toString().trim();
        String newPassword = newPasswordEditText.getText().toString().trim();


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (email.isEmpty()) {
                emailEditText.setError("Email field cannot be empty!");
                emailEditText.requestFocus();
            }
            emailEditText.setError("Please provide a valid email");
            emailEditText.requestFocus();
        } else if (oldPassword.isEmpty()) {
            oldPasswordEditText.setError("Old password is required");

        }  else if (newPassword.isEmpty()) {
            newPasswordEditText.setError("New password is required");
        }
        else if (oldPassword.length() < 6) {
            oldPasswordEditText.setError("Password field should contain " +
                    "at least 6 characters");
            oldPasswordEditText.requestFocus();

        }
        else if (newPassword.length() < 6) {
            newPasswordEditText.setError("Password field should contain " +
                    "at least 6 characters");
            newPasswordEditText.requestFocus();

        }
        viewModel.resetPassword(newPassword,new User(email,oldPassword));
        Toast.makeText(ForgotPasswordActivity.this,
                "Password changed",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}