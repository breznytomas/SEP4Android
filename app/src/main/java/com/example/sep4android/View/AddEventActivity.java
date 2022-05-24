package com.example.sep4android.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sep4android.R;
import com.example.sep4android.SensorValue.ValueTypes;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton, addEventButton;

    /* TODO maybe change it to ValueTypes enum*/
    private String[] valueTypes = {"Temperature", "Humidity", "Carbon Dioxide", "Light"};

    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        /* -------------------------------------------------- */

        backButton = findViewById(R.id.back_button_add_event);
        backButton.setOnClickListener(this);

        addEventButton = findViewById(R.id.addEventButtonItemView);
        addEventButton.setOnClickListener(this);

        /* -------------------------------------------------- */

        autoCompleteTextView = findViewById(R.id.auto_complete_text);
        adapter = new ArrayAdapter<>(this, R.layout.sensors_type_list, valueTypes);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String sensorType = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Sensor Type: " + sensorType, Toast.LENGTH_SHORT).show();
            }
        });

        /* -------------------------------------------------- */
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_add_event) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (view.getId() == R.id.addEventButtonItemView) {
            addEvent();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private void addEvent() {
        Toast.makeText(this,
                "Event Added",
                Toast.LENGTH_SHORT).show();
    }
}