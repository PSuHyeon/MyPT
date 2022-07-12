package com.example.testest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

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
        String name = intent.getStringExtra("name");
        TextView name_text = findViewById(R.id.namefield);
        name_text.setText(name);
        TextView height = findViewById(R.id.person_height);
        TextView weight = findViewById(R.id.person_weight);
        TextView age = findViewById(R.id.person_age);
        TextView gender = findViewById(R.id.person_gender);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://172.10.18.125:80/getUser";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        JSONObject jsonObject = new JSONObject(params);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            height.setText(response.getString("height") + " cm");
                            weight.setText(response.getString("weight")  + " kg");
                            age.setText(response.getString("age") + "살");
                            gender.setText(response.getString("gender"));
                        }
                        catch (Exception e){

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("check", "got error");
            }
        }){
            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes();
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
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