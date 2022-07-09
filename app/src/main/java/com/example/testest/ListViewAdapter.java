package com.example.testest;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.util.proto.ProtoOutputStream;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    ArrayList<Exercise> items;
    Context context;
    View rootView;

    public ListViewAdapter(ArrayList<Exercise> items, Context context, View rootView) {
        this.items = items;
        this.context = context;
        this.rootView = rootView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listviewitem, viewGroup, false);
        }
        CheckBox checkBox = view.findViewById(R.id.checkbox);
        TextView exerciseTypeTextView = view.findViewById(R.id.exerciseTypeTextView);
        TextView exerciseNameTextView = view.findViewById(R.id.exerciseNameTextView);
        TextView exerciseInfoTextView = view.findViewById(R.id.exerciseInfoTextView);
        Button deleteButton = view.findViewById(R.id.deleteButton);

        String info;
          if (items.get(i).type.equals("yoo")) {
              info = items.get(i).time + "분";
          } else {
              info = items.get(i).number + "회 * " + items.get(i).sett + "세트 " + items.get(i).weight + "kg";
          }

        exerciseTypeTextView.setText(items.get(i).type);
        exerciseNameTextView.setText(items.get(i).exercise);
        exerciseInfoTextView.setText(info);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(i);
                notifyDataSetChanged();
                Log.d("items", String.valueOf(items.size()));
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TextView completeTextView = rootView.findViewById(R.id.completeTextView);
                String complete;

                if (b == true) {
                    complete = Integer.toString(Integer.parseInt(completeTextView.getText().toString()) + 1);
                } else {
                    complete = Integer.toString(Integer.parseInt(completeTextView.getText().toString()) - 1);
                }
                completeTextView.setText(complete);
            }
        });
        return view;
    }
}
