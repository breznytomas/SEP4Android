package com.example.sep4android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.R;
import com.example.sep4android.RemoteDataSource.EventValue;

import java.util.ArrayList;
import java.util.List;

public class EventValuesRecyclerAdapter extends RecyclerView.Adapter<EventValuesRecyclerAdapter.EventValuesViewHolder> {
    private Context context;
    private List<EventValue> eventValueList;
    private String unitOfMeasure="";

    public EventValuesRecyclerAdapter(Context context){
        this.context = context;
        eventValueList = new ArrayList<>();
    }
    @NonNull
    @Override
    public EventValuesRecyclerAdapter.EventValuesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.event_triggered, parent,false);

        final EventValuesViewHolder viewHolder = new EventValuesViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventValuesRecyclerAdapter.EventValuesViewHolder holder, int position) {
        EventValue currentValue = eventValueList.get(position);
        holder.date.setText(currentValue.getMeasureDate().substring(0,10));
        holder.triggeredBy.setText(currentValue.getTriggeredFrom()+" by: ");
        holder.exceededBy.setText(currentValue.getExceededBy()+" "+unitOfMeasure);
        holder.value.setText("("+currentValue.getValue()+" "+unitOfMeasure+")");

    }

    @Override
    public int getItemCount() {
        return eventValueList.size();
    }

    public void setEventValueList(List<EventValue> list){
        eventValueList = list;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public static class EventValuesViewHolder extends RecyclerView.ViewHolder{
        private TextView date, triggeredBy, exceededBy, value;
        public EventValuesViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.events_triggered_date);
            triggeredBy = itemView.findViewById(R.id.events_triggered_triggered_by_text);
            exceededBy = itemView.findViewById(R.id.events_triggered_exceeded_by);
            value = itemView.findViewById(R.id.events_triggered_value);
        }
    }
}
