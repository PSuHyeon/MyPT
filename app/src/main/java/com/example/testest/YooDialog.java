package com.example.testest;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
    Menu menu = new Menu();
    ArrayList<Exercise> myExList;

    public YooDialog(@NonNull Context context, String selectedDate, ArrayList<Exercise> listItem, ListViewAdapter listViewAdapter, ListView listView, View rootView) {
        super(context);
        setContentView(R.layout.exercisedialog);

        chipGroup = findViewById(R.id.yooChipGroup);
        timeEditText = findViewById(R.id.yootime);
        doneButton = findViewById(R.id.yooDoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    time = timeEditText.getText().toString();
                } catch (Exception e) {
                }

                chip = chipGroup.findViewById(chipGroup.getCheckedChipId());
                exercise = chip.getText().toString();

                if (chip.equals("") || time.equals("")) {
                    Toast.makeText(context, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("chipGroup.getCheckedChipId()", chip.getText().toString());
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url ="http://192.249.18.125:80/moo";
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("number", "");
                params.put("sett", "");
                params.put("weight", "");
                params.put("type", "yoo");
                params.put("date", selectedDate);
                params.put("time", time);
                params.put("name", menu.name);
                params.put("id", menu.key_id);
                params.put("exercise", exercise);
                params.put("current", "no");
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





                listItem.add(new Exercise(menu.key_id,menu.name, selectedDate, "yoo", exercise, time, "", "", "", "no"));
                ListViewAdapter listViewAdapter = new ListViewAdapter(listItem, getContext(), rootView);
                listView.setAdapter(listViewAdapter);

//                Exercise exercise = new Exercise(menu.key_id, ,  );
                dismiss();
            }
        });
    }
}
