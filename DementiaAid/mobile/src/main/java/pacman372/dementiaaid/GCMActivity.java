package pacman372.dementiaaid;

import android.app.Activity;
import android.os.Bundle;

import co.mobiwise.library.GCMListener;

/**
 * Created by jieliang on 6/09/2015.
 */
public class GCMActivity extends Activity implements GCMListener {

    @Override
    public void onDeviceRegisted(String token) {}

    @Override
    public void onMessage(String from, Bundle bundle) {}

    @Override
    public void onPlayServiceError() {}

}
