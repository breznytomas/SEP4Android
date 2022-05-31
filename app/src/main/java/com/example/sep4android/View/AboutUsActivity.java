package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.sep4android.R;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back_button_add_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        back_button_add_event = findViewById(R.id.back_button_add_event);
        back_button_add_event.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.back_button_add_event)
        {
            onBackPressed();
        }
    }
}