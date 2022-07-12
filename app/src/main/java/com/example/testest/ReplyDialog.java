package com.example.testest;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
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
    ReplyAdapter madapter;
    ArrayList<Reply> replyarray = new ArrayList<Reply>();
    Menu menu = new Menu();
    NewReply replyItems;
    CommunityReplyAdapter communityReplyAdapter;

    public ReplyDialog(@NonNull Context context, String feedId) {
        super(context);
        setContentView(R.layout.replydialog);

        communityReplyEditText = findViewById(R.id.communityReplyEditText);
        communityReplyAddButton = findViewById(R.id.communityReplyAddButton);
        btn_stutdown = findViewById(R.id.btn_shutdown);
        communityReplyRecyclerView = findViewById(R.id.communityReplyRecyclerView);
        communityReplyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        madapter = new ReplyAdapter(getContext(), replyarray);
        communityReplyRecyclerView.setAdapter(madapter);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://192.249.18.125:80/getReply/" + feedId;

        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Reply>>(){}.getType();
                        ArrayList<Reply> getfeed = new Gson().fromJson(String.valueOf(response), listType);

                        replyarray = getfeed;
                        madapter = new ReplyAdapter(getContext(),replyarray);
                        communityReplyRecyclerView.setAdapter(madapter);
                        communityReplyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("check", "got error");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        communityReplyAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (communityReplyEditText.getText().toString().equals("")){
                    Toast.makeText(context, "댓글을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url ="http://192.249.18.125:80/newReply/" + feedId;
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("name", menu.name);
                params.put("feedid", feedId);
                params.put("text", communityReplyEditText.getText().toString());
                communityReplyEditText.setText("");
                long nano = System.currentTimeMillis();
                params.put("time", new SimpleDateFormat("yy-MM-dd HH:mm").format(nano));
                JSONObject jsonObject = new JSONObject(params);
                JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Reply getfeed = new Gson().fromJson(String.valueOf(response), Reply.class);

                                replyarray.add(getfeed);
                                madapter = new ReplyAdapter(getContext(),replyarray);
                                communityReplyRecyclerView.setAdapter(madapter);
                                communityReplyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
