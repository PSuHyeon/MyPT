package com.example.testest;

import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ListFragment extends Fragment {

    View rootView;
    MaterialCalendarView materialCalendarView;
    ListView listView;
    ListViewAdapter listViewAdapter;
    //ArrayAdapter<String> adapter;
    ArrayAdapter<Exercise> adapter;
    //ArrayList<String> listItem;
    ArrayList<Exercise> listItem;
    TextView dateTextView;
    TextView completeTextView;
    androidx.appcompat.widget.AppCompatButton addyooButton;
    androidx.appcompat.widget.AppCompatButton addmooButton;
    HashMap<String, ArrayList<Exercise>> plan_map = new HashMap<>();
    String selectedDate;
    Dialog yooDialog;
    Dialog mooDialog;
    String exerciseData;

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);

        completeTextView = (TextView) rootView.findViewById(R.id.completeTextView);

        materialCalendarView = rootView.findViewById(R.id.calendarView);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2020, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        // default: 오늘 날짜
       materialCalendarView.setSelectedDate(CalendarDay.today());
        ShowSelectedDate();

        materialCalendarView.addDecorators(new OnDateDecorator());
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                ShowSelectedDate();
            }
        });

        // listview
        addyooButton = rootView.findViewById(R.id.yooaddButton);
        addyooButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("listItem.size() before", String.valueOf(listItem.size()));
                yooDialog = new YooDialog(getContext(), selectedDate, listItem, listViewAdapter, listView, rootView);
                yooDialog.show();
                Log.d("listItem.size() after", String.valueOf(listItem.size()));
            }
        });

        addmooButton = rootView.findViewById(R.id.mooaddButton);
        addmooButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("listItem.size() before", String.valueOf(listItem.size()));
                mooDialog = new MooDialog(getContext(), selectedDate, listItem, listViewAdapter, listView, rootView);
                mooDialog.show();
                Log.d("listItem.size() after", String.valueOf(listItem.size()));
            }
        });

        try {
            AssetManager assetManager = getActivity().getAssets();
            InputStream is = assetManager.open("exercise.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }
            exerciseData = buffer.toString();
            Log.d("exerciseData", exerciseData);
            JSONObject jsonObject = new JSONObject(exerciseData);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            Log.d("jsonArray", String.valueOf(jsonArray.get(1)));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Exercise>>(){}.getType();
            ArrayList<Exercise> myExList = new Gson().fromJson(String.valueOf(jsonArray), listType);
            Log.d("myExList", String.valueOf(myExList.get(1)));

            listItem = myExList;
            listView = rootView.findViewById(R.id.listView);
            listViewAdapter = new ListViewAdapter(listItem, getContext(), rootView);

            listView.setAdapter(listViewAdapter);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        // rootview로 작성해주어야 갱신된 값이 반영됨
        return rootView;
    }

    public void ShowSelectedDate() {

        Menu menu = new Menu();

        dateTextView = rootView.findViewById(R.id.dateTextView);
        selectedDate = materialCalendarView.getSelectedDate().toString().split("\\{|\\}")[1];
        String month = Integer.toString(Integer.parseInt(selectedDate.split("-")[1]) + 1);
        String day = selectedDate.split("-")[2];
        selectedDate = selectedDate.split("-")[0] + "-" + month + "-" + day;
        dateTextView.setText(month + " / " + day);


        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://172.10.18.125:80/get_exercise";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("date", ""+selectedDate);
        params.put("keyid", menu.key_id);
        JSONObject jsonObject = new JSONObject(params);
        JsonArrayRequest Request = new JsonArrayRequest(com.android.volley.Request.Method.POST, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Display the first 500 characters of the response string.



                        // __________________________________________________________________________________________________________________________________________________________
                        Log.d("check", "" + response);
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

    public void SetCompleteTextView(String text) {
        if (completeTextView == null) {
            if (rootView == null) {
                Log.d("rootview is null", "null");
            }
            completeTextView = rootView.findViewById(R.id.completeTextView);
        }
    }
}