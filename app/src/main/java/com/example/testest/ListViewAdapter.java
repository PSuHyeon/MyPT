package com.example.testest;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.util.proto.ProtoOutputStream;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends BaseAdapter {
    ArrayList<Exercise> items;
    Context context;
    View rootView;
    int temp = 0;
    MaterialCalendarView materialCalendarView;

    public ListViewAdapter(ArrayList<Exercise> items, Context context, View rootView) {
        this.items = items;
        this.context = context;
        this.rootView = rootView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listviewitem, viewGroup, false);
        }
        CheckBox checkBox = view.findViewById(R.id.checkbox);
        TextView exerciseTypeTextView = view.findViewById(R.id.exerciseTypeTextView);
        TextView exerciseNameTextView = view.findViewById(R.id.exerciseNameTextView);
        TextView exerciseInfoTextView = view.findViewById(R.id.exerciseInfoTextView);
        Button deleteButton = view.findViewById(R.id.deleteButton);

        String info, type;
          if (items.get(i).type.equals("yoo")) {
              type = "유산소";
              info = items.get(i).time + "분";
          } else {
              type = "무산소";
              info = items.get(i).number + "회 * " + items.get(i).sett + "세트 " + items.get(i).weight + "kg";
          }
          temp = 0;
        if (items.get(i).current.equals("yes")){
            checkBox.setChecked(true);
        }
        else{
            checkBox.setChecked(false);
        }
        temp = 1;
        exerciseTypeTextView.setText(type);
        exerciseNameTextView.setText(items.get(i).exercise);
        exerciseInfoTextView.setText(info);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue queue = Volley.newRequestQueue(context);
                String url ="http://192.249.18.125:80/delete";
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
                items.remove(i);
                notifyDataSetChanged();

                if (items.size() == 0) {
                    materialCalendarView = rootView.findViewById(R.id.calendarView);
                    materialCalendarView.removeDecorators();
                    materialCalendarView.addDecorators(new OnDateDecorator());
                }

                if (checkBox.isChecked() == true) {
                    TextView completeTextView = rootView.findViewById(R.id.completeTextView);
                    int complete = Integer.parseInt(completeTextView.getText().toString()) - 1;
                    completeTextView.setText(Integer.toString(complete));
                }
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TextView completeTextView = rootView.findViewById(R.id.completeTextView);
                String complete;


                if (b == true && temp == 1) {

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


                    complete = Integer.toString(Integer.parseInt(completeTextView.getText().toString()) + 1);
                } else if (temp == 1) {

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



                    complete = Integer.toString(Integer.parseInt(completeTextView.getText().toString()) - 1);
                }
                else {
                    complete = Integer.toString(Integer.parseInt(completeTextView.getText().toString()));
                }
                completeTextView.setText(complete);



            }
        });
        return view;
    }
}
