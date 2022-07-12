package com.example.testest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CommunityReplyAdapter  extends RecyclerView.Adapter<CommunityReplyAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    NewReply items;
    RecyclerView communityReplyRecyclerView;

    public CommunityReplyAdapter(Context context, NewReply newReply, RecyclerView communityReplyRecyclerView) {
        this.context = context;
        this.items = newReply;
        this.communityReplyRecyclerView = communityReplyRecyclerView;
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.communityreplyrecyclerviewitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        Log.d("onCreateViewHolder","onCreateViewHolder" );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityReplyAdapter.ViewHolder holder, int position) {
        Log.d("adapter name",items.rp_name );
        holder.replyNameTextView.setText(items.rp_name);
        holder.replyTextView.setText(items.rp_text);
        holder.replyDate.setText(items.rp_time);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView replyNameTextView;
        TextView replyTextView;
        TextView replyDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("ViewHolder","ViewHolder" );

            replyNameTextView = itemView.findViewById(R.id.replyNameTextView);
            replyTextView = itemView.findViewById(R.id.replyTextView);
            replyDate = itemView.findViewById(R.id.replyDate);
        }
    }
}
