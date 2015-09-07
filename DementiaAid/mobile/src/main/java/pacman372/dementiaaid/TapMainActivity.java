package pacman372.dementiaaid;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import java.net.MalformedURLException;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

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
        i.inflate(R.layout.tab3, m.getTabContentView());//动态载入XML，而不需要Activity

        m.addTab(m.newTabSpec("tab1").setIndicator("Map").setContent(R.id.LinearLayout01));
        m.addTab(m.newTabSpec("tab2").setIndicator("My Patient").setContent(R.id.LinearLayout02));
        m.addTab(m.newTabSpec("tab3").setIndicator("My Info").setContent(R.id.LinearLayout03));

        button=(Button)findViewById(R.id.button);
    }
    /*public void creatNewPatient(){

        try {
            mClient = new MobileServiceClient(
                    "https://pacmanandroid.azure-mobile.net/",
                    "mpVjGDJAoCHcrHsuWkxJGTvjwBDZMk90",
                    this
            );
            Patient patient = new Patient();
            patient.setName("Cynthia_P");
            patient.setPhone("0424112714");
            alertDialog = new AlertDialog.Builder(this);
            mClient.getTable(Patient.class).insert(patient, new TableOperationCallback<Patient>() {
                public void onCompleted(Patient entity, Exception exception, ServiceFilterResponse response) {
                    if (exception == null) {
                        // Insert succeeded

                        alertDialog.setTitle("success...");
                        alertDialog.setMessage("Insert succeeded!");
                        alertDialog.show();

                    } else {
                        // Insert failed
                        alertDialog.setTitle("Failed...");
                        alertDialog.setMessage("Insert failed!");
                        alertDialog.show();

                    }
                }
            });
        }catch (MalformedURLException e) {

            //createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        } catch (Exception e){
            //createAndShowDialog(e, "Error");
        }

    }*/
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
