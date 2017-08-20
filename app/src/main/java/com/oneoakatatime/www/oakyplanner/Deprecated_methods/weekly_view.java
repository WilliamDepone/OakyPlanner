package com.oneoakatatime.www.oakyplanner.Deprecated_methods;

import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.oneoakatatime.www.oakyplanner.DataBaseHelper;
import com.oneoakatatime.www.oakyplanner.MainActivity;
import com.oneoakatatime.www.oakyplanner.R;
import com.oneoakatatime.www.oakyplanner.getCurrentDateInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;



import static com.oneoakatatime.www.oakyplanner.DataBaseHelper.DAYOFWEEK;

/**
 * Created by User on 5/30/2017.
 */
//TODO MAKE THIS AN EXPANDABLE LIST VIEW, BECAUSE REASONS
    /*
public class weekly_view extends android.support.v4.app.Fragment {
    int counter;

    DataBaseHelper myDb;
    int[] currentDate;
    int selectedYear,selectedMonth,selectedWeek,selecetedDay;

    int week,tab;
    Calendar dateCalendar;
    TimeZone tz;
    Bundle bundle;
    List<String> weekDays = new ArrayList<>();
    List<transferables> transferablesList;
    ExpandableListAdapter expandablelistAdapter;

    public class transferables{
        String Hour;
        String Minute;
        String Description;
        String Place;
        void assign(String a, String b,String c, String d){
            Hour = a;
            Minute = b;
            Description = c;
            Place = d;
        }
    }
    Map<String,List<transferables>> week_events = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weekly_view,container,false);
        counter = 0;
        bundle = getArguments();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ExpandableListView weekly_listView = (ExpandableListView) getActivity().findViewById(R.id.weekly_listView);

        populateWeeklyListView(currentDate[0],currentDate[1],selectedWeek);

        final SwipeDetector swipeDetector = new SwipeDetector();
        weekly_listView.setOnTouchListener(swipeDetector);

        weekly_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(swipeDetector.swipeDetected()) {
                    if(swipeDetector.getAction() == SwipeDetector.Action.LR) {
                        Log.d("list_view_fragment","Current id is: "+id);
                        myDb.deleteEvent(id);
                        populateWeeklyListView(selectedYear,selectedMonth,selectedWeek);
                    } else {

                    }
                }
            }
        });


    }
    public static weekly_view newInstance(Bundle bundle) {

        weekly_view f = new weekly_view();
        Bundle b = bundle;
        f.setArguments(b);

        return f;
    }

    public void populateWeeklyListView(int year, int month, int week) {
        month++;
        ExpandableListView weekly_listView = (ExpandableListView) getActivity().findViewById(R.id.weekly_listView);
        if (counter >0) {
           weekly_listView.clearChoices();
        }
        myDb = new DataBaseHelper(this.getActivity());
        Cursor cursor = myDb.getAllWeeklyRows(year, month, week);
        fill_data(cursor);
        expandablelistAdapter = new myExpandableListAdapter(this.getActivity(),weekDays,week_events);
        weekly_listView.setAdapter(expandablelistAdapter);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        tz = TimeZone.getDefault();
        java.util.Calendar calendarForWeek = new GregorianCalendar(tz);
        selectedWeek = bundle.getInt("selected_week", calendarForWeek.get(java.util.Calendar.WEEK_OF_YEAR));
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
    public void remoteWeeklyListViewPopulation (MainActivity.remoteWeeklyListViewPopulation event){

            selectedYear = event.passYear;
            selectedMonth = event.passMonth;
            week = event.passWeek;
        populateWeeklyListView(selectedYear,selectedMonth,week);

    }
     public void fill_data(Cursor cursor){
     transferables[] transferables_array;
         transferables_array = new transferables[420];
         for ( int i=0; i<transferables_array.length; i++) {
             transferables_array[i]=new transferables();
         }
         weekDays.add("Monday");
         weekDays.add("Tuesday");
         weekDays.add("Wednesday");
         weekDays.add("Thursday");
         weekDays.add("Friday");
         weekDays.add("Saturday");
         weekDays.add("Sunday");
         List<transferables> monday = new ArrayList<transferables>();
         List<transferables> tuesday = new ArrayList<transferables>();
         List<transferables> wednesday = new ArrayList<transferables>();
         List<transferables> thursday = new ArrayList<transferables>();
         List<transferables> friday = new ArrayList<transferables>();
         List<transferables> saturday = new ArrayList<transferables>();
         List<transferables> sunday = new ArrayList<transferables>();
         if (!cursor.isAfterLast()) {

             int y;
             y = 0;


             while ((cursor.getInt(6) == 1) && !cursor.isAfterLast()) {
                 transferables_array[y].Hour = cursor.getString(2);
                 transferables_array[y].Minute = cursor.getString(3);
                 transferables_array[y].Place = cursor.getString(4);
                 transferables_array[y].Description = cursor.getString(5);
                 monday.add(transferables_array[y]);
                 cursor.moveToNext();
                 y++;
             }


             while (cursor.getInt(cursor.getColumnIndex(DAYOFWEEK)) == 2 && !cursor.isAfterLast()) {
                 transferables_array[y].Hour = cursor.getString(cursor.getColumnIndex("HOUR"));
                 transferables_array[y].Minute = cursor.getString(cursor.getColumnIndex("MINUTE"));
                 transferables_array[y].Place = cursor.getString(cursor.getColumnIndex("PLACE"));
                 transferables_array[y].Description = cursor.getString(cursor.getColumnIndex("EVENT_DESCRIPTION"));
                 tuesday.add(transferables_array[y]);
                 y++;
                 cursor.moveToNext();
             }


             while (cursor.getInt(cursor.getColumnIndex(DAYOFWEEK)) == 3 && !cursor.isAfterLast()) {
                 transferables_array[y].Hour = cursor.getString(cursor.getColumnIndex("HOUR"));
                 transferables_array[y].Minute = cursor.getString(cursor.getColumnIndex("MINUTE"));
                 transferables_array[y].Place = cursor.getString(cursor.getColumnIndex("PLACE"));
                 transferables_array[y].Description = cursor.getString(cursor.getColumnIndex("EVENT_DESCRIPTION"));
                 wednesday.add(transferables_array[y]);
                 y++;
                 cursor.moveToNext();
             }


             while (cursor.getInt(cursor.getColumnIndex(DAYOFWEEK)) == 4 && !cursor.isAfterLast()) {
                 transferables_array[y].Hour = cursor.getString(cursor.getColumnIndex("HOUR"));
                 transferables_array[y].Minute = cursor.getString(cursor.getColumnIndex("MINUTE"));
                 transferables_array[y].Place = cursor.getString(cursor.getColumnIndex("PLACE"));
                 transferables_array[y].Description = cursor.getString(cursor.getColumnIndex("EVENT_DESCRIPTION"));
                 thursday.add(transferables_array[y]);
                 y++;
                 cursor.moveToNext();
             }


             while (cursor.getInt(cursor.getColumnIndex(DAYOFWEEK)) == 5 && !cursor.isAfterLast()) {
                 transferables_array[y].Hour = cursor.getString(cursor.getColumnIndex("HOUR"));
                 transferables_array[y].Minute = cursor.getString(cursor.getColumnIndex("MINUTE"));
                 transferables_array[y].Place = cursor.getString(cursor.getColumnIndex("PLACE"));
                 transferables_array[y].Description = cursor.getString(cursor.getColumnIndex("EVENT_DESCRIPTION"));
                 friday.add(transferables_array[y]);
                 y++;
                 cursor.moveToNext();
             }


             while (cursor.getInt(cursor.getColumnIndex(DAYOFWEEK)) == 6 && !cursor.isAfterLast()) {
                 transferables_array[y].Hour = cursor.getString(cursor.getColumnIndex("HOUR"));
                 transferables_array[y].Minute = cursor.getString(cursor.getColumnIndex("MINUTE"));
                 transferables_array[y].Place = cursor.getString(cursor.getColumnIndex("PLACE"));
                 transferables_array[y].Description = cursor.getString(cursor.getColumnIndex("EVENT_DESCRIPTION"));
                 saturday.add(transferables_array[y]);
                 y++;
                 cursor.moveToNext();
             }


             while (cursor.getInt(cursor.getColumnIndex(DAYOFWEEK)) == 7 && !cursor.isAfterLast()) {
                 transferables_array[y].Hour = cursor.getString(cursor.getColumnIndex("HOUR"));
                 transferables_array[y].Minute = cursor.getString(cursor.getColumnIndex("MINUTE"));
                 transferables_array[y].Place = cursor.getString(cursor.getColumnIndex("PLACE"));
                 transferables_array[y].Description = cursor.getString(cursor.getColumnIndex("EVENT_DESCRIPTION"));
                 sunday.add(transferables_array[y]);
                 y++;
                 cursor.moveToNext();
             }
             cursor.moveToFirst();

             week_events.put(weekDays.get(0), monday);
             week_events.put(weekDays.get(1), tuesday);
             week_events.put(weekDays.get(2), wednesday);
             week_events.put(weekDays.get(3), thursday);
             week_events.put(weekDays.get(4), friday);
             week_events.put(weekDays.get(5), saturday);
             week_events.put(weekDays.get(6), sunday);

         }

/*TODO REMOVE THIS
     boolean block;
     block  = false;
     while(x!= 24){
     while(y!= 60){
     //cursor stuff, column numbers are probably wrong, can't be arsed to check, but the general idea should be still the same.
     if (listParent.get(x).equals(cursor.getString(cursor.getColumnIndex("HOUR")))  && test_array[y].Minute.equals(cursor.getString(cursor.getColumnIndex("MINUTE")))  ){
     test_array[y].Place = cursor.getString(cursor.getColumnIndex("PLACE"));
     test_array[y].Description = cursor.getString(cursor.getColumnIndex("EVENT_DESCRIPTION"));
     y++;
     if (!block) {
     if (cursor.isAfterLast()){
     cursor.isBeforeFirst();
     block=true;
     }else cursor.moveToNext();}
     }else y++;
     }
     x++;
     }




     }

}
*/