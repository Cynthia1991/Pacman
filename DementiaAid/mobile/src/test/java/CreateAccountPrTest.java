import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import pacman372.dementiaaid.CreateAccount.CreateAccount;
import pacman372.dementiaaid.CreateAccount.CreateAccountPr;
import pacman372.dementiaaid.CreateAccount.CreateAccountView;
import pacman372.dementiaaid.Login.LoginActivity;
import pacman372.dementiaaid.R;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jieliang on 8/10/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateAccountPrTest {

    @Mock
    private CreateAccountPr pr;
    @Mock
    private CreateAccountView createaccountview;
    @Mock
    private CreateAccount createaccount;
    @Mock
    private View loginActivity;
    @Mock
    private  String device_id;
    @Before
    public void setUp() throws Exception {
        pr =new CreateAccountPr(createaccountview,createaccount);
    }

    @Test
    public void showConfirmationOfPasswordIsWrong() throws Exception {
      when(createaccountview.getNewUsername()).thenReturn("uio");
      when(createaccountview.getNewPassWord()).thenReturn("aaa");
      when(createaccountview.getConfirmPassword()).thenReturn(("ccc"));
        pr.OnCreateAccountClicked(loginActivity,device_id);
      verify(createaccountview).showMismatchError(R.string.password_mismatch_error);
    }

    @Test
        public void showWhenUsernameIsEmpty() throws Exception {
        when(createaccountview.getNewUsername()).thenReturn("");
        pr.OnCreateAccountClicked(loginActivity,device_id);
        verify(createaccountview).showEmptyError1(R.string.empty_error);
    }
    @Test
    public void showWhenPasswordIsEmpty() throws Exception {
        when(createaccountview.getNewPassWord()).thenReturn("");
        pr.OnCreateAccountClicked(loginActivity,device_id);
        verify(createaccountview).showEmptyError2(R.string.empty_error);
    }
    @Test
    public void showWhenConfirmIsEmpty() throws Exception {
        when(createaccountview.getConfirmPassword()).thenReturn("");
        pr.OnCreateAccountClicked(loginActivity,device_id);
        verify(createaccountview).showEmptyError3(R.string.empty_error);
    }

    @Test
    public void WhenFailtoStoreNewUser() throws Exception {
        when(createaccountview.getNewUsername()).thenReturn("uio");
        when(createaccountview.getNewPassWord()).thenReturn("aaa");
        when(createaccountview.getConfirmPassword()).thenReturn(("aaa"));
        pr.OnCreateAccountClicked(loginActivity,device_id);
        //when(createaccount.Create("uio", "aaa")).thenReturn(true);
      verify(createaccountview).ShowFailedError(R.string.create_failed);
    }

    @Test
    public void CreateAccountSuccessFul() throws Exception {
        when(createaccountview.getNewUsername()).thenReturn("uio");
        when(createaccountview.getNewPassWord()).thenReturn("aaa");
        when(createaccountview.getConfirmPassword()).thenReturn(("aaa"));
        pr.OnCreateAccountClicked(loginActivity,device_id);
       // when(createaccount.Create("uio", "aaa")).thenReturn(true);
       verify(createaccountview).startLoginActivity(loginActivity);
    }
}
