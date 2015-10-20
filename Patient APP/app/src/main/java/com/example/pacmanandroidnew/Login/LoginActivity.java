package com.example.pacmanandroidnew.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pacmanandroidnew.CallForHelp.MainActivity;
import com.example.pacmanandroidnew.R;
import com.example.pacmanandroidnew.CreateAccount.CreateAccountActivity;

public class LoginActivity extends Activity {

    public final String ERROR_KEY = "LoginViewTest:";

    private PatientVM patientVM = new PatientVM();
    private AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnSignup=(Button)findViewById(R.id.button4);
        btnSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }

        });

    }

    public void tryLogin(View view){
        EditText uName = (EditText) findViewById(R.id.editText);
        EditText uPass = (EditText) findViewById(R.id.editText2);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (patientVM.login(this, uName.getText().toString(), uPass.getText().toString())) {
                Intent intent = new Intent(this, MainActivity.class);
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
            alertDialog.setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                }
            });
            alertDialog.show();
            Log.d(ERROR_KEY, "No Network Connection");
        }
    }

}

