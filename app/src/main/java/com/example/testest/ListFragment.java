package com.example.testest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ListFragment extends Fragment {

    View rootView;
    MaterialCalendarView materialCalendarView;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItem;
    TextView dateTextView;
    androidx.appcompat.widget.AppCompatButton deleteButton;
    androidx.appcompat.widget.AppCompatButton addButton;

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

        // calendar
        materialCalendarView = rootView.findViewById(R.id.calendarView);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2020, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(new OnDateDecorator());
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {

            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Log.d("clicked", "clicked");
                dateTextView = rootView.findViewById(R.id.dateTextView);
                Log.d("materialCalendarView.getSelectedDate().toString()", materialCalendarView.getSelectedDate().toString());
                dateTextView.setText(materialCalendarView.getSelectedDate().toString());
            }
        });

        // listview
        addButton = rootView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItem.add("0");
            }
        });
        listItem = new ArrayList<String>();

                // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }
}