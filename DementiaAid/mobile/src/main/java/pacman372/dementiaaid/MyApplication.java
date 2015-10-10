package pacman372.dementiaaid;

import android.app.Application;
import android.content.Context;

/**
 * Created by jieliang on 11/10/2015.
 */
public class MyApplication extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}