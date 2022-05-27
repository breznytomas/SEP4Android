package com.example.sep4android.Adapter;

import android.app.Activity;
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

public class BoardRecyclerAdapter extends RecyclerView.Adapter<BoardRecyclerAdapter.BoardViewHolder> {

    private Context context;
    private List<Board> list;

    public BoardRecyclerAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.greenhouse_modules_recycler_view, parent, false);

        final BoardViewHolder viewHolder = new BoardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        Board currentBoard = list.get(position);
        holder.greenhouseName.setText(currentBoard.getName().toUpperCase());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, GreenhouseSensorsActivity.class);
            Activity activity = (Activity) view.getContext();
            intent.putExtra("name", currentBoard.getName().toUpperCase());
            context.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BoardViewHolder extends RecyclerView.ViewHolder {

        private TextView greenhouseName;
        private LinearLayout linear_layout_home;

        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);
            linear_layout_home = itemView.findViewById(R.id.module_container);
            greenhouseName = itemView.findViewById(R.id.greenhouseModuleNameText);
        }
    }

    public void setList(List<Board> list) {
        this.list = list;
    }
}
