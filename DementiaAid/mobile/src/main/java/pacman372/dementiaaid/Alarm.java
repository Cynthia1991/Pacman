package pacman372.dementiaaid;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.os.SystemClock;

/**
 * Created by jieliang on 8/09/2015.
 */
public class Alarm extends Activity{
    /* alarm */
    final static private long ONE_SECOND = 1000;
    final static private long TWENTY_SECONDS = ONE_SECOND * 20;
    PendingIntent pi;
    BroadcastReceiver br;
    AlarmManager am;
    public void alarm1() {
             am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
            TWENTY_SECONDS, pi);
         }



}

