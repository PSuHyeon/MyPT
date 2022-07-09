package com.example.testest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Trainer1 extends Fragment {
    View rootView;
    Spinner yearSpinner;
    Spinner monthSpinner;
    Spinner daySpinner;
    Spinner personSpinner;
    RadioGroup radioGroup;
    Button trainerDoneButton;
    int condition = 0; // 날짜별이면 0, 회원별이면 1

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_trainer1, container, false);

        radioGroup = rootView.findViewById(R.id.trainerRadioGroup);
        ArrayList<String> yearList = new ArrayList<String>();
        ArrayList<String> monthList = new ArrayList<String>();
        ArrayList<String> dayList = new ArrayList<String>();
        personSpinner = rootView.findViewById(R.id.personSpinner);
        yearSpinner = rootView.findViewById(R.id.yearSpinner);
        monthSpinner = rootView.findViewById(R.id.monthSpinner);
        daySpinner = rootView.findViewById(R.id.daySpinner);
        personSpinner.setVisibility(View.GONE);
        yearSpinner.setVisibility(View.VISIBLE);
        monthSpinner.setVisibility(View.VISIBLE);
        daySpinner.setVisibility(View.VISIBLE);

        yearList.add("년");
        monthList.add("월");
        dayList.add("일");

        for (int i = 2020; i <= 2030; i++) {
            yearList.add(Integer.toString(i));
        }

        for (int i = 1; i <= 12; i++) {
            monthList.add(Integer.toString(i));
        }

        for (int i = 1; i <= 31; i++) {
            dayList.add(Integer.toString(i));
        }

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, yearList);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, monthList);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, dayList);
        yearSpinner.setAdapter(yearAdapter);
        monthSpinner.setAdapter(monthAdapter);
        daySpinner.setAdapter(dayAdapter);

        Log.d("yearList.get(0)", yearList.get(0));
        yearSpinner.setSelection(0);
        monthSpinner.setSelection(0);
        daySpinner.setSelection(0);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                switch(checkedID) {
                    case R.id.dayRadioButton:
                        condition = 0; // 날짜별이면 0
                        Toast.makeText(getContext(), "날짜별 리스트를 조회합니다", Toast.LENGTH_SHORT).show();
                        ArrayList<String> yearList = new ArrayList<String>();
                        ArrayList<String> monthList = new ArrayList<String>();
                        ArrayList<String> dayList = new ArrayList<String>();
                        personSpinner = rootView.findViewById(R.id.personSpinner);
                        yearSpinner = rootView.findViewById(R.id.yearSpinner);
                        monthSpinner = rootView.findViewById(R.id.monthSpinner);
                        daySpinner = rootView.findViewById(R.id.daySpinner);
                        personSpinner.setVisibility(View.GONE);
                        yearSpinner.setVisibility(View.VISIBLE);
                        monthSpinner.setVisibility(View.VISIBLE);
                        daySpinner.setVisibility(View.VISIBLE);

                        yearList.add("년");
                        monthList.add("월");
                        dayList.add("일");

                        for (int i = 2020; i <= 2030; i++) {
                            yearList.add(Integer.toString(i));
                        }

                        for (int i = 1; i <= 12; i++) {
                            monthList.add(Integer.toString(i));
                        }

                        for (int i = 1; i <= 31; i++) {
                            dayList.add(Integer.toString(i));
                        }

                        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, yearList);
                        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, monthList);
                        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, dayList);
                        yearSpinner.setAdapter(yearAdapter);
                        monthSpinner.setAdapter(monthAdapter);
                        daySpinner.setAdapter(dayAdapter);

                        yearSpinner.setSelection(0);
                        monthSpinner.setSelection(0);
                        daySpinner.setSelection(0);
                        break;

                    case R.id.personRadioButton:
                        condition = 1; // 회원별이면 1
                        Toast.makeText(getContext(), "회원별 리스트를 조회합니다", Toast.LENGTH_SHORT).show();
                        ArrayList<String> personList = new ArrayList<String>();
                        personSpinner = rootView.findViewById(R.id.personSpinner);
                        yearSpinner = rootView.findViewById(R.id.yearSpinner);
                        monthSpinner = rootView.findViewById(R.id.monthSpinner);
                        daySpinner = rootView.findViewById(R.id.daySpinner);
                        yearSpinner.setVisibility(View.GONE);
                        monthSpinner.setVisibility(View.GONE);
                        daySpinner.setVisibility(View.GONE);
                        personSpinner.setVisibility(View.VISIBLE);

                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        String url ="http://172.10.18.125:80/get_trainee";
                        StringRequest Request = new StringRequest(com.android.volley.Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Display the first 500 characters of the response string.
                                        ArrayList<String> personList = new ArrayList<String>(Arrays.asList(response.split(",")));
                                        Log.d("byhuman", ""+personList);
                                        personList.set(0, "회원명");
                                        ArrayAdapter<String> personAdapter = new ArrayAdapter<>(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, personList);
                                        personSpinner.setAdapter(personAdapter);
                                        personSpinner.setSelection(0);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("byhuman", "got error" + personList);
                            }

                        }
                        );
                        // Add the request to the RequestQueue.
                        queue.add(Request);




                }
            }
        });

        RecyclerView dateRecyclerView = rootView.findViewById(R.id.dateRecyclerView);
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        trainerDoneButton = rootView.findViewById(R.id.trainerDoneButton);
        trainerDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (condition == 0) { // 날짜별

                    if (yearSpinner.getSelectedItem().toString() == "년" ||
                            monthSpinner.getSelectedItem().toString() == "월" ||
                            daySpinner.getSelectedItem().toString() == "일") {
                        Toast.makeText(getContext(), "날짜를 선택하세요", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String year, month, day, date; // 선택된 날짜와 그걸 조합한 최종 날짜 date
                    year = yearSpinner.getSelectedItem().toString();
                    month = monthSpinner.getSelectedItem().toString();
                    day = daySpinner.getSelectedItem().toString();
                    date = year + "-" + month + "-" + day; // 최종날짜 (2022-06-08 형식)

                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    String url ="http://172.10.18.125:80/get_by_date";
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("date", date);
                    JSONObject jsonObject = new JSONObject(params);
                    JsonArrayRequest Request = new JsonArrayRequest(com.android.volley.Request.Method.POST, url,null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    // Display the first 500 characters of the response string.
                                    Log.d("bydate", ""+response);
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
                    // 서버 요청 필요

                } else { // 회원별

                    if (personSpinner.getSelectedItem().toString() == "회원명") {
                        Toast.makeText(getContext(), "회원명을 선택하세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String trainee; // 선택된 회원
                    trainee = personSpinner.getSelectedItem().toString();

                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    String url ="http://172.10.18.125:80/get_by_name";
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("name", trainee);
                    JSONObject jsonObject = new JSONObject(params);
                    JsonArrayRequest Request = new JsonArrayRequest(com.android.volley.Request.Method.POST, url,null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    // Display the first 500 characters of the response string.
                                    Log.d("byname", ""+response);
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
                    // 서버 요청 필요


                    // 서버 요청 필요

                }
            }
        });

        return rootView;
    }
}