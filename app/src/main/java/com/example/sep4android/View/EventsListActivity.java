package com.example.sep4android.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.sep4android.R;
import com.example.sep4android.ViewModel.EventsListViewModel;

public class EventsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        EventsListViewModel viewModel = new ViewModelProvider(this).get(EventsListViewModel.class);
    }
}