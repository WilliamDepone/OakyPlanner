package com.oneoakatatime.www.oakyplanner;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.view.View;
import android.widget.SimpleCursorAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by User on 5/28/2017.
 */
// TODO POTENTIONAL SOLUTIONS : 1) IT COULD BE SOMETHING WITH THE FRAGMENT LIFE CYCLE (on resume might have to be initiated) 2) A THOROUGH EXAMINATION MIGHT BE NECSESSARY 3) THE PROBLEM MIGHT BE SOMETHING WITH THE CONTEXT.
public class monthlyView extends android.support.v4.app.Fragment {
    int counter;

    DataBaseHelper myDb;
    int[] currentDate,selectedDate;
    SwipeDetector swipeDetector;

    CalendarView calendar;
    Comunicator com;
    int week,tab;
    Calendar dateCalendar;
    TimeZone tz;
    boolean Datechanged;
    Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.monthly_view,container,false);
        Datechanged = false;
        currentDate = new int[4];
        selectedDate = new int[4];
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //mega fragment start
        super.onActivityCreated(savedInstanceState);

        //////////////////////////////////////

        /////////////////////////////////////

        ListView fragment_listView = (ListView)getActivity().findViewById(R.id.fragment_listView);
        CalendarView calendar = (CalendarView) getActivity().findViewById(R.id.MonthlyCalendarView);



        tz = TimeZone.getDefault();
        java.util.Calendar calendarForWeek = new GregorianCalendar(tz);
        currentDate[0] = calendarForWeek.get(java.util.Calendar.YEAR);
        currentDate[1] = calendarForWeek.get(java.util.Calendar.MONTH);
        currentDate[3] = calendarForWeek.get(java.util.Calendar.DAY_OF_MONTH);
        currentDate[2] = calendarForWeek.get(java.util.Calendar.WEEK_OF_YEAR);

        if(savedInstanceState != null){
            populateListView(savedInstanceState.getInt("selected_year"),savedInstanceState.getInt("selected_month"),savedInstanceState.getInt("selected_day"));
            EventBus.getDefault().post(new CurrentWeekSelected(savedInstanceState.getInt("selected_year"),savedInstanceState.getInt("selected_month"),savedInstanceState.getInt("selected_week"),savedInstanceState.getInt("selected_day")));
        }else {populateListView(currentDate[0],currentDate[1],currentDate[3]);
        EventBus.getDefault().post(new CurrentWeekSelected(currentDate[0],currentDate[1],currentDate[2],currentDate[3]));}
        //calendar fragment start

        com = (Comunicator) getActivity();


        final java.util.Calendar dateCalendar = new java.util.GregorianCalendar(tz);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dateCalendar.set(java.util.Calendar.YEAR,year);
                dateCalendar.set(java.util.Calendar.MONTH,month);
                dateCalendar.set(java.util.Calendar.DAY_OF_MONTH,dayOfMonth);
                week= dateCalendar.get(java.util.Calendar.WEEK_OF_YEAR);
                populateListView(year,month,dayOfMonth);
                selectedDate[0] = year;
                selectedDate[1] = month;
                selectedDate[3] = dayOfMonth;
                selectedDate[2] = week;
                Datechanged = true;
                EventBus.getDefault().post(new CurrentWeekSelected(selectedDate[0],selectedDate[1],selectedDate[2],selectedDate[3]));


            }

        });


        calendar.setOnLongClickListener(new View.OnLongClickListener() {
            // TODO CHECK IF THIS WORKS
            @Override
            public boolean onLongClick(View v) {
                if (Datechanged ==true){
                    EventBus.getDefault().post(new CurrentWeekSelected(selectedDate[0],selectedDate[1],selectedDate[2],selectedDate[3]));
                return false;}
                else {
                    EventBus.getDefault().post(new CurrentWeekSelected(currentDate[0],currentDate[1],currentDate[2],currentDate[3]));
                    return false;
                }
            }
        });


        // calendar fragment end
        //////////////////// List view fragment start

        counter = 0;



        com= (Comunicator) getActivity();

        final SwipeDetector swipeDetector = new SwipeDetector();
        fragment_listView.setOnTouchListener(swipeDetector);

        fragment_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(swipeDetector.swipeDetected()) {
                    if(swipeDetector.getAction() == SwipeDetector.Action.LR) {

                        myDb.deleteEvent(id);
                        populateListView(selectedDate[0],selectedDate[1],selectedDate[3]);
                    } else {

                    }
                }
            }
        });

        fragment_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                com.createInputEdit(id);


                return false;
            }
        });
        /////////////// ListView fragment part end


    }

    public String[] populateListView(int year, int month, int day) {
        month++;
        String[] fromDataBaseRowId = new String[0];
        ListView fragment_listView = (ListView)getActivity().findViewById(R.id.fragment_listView);


        if(myDb == null){
        myDb = new DataBaseHelper(this.getActivity());
        }
        Cursor cursor = myDb.getAllRows(year, month, day);



        /** take from database time and description**/
        String[] fromDataBaseTD = new String[]{DataBaseHelper.HOUR, DataBaseHelper.MINUTE, DataBaseHelper.EVENT_DESCRIPTION};


        int[] toViewIds = new int[]{R.id.tpd_hours, R.id.tpd_minutes, R.id.tpd_description};

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this.getActivity(),
                R.layout.tpd_row,
                cursor,
                fromDataBaseTD,
                toViewIds
        );

        fragment_listView.setAdapter(cursorAdapter);
        if (cursor != null) {
            counter++;
            /** TODO FIX THIS DAMN THING */



        }

        return fromDataBaseRowId;
    }
    public static monthlyView newInstance() {

        monthlyView f = new monthlyView();


        return f;
    }




    public class CurrentWeekSelected {
        public int weekNumber,yearNumber,monthNumber,dayNumber;
            public CurrentWeekSelected(int a,int b,int c,int d){
                weekNumber = c;
                yearNumber = a;
                monthNumber =b;
                dayNumber = d;
            }

    }

    @Override
    public void onResume() {


        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selected_year",selectedDate[0]);
        outState.putInt("selected_month",selectedDate[1]);
        outState.putInt("selected_week",selectedDate[2]);
        outState.putInt("selected_day",selectedDate[3]);
    }

}
