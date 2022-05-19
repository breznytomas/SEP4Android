package com.example.sep4android.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sep4android.R;

import java.util.ArrayList;
import java.util.List;

public class GreenhouseHomeActivity extends AppCompatActivity {

    private Button button;
    private DrawerLayout drawer;
    private ListView listView;

    private ImageView noDeviceImage;
    private TextView noDeviceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhouse_home_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.green_alternative));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        button = findViewById(R.id.buttonHe);

        listView = findViewById(R.id.list_view);

        List<String> list = new ArrayList<>();

        noDeviceImage = findViewById(R.id.no_device_connected_image);
        noDeviceText = findViewById(R.id.no_device_connected_text);

        if (list.isEmpty()) {
            noDeviceText.setVisibility(View.VISIBLE);
            noDeviceImage.setVisibility(View.VISIBLE);
        } else {
            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
            listView.setAdapter(arrayAdapter);
        }


    }
}