package com.example.testest;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.chip.ChipGroup;

public class MooDialog extends Dialog {
    ChipGroup chipGroup;
    EditText timeEditText;
    EditText numberEditText;
    EditText setEditText;
    EditText weightEditText;
    Button doneButton;

    public MooDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.exercisedialog);

        chipGroup = findViewById(R.id.mooChipGroup);
        timeEditText = findViewById(R.id.mootime);
        numberEditText = findViewById(R.id.moonumber);
        setEditText = findViewById(R.id.mooset);
        weightEditText = findViewById(R.id.mooweight);
        doneButton = findViewById(R.id.mooDoneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("chipGroup.getCheckedChipId()", String.valueOf(chipGroup.getCheckedChipId()));
                Log.d("timeEditText.getText().toString()", timeEditText.getText().toString());
                Log.d("numberEditText.getText().toString()", numberEditText.getText().toString());
                Log.d("setEditText.getText().toString()", setEditText.getText().toString());
                Log.d("weightEditText.getText().toString()", weightEditText.getText().toString());
                dismiss();
            }
        });
    }
}
