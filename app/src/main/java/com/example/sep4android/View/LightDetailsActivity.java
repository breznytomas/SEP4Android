package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sep4android.R;

import java.util.Calendar;

public class LightDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton;
    private TextView localTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_details);

        /* -------------------------------------------------- */

        backButton = findViewById(R.id.back_button_light_details);
        backButton.setOnClickListener(this);

        /* -------------------------------------------------- */

        localTime = findViewById(R.id.localTimeValueLight);

        Thread localTimeThread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateLocalTimeTextView();
                            }

                            private void updateLocalTimeTextView() {
                                String time = "dd/MM/yyyy HH:mm:ss";
                                localTime.setText(DateFormat.format(time, Calendar.getInstance().getTime()));
                            }
                        });
                    }
                } catch (InterruptedException e) {

                }
            }
        };

        localTimeThread.start();

        /* -------------------------------------------------- */
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_light_details)
        {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}