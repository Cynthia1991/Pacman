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

    @Before
    public void setUp() throws Exception {
        pr =new CreateAccountPr(createaccountview,createaccount);
    }

    @Test
    public void showConfirmationOfPasswordIsWrong() throws Exception {
      when(createaccountview.getNewUsername()).thenReturn("uio");
      when(createaccountview.getNewPassWord()).thenReturn("aaa");
      when(createaccountview.getConfirmPassword()).thenReturn(("ccc"));
        pr.OnCreateAccountClicked();
      verify(createaccountview).showMismatchError(R.string.password_mismatch_error);
    }

    @Test
        public void showWhenUsernameIsEmpty() throws Exception {
        when(createaccountview.getNewUsername()).thenReturn("");
        pr.OnCreateAccountClicked();
        verify(createaccountview).showEmptyError(R.string.empty_error);
    }
    @Test
    public void showWhenPasswordIsEmpty() throws Exception {
        when(createaccountview.getNewPassWord()).thenReturn("");
        pr.OnCreateAccountClicked();
        verify(createaccountview).showEmptyError(R.string.empty_error);
    }
    @Test
    public void showWhenConfirmIsEmpty() throws Exception {
        when(createaccountview.getConfirmPassword()).thenReturn("");
        pr.OnCreateAccountClicked();
        verify(createaccountview).showEmptyError(R.string.empty_error);
    }

    @Test
    public void WhenFailtoStoreNewUser() throws Exception {
        when(createaccountview.getNewUsername()).thenReturn("uio");
        when(createaccountview.getNewPassWord()).thenReturn("aaa");
        when(createaccountview.getConfirmPassword()).thenReturn(("aaa"));
        pr.OnCreateAccountClicked();
        when(createaccount.Create("uio", "aaa")).thenReturn(true);
      verify(createaccountview).ShowFailedError(R.string.create_failed);
    }

    @Test
    public void CreateAccountSuccessFul() throws Exception {
        when(createaccountview.getNewUsername()).thenReturn("uio");
        when(createaccountview.getNewPassWord()).thenReturn("aaa");
        when(createaccountview.getConfirmPassword()).thenReturn(("aaa"));
        pr.OnCreateAccountClicked();
        when(createaccount.Create("uio", "aaa")).thenReturn(true);
       verify(createaccountview).startLoginActivity();
    }

}
