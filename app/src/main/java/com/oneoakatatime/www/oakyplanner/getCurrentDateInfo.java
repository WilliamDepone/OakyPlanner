package com.oneoakatatime.www.oakyplanner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by User on 5/31/2017.
 */
public class getCurrentDateInfo {

    int[] currentDate;
    TimeZone tz;
   public int[] getInfo(){

        currentDate = new int[4];
        tz = TimeZone.getDefault();
        java.util.Calendar calendarForWeek = new GregorianCalendar(tz);
        currentDate[0] = calendarForWeek.get(Calendar.YEAR);
        currentDate[1] = calendarForWeek.get(Calendar.MONTH);
        currentDate[2] = calendarForWeek.get(Calendar.DAY_OF_MONTH);
        currentDate[3] = calendarForWeek.get(Calendar.WEEK_OF_YEAR);
       return currentDate;
    }

}
