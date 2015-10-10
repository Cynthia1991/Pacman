package pacman372.dementiaaid.CreateAccount;

import android.view.View;

/**
 * Created by jieliang on 8/10/2015.
 */
public interface CreateAccountView {


    String getNewPassWord();

    String  getConfirmPassword();

    String getNewUsername();

    void showMismatchError(int resId);
    void showEmptyError(int resid);

    void startLoginActivity(View view);

    void ShowFailedError(int resId);
}
