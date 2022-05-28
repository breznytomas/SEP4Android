package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sep4android.R;

public class EditEventActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView type;
    private ImageView backButton, editButton;
    private EditText renameEvent, setTopValue, setBottomValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        type = findViewById(R.id.editEventSensorTypeTextEvent);
        backButton = findViewById(R.id.back_button_edit_event);
        backButton.setOnClickListener(this);

        editButton = findViewById(R.id.editEventButtonItemView);
        editButton.setOnClickListener(this);

        renameEvent = findViewById(R.id.editEventNameEditTextEvent);
        setTopValue = findViewById(R.id.editEventTopEditTextEvent);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            String sensorType = bundle.getString("type");
            type.setText(sensorType);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_edit_event) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (view.getId() == R.id.editEventButtonItemView) {
            updateEvent();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private void updateEvent() {
        // TODO implement the method
    }
}