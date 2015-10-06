package pacman372.patient_client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by chuan on 21/09/2015.
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
