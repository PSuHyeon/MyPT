package com.example.testest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DateRecyclerViewAdapter extends RecyclerView.Adapter<DateRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    RecyclerView recyclerView;
    Context context;
    ArrayList<Exercise> items;

    public DateRecyclerViewAdapter(RecyclerView recyclerView, Context context, ArrayList<Exercise> items) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.items = items;
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyclerviewitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DateRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.dateName.setText(items.get(position).name);
        holder.dateExercise.setText(items.get(position).exercise);
        String info;
        if (items.get(position).type.equals("yoo")) {
            info = items.get(position).time + "분";
        } else {
            info = items.get(position).number + "회 * " + items.get(position).sett + "세트 "
                    + items.get(position).weight + " kg";
        }
        holder.dateExercise.setText(info);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateName;
        TextView dateExercise;
        TextView dateInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateName = itemView.findViewById(R.id.date_name);
            dateExercise = itemView.findViewById(R.id.date_exercise);
            dateInfo = itemView.findViewById(R.id.date_info);

        }
    }
}
