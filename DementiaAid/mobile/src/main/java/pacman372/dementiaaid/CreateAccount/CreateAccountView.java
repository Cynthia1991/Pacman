package pacman372.dementiaaid.CreateAccount;

/**
 * Created by jieliang on 8/10/2015.
 */
public interface CreateAccountView {


    String getNewPassWord();

    String  getConfirmPassword();

    String getNewUsername();

    void showMismatchError(int resId);
    void showEmptyError(int resid);

    void startLoginActivity();

    void ShowFailedError(int resId);
}
