package com.example.testest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_person_info);

        Intent intent = getIntent();
        String arr = intent.getStringExtra("array");
        Log.d("arr", arr);

//        JSONArray jsonArray = null;
//        try {
//            jsonArray = new JSONArray(arr);
//            Log.d("jsonArray", String.valueOf(jsonArray.get(0)));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Exercise>>(){}.getType();
        ArrayList<Exercise> myExList = new Gson().fromJson(String.valueOf(arr), listType);

        clickRecyclerView = findViewById(R.id.recyclerViewField);
        listItem = myExList;
        clickInfoRecyclerViewAdapter = new ClickInfoRecyclerViewAdapter(clickRecyclerView, getApplicationContext(), listItem);

        clickRecyclerView.setAdapter(clickInfoRecyclerViewAdapter);



    }
}