package pacman372.dementiaaid.CreateAccount;

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
    public void OnCreateAccountClicked()
    {
       String new_username=createAccountView.getNewUsername();
        String new_password=createAccountView.getNewPassWord();
        String password_confirm=createAccountView.getConfirmPassword();
        if(new_username.isEmpty()||password_confirm.isEmpty()||new_password.isEmpty())
        {
            createAccountView.showEmptyError(R.string.empty_error);
            return;
        }

        if(new_password!=password_confirm)
        {
            createAccountView.showEmptyError(R.string.password_mismatch_error);
        }
        boolean StoreSuccess= createAccount.StoreDetail(new_username,new_password);
        if(StoreSuccess)
        {
            createAccountView.startLoginActivity();
            return;

        }

    }


}
