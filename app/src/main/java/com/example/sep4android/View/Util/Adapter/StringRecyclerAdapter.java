package com.example.sep4android.View.Util.Adapter;

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

import com.example.sep4android.R;
import com.example.sep4android.View.GreeneticsSensorsActivity;

import java.util.List;

public class StringRecyclerAdapter extends RecyclerView.Adapter<StringRecyclerAdapter.StringViewHolder> {

    private Context context;
    private List<String> list;
    private StringViewHolder viewHolderCopy;

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
        holder.greenhouseName.setText(list.get(position));

        holder.itemView.setOnClickListener(view -> {
            holder.greenhouseButton.setImageResource(R.drawable.devices_module);
            Intent intent = new Intent(context, GreeneticsSensorsActivity.class);
            intent.putExtra("name", list.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class StringViewHolder extends RecyclerView.ViewHolder {

        TextView greenhouseName;
        ImageView greenhouseButton;
        LinearLayout linear_layout_home;

        public StringViewHolder(@NonNull View itemView) {
            super(itemView);
            linear_layout_home = itemView.findViewById(R.id.module_container);
            greenhouseName = itemView.findViewById(R.id.greenhouseModuleNameText);
            greenhouseButton = itemView.findViewById(R.id.greenhouseModuleImage);
        }
    }
}
