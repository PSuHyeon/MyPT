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

public class CommunityDialog extends Dialog {
    private  com.google.android.material.textfield.TextInputLayout newContents;
    private Button shutdownClick;
    private ImageView newImageView;
    String text_contents;
    CommunityFragment communityFragment;
    private CustomDialogListener customDialogListener;

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
                dismiss();
            }
        });
    }
}
