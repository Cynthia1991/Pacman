package pacman372.dementiaaid.Login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.pushbots.push.Pushbots;

import pacman372.dementiaaid.CreateAccount.CreateAccountActivity;
import pacman372.dementiaaid.R;
import pacman372.dementiaaid.TapMainActivity;

public class LoginActivity extends AppCompatActivity {
    public final static String NAME_KEY = "pacman372.dementiaaid.LOGINNAME";
    public final static String PASS_KEY = "pacman372.dementiaaid.LOGINPASS";
    public final String ERROR_KEY = "LoginViewTest: ";
    private carerVM CarerVM = new carerVM();

    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Pushbots.sharedInstance().init(this);
        Log.d(ERROR_KEY, "pushbots ID:"+ Pushbots.sharedInstance().regID());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void tryLogin(View view){
        EditText uName = (EditText) findViewById(R.id.username_edit);
        EditText uPass = (EditText) findViewById(R.id.password_edit);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (CarerVM.login(this, uName.getText().toString(), uPass.getText().toString())) {
                Intent intent = new Intent(this, TapMainActivity.class);
                startActivity(intent);
            } else {
                alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("Login Failed");
                alertDialog.setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
                alertDialog.show();
            }
        } else {
            alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("No Network Connection");
            alertDialog.setNegativeButton("Try Again",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                }
            });
            alertDialog.show();
            Log.d(ERROR_KEY, "No Network Connection");
        }
    }

    public void StartRegister(View view)
    {
        Intent intent= new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
        finish();
    }
}
