package com.example.sep4android.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Adapter.ViewEventRecyclerAdapter;
import com.example.sep4android.Model.Board;
import com.example.sep4android.R;
import com.example.sep4android.ViewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewEventsListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton;
    private RecyclerView recyclerViewEvents;
    private List<Board> boardList;

    private final String EMAIL_TEST = "policja@gov.pl";
    private HomeViewModel homeViewModel;
    private ViewEventRecyclerAdapter viewEventsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events_list);

        backButton = findViewById(R.id.back_button_view_events);
        backButton.setOnClickListener(this);

        recyclerViewEvents = findViewById(R.id.eventsListRecyclerView);
        boardList = new ArrayList<>();

        viewEventsRecyclerAdapter = new ViewEventRecyclerAdapter(this);
        recyclerViewEvents.setHasFixedSize(true);
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewEvents.setAdapter(viewEventsRecyclerAdapter);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getBoardsLiveData(EMAIL_TEST).observe(this, new Observer<List<Board>>() {
            @Override
            public void onChanged(List<Board> boards) {
                viewEventsRecyclerAdapter.setList(boards);
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