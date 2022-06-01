package com.example.sep4android.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.View.Util.Adapter.EventValuesRecyclerAdapter;
import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.EventValue;
import com.example.sep4android.RemoteDataSource.SensorValue;
import com.example.sep4android.View.Util.CustomMarkerView;
import com.example.sep4android.ViewModel.TemperatureDetailsViewModel;
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

public class TemperatureDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    /* TODO make the sensors color and text validation */

    private ImageView backButton, addEventButton;

    private TextView localTime, lastUpdatedTime, currentValue, averageValue, ratioValue, greenhouseName, sensorCondition;
    private LineChart lineChart;
    private RadioButton rb8Hours, rb24Hours, rb7Days, rb1Month;
    private RadioGroup radioGroup;
    private String boardId = "";
    private long chosenChartTimeMillis;
    private TemperatureDetailsViewModel viewModel;
    private List<Entry> entriesToShow;
    private List<Entry> allEntries;
    private EditText dateFromET;
    private EditText dateToET;
    private Button fetchBt,datePickFrom,datePickTo;;
    private RecyclerView eventsTriggeredRecycler;

    private final Calendar myCalendar = Calendar.getInstance();
    private String datePickTag = "";
    String resource="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_temperature_details);

        viewModel = new ViewModelProvider(this)
                .get(TemperatureDetailsViewModel.class);

        backButton = findViewById(R.id.back_button_temperature_details);
        backButton.setOnClickListener(this);

        fetchBt = findViewById(R.id.fetch_dim_Temperature);

        addEventButton = findViewById(R.id.addTemperatureEventsButtonItemView);
        addEventButton.setOnClickListener(this);

        lastUpdatedTime = findViewById(R.id.updatedLastValueTemperature);
        currentValue = findViewById(R.id.currentValueTemperature);

        averageValue = findViewById(R.id.average_value_Temperature);
        ratioValue = findViewById(R.id.ratio_value_Temperature);
        greenhouseName = findViewById(R.id.greenhouseNameTextViewTemperatureDetails);
        sensorCondition = findViewById(R.id.sensorConditionTemperature);

        /* -------------------------------------------------- */
        // Get data from Sensor Main Activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            String name = bundle.getString("greenhouseName");
            String resource = bundle.getString("valueType");
            boardId = bundle.getString("boardId");
            Log.d("TEMPERATURE", boardId);
            greenhouseName.setText(name);
            sensorCondition.setText(resource);
        }

        /* -------------------------------------------------- */
        dateFromET = findViewById(R.id.date_pick_from_ET_Temperature);

        dateToET = findViewById(R.id.date_pick_TO_ET_Temperature);

        datePickTo = findViewById(R.id.date_pick_to_Temperature);
        datePickFrom = findViewById(R.id.date_pick_from_Temperature);
        datePickTag = "";
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(datePickTag);
            }
        };
        datePickTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TemperatureDetailsActivity.this,date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                datePickTag ="TO";
            }
        });
        datePickFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TemperatureDetailsActivity.this,date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                datePickTag ="FROM";
            }
        });

        /* -------------------------------------------------- */
        /* LineChart */
        chosenChartTimeMillis = (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30));
        rb8Hours = findViewById(R.id.radioButtonTemperature_8H);
        rb24Hours = findViewById(R.id.radioButtonTemperatureDay);
        rb7Days = findViewById(R.id.radioButtonTemperatureWeek);
        rb1Month = findViewById(R.id.radioButtonTemperatureMonth);
        rb1Month.setChecked(true);
        radioGroup = findViewById(R.id.radio_group_Temperature);
        MarkerView mv = new CustomMarkerView(this, R.layout.tv_content_view);
        lineChart = findViewById(R.id.temperature_chart);
        lineChart.setTouchEnabled(true);
        lineChart.setMarkerView(mv);
        configureLineChart();


        viewModel.getTemperatureValueLiveData(boardId).observe(this, new Observer<List<SensorValue>>() {
            @Override
            public void onChanged(List<SensorValue> sensorValues) {
                if (!sensorValues.isEmpty()) {
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
        adapter.setUnitOfMeasure("°C");
        eventsTriggeredRecycler = findViewById(R.id.events_triggered_recycler_Temperature);
        eventsTriggeredRecycler.setHasFixedSize(true);
        eventsTriggeredRecycler.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        eventsTriggeredRecycler.setAdapter(adapter);


        fetchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getAverageTemperature(boardId,dateFromET.getText().toString(),dateToET.getText().toString()).observe(TemperatureDetailsActivity.this, new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        if (aDouble == null) {
                            averageValue.setText("N/A");
                        } else if (aDouble < -900) {
                            averageValue.setText("No data recorded for this time period");
                        } else
                            averageValue.setText(String.valueOf(aDouble));
                    }
                });
                viewModel.getTriggerRatioTemperature(boardId,dateFromET.getText().toString(),dateToET.getText().toString()).observe(TemperatureDetailsActivity.this, new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        if (aDouble == null) {
                            ratioValue.setText("N/A");
                        } else
                            ratioValue.setText(String.valueOf(aDouble));
                    }
                });
                viewModel.getEventValuesTemperature(boardId,dateFromET.getText().toString(),dateToET.getText().toString()).observe(TemperatureDetailsActivity.this, new Observer<List<EventValue>>() {
                    @Override
                    public void onChanged(List<EventValue> eventValues) {
                        adapter.clear();
                        if (eventValues != null) {
                            adapter.setEventValueList(eventValues);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

        /* -------------------------------------------------- */

        localTime = findViewById(R.id.localTimeValueTemperature);

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

                            private void updateLocalTimeTextView() {
                                String time = "dd/MM/yyyy HH:mm:ss";
                                localTime.setText(DateFormat.format(time, Calendar.getInstance().getTime()));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        localTimeThread.start();

        /* -------------------------------------------------- */
    }

    private void refreshLineChart(List<Entry> entries) {

        if (entries!=null && !entries.isEmpty()) {
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            LineDataSet valuesDataSet = new LineDataSet(entries, "temperature");
            valuesDataSet.setLineWidth(2);

            valuesDataSet.setLabel("Temperature in °C");
            valuesDataSet
                    .setDrawValues(false);

            valuesDataSet.setColor(Color.rgb(255, 255, 255));
            valuesDataSet.setCircleColor(Color.rgb(255, 255, 255));
            valuesDataSet.setCircleColorHole(Color.rgb(0, 145, 81));

            dataSets.add(valuesDataSet);
            LineData lineData = new LineData(dataSets);
            lineChart.setData(lineData);

            lineChart.invalidate();
        } else {
            lineChart.clear();
        }
    }
    private void updateLabel(String datePickTag) {
        String myFormat = "YYYY-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat,Locale.ENGLISH);
        if(datePickTag.equals("TO"))
            dateToET.setText(dateFormat.format(myCalendar.getTime()));
        else if(datePickTag.equals("FROM"))
            dateFromET.setText(dateFormat.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_temperature_details) {
            onBackPressed();
        } else if (view.getId() == R.id.addTemperatureEventsButtonItemView) {
            Intent i  =  new Intent(this, AddEventActivity.class);
            i.putExtra("boardId",boardId);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private List<Entry> convertToChartData(List<SensorValue> sensorValues) {
        List<Entry> entries = new ArrayList<>();
        for (SensorValue sensorValue : sensorValues) {
            float y = Float.parseFloat(sensorValue.getValue());
            float x = sensorValue.getTimestamp().getTime();

            entries.add(new Entry(x, y));
            Log.d("chart-check", String.valueOf(x));
        }

        Comparator<Entry> comparator = new Comparator<Entry>() {

            @Override
            public int compare(Entry t0, Entry t1) {
                return Float.compare(t0.getX(), t1.getX());
            }
        };
        entries.sort(comparator);
        return entries;
    }

    private List<Entry> filterEntries(List<Entry> toFilter) {
        if(toFilter!=null)
            return toFilter.stream().filter(e -> e.getX() > (chosenChartTimeMillis)).collect(Collectors.toList());
        else
            return null;
    }

    private void configureLineChart() {
        Description desc = new Description();
        desc.setText("");
        lineChart.setDescription(desc);

        lineChart.setExtraLeftOffset(15);
        lineChart.setExtraTopOffset(15);
        lineChart.setExtraBottomOffset(15);
        lineChart.setExtraRightOffset(15);
        lineChart.disableScroll();

        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisLeft().setDrawZeroLine(false);
        lineChart.getAxisLeft().setGridLineWidth(1);
//        lineChart.getAxisLeft().setGridColor(com.google.android.material.R.color.material_blue_grey_800);

        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisLeft().setDrawZeroLine(false);
        lineChart.getAxisLeft().setGridLineWidth(1);
//        lineChart.getAxisRight().setTextColor(com.google.android.material.R.color.material_blue_grey_800);

//        lineChart.getRendererRightYAxis().getPaintGrid().setColor(Color.green(1));
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextColor(com.google.android.material.R.color.material_blue_grey_800);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                long millis = (long) value;
                return mFormat.format(new Date(millis));
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonTemperature_8H:
                if (checked)
                    chosenChartTimeMillis = (System.currentTimeMillis() - TimeUnit.HOURS.toMillis(8));
                break;
            case R.id.radioButtonTemperatureDay:
                if (checked)
                    chosenChartTimeMillis = (System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24));
                break;
            case R.id.radioButtonTemperatureWeek:
                if (checked)
                    chosenChartTimeMillis = (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7));
                break;
            case R.id.radioButtonTemperatureMonth:
                if (checked)
                    chosenChartTimeMillis = (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30));
                break;
        }
        Log.d("chart-check", String.valueOf(chosenChartTimeMillis));
        entriesToShow = filterEntries(allEntries);
        refreshLineChart(entriesToShow);

    }




}