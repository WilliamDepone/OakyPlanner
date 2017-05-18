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
/** TODO 1) ADD THE NEW FRAGMENT FUNCTION 2) DELETE TASK WITH FLICK 3) ADD TABS 4) WEEKLY VIEW 5) DAILY VIEW 6) SPIDER 7) GOOGLE MAPS THING **/
public class MainActivity extends AppCompatActivity implements Comunicator {
    /* TODO REMOVE FABRICATED VARIABLES */
    int fabricated_event_year, fabricated_event_month, fabricated_event_day, fabricated_event_hour, fabricated_event_minute;
   int[] currentDate;
    String [] rowIds;
    String fabricated_event_description, fabricated_event_place;
    CalendarView monthlyCalendar;
    DataBaseHelper myDb;

TimeZone tz;
    boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        currentDate = new int[3];
        check = false;

        tz = TimeZone.getDefault();
        Calendar calendar = new GregorianCalendar(tz);
        currentDate[0] = calendar.get(Calendar.YEAR);
        currentDate[1] = calendar.get(Calendar.MONTH);
        currentDate[2] = calendar.get(Calendar.DAY_OF_MONTH);

        myDb = new DataBaseHelper(this);
        monthlyCalendar = (CalendarView) findViewById(R.id.MonthlyCalendarView);
        fabricateData();
        myDb.insertData1(fabricated_event_year,fabricated_event_month,fabricated_event_day,fabricated_event_hour,fabricated_event_minute,fabricated_event_description,fabricated_event_place);
     /** TODO CHECK IF THIS WORKS ON A DIFFIRENT DATE, IT POTENTIONALLY COULD NOT **/
         rowIds= populateListView(currentDate[0],currentDate[1],currentDate[2]);
        final ListView monthlyListView = (ListView) findViewById(R.id.ListView1);
        monthlyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                input_edit frag = new input_edit();
                FragmentManager manager = getFragmentManager();

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.main_activity_layout,frag,"input_edit_fragment");
                transaction.commit();
                manager.executePendingTransactions();
                input_edit fragment1= (input_edit) manager.findFragmentByTag("input_edit_fragment");
                fragment1.mapping(rowIds[position],myDb);


                return false;
            }
        });


        monthlyCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
/* Passes in the currently selected date and populates the list view if there is something to populate it with.*/
             rowIds = populateListView(year,month,dayOfMonth);

            }
        });




    }


    /* TODO DONT FORGET TO REMOVE THIS BEFORE RELEASE... FUDGE */
    private void fabricateData(){
        fabricated_event_year = 2017;
        fabricated_event_month = 5;
        fabricated_event_day = 11;
        fabricated_event_hour = 12;
        fabricated_event_minute = 0;
        fabricated_event_description = "this is dummy data";
        fabricated_event_place = "Placey-mcplacey";

    }
    private String[] populateListView(int year, int month, int day) {
        String[] fromDataBaseRowId = new String[0];
        ListView monthlyListView = (ListView) findViewById(R.id.ListView1);
        if (check == true) {
            monthlyListView.clearChoices();
        }

        Cursor cursor = myDb.getAllRows(year, month, day);
        startManagingCursor(cursor);
        /** take from database time and description**/
        String[] fromDataBaseTD = new String[]{DataBaseHelper.HOUR, DataBaseHelper.MINUTE, DataBaseHelper.EVENT_DESCRIPTION};


        int[] toViewIds = new int[]{R.id.tpd_hours, R.id.tpd_minutes, R.id.tpd_description};

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.tpd_row,
                cursor,
                fromDataBaseTD,
                toViewIds
        );

        monthlyListView.setAdapter(cursorAdapter);
        if (cursor != null) {
            check = true;
            /** TODO FIX THIS DAMN THING */

            if (cursor.getCount() > 0) {
                fromDataBaseRowId = new String[]{cursor.getString(cursor.getColumnIndex("ID_1"))};
            }

        }
        return fromDataBaseRowId;
    }

    @Override
    public void rowIdTransfer(String id) {

    }
}
