package com.example.sep4android.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Adapter.BoardRecyclerAdapter;
import com.example.sep4android.Adapter.StringRecyclerAdapter;
import com.example.sep4android.Model.Board;
import com.example.sep4android.R;
import com.example.sep4android.ViewModel.HomeViewModel;

import java.util.List;

public class GreenhouseHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawer;

    private RecyclerView modulesRecyclerView;

    private ImageView noDeviceImage, addButton;
    private TextView noDeviceText;
    private ProgressBar loadingIndicator;
    private final String EMAIL_TEST = "policja@gov.pl";

    /* TODO add a progressbar as the data is fetched from the server*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhouse_home_page);

        addButton = findViewById(R.id.add_button_home_page);
        addButton.setOnClickListener(this);

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        BoardRecyclerAdapter adapter = new BoardRecyclerAdapter(this);

        /* ----------------------------------------------------------------------------------------------------------------------------- */
        /* Navigation Drawer */

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.green_alternative));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /* ----------------------------------------------------------------------------------------------------------------------------- */
        /* RecyclerView */

        loadingIndicator = findViewById(R.id.board_list_loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        modulesRecyclerView = findViewById(R.id.list_view);
        noDeviceImage = findViewById(R.id.no_device_connected_image);
        noDeviceText = findViewById(R.id.no_device_connected_text);

        modulesRecyclerView.setHasFixedSize(true);
        modulesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        modulesRecyclerView.setAdapter(adapter);

        homeViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading) loadingIndicator.setVisibility(View.VISIBLE);
                else loadingIndicator.setVisibility(View.GONE);
            }
        });
        homeViewModel.getBoardsLiveData(EMAIL_TEST).observe(this, new Observer<List<Board>>() {
            @Override
            public void onChanged(List<Board> boards) {
                homeViewModel.getIsLoading().postValue(false);
                adapter.setList(boards);
                adapter.notifyDataSetChanged();

                if (boards.isEmpty()) {
                    noDeviceText.setVisibility(View.VISIBLE);
                    noDeviceImage.setVisibility(View.VISIBLE);
                }
            }
        });

        /* ----------------------------------------------------------------------------------------------------------------------------- */
    }

    @Override
    protected void onResume() {
        super.onResume();
        addButton.setImageResource(R.drawable.add_button_default);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_button_home_page) {
            addButton.setImageResource(R.drawable.add_button_clicked);
            startActivity(new Intent(GreenhouseHomeActivity.this, AddGreenhouseBoardActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}