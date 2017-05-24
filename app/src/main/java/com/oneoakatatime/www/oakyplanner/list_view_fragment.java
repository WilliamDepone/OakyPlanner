package com.oneoakatatime.www.oakyplanner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.oneoakatatime.www.oakyplanner.DataBaseHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import com.oneoakatatime.www.oakyplanner.SwipeDetector;

/**
 * Created by User on 5/18/2017.
 */

public class list_view_fragment extends Fragment {
    int counter;
    ListView fragment_listView;
    Comunicator com;
    DataBaseHelper myDb;
    int[] currentDate;
    TimeZone tz;
    SwipeDetector swipeDetector;
    int selectedYear,selectedMonth,selecetedDay;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        counter = 0;
        ListView fragment_listView = (ListView) getActivity().findViewById(R.id.fragment_listView);
        super.onActivityCreated(savedInstanceState);
        com= (Comunicator) getActivity();
        currentDate = new int[3];
        tz = TimeZone.getDefault();
        Calendar calendar = new GregorianCalendar(tz);
        currentDate[0] = calendar.get(Calendar.YEAR);
        currentDate[1] = calendar.get(Calendar.MONTH);
        currentDate[2] = calendar.get(Calendar.DAY_OF_MONTH);
        populateListView(currentDate[0],currentDate[1],currentDate[2]);

        /////////////////////////////////////////////////////////////////////
        final SwipeDetector swipeDetector = new SwipeDetector();
        fragment_listView.setOnTouchListener(swipeDetector);

        fragment_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(swipeDetector.swipeDetected()) {
                    if(swipeDetector.getAction() == SwipeDetector.Action.LR) {
                        Log.d("list_view_fragment","Current id is: "+id);
                        myDb.deleteEvent(id);
                        populateListView(selectedYear,selectedMonth,selecetedDay);
                    } else {

                    }
                }
            }
        });


            ///////////////////////////////////////////////////////////////////////
        fragment_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                com.createInputEdit(id);


                return false;
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_fragment,container,false);

        return view;
    }


    public String[] populateListView(int year, int month, int day) {
ListView fragment_listView = (ListView) getActivity().findViewById(R.id.fragment_listView);
        String[] fromDataBaseRowId = new String[0];
        selecetedDay = day;
        selectedMonth = month;
        selectedYear = year;

        if (counter >0) {
            fragment_listView.clearChoices();
        }

        myDb = new DataBaseHelper(this.getActivity());
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

            if (cursor.getCount() > 0) {
                fromDataBaseRowId = new String[]{cursor.getString(cursor.getColumnIndex("ID_1"))};
            }

        }
        return fromDataBaseRowId;
    }


}









