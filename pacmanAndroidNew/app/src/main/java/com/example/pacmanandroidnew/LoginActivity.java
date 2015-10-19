package com.example.pacmanandroidnew;

import android.app.Activity;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;

public class LoginActivity extends Activity {
    private MobileServiceClient mClient;
    private MobileServiceTable<LoginDetail> mToDoTable;
    private TextView t1;
    private String info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText textUsername=(EditText)findViewById(R.id.editText);
         final EditText textPassword=(EditText)findViewById(R.id.editText2);
        //t1=(TextView)findViewById(R.id.textView);
        Button btnLogin=(Button)findViewById(R.id.button3);
        Button btnSignup=(Button)findViewById(R.id.button4);
        //btnLogin.setOnClickListener(this);
        //btnSignup.setOnClickListener(this);
        btnSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }

        });
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final LoginDetail item = new LoginDetail();
                item.username =textUsername.getText().toString() ;
                final String password=textPassword.getText().toString();
                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            MobileServiceList<LoginDetail> results=mToDoTable.where().field("username").eq(item.username).execute().get();
                            StringBuffer sb = new StringBuffer();
                            for(LoginDetail p:results){
                               //t1.setText(p.password);
                                sb.append(p.password);
                            }
                            //t1.setText(sb.toString());
                            if(password.equals(sb.toString().trim())) {

                                info="success";
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("username",item.username);
                                startActivity(intent);

                            }else{
                                info="username or password is wrong";
                            }

                        } catch (Exception exception) {
                            //createAndShowDialog(exception, "Error");
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {

                        Toast.makeText(getApplicationContext(), info,
                                Toast.LENGTH_LONG).show();
                        //do stuff
                        //how to return a variable here?
                    }
                }.execute();



            }

        });


        try {
            mClient = new MobileServiceClient(
                    "https://pacmanandroidnew.azure-mobile.net/",
                    "yvhFQLOdzpcMzjbgJDkWxhHhFEGhNd51",
                    this
            );


            mToDoTable=mClient.getTable("patientLogin",LoginDetail.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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

