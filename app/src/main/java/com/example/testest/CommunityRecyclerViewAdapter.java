package com.example.testest;

import static com.example.testest.CommunityDialog.StringToBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CommunityRecyclerViewAdapter extends RecyclerView.Adapter<CommunityRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    RecyclerView CommunityRecyclerView;
    Context context;
    ArrayList<NewUpload> items;
    ReplyDialog dialog;
    Bitmap bitmap;

    public CommunityRecyclerViewAdapter(RecyclerView CommunityRecyclerView, Context context, ArrayList<NewUpload> items) {
        CommunityRecyclerView = CommunityRecyclerView;
        this.context = context;
        this.items = items;
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.communityrecyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.communityNameTextView.setText(items.get(position).name);

        String year, month, day, hour, minute;
        year = items.get(position).date.split("-")[0];
        month = items.get(position).date.split("-")[1];
        day = items.get(position).date.split("-")[2];
        day = day.split(" ")[0];
        hour = items.get(position).date.split("-")[2].split(" ")[1].split(":")[0];
        minute = items.get(position).date.split("-")[2].split(" ")[1].split(":")[1];

        String time = year + "년 " + month + "월 " + day + "일\n " + hour + "시 " + minute + "분";
        holder.communityDateTextView.setText(time);
        holder.communityCardTextView.setText(items.get(position).contents);
        holder.feedId.setText(items.get(position).id);
        Log.d("item", ""+items.get(position).image);
        holder.communityImageView.setImageBitmap(StringToBitmap(items.get(position).image));
        // 구글 드라이브 접근
//        DownloadImageTask downloadImageTask = new DownloadImageTask(holder.communityImageView);
//        BitmapDrawable drawable = new BitmapDrawable(bitmap);
//        try {
//            bitmap = downloadImageTask.execute("https://drive.google.com/uc?export=download&id=1-FkYrdLO_N9Gol9-ZpLEUA85uvpHSDrM").get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView communityNameTextView;
        TextView communityDateTextView;
        ImageView communityImageView;
        TextView communityCardTextView;
        CardView communityCardView;
        TextView feedId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            communityNameTextView = itemView.findViewById(R.id.communityNameTextView);
            communityDateTextView = itemView.findViewById(R.id.communityDateTextView);
            communityImageView = itemView.findViewById(R.id.communityImageView);
            communityCardTextView = itemView.findViewById(R.id.communityCardTextView);
            communityCardView = itemView.findViewById(R.id.communityCardView);
            feedId = itemView.findViewById(R.id.feedId);
            communityCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = new ReplyDialog(context, feedId.getText().toString());
                    dialog.show();
                }
            });

        }
    }
}
