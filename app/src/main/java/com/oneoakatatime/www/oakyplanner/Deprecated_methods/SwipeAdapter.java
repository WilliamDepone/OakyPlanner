package com.oneoakatatime.www.oakyplanner.Deprecated_methods;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by User on 5/28/2017.
 */
/*
public class SwipeAdapter extends FragmentStatePagerAdapter {
    private String tabTitles[] = new String[]{"MONTH", "WEEK","DAY"};

    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    Fragment fragment1, fragment2, fragment3;

    int currentDate[];
    TimeZone tz;

    @Override
    public Fragment getItem(int position) {
        currentDate = new int[3];
        tz = TimeZone.getDefault();
        java.util.Calendar calendarForWeek = new GregorianCalendar(tz);
        currentDate[0] = calendarForWeek.get(java.util.Calendar.YEAR);
        currentDate[1] = calendarForWeek.get(java.util.Calendar.MONTH);
        currentDate[2] = calendarForWeek.get(Calendar.WEEK_OF_YEAR);
        Bundle bundle = new Bundle();

        switch (position) {

            case 0:
                return monthlyView.newInstance();
            case 1:
                fragment1 = weekly_view.newInstance(bundle);
                return fragment1;
            case 2:
                return daily_view.newInstance(bundle);
            default:
                return monthlyView.newInstance();

        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabTitles[position];
    }


}
*/