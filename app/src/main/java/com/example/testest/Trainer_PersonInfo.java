package com.example.testest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Trainer_PersonInfo extends AppCompatActivity {
    ArrayList<Exercise> listItem;
    RecyclerView clickRecyclerView;
    ClickInfoRecyclerViewAdapter clickInfoRecyclerViewAdapter;
    View rootView;
    TextView infoDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_person_info);

        Intent intent = getIntent();
        String arr = intent.getStringExtra("array");

        RecyclerView clickRecyclerView = findViewById(R.id.recyclerViewField);
        clickRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Exercise>>(){}.getType();
        ArrayList<Exercise> myExList = new Gson().fromJson(String.valueOf(arr), listType);

        clickRecyclerView = findViewById(R.id.recyclerViewField);
        listItem = myExList;
        clickInfoRecyclerViewAdapter = new ClickInfoRecyclerViewAdapter(clickRecyclerView, this, listItem);
        clickRecyclerView.setAdapter(clickInfoRecyclerViewAdapter);

        infoDate = findViewById(R.id.infoDate);
        String year = myExList.get(0).date.split("-")[0];
        String month = myExList.get(0).date.split("-")[1];
        String day = myExList.get(0).date.split("-")[2];
        infoDate.setText(year + "년 " + month + "월 " + day + "일");

    }
}