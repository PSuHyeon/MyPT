package com.example.testest;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

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
    String number;
    String set;

    public YooDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.exercisedialog);

        chipGroup = findViewById(R.id.yooChipGroup);
        timeEditText = findViewById(R.id.yootime);
        numberEditText = findViewById(R.id.yoonumber);
        setEditText = findViewById(R.id.yooset);
        doneButton = findViewById(R.id.yooDoneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    time = timeEditText.getText().toString();
                    number = numberEditText.getText().toString();
                    set = setEditText.getText().toString();
                } catch (Exception e) {
                }
                chip = chipGroup.findViewById(chipGroup.getCheckedChipId());
                exercise = chip.getText().toString();

                Log.d("chipGroup.getCheckedChipId()", chip.getText().toString());
                dismiss();
            }
        });
    }
}
