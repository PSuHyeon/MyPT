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

import com.bumptech.glide.Glide;

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
                            Log.d("contents", contents);
                            items.add(new NewUpload("우다연", "2022년 07월 11일", uri, contents));
                            adapter = new CommunityRecyclerViewAdapter(communityRecyclerView, getContext(), items);
                            communityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            communityRecyclerView.setAdapter(adapter);
                            // uri, contents, name, date 전달하면 됨 (데베 저장) ArrayList

                        }
                    });
                    communityDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}

class NewUpload {
    String name;
    String date;
    Uri uri;
    String contents;

    public NewUpload(String name, String date, Uri uri, String contents) {
        this.name = name;
        this.date = date;
        this.uri = uri;
        this.contents = contents;
    }
}