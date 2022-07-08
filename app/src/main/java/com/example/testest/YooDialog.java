package com.example.testest;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class YooDialog extends Dialog {
    ChipGroup chipGroup;
    EditText timeEditText;
    EditText numberEditText;
    EditText setEditText;
    Button doneButton;

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
                Log.d("chipGroup.getCheckedChipId()", String.valueOf(chipGroup.getCheckedChipId()));
                Log.d("timeEditText.getText().toString()", timeEditText.getText().toString());
                Log.d("numberEditText.getText().toString()", numberEditText.getText().toString());
                Log.d("setEditText.getText().toString()", setEditText.getText().toString());
                dismiss();
            }
        });
    }
}
