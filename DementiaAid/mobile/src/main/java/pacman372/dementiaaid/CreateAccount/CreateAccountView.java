package pacman372.dementiaaid.CreateAccount;

import android.view.View;

/**
 * Created by jieliang on 8/10/2015.
 */
public interface CreateAccountView {


    String getNewPassWord();

    String  getConfirmPassword();

    String getNewUsername();
    Boolean tryconnection();
    Boolean connectionError(Boolean th);
    void showMismatchError(int resId);
    void showEmptyError1(int resid);
    void showEmptyError2(int resid);
    void showEmptyError3(int resid);

    void startLoginActivity(View view);

    void ShowFailedError(int resId);
}
