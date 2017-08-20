package com.oneoakatatime.www.oakyplanner;



import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/** TODO  4) WEEKLY VIEW   5.5) Login screen 6) SPIDER 7) GOOGLE MAPS THING **/
public class MainActivity extends AppCompatActivity {
    /* TODO REMOVE FABRICATED VARIABLES */
    int fabricated_event_year, fabricated_event_month,fabricated_event_week, fabricated_event_day, fabricated_event_hour, fabricated_event_minute,id;
    Fragment frag0;

    String fabricated_event_description, fabricated_event_place;

    DataBaseHelper myDb;
    int selectedYear,selectedMonth,selectedDay,selectedWeek;
    Bundle bundle = new Bundle();
    android.support.v7.app.ActionBar actionBar;
    MenuItem action_bar_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DataBaseHelper(this);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //fabricateWeeklyData();
        id = 0;
         fragmentStateChange(id,null,0,0,0,0);
        /** Fabricated until further notice
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
**/

    }




    private void  fragmentStateChange(int id, Fragment frag0, int event_id, int selectedYear, int selectedMonth, int selectedDay){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction  transaction = fragmentManager.beginTransaction();

        switch(id){
            case 0: {Fragment1 frag = new Fragment1();

                transaction.add(R.id.main_activity_layout,frag,"fragment_state_1");
                transaction.addToBackStack("fragment_state_1");
                transaction.commit();
                break;

            }
            case 1:{

                frag0 =  fragmentManager.findFragmentByTag("fragment_state_1");
                transaction.addToBackStack("fragment_state_1");
                transaction.remove(frag0);
                input_edit frag1 = new input_edit();
                Bundle bundle = new Bundle();
                bundle.putBoolean("New?",false );
                bundle.putInt("Event id",event_id);
                bundle.putInt("Selected year",selectedYear);
                bundle.putInt("Selected month",selectedMonth);
                bundle.putInt("Selected day",selectedDay);
                frag1.setArguments(bundle);
                transaction.add(R.id.main_activity_layout,frag1,"fragment_state_2");
                transaction.commit();
                break;

            }
            case 2:{

                frag0 = fragmentManager.findFragmentByTag("fragment_state_2");
                transaction.addToBackStack("fragment_state_2");
                transaction.remove(frag0);
                Fragment frag1 = fragmentManager.findFragmentByTag("fragment_state_1");
                transaction.add(R.id.main_activity_layout,frag1);
                transaction.commit();
                action_bar_button.setEnabled(true);
                actionBar.invalidateOptionsMenu();

                break;

            }
            case 3:{
                AlarmNotificationFragment frag = new AlarmNotificationFragment();
                transaction.add(R.id.main_activity_layout,frag,"alarm_fragment_state");
                transaction.addToBackStack("alarm_fragment_state");
                transaction.commit();
                break;

            }
            case 4:{
                frag0 =  fragmentManager.findFragmentByTag("fragment_state_1");
                transaction.addToBackStack("fragment_state_1");
                transaction.remove(frag0);
                input_edit frag1 = new input_edit();
                Bundle bundle = new Bundle();
                bundle.putBoolean("New?",true );
                bundle.putInt("Selected year",selectedYear);
                bundle.putInt("Selected month",selectedMonth);
                bundle.putInt("Selected day",selectedDay);
                frag1.setArguments(bundle);
                transaction.add(R.id.main_activity_layout,frag1,"fragment_state_2");
                transaction.commit();
            }

        }




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Subscribe( threadMode =  ThreadMode.MAIN)
    public void OnFragmentChangeMessageGet (MyRecyclerAdapter.ChangeFragmentToTwoEvent event)
    {
          fragmentStateChange(event.change_to,frag0,event.id,event.selectedYear,event.selectedMonth,event.selectedDay);
    }
    @Subscribe( threadMode =  ThreadMode.MAIN)
    public Bundle OnDateChangeMessageGet (Fragment1.currentlySelectedDayData event)
    {

        bundle.putInt("Selected day",event.day);
        bundle.putInt("Selected month",event.month);
        bundle.putInt("Selected year",event.year);
        return bundle;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.a){
            getCurrentDateInfo todaysInfoObj = new getCurrentDateInfo();
            int[] todaysInfoArray =  todaysInfoObj.getInfo();
            action_bar_button = item;
            item.setEnabled(false);
            actionBar.invalidateOptionsMenu();
           if(bundle == null){

            fragmentStateChange(4,null,0,todaysInfoArray[0],todaysInfoArray[1],todaysInfoArray[2]);}
            else {
               fragmentStateChange(4,null,0,bundle.getInt("Selected year"),bundle.getInt("Selected month"),bundle.getInt("Selected day"));
           }
        }
        return super.onOptionsItemSelected(item);
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
    private void fabricateWeeklyData() {
        fabricated_event_year = 2017;
        fabricated_event_month =7;
        fabricated_event_day = 19;
        fabricated_event_week = 30;
        fabricated_event_description = "this is planner dummy data number 2";
        fabricated_event_place = "dummy place";
        int i,from_hour = 0,until_hour=12,from_minutes=0,until_minutes=30;
        i=0;

        while (i<12){
            myDb.insertData1(fabricated_event_year,fabricated_event_month,fabricated_event_week,fabricated_event_day,from_hour,until_hour,from_minutes,until_minutes,fabricated_event_description,fabricated_event_place,"Exact time");
            from_hour++;
            until_hour++;
            from_minutes+=5;
            until_minutes+=5;
            i++;
        }
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


}
