package pacman372.dementiaaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    public final static String NAME_KEY = "pacman372.dementiaaid.LOGINNAME";
    public final static String PASS_KEY = "pacman372.dementiaaid.LOGINPASS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        Intent intent = new Intent(this, LoginResult.class);
        EditText uName = (EditText) findViewById(R.id.username_edit);
        EditText uPass = (EditText) findViewById(R.id.password_edit);
        intent.putExtra(NAME_KEY, uName.getText().toString());
        intent.putExtra(PASS_KEY, uPass.getText().toString());
        startActivity(intent);
    }
}
