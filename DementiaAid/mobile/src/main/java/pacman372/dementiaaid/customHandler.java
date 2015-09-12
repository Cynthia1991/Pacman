package pacman372.dementiaaid;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.pushbots.push.PBNotificationIntent;
import com.pushbots.push.Pushbots;
import com.pushbots.push.utils.PBConstants;

import java.util.HashMap;

/**
 * Created by jieliang on 7/09/2015.
 */
public class customHandler extends BroadcastReceiver  {
    private static final String TAG = "customHandler";
private Notification  notification;
    private NotificationManager nManager;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        Log.d(TAG, "action=" + action);
        // Handle Push Message when opened
        if (action.equals(PBConstants.EVENT_MSG_OPEN)) {
            //Check for Pushbots Instance
            Pushbots pushInstance = Pushbots.sharedInstance();
            if(!pushInstance.isInitialized()){
                Log.d(TAG,"inital bots");
                Pushbots.sharedInstance().init(context.getApplicationContext());
                //AlarmReceiver alarmReceiver=new AlarmReceiver();
                //alarmReceiver.setAlarm(TapMainActivity.context1);

            }

            //Clear Notification array
            if(PBNotificationIntent.notificationsArray != null){
                PBNotificationIntent.notificationsArray = null;
            }

            HashMap<?, ?> PushdataOpen = (HashMap<?, ?>) intent.getExtras().get(PBConstants.EVENT_MSG_OPEN);
            Log.w(TAG, "User clicked notification with Message: " + PushdataOpen.get("message"));

            //Report Opened Push Notification to Pushbots
            if(Pushbots.sharedInstance().isAnalyticsEnabled()){
                Pushbots.sharedInstance().reportPushOpened( (String) PushdataOpen.get("PUSHANALYTICS"));
                //AlarmReceiver alarmReceiver=new AlarmReceiver();
                //alarmReceiver.setAlarm(TapMainActivity.context1);

            }

            //Start lanuch Activity
            String packageName = context.getPackageName();
            Intent resultIntent = new Intent(context.getPackageManager().getLaunchIntentForPackage(packageName));
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);

            resultIntent.putExtras(intent.getBundleExtra("pushData"));
            Pushbots.sharedInstance().startActivity(resultIntent);

            // Handle Push Message when received
        }else {
            if (action.equals(PBConstants.EVENT_MSG_RECEIVE)) {
                //
                // alarmReceiver.setAlarm();
                //AlarmReceiver alarmReceiver=new AlarmReceiver();
                //alarmReceiver.setAlarm();

                // Intent intent1= new Intent(TapMainActivity.context2, AlarmReceiver.class);
                notification.flags = Notification.FLAG_INSISTENT;
                notification.defaults = Notification.DEFAULT_SOUND;
                notification.defaults = Notification.DEFAULT_ALL;
                //intent = new Intent(NotificationActivity.this, NotificationResult.class);
                nManager.notify(123,notification);
                HashMap<?, ?> PushdataOpen = (HashMap<?, ?>) intent.getExtras().get(PBConstants.EVENT_MSG_RECEIVE);
                Log.w(TAG, "User Received notification with Message: " + PushdataOpen.get("message"));



            }
        }


    }



    }









