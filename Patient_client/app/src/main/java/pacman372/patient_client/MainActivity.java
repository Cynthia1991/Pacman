package pacman372.patient_client;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {


    SampleAlarmReceiver alarm = new SampleAlarmReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1=(Button)findViewById(R.id.button);
        btn1.setOnClickListener(this);
        Button btn2=(Button)findViewById(R.id.button2);
        btn2.setOnClickListener(this);

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.button: {
                // do something for button 1 click
                alarm.setAlarm(this);
                break;
            }

            case R.id.button2: {
                // do something for button 2 click
                alarm.cancelAlarm(this);
                break;
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
