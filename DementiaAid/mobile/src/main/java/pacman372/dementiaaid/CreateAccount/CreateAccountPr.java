package pacman372.dementiaaid.CreateAccount;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pushbots.push.Pushbots;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pacman372.dementiaaid.Login.LoginActivity;
import pacman372.dementiaaid.MyApplication;
import pacman372.dementiaaid.R;

/**d
 * Created by jieliang on 8/10/2015.
 */
public class CreateAccountPr
{
  private CreateAccountView createAccountView;
    private CreateAccount createAccount;
    private String url="http://pacmandementiaaid.azurewebsites.net/api/Carer";


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
       new  CreateAccount().execute(url,new_username,new_password,device_id);
        Pushbots.sharedInstance().register();
       Log.d("push_id", Pushbots.sharedInstance().regID());
        //Toast.makeText(view.getContext(),StoreSuccess,Toast.LENGTH_LONG).show();
        //createAccountView.startLoginActivity(view);


    }




}
