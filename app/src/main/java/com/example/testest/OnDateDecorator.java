package com.example.testest;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Date;

public class OnDateDecorator implements DayViewDecorator {

    private CalendarDay date;

    public OnDateDecorator() {
        date = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan(1.4f));
        //view.addSpan(new ForegroundColorSpan(Color.parseColor("#5BA680")));
        view.addSpan(new ForegroundColorSpan(Color.parseColor("#FAAA34")));
    }

    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}