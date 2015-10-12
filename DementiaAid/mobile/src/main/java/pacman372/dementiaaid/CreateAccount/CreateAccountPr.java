package pacman372.dementiaaid.CreateAccount;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

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

/**
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
    public void OnCreateAccountClicked(View view)
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
          new CreateAccount().execute(url,new_username, password_confirm);
    }




}
