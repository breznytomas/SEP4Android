package com.example.sep4android.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Model.Event;
import com.example.sep4android.R;
import com.example.sep4android.View.EditEventActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewEventRecyclerAdapter extends RecyclerView.Adapter<ViewEventRecyclerAdapter.ViewEventViewHolder> {

    private Context context;
    private List<Event> list;

    public ViewEventRecyclerAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.events_recycler_view, parent, false);

        final ViewEventViewHolder viewHolder = new ViewEventViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewEventRecyclerAdapter.ViewEventViewHolder holder, int position) {
        Event currentEvent = list.get(position);

        holder.eventName.setText(currentEvent.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView temperatureTop, co2Top, humidityTop, lightTop, temperatureBottom, co2Bottom, humidityBottom, lightBottom, eventName, timestamp;
        private ImageView editTemperatureButton, editCO2Button, editHumidityButton, editLightButton;
        private LinearLayout linear_layout_view_events;

        public ViewEventViewHolder(@NonNull View itemView) {
            super(itemView);
            linear_layout_view_events = itemView.findViewById(R.id.all_modules);
            eventName = itemView.findViewById(R.id.eventNameTextEvents);
            timestamp = itemView.findViewById(R.id.timestampTextEvents);

            editTemperatureButton = itemView.findViewById(R.id.editEventListTemperature);
            editTemperatureButton.setOnClickListener(this);
            editCO2Button = itemView.findViewById(R.id.editEventListCO2);
            editCO2Button.setOnClickListener(this);
            editHumidityButton = itemView.findViewById(R.id.editEventListHumidity);
            editHumidityButton.setOnClickListener(this);
            editLightButton = itemView.findViewById(R.id.editEventListLight);
            editLightButton.setOnClickListener(this);

            temperatureTop = itemView.findViewById(R.id.temperatureTopValue);
            co2Top = itemView.findViewById(R.id.co2TopValue);
            humidityTop = itemView.findViewById(R.id.humidityTopValue);
            lightTop = itemView.findViewById(R.id.lightTopUnits);

            temperatureBottom = itemView.findViewById(R.id.temperatureBottomValue);
            co2Bottom = itemView.findViewById(R.id.co2BottomValue);
            humidityBottom = itemView.findViewById(R.id.humidityBottomValue);
            lightBottom = itemView.findViewById(R.id.lightBottomUnits);
        }

        @Override
        public void onClick(View view) {
            editEvent(view);
        }

        private void editEvent(View view) {
            Activity activity = (Activity) view.getContext();
            Intent intent = new Intent(context, EditEventActivity.class);

            if (view.getId() == R.id.editEventListTemperature) {
                intent.putExtra("type", "Temperature");
            } else if (view.getId() == R.id.editEventListCO2) {
                intent.putExtra("type", "Carbon Dioxide");
            } else if (view.getId() == R.id.editEventListHumidity) {
                intent.putExtra("type", "Humidity");
            } else if (view.getId() == R.id.editEventListLight) {
                intent.putExtra("type", "Light");
            }

            context.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    public void setList(List<Event> list) {
        this.list = list;
    }
}
