package com.example.sep4android.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sep4android.Adapter.BoardRecyclerAdapter;
import com.example.sep4android.Adapter.StringRecyclerAdapter;
import com.example.sep4android.Model.Board;
import com.example.sep4android.R;
import com.example.sep4android.ViewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class GreenhouseHomeActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    private StringRecyclerAdapter stringRecyclerAdapter;
    private RecyclerView modulesRecyclerView;

    private ImageView noDeviceImage;
    private TextView noDeviceText;
    private final String EMAIL_TEST = "policja@gov.pl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhouse_home_page);

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
        /* Recycler View*/
        modulesRecyclerView = findViewById(R.id.list_view);
        noDeviceImage = findViewById(R.id.no_device_connected_image);
        noDeviceText = findViewById(R.id.no_device_connected_text);

        modulesRecyclerView.setHasFixedSize(true);
        modulesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        modulesRecyclerView.setAdapter(adapter);
//        List<String> greenhouseList = new ArrayList<>();
//
//        String s1 = "JENSEN'S HOME";
//        String s2 = "Test";
//        greenhouseList.add(s1);
//        greenhouseList.add(s2);


        homeViewModel.getBoardsLiveData(EMAIL_TEST).observe(this, new Observer<List<Board>>() {
            @Override
            public void onChanged(List<Board> boards) {
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
}