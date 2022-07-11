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
