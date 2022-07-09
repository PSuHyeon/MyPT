package com.example.testest;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class YooDialog extends Dialog {
    ChipGroup chipGroup;
    EditText timeEditText;
    EditText numberEditText;
    EditText setEditText;
    Button doneButton;
    Chip chip;
    String exercise;
    String time;
    Menu menu = new Menu();
    ArrayList<Exercise> myExList;

    public YooDialog(@NonNull Context context, String selectedDate, ArrayList<Exercise> listItem, ListViewAdapter listViewAdapter, ListView listView) {
        super(context);
        setContentView(R.layout.exercisedialog);

        chipGroup = findViewById(R.id.yooChipGroup);
        timeEditText = findViewById(R.id.yootime);
        doneButton = findViewById(R.id.yooDoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    time = timeEditText.getText().toString();
                } catch (Exception e) {
                }
                chip = chipGroup.findViewById(chipGroup.getCheckedChipId());
                exercise = chip.getText().toString();
                listItem.add(new Exercise("이름", selectedDate, "0", exercise, time, "0", "0", "0"));
                ListViewAdapter listViewAdapter = new ListViewAdapter(listItem, getContext());
                listView.setAdapter(listViewAdapter);

//                Exercise exercise = new Exercise(menu.key_id, ,  );
                dismiss();
            }
        });
    }
}
