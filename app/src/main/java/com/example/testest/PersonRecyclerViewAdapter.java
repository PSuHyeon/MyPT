package com.example.testest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonRecyclerViewAdapter extends RecyclerView.Adapter<PersonRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    RecyclerView recyclerView;
    Context context;
    ArrayList<Exercise> items;

    public PersonRecyclerViewAdapter(RecyclerView recyclerView, Context context, ArrayList<Exercise> items) {
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
        View view = layoutInflater.inflate(R.layout.personrecyclerviewitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.personDate.setText(items.get(position).date);
        holder.personExercise.setText(items.get(position).exercise);
        String info;
        if (items.get(position).type.equals("yoo")) {
            info = items.get(position).time + "분";
        } else {
            info = items.get(position).number + "회 * " + items.get(position).sett + "세트 "
                    + items.get(position).weight + " kg";
        }
        holder.personInfo.setText(info);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView personDate;
        TextView personExercise;
        TextView personInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            personDate = itemView.findViewById(R.id.person_date);
            personExercise = itemView.findViewById(R.id.person_exercise);
            personInfo = itemView.findViewById(R.id.person_info);

        }
    }
}
