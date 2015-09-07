package pacman372.dementiaaid;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

public class TapMainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    //private MobileServiceClient mClient;
    private AlertDialog.Builder alertDialog;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_main);
        TabHost m = (TabHost)findViewById(R.id.tabhost);
        m.setup();

        LayoutInflater i=LayoutInflater.from(this);
        i.inflate(R.layout.tab1, m.getTabContentView());
        i.inflate(R.layout.tab2, m.getTabContentView());
        i.inflate(R.layout.tab3, m.getTabContentView());

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
        getMenuInflater().inflate(R.menu.menu_tap_main, menu);
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
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = "123";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }
}
