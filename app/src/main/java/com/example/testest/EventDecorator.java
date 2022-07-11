package com.example.testest;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {
    private int color;
    private CalendarDay dates;
    //private TextView textView;

    public EventDecorator(int color, CalendarDay dates, Activity context) {
        this.color = color;
        this.dates = dates;
        //this.textView = textView;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day){
        return dates != null && day.equals(dates);
    }

    @Override
    public void decorate(DayViewFacade view) {

        view.addSpan(new DotSpan(5, color));
    }

    //public void setText(String text){
    //    textView.setText(text);
    //}
}
