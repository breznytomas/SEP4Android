package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sep4android.R;
import com.example.sep4android.Repository.MessageRepository;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MessageRepository messageRepository;
    private TextView textView;
    private String printIt;

    // For now it will be an ImageView
    private ImageView loginButton;
    private TextView registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make to run your application only in portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);


        messageRepository = MessageRepository.getInstance();

        messageRepository.getMessage(7);

        /* -------------------------------------------------- */
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* TODO add transition animations */

        loginButton = findViewById(R.id.loginButtonItemView);
        loginButton.setOnClickListener(this);

        registerButton = findViewById(R.id.registerButtonTextView);
        registerButton.setOnClickListener(this);

        /* -------------------------------------------------- */
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButtonItemView) {
            startActivity(new Intent(this, GreenhouseHomeActivity.class));
        } else if (view.getId() == R.id.registerButtonTextView) {
            startActivity(new Intent(this, RegistrationActivity.class));
        }
    }
}

