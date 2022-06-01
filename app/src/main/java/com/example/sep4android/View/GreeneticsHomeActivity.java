package com.example.sep4android.View;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.sep4android.Adapter.BoardRecyclerAdapter;
import com.example.sep4android.Model.Board;
import com.example.sep4android.Model.User;
import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.AuthentificationDataSource;
import com.example.sep4android.Repository.FetchWorker;
import com.example.sep4android.Repository.InstantiateFetchWorker;
import com.example.sep4android.ViewModel.HomeViewModel;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GreeneticsHomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private RecyclerView modulesRecyclerView;

    private ImageView noDeviceImage, addButton;
    private TextView noDeviceText;
    private ProgressBar loadingIndicator;
    private final String EMAIL_TEST = "policja@gov.pl";
    private AuthentificationDataSource auth;
    private User usernow;
    private HomeViewModel viewModel;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String email;
    private Calendar calendar = Calendar.getInstance();

    /* TODO add a progressbar as the data is fetched from the server*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        usernow = AuthentificationDataSource.loggedUser;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhouse_home_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        addButton = findViewById(R.id.add_button_home_page);
        addButton.setOnClickListener(this);

        viewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        BoardRecyclerAdapter adapter = new BoardRecyclerAdapter(this);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(EMAIL_KEY,null);
        editor = sharedPreferences.edit();
        /* ----------------------------------------------------------------------------------------------------------------------------- */
        /* Navigation Drawer */

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        TextView email = (TextView) headerLayout.findViewById(R.id.profileEmail);
        email.setText(usernow.getEmail());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.green_alternative));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /* ----------------------------------------------------------------------------------------------------------------------------- */
        /* Nav Drawer data binding */

       headerLayout = navigationView.getHeaderView(0);

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

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) loadingIndicator.setVisibility(View.VISIBLE);
                else loadingIndicator.setVisibility(View.GONE);
            }
        });

        viewModel.getBoardsLiveData(usernow.getEmail()).observe(this, new Observer<List<Board>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(List<Board> boards) {
                viewModel.getIsLoading().postValue(false);
                adapter.setList(boards);
                adapter.notifyDataSetChanged();

                if (boards.isEmpty()) {
                    noDeviceText.setVisibility(View.VISIBLE);
                    noDeviceImage.setVisibility(View.VISIBLE);
                }else{
                    noDeviceText.setVisibility(View.GONE);
                    noDeviceImage.setVisibility(View.GONE);
                }
                startWorker(boards);
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
    public void onBackPressed() {
        /* This should be empty -> To disable back button in this activity*/
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_button_home_page) {
            addButton.setImageResource(R.drawable.add_button_clicked);
            startActivity(new Intent(GreeneticsHomeActivity.this, AddBoardActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            Intent intent = new Intent(this, GreeneticsHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if ((item.getItemId() == R.id.nav_profile)) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (item.getItemId() == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (item.getItemId() == R.id.nav_logout) {
            /* TODO to implement logout */
            editor.clear();
            editor.apply();
            Intent i = new Intent(GreeneticsHomeActivity.this, GreeneticsMainActivity.class);
            startActivity(i);
            viewModel.logout(auth);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startWorker(List<Board> boards){
        String[] boardIds = new String[20];
        if(!boards.isEmpty()){
            for(int i=0;i<boards.size();i++){
                boardIds[i] = boards.get(i).getBoardId();
            }
            Data data = new Data.Builder()
                    .putStringArray("BOARD_IDS", boardIds)
                    .build();

            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresCharging(false)
                    .setRequiresBatteryNotLow(false)
                    .setRequiresStorageNotLow(false)
                    .build();
            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder
                    (InstantiateFetchWorker.class, 15, TimeUnit.MINUTES)
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build();

            WorkManager.getInstance(this)
                    .enqueueUniquePeriodicWork("Start fetching",
                            ExistingPeriodicWorkPolicy.KEEP,periodicWorkRequest);
            Log.d("WORKER", "Starting first worker");
        }

    }
}