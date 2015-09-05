package pacman372.dementiaaid;

import android.os.Bundle;

import co.mobiwise.library.GCMListenerService;

/**
 * Created by jieliang on 6/09/2015.
 */
public class CustomGCMService extends GCMListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        //Here is called even app is not running.
        //create your notification here.
    }
}