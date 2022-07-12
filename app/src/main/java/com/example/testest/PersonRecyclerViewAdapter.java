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

public class PersonRecyclerViewAdapter extends RecyclerView.Adapter<PersonRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    RecyclerView recyclerView;
    Context context;
    HashMap<String, JSONArray> items;
    ArrayList<String> days;
    int idx;
    Trainer1 trainer1 = new Trainer1();
    public PersonRecyclerViewAdapter(RecyclerView recyclerView, Context context, ArrayList<String> days, HashMap<String, JSONArray> items) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.items = items;
        this.days = days;
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.personrecyclerviewitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonRecyclerViewAdapter.ViewHolder holder, int position) {
        String date = days.get(position);
        int num = items.get(date).length();
        String year = date.split("-")[0];
        String month = date.split("-")[1];
        String day = date.split("-")[2];
        holder.personDate.setText(year + "년 " + month + "월 " + day + "일 ");
        holder.personNum.setText(Integer.toString(num) + " 건");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView personDate;
        TextView personNum;
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            personDate = itemView.findViewById(R.id.person_date);
            personNum = itemView.findViewById(R.id.person_num);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    idx = getAdapterPosition();
                    String date = days.get(idx);
                    Intent intent = new Intent(context, Trainer_PersonInfo.class);
                    intent.putExtra("name", trainer1.trainee);
                    intent.putExtra("array", String.valueOf(items.get(date)));
                    ((Activity) context).startActivityForResult(intent, 0);
                }
            });
        }
    }
}
