package com.example.sep4android.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.sep4android.R;
import com.example.sep4android.SensorValue.ValueTypes;
import com.google.android.material.textfield.TextInputLayout;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton, addEventButton;
    private EditText eventName, eventTopValue, eventBottomValue;
    private String sensorType;

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

        eventName = findViewById(R.id.NameEditTextEvent);
        eventTopValue = findViewById(R.id.TopEditTextEvent);
        eventBottomValue = findViewById(R.id.BottomEditTextEvent);

        /* -------------------------------------------------- */

        autoCompleteTextView = findViewById(R.id.auto_complete_text);
        adapter = new ArrayAdapter<>(this, R.layout.sensors_type_list, valueTypes);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                sensorType = String.valueOf(adapterView.getItemAtPosition(position));
                Log.d("SENSOR TESTING", "SensorType: " + sensorType);
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

        String name = eventName.getText().toString().trim();
        String top = eventTopValue.getText().toString().trim();
        String bottom = eventBottomValue.getText().toString().trim();
        String typeOfSensor = String.valueOf(validateSensorType());

        Log.d("DATA TEST", "Name: " + name + " Top: " + top + " Bottom: " + bottom + " SensorValue: " + typeOfSensor);
    }

    private int validateSensorType() {
        if (sensorType.equalsIgnoreCase("temperature")) {
            return 0;
        } else if (sensorType.equalsIgnoreCase("humidity")) {
            return 1;
        } else if (sensorType.equalsIgnoreCase("carbon dioxide")) {
            return 2;
        } else if (sensorType.equalsIgnoreCase("light")) {
            return 3;
        }

        return -1;
    }
}