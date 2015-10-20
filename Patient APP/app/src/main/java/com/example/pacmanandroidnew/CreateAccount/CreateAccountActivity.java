package com.example.pacmanandroidnew.CreateAccount;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pacmanandroidnew.CallForHelp.MainActivity;
import com.example.pacmanandroidnew.Login.LoginActivity;
import com.example.pacmanandroidnew.R;
import com.microsoft.windowsazure.mobileservices.*;

import static android.widget.Toast.makeText;

public class CreateAccountActivity extends Activity implements CreateAccountView {
    private MobileServiceClient mClient;
    private EditText textUsername;
    private EditText textPassword;
    private EditText textConfirm;
    private String info;
    private final String createPatient="http://pacmandementiaaid.azurewebsites.net/api/patient";
    private String username;
    private String password;
    private String device_id;

    EditText new_username;
    EditText new_password;
    EditText confirm_new_password;
    CreateAccountPr createAccountPr;
    Button button;
    //private MobileServiceTable<LoginDetail> mToDoTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        new_username=(EditText)findViewById(R.id.editText3);
        new_password=(EditText)findViewById(R.id.editText4);
        confirm_new_password=(EditText)findViewById(R.id.editText5);

        createAccountPr=  new CreateAccountPr(this,new CreateAccount());
        Button btnRegister=(Button)findViewById(R.id.button5);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (connectionError(tryconnection())) ;
                {
                    createAccountPr.OnCreateAccountClicked(v,device_tag());
                }
            }

        });

        }


    @Override
    public String getNewPassWord() {
        return new_password.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return confirm_new_password.getText().toString();
    }

    @Override
    public String getNewUsername() {
        return new_username.getText().toString();
    }

    @Override
    public Boolean tryconnection() {
        ConnectivityManager conmag=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=conmag.getActiveNetworkInfo();
        return info.isConnected();

    }

    @Override
    public Boolean connectionError(Boolean th) {
        if(!th)
        {
            Toast.makeText(this,"badconne",Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            Toast.makeText(this,"good conn",Toast.LENGTH_LONG).show();
            return true;
        }
    }

    @Override
    public void showMismatchError(int resId) {
        Toast.makeText(this,getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyError1(int resid) {
        new_username.setError(getString(resid));

    }
    @Override
    public void showEmptyError2(int resid) {

        new_password.setError(getString(resid));
    }
    @Override
    public void showEmptyError3(int resid) {

        confirm_new_password.setError(getString(resid));
    }
    @Override
    public void startLoginActivity( View view) {

        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void ShowFailedError(int resId) {

        Toast.makeText(this,getString(resId),Toast.LENGTH_LONG).show();

    }

    @Override
    public String device_tag() {
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        return   telephonyManager.getDeviceId();

    }

}
