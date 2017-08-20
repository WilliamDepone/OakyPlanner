package com.oneoakatatime.www.oakyplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;


public class Fragment1 extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<row_info> arrayList = new ArrayList<>();






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_fragment1, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        MaterialCalendarView calendarView = (MaterialCalendarView) getActivity().findViewById(R.id.materialCalendarView);

        final RecyclerView month_event_list = (RecyclerView)getActivity().findViewById(R.id.month_event_list);


        layoutManager = new LinearLayoutManager(getActivity());
        month_event_list.setLayoutManager(layoutManager);
        month_event_list.setHasFixedSize(true);
        final DataBaseHelper myDb = new DataBaseHelper(this.getActivity());
        getCurrentDateInfo dateinfo = new getCurrentDateInfo();
        int[] datearray =  dateinfo.getInfo();
        final int[] changeddatearray = new int[4];
        currentlySelectedDayData currentData = new currentlySelectedDayData(changeddatearray[0],changeddatearray[1],changeddatearray[2]);
        repopulateRecyclerView(month_event_list, datearray,myDb,currentData);
        showEvents(calendarView,myDb,datearray);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                changeddatearray[0] = date.getYear();
                changeddatearray[1] = date.getMonth();
                changeddatearray[2]= date.getDay();
                currentlySelectedDayData currentData = new currentlySelectedDayData(changeddatearray[0],changeddatearray[1],changeddatearray[2]);
                repopulateRecyclerView(month_event_list,changeddatearray,myDb,currentData);
                EventBus.getDefault().post(new currentlySelectedDayData(currentData.year,currentData.month,currentData.day));
            }
        });


        super.onActivityCreated(savedInstanceState);
    }

    public class currentlySelectedDayData{
        public int year;
        public int month;
        public int day;

        public currentlySelectedDayData(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }
    }
    public void repopulateRecyclerView (View view, int[] datearray, DataBaseHelper myDb,currentlySelectedDayData currentData){
         Cursor cursor = myDb.getAllRows(datearray[0], datearray[1]+1, datearray[2]);
        RecyclerView recyclerView = (RecyclerView) view;
        cursor.moveToFirst();
        if(cursor.getCount()!= 0){
       do{
            row_info info = new row_info(Integer.toString(cursor.getInt(1))+":"+Integer.toString(cursor.getInt(3)),Integer.toString(cursor.getInt(2))+":"+Integer.toString(cursor.getInt(4)),cursor.getString(6),cursor.getInt(0));
            arrayList.add(info);

        }while (cursor.moveToNext());}
        myDb.close();
        adapter = new MyRecyclerAdapter(arrayList,currentData);
        recyclerView.setAdapter(adapter);


    }
    public class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(5, color));
        }
    }
    private void showEvents (MaterialCalendarView v,DataBaseHelper myDb,int [] currentDates){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        ArrayList<CalendarDay> dates = new ArrayList<>();
        int[] event_dates = new int[3001];
        int x=0;
        Cursor c =  myDb.getMonthEventDays(currentDates[0],currentDates[1]+1);
        c.moveToFirst();

        if(c.getCount()!= 0){
            do{

                event_dates[x]=c.getInt(0);
                x++;
            }while (c.moveToNext());}

        int iterator=0 ;
        for (int i = 0; i < 30; i++) {

            CalendarDay day = CalendarDay.from(calendar);

            if(day.getDay() == event_dates[iterator] ){
            dates.add(day);

            iterator++;}
            calendar.add(Calendar.DATE, 1);
        }

        EventDecorator calendarDecorator = new EventDecorator(Color.RED,dates);


        v.addDecorator(calendarDecorator);

    }
}
