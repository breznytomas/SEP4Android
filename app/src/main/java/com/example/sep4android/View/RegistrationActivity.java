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

import com.example.sep4android.R;
import com.example.sep4android.ViewModel.AuthentificationViewModel;

public class RegistrationActivity extends AppCompatActivity {

    private EditText firstName, lastName, streetAddress, country, postalCode, email, password;
    private ImageView registerButton, backButton;
    private ProgressBar progressBar;
    private AuthentificationViewModel authViewModel;
    private ImageView login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registartion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        authViewModel = new ViewModelProvider(this)
                .get(AuthentificationViewModel.class);

        firstName = findViewById(R.id.registrationFirstNameEditText);
        lastName = findViewById(R.id.registrationLastNameEditText);
        streetAddress = findViewById(R.id.registrationStreetEditText);
        country = findViewById(R.id.registrationCountryEditText);
        postalCode = findViewById(R.id.registrationPostalCodeEditText);
        email = findViewById(R.id.registrationEmailEditText);
        password = findViewById(R.id.registrationPasswordEditText);
        progressBar = findViewById(R.id.progressBarRegister);

        backButton = findViewById(R.id.back_button_registration);
        backButton.setOnClickListener(view -> {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                });


        registerButton = findViewById(R.id.registrationRegisterButton);
        registerButton.setOnClickListener(view -> {
            registerUser();

            if (registerUser() == 0) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private int registerUser() {
        String firstNameTemp = firstName.getText().toString().trim();
        String lastNameTemp = lastName.getText().toString().trim();
        String streetTemp = streetAddress.getText().toString().trim();
        String countryTemp = country.getText().toString().trim();
        String postCodeTemp = postalCode.getText().toString().trim();
        String emailTemp = email.getText().toString().trim();
        String passwordTemp = password.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(emailTemp).matches()) {
            if (emailTemp.isEmpty()) {
                email.setError("Email field cannot be empty!");
                email.requestFocus();
                return -1;
            }

            email.setError("Please provide a valid email");
            email.requestFocus();
            return -1;

        } else if (passwordTemp.isEmpty() && passwordTemp.length() < 6) {
            password.setError("Password field should contain " +
                    "at least 6 characters");
            password.requestFocus();
            return -1;
        }

        progressBar.setVisibility(View.VISIBLE);

        authViewModel.register(emailTemp,passwordTemp);
        return 0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}