package com.oneoakatatime.www.oakyplanner;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlarmNotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlarmNotificationFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;


    public AlarmNotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlarmNotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlarmNotificationFragment newInstance(String param1, String param2) {
        AlarmNotificationFragment fragment = new AlarmNotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm_notification, container, false);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Button remind_me_later_button = (Button)getActivity().findViewById(R.id.remind_me_later_alarm);
        Button stop_alarm_button = (Button) getActivity().findViewById(R.id.stop_alarm_alarm);
        TextView AlarmedEventName = (TextView) getActivity().findViewById(R.id.current_event_name_alarm);
        final AlarmManager alarmManager ;
        this.context = this.getActivity();
        alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        Intent alarm_intent = new Intent(this.context,Alarm_Reciever.class);
       final PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0,alarm_intent,PendingIntent.FLAG_UPDATE_CURRENT);


        stop_alarm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);
            }
        });


        super.onActivityCreated(savedInstanceState);
    }
}
