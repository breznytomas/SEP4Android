package com.example.sep4android.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
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

import com.example.sep4android.Adapter.StringRecyclerAdapter;
import com.example.sep4android.R;

import java.util.ArrayList;
import java.util.List;

public class GreenhouseHomeActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    private StringRecyclerAdapter stringRecyclerAdapter;
    private RecyclerView modulesRecyclerView;

    private ImageView noDeviceImage;
    private TextView noDeviceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhouse_home_page);

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
        modulesRecyclerView = findViewById(R.id.list_view);
        List<String> greenhouseList = new ArrayList<>();

        String s1 = "JENSEN'S HOME";
        String s2 = "Test";
        greenhouseList.add(s1);
        greenhouseList.add(s2);

        noDeviceImage = findViewById(R.id.no_device_connected_image);
        noDeviceText = findViewById(R.id.no_device_connected_text);

        stringRecyclerAdapter = new StringRecyclerAdapter(this, greenhouseList);
        modulesRecyclerView.setHasFixedSize(true);
        modulesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        modulesRecyclerView.setAdapter(stringRecyclerAdapter);
        stringRecyclerAdapter.notifyDataSetChanged();

        if (greenhouseList.isEmpty()) {
            noDeviceText.setVisibility(View.VISIBLE);
            noDeviceImage.setVisibility(View.VISIBLE);
        }

        /* ----------------------------------------------------------------------------------------------------------------------------- */
    }
}