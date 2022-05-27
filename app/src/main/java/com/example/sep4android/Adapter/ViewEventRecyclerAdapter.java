package com.example.sep4android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Model.Board;
import com.example.sep4android.R;

import java.util.ArrayList;
import java.util.List;

public class ViewEventRecyclerAdapter extends RecyclerView.Adapter<ViewEventRecyclerAdapter.ViewEventViewHolder> {

    private Context context;
    private List<Board> list;

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
        Board currentBoard = list.get(position);
        // Add a greenhouseName
        holder.boardName.setText(currentBoard.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewEventViewHolder extends RecyclerView.ViewHolder {
        private TextView temperature, co2, humidity, light, boardName, timestamp;
        private LinearLayout linear_layout_view_events;

        public ViewEventViewHolder(@NonNull View itemView) {
            super(itemView);
            linear_layout_view_events = itemView.findViewById(R.id.all_modules);
            boardName = itemView.findViewById(R.id.boardNameTextEvents);
            temperature = itemView.findViewById(R.id.temperatureText);
            co2 = itemView.findViewById(R.id.co2Text);
            humidity = itemView.findViewById(R.id.humidityText);
            light = itemView.findViewById(R.id.lightUnits);
            timestamp = itemView.findViewById(R.id.timestampTextEvents);
        }
    }

    public void setList(List<Board> list) {
        this.list = list;
    }
}
