package com.oneoakatatime.www.oakyplanner;

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


        super.onActivityCreated(savedInstanceState);
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

        input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

    public void mapping(String id, DataBaseHelper myDb){

        int y = Integer.parseInt(id);
        Cursor c = myDb.getEventInfo(y);
         input_hours.setText(c.getString(1));
         input_minutes.setText(c.getString(2));
         input_place_edit.setText(c.getString(3));
         input_description_edit.setText(c.getString(4));

    }
}
