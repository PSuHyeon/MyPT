package com.example.testest;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CommunityFragment extends Fragment {
    RecyclerView communityRecyclerView;
    com.google.android.material.floatingactionbutton.FloatingActionButton faButton;
    View rootView;
    ArrayList<NewUpload> items;
    CommunityRecyclerViewAdapter adapter;


    public CommunityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_community, container, false);
        faButton = rootView.findViewById(R.id.faButton);
        items = new ArrayList<NewUpload>();
        communityRecyclerView = rootView.findViewById(R.id.communityRecyclerView);

        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri uri = data.getData();
                    CommunityDialog communityDialog = new CommunityDialog(getContext(), uri);
                    communityDialog.setDialogListener(new CommunityDialog.CustomDialogListener() {
                        @Override
                        public void onButtonClicked(String contents) {
                            //items.add(new NewUpload("우다연", "2022년 07월 11일", uri, contents));

                        }
                    });
                    communityDialog.show();
//                    RequestQueue queue = Volley.newRequestQueue(getContext());
//                    String url ="http://172.10.18.125:80/getfeed";
//
//                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    Gson gson = new Gson();
//                                    Type listType = new TypeToken<ArrayList<NewUpload>>(){}.getType();
//                                    ArrayList<NewUpload> getfeed = new Gson().fromJson(String.valueOf(response), listType);
//
//                                    items = getfeed;
//                                    communityRecyclerView = rootView.findViewById(R.id.communityRecyclerView);
//                                    adapter = new CommunityRecyclerViewAdapter(communityRecyclerView, getContext(), items);
//                                    communityRecyclerView.setAdapter(adapter);
//
//                                    Log.d("check", "" + response);
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.d("check", "got error");
//                        }
//                    });
//
//                    // Add the request to the RequestQueue.
//                    queue.add(stringRequest);

                    //adapter = new CommunityRecyclerViewAdapter(communityRecyclerView, getContext(), items);
                    communityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    communityRecyclerView.setAdapter(adapter);
                    // uri, contents, name, date 전달하면 됨 (데베 저장) ArrayList
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}

class NewUpload {
    String date;
    String name;
    String contents;

    public NewUpload(String date, String name, String contents) {
        this.date = date;
        this.name = name;
        this.contents = contents;
    }
}