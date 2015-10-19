package com.example.pacmanandroidnew;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pacmanandroidnew.R;

public class MainActivity extends Activity implements View.OnClickListener{
    private EditText mobile;

    SampleAlarmReceiver alarm = new SampleAlarmReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mobile=(EditText)findViewById(R.id.editText6);
        Button btn1=(Button)findViewById(R.id.button);
        btn1.setOnClickListener(this);
        Button btn2=(Button)findViewById(R.id.button2);
        btn2.setOnClickListener(this);
        Button btn3=(Button)findViewById(R.id.button6);
        btn3.setOnClickListener(this);

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

            case R.id.button6:{
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + mobile.getText().toString()));
               MainActivity.this.startActivity(intent);
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
