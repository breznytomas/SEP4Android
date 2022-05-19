package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.MessageResponse;
import com.example.sep4android.Repository.Repository;
import com.example.sep4android.ViewModel.MessageViewModel;

import java.util.List;

public class GreenhouseSensorsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView temperatureModule, co2Module;
    private TextView co2Details, greenHouseName;
    private MessageViewModel viewModel;
    private Repository repository;
    private LiveData<List<MessageResponse>> messageResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhouse_sensors);

        temperatureModule = findViewById(R.id.temperatureModule);
        temperatureModule.setOnClickListener(this);

        co2Module = findViewById(R.id.co2Module);
        co2Module.setOnClickListener(this);

        co2Details = findViewById(R.id.co2DetailsTextView);
        co2Details.setText("120");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        greenHouseName = findViewById(R.id.greenhouseNameTextDetails);

        if (bundle != null) {
            String name;
            name = bundle.getString("name");

            greenHouseName.setText(name);
        }

//        List<MessageResponse> mr = (List<MessageResponse>) repository.getReceivedMessages();
//        for (MessageResponse response : mr){
//            String content = "";
//            content += "Id:  " + response.getId() + "\n";
//            Log.d("Retrofit",content);
//        }


//        textView3.setText(mr.get(1).getId());

//        messageResponses = (MutableLiveData<List<MessageResponse>>) repository.getReceivedMessages();
//
//        for (int i = 0; i < 2 ; i++) {
//            String content = "";
//            content += messageResponses;
//            Log.d("Retrofit",content);
//            textView3.append(content);
//        }
//
//        for (MutableLiveData<MessageResponse> response : messageResponses){
//            String content = "";
//            content += "Id:  " + response.getValue().getId() + "\n";
//            Log.d("Retrofit",content);
//        }

//        textView3.setText(messageResponses.toString());


//        for (LiveData<MessageResponse> response : messageResponses) {
//            String content = "";
//            content += "Id:  " + response.getValue() + "\n";
//
//            textView3.append(content);
//        }

//        moduleTest.setOnClickListener(view -> {
//            Toast.makeText(this,
//                    "Button Clicked!",
//                    Toast.LENGTH_SHORT).show();
//        });

//        moduleTest.setOnTouchListener((view, event) -> {
//                int x = (int) event.getX();
//                if (100 <= x && x <= 800) {
//                    Toast.makeText(this,
//                            "Button Clicked!",
//                            Toast.LENGTH_SHORT).show();
//                }
//            return true;
//        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.temperatureModule) {
            startActivity(new Intent(this, TemperatureDetailsActivity.class));
        } else if (view.getId() == R.id.co2Module) {
            startActivity(new Intent(this, Co2DetailsActivity.class));
        }
    }
}