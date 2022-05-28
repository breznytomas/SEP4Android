package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.sep4android.Fragments.SettingsFragment;
import com.example.sep4android.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings_placeholder, new SettingsFragment())
                .commit();

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}