package com.example.testest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class DateRecyclerViewAdapter extends RecyclerView.Adapter<DateRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    RecyclerView recyclerView;
    Context context;
    HashMap<String, JSONArray> items;
    int idx;
    ArrayList<String> names;

    public DateRecyclerViewAdapter(RecyclerView recyclerView, Context context, ArrayList<String> names, HashMap<String, JSONArray> items) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.items = items;
        this.names = names;
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
        String name = names.get(position);
        int num = items.get(name).length();
        holder.dateName.setText(names.get(position));
        holder.dateNum.setText(Integer.toString(num) + " 건");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateName;
        TextView dateNum;
//        TextView dateExercise;
//        TextView dateInfo;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateName = itemView.findViewById(R.id.date_name);
            dateNum = itemView.findViewById(R.id.date_num);
//            dateExercise = itemView.findViewById(R.id.date_exercise);
//            dateInfo = itemView.findViewById(R.id.date_info);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    idx = getAdapterPosition();
                    String name = names.get(idx);
                    Intent intent = new Intent(context, Trainer_PersonInfo.class);
                    intent.putExtra("array", String.valueOf(items.get(name)));
                    ((Activity) context).startActivityForResult(intent, 0);
                }
            });

        }
    }
}
