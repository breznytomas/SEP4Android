package com.example.sep4android.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Adapter.BoardRecyclerAdapter;
import com.example.sep4android.Adapter.ViewEventRecyclerAdapter;
import com.example.sep4android.Model.Event;
import com.example.sep4android.R;
import com.example.sep4android.ViewModel.EventViewModel;
import com.example.sep4android.ViewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewEventsListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton;
    private RecyclerView recyclerViewEvents;

    private final String BOARD_ID_TEST = "0004A30B00259D2C";
    private EventViewModel eventViewModel;
    private ViewEventRecyclerAdapter viewEventsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events_list);

        backButton = findViewById(R.id.back_button_view_events);
        backButton.setOnClickListener(this);

        recyclerViewEvents = findViewById(R.id.eventsListRecyclerView);

        viewEventsRecyclerAdapter = new ViewEventRecyclerAdapter(this);
        recyclerViewEvents.setHasFixedSize(true);
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewEvents.setAdapter(viewEventsRecyclerAdapter);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        eventViewModel.getEvents(BOARD_ID_TEST).observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                viewEventsRecyclerAdapter.setList(events);
                viewEventsRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_view_events) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}