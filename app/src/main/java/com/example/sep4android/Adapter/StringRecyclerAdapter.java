package com.example.sep4android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.R;
import com.example.sep4android.View.GreenhouseHomeActivity;

import java.util.List;

public class StringRecyclerAdapter extends RecyclerView.Adapter<StringRecyclerAdapter.StringViewHolder> {

    private Context context;
    private List<String> list;

    public StringRecyclerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.greenhouse_modules_recycler_view, parent, false);

        final StringViewHolder viewHolder = new StringViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class StringViewHolder extends RecyclerView.ViewHolder{
        public StringViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
