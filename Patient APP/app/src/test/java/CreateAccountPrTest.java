import android.view.View;

import com.example.pacmanandroidnew.CreateAccount.CreateAccount;
import com.example.pacmanandroidnew.CreateAccount.CreateAccountPr;
import com.example.pacmanandroidnew.CreateAccount.CreateAccountView;
import com.example.pacmanandroidnew.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by lichuan on 20/10/2015.
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
    @Before
    public void setUp() throws Exception {
        pr =new CreateAccountPr(createaccountview,createaccount);
    }

    @Test
    public void showConfirmationOfPasswordIsWrong() throws Exception {
        when(createaccountview.getNewUsername()).thenReturn("uio");
        when(createaccountview.getNewPassWord()).thenReturn("aaa");
        when(createaccountview.getConfirmPassword()).thenReturn(("ccc"));
        pr.OnCreateAccountClicked(loginActivity,"190238");
        verify(createaccountview).showMismatchError(R.string.password_mismatch_error);
    }

    @Test
    public void showWhenUsernameIsEmpty() throws Exception {
        when(createaccountview.getNewUsername()).thenReturn("");
        pr.OnCreateAccountClicked(loginActivity,"190238345435");
        verify(createaccountview).showEmptyError1(R.string.empty_error);
    }
    @Test
    public void showWhenPasswordIsEmpty() throws Exception {
        when(createaccountview.getNewPassWord()).thenReturn("");
        pr.OnCreateAccountClicked(loginActivity,"190238345345");
        verify(createaccountview).showEmptyError2(R.string.empty_error);
    }
    @Test
    public void showWhenConfirmIsEmpty() throws Exception {
        when(createaccountview.getConfirmPassword()).thenReturn("");
        pr.OnCreateAccountClicked(loginActivity,"19023843535");
        verify(createaccountview).showEmptyError3(R.string.empty_error);
    }

    @Test
    public void WhenFailtoStoreNewUser() throws Exception {
        when(createaccountview.getNewUsername()).thenReturn("uio");
        when(createaccountview.getNewPassWord()).thenReturn("aaa");
        when(createaccountview.getConfirmPassword()).thenReturn(("aaa"));
        pr.OnCreateAccountClicked(loginActivity,"190238543534");
        //when(createaccount.Create("uio", "aaa")).thenReturn(true);
        verify(createaccountview).ShowFailedError(R.string.create_failed);
    }

    @Test
    public void CreateAccountSuccessFul() throws Exception {
        when(createaccountview.getNewUsername()).thenReturn("uio");
        when(createaccountview.getNewPassWord()).thenReturn("aaa");
        when(createaccountview.getConfirmPassword()).thenReturn(("aaa"));
        pr.OnCreateAccountClicked(loginActivity,"190238435345");
        // when(createaccount.Create("uio", "aaa")).thenReturn(true);
        verify(createaccountview).startLoginActivity(loginActivity);
    }

}
