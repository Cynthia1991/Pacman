package pacman372.dementiaaid;

import android.app.Activity;
import android.app.ActivityGroup;
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

import com.pushbots.push.Pushbots;

public class TapMainActivity extends ActivityGroup {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    //private MobileServiceClient mClient;
    private AlertDialog.Builder alertDialog;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_main);
        Pushbots.sharedInstance().init(this);
        Pushbots.sharedInstance().register();
        TabHost m = (TabHost)findViewById(R.id.tabhost);
        m.setup(this.getLocalActivityManager());
        //m.setup();

        LayoutInflater i=LayoutInflater.from(this);
        //i.inflate(R.layout.tab1, m.getTabContentView());
        i.inflate(R.layout.tab2, m.getTabContentView());
        i.inflate(R.layout.tab3, m.getTabContentView());//dynamic XML，no need for Activity




        Intent intent =getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        double x=0,y=0;
        int radius=0;
        double latestX=intent.getDoubleExtra("x",x);
        double latestY=intent.getDoubleExtra("y",y);
        int latestRadius=intent.getIntExtra("radius", radius);

        Intent intentNew=new Intent(this, Tab1ShowMapActivity.class);
        intentNew.putExtra("x1",latestX);
        intentNew.putExtra("y1",latestY);
        intentNew.putExtra("radius1",latestRadius);
        //startActivity(Tab1ShowMapActivity);




        m.addTab(m.newTabSpec("tab1").setIndicator("Map").setContent(intentNew));
        m.addTab(m.newTabSpec("tab2").setIndicator("Patient").setContent(R.id.LinearLayout02));
        m.addTab(m.newTabSpec("tab3").setIndicator("Carer").setContent(R.id.LinearLayout03));

        button=(Button)findViewById(R.id.button);
    }

  /*  private void receiveData(){

        Intent intent =getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        double x=0,y=0;
        int radius=0;
        double latestX=intent.getDoubleExtra("x",x);
        double latestY=intent.getDoubleExtra("y",y);
        int latestRadius=intent.getIntExtra("radius", radius);

        Intent intentNew=new Intent(this, Tab1ShowMapActivity.class);
        intentNew.putExtra("x1",latestX);
        intentNew.putExtra("y1",latestY);
        intentNew.putExtra("radius1",latestRadius);


    }*/
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
        intent.putExtra(EXTRA_MESSAGE, message);
        //startActivity(intent);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                 //当otherActivity中返回数据的时候，会响应此方法
                 //requestCode和resultCode必须与请求startActivityForResult()和返回setResult()的时候传入的值一致。
        if(requestCode==1&&resultCode==2)
        {
            int three=data.getIntExtra("three", 0);
            //result.setText(String.valueOf(three));
        }
    }


}
