package com.oneoakatatime.www.oakyplanner;


import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;



/** TODO 3) ADD TABS 4) WEEKLY VIEW 5) DAILY VIEW 6) SPIDER 7) GOOGLE MAPS THING **/
public class MainActivity extends AppCompatActivity implements Comunicator {
    /* TODO REMOVE FABRICATED VARIABLES */
    int fabricated_event_year, fabricated_event_month, fabricated_event_day, fabricated_event_hour, fabricated_event_minute;
   int[] currentDate;
    Long rowIds;
    String fabricated_event_description, fabricated_event_place;
    CalendarView monthlyCalendar;
    DataBaseHelper myDb;
    int selectedYear,selectedMonth,selectedDay;


    boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DataBaseHelper(this);
        fabricateData();
        populateActivity();
    }


    /* TODO DONT FORGET TO REMOVE THIS BEFORE RELEASE... FUDGE */
    private void fabricateData(){
        fabricated_event_year = 2017;
        fabricated_event_month = 5;
        fabricated_event_day = 22;
        int i;
        i=0;
        fabricated_event_minute = 12;
        fabricated_event_description = "this is dummy data";
        fabricated_event_place = "Placey-mcplacey";
        while(i<10){
            fabricated_event_hour =i;
            i++;
            myDb.insertData1(fabricated_event_year,fabricated_event_month,fabricated_event_day,fabricated_event_hour,fabricated_event_minute,fabricated_event_description,fabricated_event_place);

        }
        fabricated_event_day = 23;
        while(i<20){
            fabricated_event_hour =i;
            i++;
            myDb.insertData1(fabricated_event_year,fabricated_event_month,fabricated_event_day,fabricated_event_hour,fabricated_event_minute,fabricated_event_description,fabricated_event_place);

        }
    }


    @Override
    public void populateActivity() {

       list_view_fragment frag2 = new list_view_fragment();
        calendar_fragment frag1 = new calendar_fragment();
        FragmentManager manager = getFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_activity_layout,frag1,"calendar_fragment");
        transaction.add(R.id.main_activity_layout,frag2,"list_view_fragment");

        manager.executePendingTransactions();
        transaction.addToBackStack(null);
        transaction.commit();


        /**calendar_fragment fragment1= (calendar_fragment) manager.findFragmentByTag("calendar_fragment");
        list_view_fragment fragment2= (list_view_fragment) manager.findFragmentByTag("list_view_fragment");**/




    }

    @Override
    public void dateTransfer(int year, int month, int day) {
        FragmentManager manager = getFragmentManager();
        list_view_fragment fragment2 = (list_view_fragment) manager.findFragmentByTag("list_view_fragment");
        fragment2.populateListView(year,month,day);
        selectedYear = year;
        selectedMonth = month;
        selectedDay = day;
    }

    @Override
    public void createInputEdit(long rowIds) {
        input_edit frag3 = new input_edit();
        FragmentManager manager = getFragmentManager();
        calendar_fragment frag1 = (calendar_fragment) manager.findFragmentByTag("calendar_fragment");
        list_view_fragment frag2 = (list_view_fragment)manager.findFragmentByTag("list_view_fragment");
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(frag1);
        transaction.remove(frag2);
        transaction.add(R.id.main_activity_layout,frag3,"input_edit_fragment");
        transaction.addToBackStack("calendar_fragment");
        transaction.addToBackStack("list_view_fragment");
        manager.executePendingTransactions();
        transaction.commit();
        frag3.values(rowIds,myDb,selectedYear,selectedMonth,selectedDay);

    }
}
