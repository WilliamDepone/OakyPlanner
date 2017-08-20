package com.oneoakatatime.www.oakyplanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class input_edit extends android.app.Fragment {
    TextView input_hours, input_minutes,fragment_place,fragment_description,remind_me_textView_input_edit;
    Spinner input_hours_spinner_from,input_hours_spinner_until,input_minutes_spinner_from,input_minutes_spinner_until,input_edit_reminder_spinner;
    EditText input_place_edit,input_description_edit;
    Button input_button;
DataBaseHelper myDb;
    Context context;
    PendingIntent pendingIntent;
    AlarmManager alarmManager ;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int event_id;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Override
    public void setArguments(Bundle args) {

        super.setArguments(args);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_input_edit,container,false);

        return view;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DataBaseHelper myDb = new DataBaseHelper(this.getActivity());
        this.context = this.getActivity();
        input_hours = (TextView) getActivity().findViewById(R.id.input_hours);
        input_minutes = (TextView) getActivity().findViewById(R.id.input_minutes);
        fragment_place = (TextView) getActivity().findViewById(R.id.fragment_place);
        fragment_description = (TextView) getActivity().findViewById(R.id.fragment_description);
        remind_me_textView_input_edit = (TextView) getActivity().findViewById(R.id.remind_me_textView_input_edit);

        input_hours_spinner_from = (Spinner) getActivity().findViewById(R.id.input_hours_spinner_from);
        input_minutes_spinner_from = (Spinner) getActivity().findViewById(R.id.input_minutes_spinner_from);
        input_minutes_spinner_until = (Spinner) getActivity().findViewById(R.id.input_minutes_spinner_until);
        input_hours_spinner_until = (Spinner) getActivity().findViewById(R.id.input_hours_spinner_until);
        input_edit_reminder_spinner = (Spinner)getActivity().findViewById(R.id.input_edit_reminder_spinner);

        input_place_edit = (EditText) getActivity().findViewById(R.id.input_place_edit);
        input_description_edit = (EditText) getActivity().findViewById(R.id.input_description_edit);
        input_button = (Button) getActivity().findViewById(R.id.input_button);

        alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

        final Intent alarm_intent = new Intent(this.context,Alarm_Reciever.class);



        Integer[] hours_array = new Integer[24];
        Integer[] minutes_array = new Integer[60];
        String[] reminders_array = new String[13];
        reminders_array = populateArrays(hours_array,minutes_array,reminders_array);

        ArrayAdapter arrayAdapter_hours = new ArrayAdapter(this.getActivity(),R.layout.support_simple_spinner_dropdown_item,hours_array);
        input_hours_spinner_from.setAdapter(arrayAdapter_hours);
        input_hours_spinner_until.setAdapter(arrayAdapter_hours);

        ArrayAdapter arrayAdapter_minutes = new ArrayAdapter(this.getActivity(),R.layout.support_simple_spinner_dropdown_item,minutes_array);
        input_minutes_spinner_from.setAdapter(arrayAdapter_minutes);
        input_minutes_spinner_until.setAdapter(arrayAdapter_minutes);
        ArrayAdapter arrayAdapter_reminders = new ArrayAdapter(this.getActivity(),R.layout.support_simple_spinner_dropdown_item,reminders_array);
        input_edit_reminder_spinner.setAdapter(arrayAdapter_reminders);
        boolean isItNew = getArguments().getBoolean("New?");
        if(!isItNew){
         mapping(getArguments().getInt("Event id"),myDb,reminders_array);}
        input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /** TODO 1) Save all data into the database with a new record
             * 2) close fragment 3, re-open fragment 1 and 2 **/

                TimeZone tz = TimeZone.getDefault();

                final java.util.Calendar dateCalendar = new java.util.GregorianCalendar(tz);
                dateCalendar.set(java.util.Calendar.YEAR,getArguments().getInt("Selected year"));
                dateCalendar.set(java.util.Calendar.MONTH,getArguments().getInt("Selected month")+1);
                dateCalendar.set(java.util.Calendar.DAY_OF_MONTH,getArguments().getInt("Selected day"));
                dateCalendar.set(Calendar.HOUR_OF_DAY,input_hours_spinner_from.getSelectedItemPosition());
                dateCalendar.set(Calendar.MINUTE,input_minutes_spinner_from.getSelectedItemPosition());
                int selectedWeek= dateCalendar.get(java.util.Calendar.WEEK_OF_YEAR);

                myDb.insertData1(getArguments().getInt("Selected year"),getArguments().getInt("Selected month")+1,selectedWeek,getArguments().getInt("Selected day"),input_hours_spinner_from.getSelectedItemPosition(),input_hours_spinner_until.getSelectedItemPosition(),input_minutes_spinner_from.getSelectedItemPosition(),input_minutes_spinner_until.getSelectedItemPosition(),input_description_edit.getText().toString(),input_place_edit.getText().toString(),input_edit_reminder_spinner.getSelectedItem().toString());

                pendingIntent = PendingIntent.getBroadcast(getActivity(),0,alarm_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                int pos = input_edit_reminder_spinner.getSelectedItemPosition();
                alarmSetter(pendingIntent,dateCalendar,alarmManager,input_edit_reminder_spinner.getSelectedItemPosition());

                EventBus.getDefault().post(new MyRecyclerAdapter.ChangeFragmentToTwoEvent(2,0,0,0,0));

                boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                        new Intent("com.my.package.MY_UNIQUE_ACTION"),
                        PendingIntent.FLAG_NO_CREATE) != null);

                if (alarmUp)
                {
                    Log.e("myTag", "Alarm is already active");
                }

            }
        });
    }

    private void alarmSetter(PendingIntent pendingIntent, java.util.Calendar calendar, AlarmManager alarmManager, int selectedItemPosition) {
        switch (selectedItemPosition){
            case 1:break;
            case 2: calendar.set(Calendar.MONTH,Calendar.MONTH-1); alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);break;
            case 3: calendar.set(Calendar.WEEK_OF_YEAR,Calendar.WEEK_OF_YEAR-1); alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);break;
            case 4: calendar.set(Calendar.DAY_OF_MONTH,Calendar.DAY_OF_MONTH-1); alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);break;
            case 5: calendar.set(Calendar.HOUR,Calendar.HOUR-12); alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);break;
            case 6: calendar.set(Calendar.HOUR,Calendar.HOUR-6); alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);break;
            case 7: calendar.set(Calendar.HOUR,Calendar.HOUR-3); alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);break;
            case 8: calendar.set(Calendar.HOUR,Calendar.HOUR-1); calendar.set(Calendar.MINUTE,calendar.MINUTE-30); alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);break;
            case 9: calendar.set(Calendar.MINUTE,Calendar.MINUTE-30); alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);break;
            case 10: calendar.set(Calendar.MINUTE,Calendar.MINUTE-15); alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);break;
            case 11: calendar.set(Calendar.MINUTE,Calendar.MINUTE-5); alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);break;
            case 12: alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent); break;

        }

    }

    private String[] populateArrays(Integer[] hours_array, Integer[] minutes_array, String[] reminders_array) {
        int i,x;
        i=0;
        x=0;

        while(i<24){
            hours_array[i]=0;
            i++;
        }
        while(x<60){
            minutes_array[x]=0;
            x++;
        }
        i=0;
        x=0;
        while (i<24){
            hours_array[i]=i;
            i++;

        }
        while (x<60){
            minutes_array[x]=x;
            x++;

        }
        reminders_array[0] = "No reminders";
        reminders_array[1] = "A month before";
        reminders_array[2] = "A week before";
        reminders_array[3] = "A day before";
        reminders_array[4] = "12 hours before";
        reminders_array[5] = "6 hours before";
        reminders_array[6] = "3 hours before";
        reminders_array[7] = "1,5 hours before";
        reminders_array[8] = "1 hour before";
        reminders_array[9] = "30 minutes before";
        reminders_array[10] = "15 minutes before";
        reminders_array[11] = "5 minutes before";
        reminders_array[12] = "Exactly on time";
        return reminders_array;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void mapping(int event_id,DataBaseHelper myDb,String[] reminders_array){

        Cursor c = myDb.getEventInfo((long)event_id);
        int event_hour_from,event_hour_until,event_minute_from,even_minute_until;
        event_hour_from = Integer.parseInt(c.getString(0));
        event_hour_until = Integer.parseInt(c.getString(1));
        event_minute_from = Integer.parseInt(c.getString(2));
        even_minute_until = Integer.parseInt(c.getString(3));
         input_hours_spinner_from.setSelection(event_hour_from);
         input_minutes_spinner_from.setSelection(event_minute_from);
        input_hours_spinner_until.setSelection(event_hour_until);
        input_minutes_spinner_until.setSelection(even_minute_until);
         input_place_edit.setText(c.getString(4));
         input_description_edit.setText(c.getString(5));
        int x=0;
        for(int i =0;i<13;i++){
            if(reminders_array[i] == c.getString(6)){
                x=i;
            }
        }
        input_edit_reminder_spinner.setSelection(x);

    }


}
