package com.example.sep4android.View;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4android.Model.Event;
import com.example.sep4android.R;
import com.example.sep4android.ViewModel.EditEventViewModel;

public class EditEventActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView typeTV;
    private ImageView backButton, editButton;
    private EditText renameEvent, setTopValue, setBottomValue;
    private Event event;
    private EditEventViewModel viewModel;
    private String boardId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        viewModel = new ViewModelProvider(this).get(EditEventViewModel.class);
        backButton = findViewById(R.id.back_button_edit_event);
        backButton.setOnClickListener(this);

        editButton = findViewById(R.id.editEventButtonItemView);
        editButton.setOnClickListener(this);

        typeTV = findViewById(R.id.typeTextView);
        renameEvent = findViewById(R.id.editEventNameEditTextEvent);
        setTopValue = findViewById(R.id.editEventTopEditTextEvent);
        setBottomValue = findViewById(R.id.editEventBottomEditTextEvent);


        /* Data binding */
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            boardId = bundle.getString("boardId");
            Log.d("EDIT_EVENT",boardId);
            event = new Event(bundle.getInt("eventId"),
                    bundle.getString("eventName"),
                    bundle.getInt("eventType"),
                    bundle.getFloat("eventTop"),
                    bundle.getFloat("eventBottom"));
        }
        switch(event.getType()) {
            case 0:
                //temperature
                typeTV.append(" temperature event");
                break;
            case 1:
                //humidity
                typeTV.append(" humidity event");
                break;
            case 2:
                //co2
                typeTV.append(" CO2 event");
                break;
            case 3:
                //light
                typeTV.append(" light event");

        }

        renameEvent.setText(event.getName());
        setBottomValue.setText(String.valueOf(event.getBottom()));
        setTopValue.setText(String.valueOf(event.getTop()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_edit_event) {
            onBackPressed();
        } else if (view.getId() == R.id.editEventButtonItemView) {
            updateEvent();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private void updateEvent() {
        Event newEvent = new Event(event.getEventId(),renameEvent.getText().toString().trim(),
                event.getType(),Float.parseFloat(setTopValue.getText().toString().trim()),
                Float.parseFloat(setBottomValue.getText().toString().trim()));

        viewModel.putEvent(boardId,newEvent);
        viewModel.refreshRepo(boardId);
        Toast.makeText(this, "Event edited", Toast.LENGTH_SHORT).show();
        onBackPressed();

    }
}