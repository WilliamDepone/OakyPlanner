package com.oneoakatatime.www.oakyplanner;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import java.util.GregorianCalendar;
import java.util.TimeZone;


public class input_edit extends android.app.Fragment {
    TextView input_hours, input_minutes,fragment_place,fragment_description;
    Spinner input_hours_spinner_from,input_hours_spinner_until,input_minutes_spinner_from,input_minutes_spinner_until;
    EditText input_place_edit,input_description_edit;
    Button input_button;
DataBaseHelper myDb;



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
        input_hours = (TextView) getActivity().findViewById(R.id.input_hours);
        input_minutes = (TextView) getActivity().findViewById(R.id.input_minutes);
        fragment_place = (TextView) getActivity().findViewById(R.id.fragment_place);
        fragment_description = (TextView) getActivity().findViewById(R.id.fragment_description);
        input_hours_spinner_from = (Spinner) getActivity().findViewById(R.id.input_hours_spinner_from);
        input_minutes_spinner_from = (Spinner) getActivity().findViewById(R.id.input_minutes_spinner_from);
        input_minutes_spinner_until = (Spinner) getActivity().findViewById(R.id.input_minutes_spinner_until);
        input_hours_spinner_until = (Spinner) getActivity().findViewById(R.id.input_hours_spinner_until);

        input_place_edit = (EditText) getActivity().findViewById(R.id.input_place_edit);
        input_description_edit = (EditText) getActivity().findViewById(R.id.input_description_edit);
        input_button = (Button) getActivity().findViewById(R.id.input_button);





        Integer[] hours_array = new Integer[24];
        Integer[] minutes_array = new Integer[60];
        populateArrays(hours_array,minutes_array);

        ArrayAdapter arrayAdapter_hours = new ArrayAdapter(this.getActivity(),R.layout.support_simple_spinner_dropdown_item,hours_array);
        input_hours_spinner_from.setAdapter(arrayAdapter_hours);
        input_hours_spinner_until.setAdapter(arrayAdapter_hours);

        ArrayAdapter arrayAdapter_minutes = new ArrayAdapter(this.getActivity(),R.layout.support_simple_spinner_dropdown_item,minutes_array);
        input_minutes_spinner_from.setAdapter(arrayAdapter_minutes);
        input_minutes_spinner_until.setAdapter(arrayAdapter_minutes);
        mapping(getArguments().getInt("Event id"),myDb);
        input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /** TODO 1) Save all data into the database with a new record
             * 2) close fragment 3, re-open fragment 1 and 2 **/
                TimeZone tz = TimeZone.getDefault();
                java.util.Calendar calendarForWeek = new GregorianCalendar(tz);
                final java.util.Calendar dateCalendar = new java.util.GregorianCalendar(tz);
                dateCalendar.set(java.util.Calendar.YEAR,getArguments().getInt("Selected year"));
                dateCalendar.set(java.util.Calendar.MONTH,getArguments().getInt("Selected month"));
                dateCalendar.set(java.util.Calendar.DAY_OF_MONTH,getArguments().getInt("Selected day"));
                int selectedWeek= dateCalendar.get(java.util.Calendar.WEEK_OF_YEAR);

             myDb.insertData1(getArguments().getInt("Selected year"),getArguments().getInt("SelectedMonth"),selectedWeek,getArguments().getInt("Selected day"),input_hours_spinner_from.getSelectedItemPosition(),input_hours_spinner_until.getSelectedItemPosition(),input_minutes_spinner_from.getSelectedItemPosition(),input_minutes_spinner_until.getSelectedItemPosition(),input_description_edit.getText().toString(),input_place_edit.getText().toString());

                EventBus.getDefault().post(new MyRecyclerAdapter.ChangeFragmentToTwoEvent(2,0,0,0,0));



            }
        });
    }

    private void populateArrays(Integer[] hours_array, Integer[] minutes_array) {
        int i,x,y;
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

    public void mapping(int event_id,DataBaseHelper myDb){

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

    }


}
