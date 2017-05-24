package com.oneoakatatime.www.oakyplanner;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Spinner;
import com.oneoakatatime.www.oakyplanner.DataBaseHelper;


public class input_edit extends Fragment {
    TextView input_hours, input_minutes,fragment_place,fragment_description;
    Spinner input_hours_spinner,input_minutes_spinner;
    EditText input_place_edit,input_description_edit;
    Button input_button;
    Comunicator com;
    DataBaseHelper myDb;
    Long rowId;
    int selectedYear,selectedMonth,selectedDay;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_input_edit,container,false);

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        input_hours = (TextView) getActivity().findViewById(R.id.input_hours);
        input_minutes = (TextView) getActivity().findViewById(R.id.input_minutes);
        fragment_place = (TextView) getActivity().findViewById(R.id.fragment_place);
        fragment_description = (TextView) getActivity().findViewById(R.id.fragment_description);
        input_hours_spinner = (Spinner) getActivity().findViewById(R.id.input_hours_spinner);
        input_minutes_spinner = (Spinner) getActivity().findViewById(R.id.input_minutes_spinner);
        input_place_edit = (EditText) getActivity().findViewById(R.id.input_place_edit);
        input_description_edit = (EditText) getActivity().findViewById(R.id.input_description_edit);
        input_button = (Button) getActivity().findViewById(R.id.input_button);

        com= (Comunicator) getActivity();

        super.onActivityCreated(savedInstanceState);

        Integer[] hours_array = new Integer[24];
        Integer[] minutes_array = new Integer[60];
        populateArrays(hours_array,minutes_array);

        ArrayAdapter arrayAdapter_hours = new ArrayAdapter(this.getActivity(),R.layout.support_simple_spinner_dropdown_item,hours_array);
        input_hours_spinner.setAdapter(arrayAdapter_hours);
        ArrayAdapter arrayAdapter_minutes = new ArrayAdapter(this.getActivity(),R.layout.support_simple_spinner_dropdown_item,minutes_array);
        input_minutes_spinner.setAdapter(arrayAdapter_minutes);
        mapping(rowId,myDb);
        input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /** TODO 1) Save all data into the database with a new record
             * 2) close fragment 3, re-open fragment 1 and 2 **/
            myDb.insertData1(selectedYear,selectedMonth,selectedDay,input_hours_spinner.getSelectedItemPosition(),input_minutes_spinner.getSelectedItemPosition(),input_description_edit.getText().toString(),input_place_edit.getText().toString());
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                input_edit frag3 = (input_edit) manager.findFragmentByTag("input_edit_fragment");
                transaction.remove(frag3);
                transaction.addToBackStack("input_edit_fragment");
                transaction.commit();
                com.populateActivity();

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

    public void mapping(Long id, DataBaseHelper myDb){



        Cursor c = myDb.getEventInfo(id);
        int event_hour,event_minute;
        event_hour = Integer.parseInt(c.getString(1));
        event_minute = Integer.parseInt(c.getString(2));
         input_hours_spinner.setSelection(event_hour);
         input_minutes_spinner.setSelection(event_minute);
         input_place_edit.setText(c.getString(3));
         input_description_edit.setText(c.getString(4));

    }
    public void values(Long id,DataBaseHelper myDbinput,int selectedYearinput,int selectedMonthinput,int selectedDayinput){
        rowId = id;
        myDb = myDbinput;
        selectedYear = selectedYearinput;
        selectedMonth = selectedMonthinput;
        selectedDay=selectedDayinput;

    }

}
