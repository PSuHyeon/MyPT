package com.example.testest;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class ReplyDialog extends Dialog {
    EditText communityReplyEditText;
    androidx.appcompat.widget.AppCompatButton communityReplyAddButton;
    RecyclerView communityReplyRecyclerView;
    Button btn_stutdown;
    Menu menu = new Menu();
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public ReplyDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.replydialog);

        communityReplyEditText = findViewById(R.id.communityReplyEditText);
        communityReplyAddButton = findViewById(R.id.communityReplyAddButton);
        btn_stutdown = findViewById(R.id.btn_shutdown);
        communityReplyRecyclerView = findViewById(R.id.communityReplyRecyclerView);
        communityReplyAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 서버 접근
//                RequestQueue queue = Volley.newRequestQueue(getContext());
//                String url ="http://172.10.18.125:80/feed";
//                HashMap<String, String> params = new HashMap<String, String>();
//                params.put("number", "");
//                params.put("sett", "");
//                params.put("weight", "");
//                params.put("contents", "yoo");
//                params.put("date", selectedDate);
//                params.put("time", time);
//                params.put("name", menu.name);
//                params.put("id", menu.key_id);
//                JSONObject jsonObject = new JSONObject(params);
//                // Request a string response from the provided URL.
//                JsonObjectRequest Request = new JsonObjectRequest(com.android.volley.Request.Method.POST, url,null,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                // Display the first 500 characters of the response string.
//
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("check", "got error");
//                    }
//
//                }
//                ){
//                    @Override
//                    public byte[] getBody() {
//                        return jsonObject.toString().getBytes();
//                    }
//                };
//                Request.setRetryPolicy(new DefaultRetryPolicy(
//                        0,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//                // Add the request to the RequestQueue.
//                queue.add(Request);
            }
        });

        btn_stutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



    }
}
