package com.example.pacmanandroidnew.ShareLocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.pacmanandroidnew.ShareLocation.SampleAlarmReceiver;

/**
 * Created by lichuan on 11/10/2015.
 */
public class SampleBootReceiver extends BroadcastReceiver {
    SampleAlarmReceiver alarm = new SampleAlarmReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            alarm.setAlarm(context);
        }
    }
}
