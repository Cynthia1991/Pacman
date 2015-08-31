package com.example.lichuan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.view.LayoutInflater;
import android.widget.Button;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;


public class MainActivity extends FragmentActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost m = (TabHost)findViewById(R.id.tabhost);
        m.setup();

        LayoutInflater i=LayoutInflater.from(this);
        i.inflate(R.layout.tab1, m.getTabContentView());
        i.inflate(R.layout.tab2, m.getTabContentView());
        i.inflate(R.layout.tab3, m.getTabContentView());//动态载入XML，而不需要Activity

        m.addTab(m.newTabSpec("tab1").setIndicator("Map").setContent(R.id.LinearLayout01));
        m.addTab(m.newTabSpec("tab2").setIndicator("My Patient").setContent(R.id.LinearLayout02));
        m.addTab(m.newTabSpec("tab3").setIndicator("My Info").setContent(R.id.LinearLayout03));

        button=(Button)findViewById(R.id.button);
        //button.performClick(){
          //  Intent in=new Intent();
          //  in.setClass(this, tab1.class);
        //};
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
