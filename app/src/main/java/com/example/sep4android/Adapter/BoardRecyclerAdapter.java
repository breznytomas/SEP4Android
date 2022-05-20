package com.example.sep4android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Model.Board;
import com.example.sep4android.R;
import com.example.sep4android.View.GreenhouseSensorsActivity;

import java.util.ArrayList;
import java.util.List;

public class BoardRecyclerAdapter extends RecyclerView.Adapter<BoardRecyclerAdapter.StringViewHolder> {

    private Context context;
    private List<Board> list;

    public BoardRecyclerAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.greenhouse_modules_recycler_view, parent, false);

        final StringViewHolder viewHolder = new StringViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
        Board currentBoard = list.get(position);
        holder.greenhouseName.setText(currentBoard.getName().toUpperCase());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, GreenhouseSensorsActivity.class);
            intent.putExtra("name", currentBoard.getName().toUpperCase());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class StringViewHolder extends RecyclerView.ViewHolder {

        TextView greenhouseName;
        LinearLayout linear_layout_home;

        public StringViewHolder(@NonNull View itemView) {
            super(itemView);
            linear_layout_home = itemView.findViewById(R.id.module_container);
            greenhouseName = itemView.findViewById(R.id.greenhouseModuleNameText);
        }
    }

    public void setList(List<Board> list) {
        this.list = list;
    }
}
