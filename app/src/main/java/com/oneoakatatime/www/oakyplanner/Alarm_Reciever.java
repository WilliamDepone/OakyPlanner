package com.oneoakatatime.www.oakyplanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by AIGARS on 29/07/2017.
 */

public class Alarm_Reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
Intent serviceIntent = new Intent(context,RingTonePlayingService.class);
        context.startService(serviceIntent);


    }
}
