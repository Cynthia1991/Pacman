package pacman372.dementiaaid.CreateAccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import pacman372.dementiaaid.Login.LoginActivity;
import pacman372.dementiaaid.R;

/**
 * Created by jieliang on 8/10/2015.
 */
public class CreateAccountActivity extends Activity implements CreateAccountView {

    EditText new_username;
    EditText new_password;
    EditText confirm_new_password;
    CreateAccountPr createAccountPr;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_account);
        new_password=(EditText)findViewById(R.id.new_password);
        new_username=(EditText)findViewById(R.id.new_username);
        confirm_new_password=(EditText)findViewById(R.id.newpassword_confirm);
       createAccountPr=  new CreateAccountPr(this,new CreateAccount());
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createAccountPr.OnCreateAccountClicked(v);
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
    public void showMismatchError(int resId) {
         new_password.setError(getString(resId));
    }

    @Override
    public void showEmptyError(int resid) {
        new_username.setError(getString(resid));
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
}
