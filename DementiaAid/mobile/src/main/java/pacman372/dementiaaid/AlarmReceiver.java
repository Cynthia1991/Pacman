package pacman372.dementiaaid;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by jieliang on 8/09/2015.
 */
public class AlarmReceiver extends Activity {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    public AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Vibrator vibrator =(Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(5000);
    }




}


