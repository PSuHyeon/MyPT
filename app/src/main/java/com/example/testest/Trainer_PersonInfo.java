package com.example.testest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Trainer_PersonInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_person_info);

        Intent intent = getIntent();
        String arr = intent.getStringExtra("array");
        Log.d("arr", arr);
    }
}