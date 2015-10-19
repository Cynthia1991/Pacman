package pacman372.dementiaaid.CarerDetailsScreen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pacman372.dementiaaid.R;
import pacman372.dementiaaid.TapMainActivity;

public class CarerDetailsActivity extends AppCompatActivity {

    //for the set fence activity
    Button button2;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Context context;
    //end fence variables

    CarerDetailsVM carerDetailsVM ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carer_details);
        carerDetailsVM = new CarerDetailsVM(this);
        /** This is entirely for the set fence activity and setting up the alarm notification*/
        Intent intent =getIntent();
        button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){@Override
                                                              public void onClick(View view) {


            alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, TapMainActivity.class);
            alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() +
                            10 * 1000, alarmIntent);
        }
        });

        TextView carerName = (TextView) findViewById(R.id.textViewCarerName);
        carerName.setText(carerDetailsVM.carerName);
        TextView carerIDView = (TextView) findViewById(R.id.textViewCarerID);
        carerIDView.setText(String.valueOf(carerDetailsVM.carerID));
        TextView carerPhone = (TextView) findViewById(R.id.textViewCarerPhone);
        carerPhone.setText(String.valueOf(carerDetailsVM.carerPhone));
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carer_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
