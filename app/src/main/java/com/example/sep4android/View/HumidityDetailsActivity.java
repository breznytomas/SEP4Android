package com.example.sep4android.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sep4android.Adapter.EventValuesRecyclerAdapter;
import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.EventValue;
import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.View.Util.CustomMarkerView;
import com.example.sep4android.ViewModel.Co2DetailsViewModel;
import com.example.sep4android.ViewModel.HumidityDetailsViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HumidityDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton, addEventButton;
    private TextView localTime, lastUpdatedTime, sensorId, currentValue,averageValue,ratioValue,greenhouseName,sensorCondition;
    private LineChart lineChart;
    private RadioButton rb8Hours, rb24Hours, rb7Days, rb1Month;
    private RadioGroup radioGroup;
    private String boardId = "";
    private long chosenChartTimeMillis;
    private HumidityDetailsViewModel viewModel;
    private List<Entry> entriesToShow;
    private List<Entry> allEntries;
    private static String dimDateTo;
    private static String dimDateFrom;
    private static EditText dateFromET;
    private static EditText dateToET;
    private Button fetchBt;
    private RecyclerView eventsTriggeredRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_details);

        viewModel = new ViewModelProvider(this)
                .get(HumidityDetailsViewModel.class);

        /* -------------------------------------------------- */

        backButton = findViewById(R.id.back_button_humidity_details);
        backButton.setOnClickListener(this);

        addEventButton = findViewById(R.id.addHumidityEventsButtonItemView);
        addEventButton.setOnClickListener(this);

        fetchBt = findViewById(R.id.fetch_dim_humidity);

        lastUpdatedTime = findViewById(R.id.updatedLastValueHumidity);
        currentValue = findViewById(R.id.currentValueHumidity);
        averageValue = findViewById(R.id.average_value_humidity);
        ratioValue = findViewById(R.id.ratio_value_humidity);
        greenhouseName = findViewById(R.id.greenhouseNameTextViewHumidityDetails);
        sensorCondition = findViewById(R.id.sensorConditionHumidity);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null){
            String name = bundle.getString("greenhouseName");
            String resource = bundle.getString("valueType");
            boardId = bundle.getString("boardId");
            greenhouseName.setText(name);
            sensorCondition.setText(resource);
        }

        dimDateTo = new SimpleDateFormat("YYYY-MM-dd").format(new Date(System.currentTimeMillis()));
        dimDateFrom =new SimpleDateFormat("YYYY-MM-dd").format(new Date(System.currentTimeMillis()- TimeUnit.DAYS.toMillis(30))) ;
        dateFromET = findViewById(R.id.date_pick_from_ET_humidity);
        dateFromET.setText(dimDateFrom);
        dateToET = findViewById(R.id.date_pick_TO_ET_humidity);
        dateToET.setText(dimDateTo);

        /* -------------------------------------------------- */
        /* LineChart */
        chosenChartTimeMillis = (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30));
        rb8Hours = findViewById(R.id.radioButtonHumidity_8H);
        rb24Hours = findViewById(R.id.radioButtonHumidityDay);
        rb7Days = findViewById(R.id.radioButtonHumidityWeek);
        rb1Month = findViewById(R.id.radioButtonHumidityMonth);
        rb1Month.setChecked(true);
        radioGroup = findViewById(R.id.radio_group_humidity);
        MarkerView mv = new CustomMarkerView(this, R.layout.tv_content_view);
        lineChart = findViewById(R.id.humidity_chart);
        lineChart.setTouchEnabled(true);
        lineChart.setMarkerView(mv);
        configureLineChart();
        /* -------------------------------------------------- */

        viewModel.getHumidityValueLiveData(boardId).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                if(!sensorValues.isEmpty()){
                    Date unformattedDate = sensorValues.get(sensorValues.size() - 1).getTimestamp();
                    String formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(unformattedDate);

                    lastUpdatedTime.setText(formattedDate);
                    currentValue.setText(sensorValues.get(sensorValues.size() - 1).getValue());

                    allEntries = convertToChartData(sensorValues);
                    entriesToShow = filterEntries(allEntries);
                    refreshLineChart(entriesToShow);
                }
            }
        });

        /*
         * Events Triggered RecyclerView
         */
        EventValuesRecyclerAdapter adapter = new EventValuesRecyclerAdapter(this);
        adapter.setUnitOfMeasure("%");
        eventsTriggeredRecycler = findViewById(R.id.events_triggered_recycler_humidity);
        eventsTriggeredRecycler.setHasFixedSize(true);
        eventsTriggeredRecycler.setLayoutManager(
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        eventsTriggeredRecycler.setAdapter(adapter);


        fetchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getAverageHumidity(boardId,dimDateFrom,dimDateTo).observe(HumidityDetailsActivity.this, new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        if(aDouble==null){
                            averageValue.setText("N/A");
                        }
                        else if( aDouble< -900){
                            averageValue.setText("No data recorded for this time period");
                        } else
                            averageValue.setText(String.valueOf(aDouble));
                    }
                });
                viewModel.getTriggerRatioHumidity(boardId,dimDateFrom,dimDateTo).observe(HumidityDetailsActivity.this, new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        if(aDouble==null){
                            ratioValue.setText("N/A");
                        } else
                            ratioValue.setText(String.valueOf(aDouble));
                    }
                });
                viewModel.getEventValuesHumidity(boardId,dimDateFrom,dimDateTo).observe(HumidityDetailsActivity.this, new Observer<List<EventValue>>() {
                    @Override
                    public void onChanged(List<EventValue> eventValues) {
                        if(eventValues!=null){
                            adapter.setEventValueList(eventValues);
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
            }
        });

        /* -------------------------------------------------- */

        localTime = findViewById(R.id.localTimeValueHumidity);

        Thread localTimeThread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateLocalTimeTextView();
                            }
                        });
                    }
                } catch (InterruptedException e) {

                }
            }
        };

        localTimeThread.start();

        /* -------------------------------------------------- */
    }
    private void refreshLineChart(List<Entry> entries) {
        if(!entries.isEmpty()) {
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            LineDataSet valuesDataSet = new LineDataSet(entries, "Humidity");
            valuesDataSet.setDrawCircles(false);
            valuesDataSet.setCircleRadius(2);
            valuesDataSet.setDrawValues(false);
            valuesDataSet.setLineWidth(1);
            valuesDataSet.setColor(Color.rgb(255, 255, 255));
            valuesDataSet.setCircleColor(Color.rgb(255, 255, 255));
            dataSets.add(valuesDataSet);
            LineData lineData = new LineData(dataSets); //widzisz to kurwa?
            lineChart.setData(lineData);
            lineChart.invalidate();
        } else {
            lineChart.clear();
        }
    }

    private void updateLocalTimeTextView() {
        String time = "dd/MM/yyyy HH:mm:ss";
        localTime.setText(DateFormat.format(time, Calendar.getInstance().getTime()));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_humidity_details) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (view.getId() == R.id.addHumidityEventsButtonItemView) {
            startActivity(new Intent(this, AddEventActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private List<Entry>  convertToChartData(List<SensorValue> sensorValues){
        List<Entry> entries = new ArrayList<>();
        for (SensorValue sensorValue: sensorValues) {
            float y = Float.parseFloat(sensorValue.getValue());
            float x = sensorValue.getTimestamp().getTime();

            entries.add(new Entry(x,y));
            Log.d("chart-check",String.valueOf(x));

        }
        long currentTime = System.currentTimeMillis();

        Comparator<Entry> comparator = new Comparator<Entry>(){

            @Override
            public int compare(Entry t0, Entry t1) {
                return Float.compare(t0.getX(),t1.getX());
            }
        };
        entries.sort(comparator);
        return entries;
    }

    private List<Entry> filterEntries(List<Entry> toFilter) {
        return toFilter.stream().filter(e->e.getX()>(chosenChartTimeMillis)).collect(Collectors.toList());
    }

    private void configureLineChart(){
        Description desc = new Description();
        desc.setText("");
        lineChart.setDescription(desc);
        lineChart.setExtraLeftOffset(15);
        lineChart.setExtraRightOffset(15);
        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisRight().setDrawLabels(true);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM hh:mm", Locale.ENGLISH);
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                long millis = (long) value;
                return mFormat.format(new Date(millis));
            }
        });
    }

    public void onRadioButtonClicked(View view ) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()){
            case R.id.radioButtonHumidity_8H:
                if(checked)
                    chosenChartTimeMillis = (System.currentTimeMillis() - TimeUnit.HOURS.toMillis(8));
                break;
            case R.id.radioButtonHumidityDay:
                if(checked)
                    chosenChartTimeMillis = (System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24));
                break;
            case R.id.radioButtonHumidityWeek:
                if(checked)
                    chosenChartTimeMillis = (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7));
                break;
            case R.id.radioButtonHumidityMonth:
                if(checked)
                    chosenChartTimeMillis = (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30));
                break;
        }
        Log.d("chart-check",String.valueOf(chosenChartTimeMillis));
        entriesToShow = filterEntries(allEntries);
        refreshLineChart(entriesToShow);

    }

    public void showDateFromPickerDialog(View v){
        DialogFragment newFragment = new Co2DetailsActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"dateFromPicker");
    }
    public void showDateToPickerDialog(View v){
        DialogFragment newFragment = new Co2DetailsActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"dateToPicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener{
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),this,year,month,day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            switch(getTag()){
                case "dateFromPicker":
                    dimDateFrom = year+"-"+(month+1)+"-"+day;
                    dateFromET.setText(dimDateFrom);
                    break;
                case "dateToPicker":
                    dimDateTo = year+"-"+(month+1)+"-"+day;
                    dateToET.setText(dimDateTo);
                    break;
            }
        }
    }
}