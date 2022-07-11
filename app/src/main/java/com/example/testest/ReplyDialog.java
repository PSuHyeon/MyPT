package com.example.testest;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
                // 서버 접근
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
