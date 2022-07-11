package com.example.testest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CommunityDialog extends Dialog {
    private  com.google.android.material.textfield.TextInputLayout newContents;
    private Button shutdownClick;
    private ImageView newImageView;
    String text_contents;
    CommunityFragment communityFragment;
    private CustomDialogListener customDialogListener;
    Menu menu = new Menu();
    CommunityFragment cm = new CommunityFragment();
    // 현재 시간 구하기
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String getTime = dateFormat.format(date);

    interface CustomDialogListener {
        void onButtonClicked(String contents);
    }

    public void setDialogListener(CustomDialogListener customDialogListener) {
        this.customDialogListener = customDialogListener;
    }

    public CommunityDialog(@NonNull Context context, Uri uri) {
        super(context);
        setContentView(R.layout.communitynewdialog);

        newContents = findViewById(R.id.newContents);
        shutdownClick = findViewById(R.id.btn_shutdown);
        newImageView = findViewById(R.id.newImageView);
        shutdownClick = findViewById(R.id.btn_shutdown);

        // uri로 이미지 띄우기
        newImageView.setImageURI(uri);

        shutdownClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_contents = newContents.getEditText().getText().toString();
                customDialogListener.onButtonClicked(text_contents);

                // 서버 접근
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url ="http://172.10.18.125:80/feed";
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("contents", text_contents);
                params.put("date", getTime);
                params.put("name", menu.name);
                params.put("id", menu.key_id);
                // image, position

                JSONObject jsonObject = new JSONObject(params);
                // Request a string response from the provided URL.
                JsonObjectRequest Request = new JsonObjectRequest(com.android.volley.Request.Method.POST, url,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Display the first 500 characters of the response string.
                                Log.d("upload", "success");
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
                dismiss();
            }
        });
    }
}
