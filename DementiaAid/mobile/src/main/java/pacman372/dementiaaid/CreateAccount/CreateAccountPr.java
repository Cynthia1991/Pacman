package pacman372.dementiaaid.CreateAccount;

import android.content.Intent;
import android.view.View;

import pacman372.dementiaaid.Login.LoginActivity;
import pacman372.dementiaaid.R;

/**
 * Created by jieliang on 8/10/2015.
 */
public class CreateAccountPr
{
  private CreateAccountView createAccountView;
    private CreateAccount createAccount;

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
        if(new_username.isEmpty()||password_confirm.isEmpty()||new_password.isEmpty())
        {
            createAccountView.showEmptyError(R.string.empty_error);
            return;
        }

        if(!new_password.equals(password_confirm))
        {
            createAccountView.showEmptyError(R.string.password_mismatch_error);
        }
        boolean StoreSuccess= createAccount.Create(new_username, new_password);
        if(StoreSuccess)
        {

            createAccountView.startLoginActivity(view);
            return;

        }

    }


}
