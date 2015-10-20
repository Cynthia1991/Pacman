package com.example.pacmanandroidnew.CreateAccount;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.pacmanandroidnew.R;

/**
 * Created by lichuan on 20/10/2015.
 */

    public class CreateAccountPr
    {
        private CreateAccountView createAccountView;
        private CreateAccount createAccount;
        private String url="http://pacmandementiaaid.azurewebsites.net/api/patient";
        public Context context;

        public CreateAccountPr(CreateAccountView createAccountView,CreateAccount createAccount)
        {
            this.createAccountView=createAccountView;
            this.createAccount=createAccount;
        }
        public void OnCreateAccountClicked(View view,String device_id)
        {

            String new_username=createAccountView.getNewUsername();
            String new_password=createAccountView.getNewPassWord();
            String password_confirm=createAccountView.getConfirmPassword();
            if(new_username.isEmpty())
            {
                createAccountView.showEmptyError1(R.string.empty_error);
                return;
            }
            if(new_password.isEmpty())
            {
                createAccountView.showEmptyError2(R.string.empty_error);
                return;
            }
            if(password_confirm.isEmpty())
            {
                createAccountView.showEmptyError3(R.string.empty_error);
                return;
            }
            if(!new_password.equals(password_confirm))
            {
                createAccountView.showMismatchError(R.string.password_mismatch_error);
                return;
            }
            //String StoreSuccess= createAccount.makeRequest(url,new_username,password_confirm);


            new  CreateAccount().execute(url, new_username, new_password, device_id);
            // Pushbots.sharedInstance().register();

            //Log.d("push_id", Pushbots.sharedInstance().regID());

            //new  CreateAccount().execute(url,new_username,new_password,device_id);
           // /Pushbots.sharedInstance().register();
            //Log.d("push_id", Pushbots.sharedInstance().regID());
            //Toast.makeText(view.getContext(),StoreSuccess,Toast.LENGTH_LONG).show();
            createAccountView.startLoginActivity(view);


        }



    }

