package com.example.pacmanandroidnew;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class RegisterActivity extends Activity {
    private MobileServiceClient mClient;
    private EditText textUsername;
    private EditText textPassword;
    private EditText textConfirm;
    private String info;
    private final String createPatient="http://pacmandementiaaid.azurewebsites.net/api/patient";
    //private MobileServiceTable<LoginDetail> mToDoTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textUsername=(EditText)findViewById(R.id.editText3);
        textPassword=(EditText)findViewById(R.id.editText4);
        textConfirm=(EditText)findViewById(R.id.editText5);
        Button btnRegister=(Button)findViewById(R.id.button5);
        //final String deviceId=device_tag();
       // TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
       // final String imei=tm.getDeviceSoftwareVersion().toString().trim();
        btnRegister.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View view) {

                final LoginDetail item = new LoginDetail();
                item.username =textUsername.getText().toString() ;
                final String username=textUsername.getText().toString();
                final String password=textPassword.getText().toString().trim();
                final String passwordConfirm=textConfirm.getText().toString().trim();
                if ((textPassword.getText().toString().trim()).equals(textConfirm.getText().toString().trim())){
                    item.password=textPassword.getText().toString();
                }else{
                    info="password is not the same";
                    Toast.makeText(getApplicationContext(), info,
                           Toast.LENGTH_LONG).show();
                }




                // Insert the new item
                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            final String deviceId=device_tag();
                            RegisterPatient create=new RegisterPatient();
                             info=create.uploadInformation(createPatient, username, password, deviceId);
                           // MobileServiceList<LoginDetail> results=mToDoTable.where().field("username").eq(item.username).execute().get();
                            //StringBuffer sb = new StringBuffer();
                            //for(LoginDetail p:results){
                                //t1.setText(p.password);
                           //     sb.append(p.username);
                           // }

                           // if(sb.toString().trim().equals(username)) {
                             //   info="username exist";

                            //}else {


//                                if ((password.equals(passwordConfirm)) && (username != null) && (password != null && passwordConfirm != null)) {
//                                    mToDoTable.insert(item).get();
//                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                                    startActivity(intent);
//                                    info = "success";
//                                } else {
//                                    info = "register failed";
//                                }
//                            }

                        } catch (Exception exception) {
                          createAndShowDialog(exception, "Error");
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        Toast.makeText(getApplicationContext(), info,
                                Toast.LENGTH_LONG).show();
                        //TextView t1=(TextView) findViewById(R.id.textView);
                        //t1.setText(info);
                        //do stuff
                        //how to return a variable here?
                        if(info.trim().equals("success")) {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }.execute();

               //runAsyncTask(task);
            }




        });


//        try {
//            mClient = new MobileServiceClient(
//                    "https://pacmanandroidnew.azure-mobile.net/",
//                    "yvhFQLOdzpcMzjbgJDkWxhHhFEGhNd51",
//                    this
//            );
//
//
//            mToDoTable=mClient.getTable("patientLogin",LoginDetail.class);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        //LoginDetail item = new LoginDetail();
        //item.username =textUsername.getText().toString() ;
        //if((textPassword.getText().toString().trim()).equals(textConfirm.getText().toString().trim())){
        //    item.password=textPassword.getText().toString();

        }



    public String device_tag() {
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        return   telephonyManager.getDeviceId();

    }

    private void createAndShowDialog(final Exception message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage((CharSequence) message);
        builder.setTitle(title);
        builder.create().show();
    }


}
