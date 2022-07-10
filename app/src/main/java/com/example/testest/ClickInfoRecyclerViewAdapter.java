package com.example.testest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ClickInfoRecyclerViewAdapter extends RecyclerView.Adapter<ClickInfoRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    RecyclerView clickInfoRecyclerView;
    Context context;
    ArrayList<Exercise> items;
    int idx;

    public ClickInfoRecyclerViewAdapter(RecyclerView clickInfoRecyclerView, Context context, ArrayList<Exercise> items) {
        this.clickInfoRecyclerView = clickInfoRecyclerView;
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
        View view = layoutInflater.inflate(R.layout.clickrecyclerviewitem, parent, false);
        ClickInfoRecyclerViewAdapter.ViewHolder viewHolder = new ClickInfoRecyclerViewAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClickInfoRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.clickExercise.setText(items.get(position).exercise);
        String info;
        if (items.get(position).type.equals("yoo")) {
            info = items.get(position).time + "분";
        } else {
            info = items.get(position).number + "회 * " + items.get(position).sett + "세트 "
                    + items.get(position).weight + " kg";
        }

        if (items.get(position).current.equals("yes")) {
            holder.checkBox.setChecked(true);
        }

        holder.clickInfo.setText(info);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView clickExercise;
        TextView clickInfo;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clickExercise = itemView.findViewById(R.id.click_exercise);
            clickInfo = itemView.findViewById(R.id.click_info);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
