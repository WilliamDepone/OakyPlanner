package com.oneoakatatime.www.oakyplanner;



import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.support.v4.view.ViewPager;

import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.app.FragmentManager;

import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Context;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/** TODO  4) WEEKLY VIEW   5.5) Login screen 6) SPIDER 7) GOOGLE MAPS THING **/
public class MainActivity extends AppCompatActivity implements Comunicator {
    /* TODO REMOVE FABRICATED VARIABLES */
    int fabricated_event_year, fabricated_event_month,fabricated_event_week, fabricated_event_day, fabricated_event_hour, fabricated_event_minute;
   int[] currentDate;

    String fabricated_event_description, fabricated_event_place;

    DataBaseHelper myDb;
    int selectedYear,selectedMonth,selectedDay,selectedWeek;

    NonSwipeableViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DataBaseHelper(this);
        fabricateWeeklyData();
        viewPager=(NonSwipeableViewPager) findViewById(R.id.view_pager);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        getCurrentDateInfo curdat = new getCurrentDateInfo();
        currentDate = curdat.getInfo();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {



            }

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 1:{


                        EventBus.getDefault().post(new remoteWeeklyListViewPopulation(true,selectedYear,selectedMonth,selectedWeek,selectedDay));
                    }
                    case 2: {
                        EventBus.getDefault().post(new remoteDailyListViewPopulation(true,selectedYear,selectedMonth,selectedWeek,selectedDay));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }



    /* TODO DONT FORGET TO REMOVE THIS BEFORE RELEASE... FUDGE */
    private void fabricateData(){/*
        fabricated_event_year = 2017;
        fabricated_event_month = 5;
        fabricated_event_day = 22;
        fabricated_event_week = 21;
        int i;
        i=0;
        fabricated_event_minute = 12;
        fabricated_event_description = "this is dummy data";
        fabricated_event_place = "Placey-mcplacey";
        while(i<10){
            fabricated_event_hour =i;
            i++;
            myDb.insertData1(fabricated_event_year,fabricated_event_month,fabricated_event_week,fabricated_event_day,fabricated_event_hour,fabricated_event_minute,fabricated_event_description,fabricated_event_place);

        }
        fabricated_event_day = 23;
        while(i<20){
            fabricated_event_hour =i;
            i++;
            myDb.insertData1(fabricated_event_year,fabricated_event_month,fabricated_event_week,fabricated_event_day,fabricated_event_hour,fabricated_event_minute,fabricated_event_description,fabricated_event_place);

        }
    */}
    private void fabricateWeeklyData(){
        fabricated_event_year = 2017;
        fabricated_event_month = 6;

        fabricated_event_week=24;

        int i,y,x;
        i=0;
        y=4;
        x=0;
        fabricated_event_minute = 12;
        fabricated_event_description = "this is dummy data";
        fabricated_event_place = "Placey-mcplacey";
       while(x<7&&y<11 ){
           i=0;
           x++;
           fabricated_event_day = y;
            y++;
           while(i<10){
            fabricated_event_hour =i;
            i++;
            myDb.insertData1(fabricated_event_year,fabricated_event_month,fabricated_event_week,x,fabricated_event_day,fabricated_event_hour,fabricated_event_minute,fabricated_event_description,fabricated_event_place);

        }}


        }










    @Override
    public void dateTransfer(Bundle bundle) {

        viewPager.setCurrentItem(1);


    }

    @Override
    public void createInputEdit(long rowIds) {
        input_edit frag3 = new input_edit();
        FragmentManager manager = getFragmentManager();



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
     /**
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void OnWeekAcquisition (monthlyView.CurrentWeekSelected event){
        viewPager.setCurrentItem(1,true);
    }**/
    public class remoteWeeklyListViewPopulation {
        public boolean populate; int passYear,passMonth,passWeek,passDay;
        public remoteWeeklyListViewPopulation(boolean a,int c, int d, int e , int f){
            populate = a;

            passYear = c;
            passMonth = d;
            passWeek = e;
            passDay = f;
        }

    }
    public class remoteDailyListViewPopulation {
        public boolean populate;int passYear,passMonth,passWeek,passDay;
        public remoteDailyListViewPopulation(boolean a,int c, int d, int e , int f){
            populate = a;

            passYear = c;
            passMonth = d;
            passWeek = e;
            passDay = f;
        }

    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void OnWeekAcquisition (monthlyView.CurrentWeekSelected event){
        selectedWeek = event.weekNumber;
        selectedYear = event.yearNumber;
        selectedMonth = event.monthNumber;
        selectedDay = event.dayNumber;
    }

}
