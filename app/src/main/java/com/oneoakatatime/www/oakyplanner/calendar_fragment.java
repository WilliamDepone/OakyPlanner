package com.oneoakatatime.www.oakyplanner;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

/**
 * Created by User on 5/19/2017.
 */

public class calendar_fragment extends Fragment {
    CalendarView calendar;
    Comunicator com;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calendar_fragment,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        CalendarView calendar = (CalendarView) getActivity().findViewById(R.id.MonthlyCalendarView);
        com = (Comunicator) getActivity();
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month++;
/* Passes in the currently selected date and populates the list view if there is something to populate it with.*/
                com.dateTransfer(year,month,dayOfMonth);

            }
        });

        super.onActivityCreated(savedInstanceState);
    }

}
