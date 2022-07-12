package com.example.testest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ClickInfoRecyclerViewAdapter extends RecyclerView.Adapter<ClickInfoRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    RecyclerView clickInfoRecyclerView;
    Context context;
    ArrayList<Exercise> items;
    int idx;

    public ClickInfoRecyclerViewAdapter(RecyclerView clickInfoRecyclerView, Context context, ArrayList<Exercise> items) {
        this.clickInfoRecyclerView = clickInfoRecyclerView;
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
        View view = layoutInflater.inflate(R.layout.clickrecyclerviewitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    int temp = 0;
    @Override
    public void onBindViewHolder(@NonNull ClickInfoRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.clickExercise.setText(items.get(position).exercise);
        String info;
        if (items.get(position).type.equals("yoo")) {
            info = items.get(position).time + "분";
        } else {
            info = items.get(position).number + "회 * " + items.get(position).sett + "세트 "
                    + items.get(position).weight + " kg";
        }

        if (items.get(position).current.equals("yes")) {
            holder.checkBox.setChecked(true);
        }
        temp = 1;

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String complete;


                if (b == true && temp == 1) {
                    int i = holder.getAdapterPosition();
                    RequestQueue queue = Volley.newRequestQueue(context);
                    String url ="http://192.249.18.125:80/checkbox";
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("name", items.get(i).name);
                    params.put("id", items.get(i).id);
                    params.put("date", items.get(i).date);
                    params.put("type", items.get(i).type);
                    params.put("exercise", items.get(i).exercise);
                    params.put("time", items.get(i).time);
                    params.put("sett", items.get(i).sett);
                    params.put("weight", items.get(i).weight);
                    params.put("number", items.get(i).number);
                    params.put("current", "yes");
                    JSONObject jsonObject = new JSONObject(params);
                    JsonArrayRequest Request = new JsonArrayRequest(com.android.volley.Request.Method.POST, url,null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    // Display the first 500 characters of the response string.

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

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


                } else if (temp == 1) {
                    int i = holder.getAdapterPosition();
                    RequestQueue queue = Volley.newRequestQueue(context);
                    String url ="http://192.249.18.125:80/checkbox";
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("name", items.get(i).name);
                    params.put("id", items.get(i).id);
                    params.put("date", items.get(i).date);
                    params.put("type", items.get(i).type);
                    params.put("exercise", items.get(i).exercise);
                    params.put("time", items.get(i).time);
                    params.put("sett", items.get(i).sett);
                    params.put("weight", items.get(i).weight);
                    params.put("number", items.get(i).number);
                    params.put("current", "no");
                    JSONObject jsonObject = new JSONObject(params);
                    JsonArrayRequest Request = new JsonArrayRequest(com.android.volley.Request.Method.POST, url,null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    // Display the first 500 characters of the response string.
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("check", "" + error);
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




                }
                else {

                }
            }
        });
        holder.clickInfo.setText(info);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView clickExercise;
        TextView clickInfo;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clickExercise = itemView.findViewById(R.id.click_exercise);
            clickInfo = itemView.findViewById(R.id.click_info);
            checkBox = itemView.findViewById(R.id.clickCheckBox);
        }

    }
}
