package com.example.testest;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.client.Url;

public class ChatFragment extends Fragment {

    public ChatFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View activity = inflater.inflate(R.layout.fragment_trainer2, container, false);

        RecyclerView recyclerView = activity.findViewById(R.id.chat_recyclerView);
        ArrayList<Trainee> trainees = new ArrayList<Trainee>();
        String url = "http://172.10.18.125:80/get_trainee_info";

        RequestQueue queue = Volley.newRequestQueue(getContext());

        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Display the first 500 characters of the response string.
                        for (int i = 0; i< response.length(); i++){
                            try{
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String age = jsonObject.getString("age");
                                String kg = jsonObject.getString("weight");
                                trainees.add(new Trainee(name, age, kg, ""));
                            }
                            catch (JSONException e){

                            }
                        }
                        recyclerView.setAdapter(new ChatAdapter(getContext(), trainees));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("check", "got error");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(request);
        return activity;
    }
}