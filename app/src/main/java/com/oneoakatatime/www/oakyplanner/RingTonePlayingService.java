package com.oneoakatatime.www.oakyplanner;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by AIGARS on 29/07/2017.
 */

public class RingTonePlayingService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().post(new MyRecyclerAdapter.ChangeFragmentToTwoEvent(3,0,0,0,0));
        return  START_NOT_STICKY;
    }
}
