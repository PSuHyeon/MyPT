package com.example.testest;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

import java.util.ArrayList;

public class MooDialog extends Dialog {
    ChipGroup chipGroup;
    EditText numberEditText;
    EditText setEditText;
    EditText weightEditText;
    Button doneButton;
    Chip chip;
    String exercise;
    String number;
    String set;
    String weight;

    public MooDialog(@NonNull Context context, String selectedDate, ArrayList<Exercise> listItem, ListViewAdapter listViewAdapter, ListView listView) {
        super(context);
        Menu menu = new Menu();
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
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url ="http://172.10.18.125:80/moo";
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("number", number);
                params.put("sett", set);
                params.put("weight", weight);
                params.put("type", "moo");
                params.put("date", selectedDate);
                params.put("time", "");
                params.put("name", menu.name);
                params.put("id", menu.key_id);
                params.put("exercise", exercise);
                JSONObject jsonObject = new JSONObject(params);
                // Request a string response from the provided URL.
                JsonObjectRequest Request = new JsonObjectRequest(com.android.volley.Request.Method.POST, url,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Display the first 500 characters of the response string.

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("check", "got error");
                    }

                }
                ){
                    @Override
                    public byte[] getBody() {
                        return jsonObject.toString().getBytes();
                    }
                };
                Request.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                // Add the request to the RequestQueue.
                queue.add(Request);
                chip = chipGroup.findViewById(chipGroup.getCheckedChipId());
                exercise = chip.getText().toString();
                listItem.add(new Exercise("이름", selectedDate, "무산소", exercise, "0", number, set, weight));
                ListViewAdapter listViewAdapter = new ListViewAdapter(listItem, getContext());
                listView.setAdapter(listViewAdapter);
                dismiss();
            }
        });
    }
}
