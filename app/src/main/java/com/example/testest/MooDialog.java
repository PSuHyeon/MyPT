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

public class MooDialog extends Dialog {
    ChipGroup chipGroup;
    EditText timeEditText;
    EditText numberEditText;
    EditText setEditText;
    EditText weightEditText;
    Button doneButton;
    Chip chip;
    String exercise;
    String number;
    String set;
    String weight;

    public MooDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.exercisemoodialog);

        chipGroup = findViewById(R.id.mooChipGroup);
        numberEditText = findViewById(R.id.moonumber);
        setEditText = findViewById(R.id.mooset);
        weightEditText = findViewById(R.id.mooweight);
        doneButton = findViewById(R.id.mooDoneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    number = numberEditText.getText().toString();
                    set = setEditText.getText().toString();
                    weight = weightEditText.getText().toString();
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
