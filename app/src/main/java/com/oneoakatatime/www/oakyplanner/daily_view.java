package com.oneoakatatime.www.oakyplanner;

import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by User on 6/8/2017.
 */
//TODO CURRENTLY SELECTED DAY EVENTS GET DISPLAYED ON THE TAB
//TODO UPON LONG CLICKING AN ITEM IN THE LIST YOU CAN ADD OR EDIT EVENT CORESPONDING TO THE TIME("PLACE") IT HAS BEEN CLICKED ON
public class daily_view extends android.support.v4.app.Fragment {
    int counter;

    DataBaseHelper myDb;
    int[] currentDate;
    int selectedYear,selectedMonth,selectedDay;
    Comunicator com;
    int week;
    TimeZone tz;
    Bundle bundle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_view,container,false);
        counter = 0;
        bundle = getArguments();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView daily_listView = (ListView) getActivity().findViewById(R.id.daily_listView);

        populateDailyListView(currentDate[0],currentDate[1],selectedDay);
        com = (Comunicator) getActivity();
        final SwipeDetector swipeDetector = new SwipeDetector();
        daily_listView.setOnTouchListener(swipeDetector);

        daily_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(swipeDetector.swipeDetected()) {
                    if(swipeDetector.getAction() == SwipeDetector.Action.LR) {
                        Log.d("list_view_fragment","Current id is: "+id);
                        myDb.deleteEvent(id);
                        populateDailyListView(selectedYear,selectedMonth,selectedDay);
                    } else {

                    }
                }
            }
        });


    }
    public static daily_view newInstance(Bundle bundle) {

        daily_view f = new daily_view();
        Bundle b = bundle;
        f.setArguments(b);

        return f;
    }

    public String[] populateDailyListView(int year, int month, int day) {
        month++;
        ListView daily_listView = (ListView) getActivity().findViewById(R.id.daily_listView);
        String[] fromDataBaseRowId = new String[0];


        if (counter >0) {
            daily_listView.clearChoices();
        }

        myDb = new DataBaseHelper(this.getActivity());

        Cursor cursor = myDb.getAllRows(year,month,day);


        /** take from database time and description**/
        String[] fromDataBaseTD = new String[]{ DataBaseHelper.HOUR, DataBaseHelper.MINUTE,DataBaseHelper.PLACE,DataBaseHelper.EVENT_DESCRIPTION};


        int[] toViewIds = new int[]{ R.id.daily_hours, R.id.daily_minutes,R.id.daily_place,R.id.daily_description};

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this.getActivity(),
                R.layout.daily_list_view_row,
                cursor,
                fromDataBaseTD,
                toViewIds
        );

        daily_listView.setAdapter(cursorAdapter);
        if (cursor != null) {
            counter++;


            if (cursor.getCount() > 0) {
                fromDataBaseRowId = new String[]{cursor.getString(cursor.getColumnIndex("ID_1"))};
            }

        }
        return fromDataBaseRowId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        tz = TimeZone.getDefault();
        java.util.Calendar calendarForWeek = new GregorianCalendar(tz);
        selectedDay = bundle.getInt("selected_day", calendarForWeek.get(java.util.Calendar.DAY_OF_MONTH));
        getCurrentDateInfo dat = new getCurrentDateInfo();
        currentDate = dat.getInfo();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void OnWeekAcquisition (monthlyView.CurrentWeekSelected event){
        week = event.weekNumber;
        selectedYear = event.yearNumber;
        selectedMonth = event.monthNumber;

    }
    @Subscribe (threadMode = ThreadMode.POSTING)
    public void remoteDailyListViewPopulation (MainActivity.remoteDailyListViewPopulation event){


            selectedYear = event.passYear;
            selectedMonth = event.passMonth;
            selectedDay = event.passDay;
        populateDailyListView(selectedYear,selectedMonth,selectedDay);
    }


}
